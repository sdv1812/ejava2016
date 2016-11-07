/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca3.business;

import ejava.ca3.model.Pod;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.client.WebTarget;

@Stateless
public class PodBean {
    @PersistenceContext EntityManager em; 
    
     public List<Pod> getAllPods(){
      return em.createNamedQuery("Pod.findAll",Pod.class).getResultList();
  } 
     public void submitPodDetails(String podId, String note, byte[] image, String time) throws ParseException{
         
        Long id = Long.parseLong(podId); 
        Date newDate = new Date(Long.parseLong(time));   
        Query query =  em.createQuery("Update Pod p set p.note =:note, p.image = :image, p.deliveryDate = :deliveryDate where p.podId = :podId"); 
        query.setParameter("note", note);
        query.setParameter("image", image);
        query.setParameter("deliveryDate", newDate);
        query.setParameter("podId", id);
        query.executeUpdate();
     }
     
     public void writeAck(String ackId, String podId){
        Query query =  em.createQuery("Update Pod p set p.ackId =:ackId where p.podId = :podId");  
        query.setParameter("ackId", ackId);
        query.setParameter("podId", Integer.parseInt(podId));
        query.executeUpdate();
     }
     
}
