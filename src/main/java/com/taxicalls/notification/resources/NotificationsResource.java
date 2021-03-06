package com.taxicalls.notification.resources;

import com.taxicalls.notification.model.Notification;
import com.taxicalls.protocol.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class NotificationsResource {

    private final EntityManager em;

    public NotificationsResource() {
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
    public Response createNotification(Notification notification) {
        em.getTransaction().begin();
        em.persist(notification);
        em.getTransaction().commit();
        return Response.successful(notification);
    }

    @GET
    public Response getNotifications() {
        List<Notification> notifications = em.createNamedQuery("Notification.findAll", Notification.class).getResultList();
        return Response.successful(notifications);
    }

    @GET
    @Path("/{id}")
    public Response getNotification(@PathParam("id") Long id) {
        Notification notification = em.find(Notification.class, id);
        if (notification == null) {
            return Response.notFound();
        }
        return Response.successful(notification);
    }

}
