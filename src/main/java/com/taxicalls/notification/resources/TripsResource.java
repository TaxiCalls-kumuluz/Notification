package com.taxicalls.notification.resources;

import com.taxicalls.notification.model.Driver;
import com.taxicalls.notification.model.Notification;
import com.taxicalls.notification.model.Passenger;
import com.taxicalls.notification.model.Trip;
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

@Path("/trips")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class TripsResource {

    private static final Logger LOGGER = Logger.getLogger(TripsResource.class.getName());

    private final EntityManager em;

    public TripsResource() {
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
    public Response createTrip(Trip trip) {
        LOGGER.log(Level.INFO, "createTrip() invoked");
        Notification notification = new Notification();
        notification.setFromEntity(Driver.class.getSimpleName());
        notification.setFromId(trip.getDriver().getId());
        notification.setToEntity(Passenger.class.getSimpleName());
        em.getTransaction().begin();
        notification.setToId(trip.getAuthor().getId());
        em.persist(notification);
        for (Passenger passenger : trip.getPassengers()) {
            notification.setToId(passenger.getId());
            em.persist(notification);
        }
        em.getTransaction().commit();
        return Response.successful(notification);
    }
}
