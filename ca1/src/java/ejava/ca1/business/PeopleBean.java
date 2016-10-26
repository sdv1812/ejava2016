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


@Stateless

public class PeopleBean {
    @PersistenceContext private EntityManager em;
    
    public void createPeople(String name, String email){
       String pid = UUID.randomUUID().toString().substring(0, 8);
        
        People people = new People();
        people.setEmail(email);
        people.setName(name);
        people.setPid(pid);
               
        em.persist(people);
    }
        
        public List<People> getAppointmentDetails(String email){
            TypedQuery<People> query = em.createQuery("select a from People a where  a.email = :email",People.class);
                query.setParameter("email", email);               
		List<People> people = query.getResultList();
                return people; 
        }
    
}
