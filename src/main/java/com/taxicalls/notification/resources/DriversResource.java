package com.taxicalls.notification.resources;

import com.taxicalls.notification.model.Driver;
import com.taxicalls.notification.model.Notification;
import com.taxicalls.notification.model.Passenger;
import com.taxicalls.protocol.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/drivers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class DriversResource {

    private static final Logger LOGGER = Logger.getLogger(DriversResource.class.getName());

    private final EntityManager em;

    public DriversResource() {
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<>();
        env.keySet().forEach((envName) -> {
            if (envName.contains("DATABASE_USER")) {
                configOverrides.put("javax.persistence.jdbc.user", env.get(envName));
            } else if (envName.contains("DATABASE_PASS")) {
                configOverrides.put("javax.persistence.jdbc.password", env.get(envName));
            }
        });
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("notification", configOverrides);
        this.em = emf.createEntityManager();
    }

    @POST
    public Response chooseDriver(ChooseDriverRequest chooseDriverRequest) {
        LOGGER.log(Level.INFO, "chooseDriver() invoked");
        Notification notification = new Notification();
        notification.setFromEntity(Passenger.class.getSimpleName());
        notification.setFromId(chooseDriverRequest.getPassenger().getId());
        notification.setToEntity(Driver.class.getSimpleName());
        notification.setToId(chooseDriverRequest.getDriver().getId());
        em.getTransaction().begin();
        em.persist(notification);
        em.getTransaction().commit();
        return Response.successful(notification);
    }
}
