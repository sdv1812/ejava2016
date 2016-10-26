/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca1.rest;

import ejava.ca1.business.AppointmentBean;
import ejava.ca1.model.Appointment;
import java.util.List;
import javax.ejb.EJB;
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
import javax.ws.rs.core.Response;

@Path("/appointment/{email}")
@RequestScoped
public class AppointmentResource {
    @EJB private AppointmentBean appointmentBean;
    List<Appointment> appointments;
    @GET
    @Produces("application/json")
    public Response getAppointments(@PathParam("email") String email, @Suspended AsyncResponse async){
        
     appointments = appointmentBean.getAppointmentDetails(email);
        JsonArrayBuilder builder = Json.createArrayBuilder();
    JsonObjectBuilder arrBuilder = Json.createObjectBuilder();
    for(Appointment a :appointments ){
        arrBuilder.add("appointmentId", a.getApptId())
                    .add("description",a.getDescription())
                    .add("personId",a.getPid().getPid())
                    .add("dateTime",a.getApptDate().toString());
        builder.add(arrBuilder);
    }
    return Response.ok(builder.build()).build();
}
}
