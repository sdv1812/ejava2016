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
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

@Path("/appointment/{email}")
@RequestScoped
public class AppointmentResource {

    @Resource(mappedName = "concurrent/Deadpool")
    private ManagedScheduledExecutorService executors;

    List<Appointment> appointments;

    @GET
    @Produces("application/json")
    public void getAppointments(@PathParam("email") String email, @Suspended AsyncResponse async) {
        AppointmentBean appointmentBean = new AppointmentBean();
        appointmentBean.setSearchCriteria(email);
        appointmentBean.setAsyncResponse(async);

//     appointments = appointmentBean.getAppointmentDetails(email);
        executors.execute(appointmentBean);

    }
}
