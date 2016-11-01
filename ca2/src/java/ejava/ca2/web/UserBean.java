/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca2.web;

import ejava.ca2.model.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public void register(String username, String password) throws NoSuchAlgorithmException {
        User user = new User();
        user.setUsername(username);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        password = byteArrayToHex(md.digest());
        user.setPassword(password);
        em.persist(user);
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

}
