/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca1.rest;

import ejava.ca1.business.AppointmentBean;
import ejava.ca1.model.Appointment;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;

public class AppointmentTask implements Runnable{
    private String email;
    private AsyncResponse async;
    private AppointmentBean bean;
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setAsync(AsyncResponse async){
        this.async = async;
    }
    
    public void setAppointmentBean(AppointmentBean bean){
        this.bean = bean;
    }
    
    @Override
    public void run() {
        List<Appointment>appointments = bean.getAppointmentDetails(email);
        JsonArrayBuilder builder = Json.createArrayBuilder();
        JsonObjectBuilder arrBuilder = Json.createObjectBuilder();
    for(Appointment a :appointments ){
        arrBuilder.add("appointmentId", a.getApptId())
                    .add("description",a.getDescription())
                    .add("personId",a.getPid().getPid())
                    .add("dateTime",a.getApptDate().toString());
        builder.add(arrBuilder);
    }
        Response res = Response.ok(builder.build()).build();
        (async).resume(res);
}
}
