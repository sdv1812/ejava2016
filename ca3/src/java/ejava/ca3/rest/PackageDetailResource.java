/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca3.rest;

import ejava.ca3.business.PackageDetailBean;
import ejava.ca3.business.PodBean;
import ejava.ca3.model.Delivery;
import ejava.ca3.model.Pod;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/items")
public class PackageDetailResource {
    @EJB PackageDetailBean packageDetailBean; 
    @EJB PodBean podBean; 
    
        @GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOne() {
                 
            JsonArrayBuilder message=Json.createArrayBuilder();
//            List<Delivery> delivery = packageDetailBean.getAllDelivery();
            List<Pod> pod = podBean.getAllPods();
            JsonObjectBuilder note = Json.createObjectBuilder();
            for(Pod p: pod) 
                {
                    System.out.println("ack Id for Pod" +p.getAckId());
                    if(p.getAckId()==null){
                        note.add("teamId","ed29b478");
                        note.add("podId", p.getPodId());
                        note.add("address", p.getPkgId().getAddress());
                        note.add("name",p.getPkgId().getName());
                        note.add("phone",p.getPkgId().getName());
                        message.add(note);
                }
                }
            
            return Response.ok(message  .build()).build();
	}
}
