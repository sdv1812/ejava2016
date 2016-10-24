/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca1.rest;

import ejava.ca1.business.PeopleBean;
import ejava.ca1.model.Appointment;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 *
 * @author Sanskar
 */
@RequestScoped

@Path("/people")  
public class PeopleResource {
      @EJB private PeopleBean peopleBean;
      @POST
      @Produces("application/json")
      @Consumes("application/x-www-form-urlencoded")
      public Response post(MultivaluedMap<String, String> peopleTable){
          String email = peopleTable.getFirst("email");
          String name = peopleTable.getFirst("name");
          if(email == null || name == null){
              return Response.status(Response.Status.BAD_REQUEST).build();
          }
          peopleBean.createPeople(name,email);
          
          return Response.ok().build();
      }
        List<Appointment> appointments;
    @GET
    @Produces("application/json")
    public Response verifyAppointment(@QueryParam("email") String email){
        
     //   System.out.println("inside Verfied appppp" +email);
     appointments = peopleBean.getAppointmentDetails(email);
    JsonObjectBuilder arrBuilder = Json.createObjectBuilder();
    for(Appointment a :appointments ){
        arrBuilder.add("AppId", a.getApptId())
                    .add("Description",a.getDescription())
                    .add("pid",a.getPid().getPid())
                    .add("appDate",a.getApptDate().toString());
    }
    return Response.ok(arrBuilder.build()).build();        
 
}
}
