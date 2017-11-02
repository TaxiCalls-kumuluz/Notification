package com.taxicalls.notification.resources;

import com.taxicalls.notification.model.Notification;
import com.taxicalls.protocol.Response;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/checks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ChecksResource {

    private final EntityManager em;

    public ChecksResource() {
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
    public Response checkNotifications(CheckNotificationsRequest checkNotificationsRequest) {
        Long id = checkNotificationsRequest.getId();
        String entity = checkNotificationsRequest.getEntity();
        Collection<Notification> notifications = em.createNamedQuery("Notification.findAll", Notification.class).getResultList();
        for (Notification notification : notifications) {
            if (notification.getToEntity().equals(entity) && notification.getToId().equals(id)) {
                notifications.add(notification);
            }
        }
        return Response.successful(notifications);
    }

}
