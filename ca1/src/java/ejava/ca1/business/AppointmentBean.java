/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca1.business;

import ejava.ca1.model.Appointment;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Sanskar
 */
@Stateless
public class AppointmentBean {
        @PersistenceContext private EntityManager em;
        
        public List<Appointment> getAppointmentDetails(String emailID){
            TypedQuery<Appointment> query = em.createQuery("select a.appointmentList from People a where a.email = :email",Appointment.class);
                query.setParameter("email", emailID);               
		List<Appointment> people = query.getResultList();
                return people; 
        }
    
}
