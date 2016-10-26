/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca1.rest;

import ejava.ca1.business.AppointmentBean;
import ejava.ca1.model.Appointment;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

@Path("/appointment/{email}")
@RequestScoped
public class AppointmentResource {
    @EJB private AppointmentBean appointmentBean;
    List<Appointment> appointments;

    @Resource(mappedName = "concurrent/Deadpool")
    private ManagedScheduledExecutorService executors;
    @GET
    @Produces("application/json")
    public void getAppointments(@PathParam("email") String email, @Suspended AsyncResponse async){
        
       AppointmentTask task = new AppointmentTask();
       task.setEmail(email);
       task.setAsync(async);
       task.setAppointmentBean(appointmentBean);
       executors.execute(task);
       
           }
    
}

