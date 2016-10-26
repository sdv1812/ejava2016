/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca1.business;

import ejava.ca1.model.Appointment;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.AsyncContext;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;

/**
 *
 * @author Sanskar
 */
@Stateless
public class AppointmentBean implements Runnable {

    @PersistenceContext
    private EntityManager em;
    private AsyncResponse async;
    private String emailId;

//    public List<Appointment> getAppointmentDetails(String emailID) {
//        TypedQuery<Appointment> query = em.createQuery("select a.appointmentList from People a where a.email = :email", Appointment.class);
//        query.setParameter("email", emailID);
//        List<Appointment> people = query.getResultList();
//        return people;
//    }

    public void setAsyncResponse(AsyncResponse async) {
     
        this.async =  async;
            System.out.println("wefasdyes it is instance of AsyncContext");
    }

    @Override
    public void run() {
        System.out.println("I am in run function ");
        System.out.println("Am i Null??? >> "+em);
        TypedQuery<Appointment> query = em.createQuery("select a.appointmentList from People a where a.email = :email", Appointment.class);
        List<Appointment> appointments = query.getResultList();
        System.out.println("I am printing the resuls" +appointments);
        JsonArrayBuilder builder = Json.createArrayBuilder();
        JsonObjectBuilder arrBuilder = Json.createObjectBuilder();
        for (Appointment a : appointments) {
            arrBuilder.add("appointmentId", a.getApptId())
                    .add("description", a.getDescription())
                    .add("personId", a.getPid().getPid())
                    .add("dateTime", a.getApptDate().toString());
            builder.add(arrBuilder);
        }
        Response res = Response.ok(builder.build()).build();
        (async).resume(res);
    }

    public void setSearchCriteria(String emailId) {
        this.emailId = emailId;
    }

}
