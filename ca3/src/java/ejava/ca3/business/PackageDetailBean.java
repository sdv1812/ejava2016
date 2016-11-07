/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca3.business;

import ejava.ca3.model.Delivery;
import ejava.ca3.model.Pod;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PackageDetailBean {
    @PersistenceContext EntityManager em; 
    
  public void submitDetails(String name, String address, String phone){
     Delivery delivery = new Delivery ();
     delivery.setName(name);
     delivery.setAddress(address);
     delivery.setPhone(phone);
     delivery.setCreateDate(new Date());
     em.persist(delivery);
      System.out.println("Getting package id" +delivery.getPkgId());
     Pod pod = new Pod();
     int id =  em.createQuery("select max(u.pkgId) from Delivery u", Integer.class).getSingleResult();
      System.out.println("Id from fgjfggf" +id);
     pod.setPkgId(em.find(Delivery.class, id));
     em.persist(pod);
     
  }  
  
  public List<Delivery> getAllDelivery(){
      return em.createNamedQuery("Delivery.findAll",Delivery.class).getResultList();
  }
}
