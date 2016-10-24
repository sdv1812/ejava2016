/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca1.business;

import ejava.ca1.model.Appointment;
import ejava.ca1.model.People;
import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Sanskar
 */
@Stateless

public class PeopleBean {
    @PersistenceContext private EntityManager em;
    
    public void createPeople(String name, String email){
       String pid = UUID.randomUUID().toString().substring(0, 8);
        
//       TypedQuery<People> query = em.createNamedQuery(
//				"People.create", People.class);
        People people = new People();
        people.setEmail(email);
        people.setName(name);
        people.setPid(pid);
               
        em.persist(people);
    }
        
        public List<Appointment> getAppointmentDetails(String emailID){
            System.out.println("people inside appbean" +emailID);
            TypedQuery<Appointment> query = em.createQuery("select a.appointmentList from People a where  a.email = :email",Appointment.class);
                query.setParameter("email", emailID);               
		List<Appointment> people = query.getResultList();
                System.out.println("people listksdgfadhgkhsdaf" +people.toString());
                return people; 
        }
    
}
