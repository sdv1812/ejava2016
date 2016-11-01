/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca2.web;


import ejava.ca2.model.Users;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Srishti Miglani
 */
@Stateless
public class UserBean {

    @PersistenceContext private EntityManager em;

    public void register(String username, String password){
        
            Users user = new Users();
            user.setUserid(username);
            String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);       
            user.setPassword(sha256hex);
            em.persist(user);
        
    }

}
