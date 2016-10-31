/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca2.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Srishti Miglani
 */
@Entity(name= "users")
public class User implements Serializable{
     private static final long serialVersionUID = 1L;
    @Id 
    @Column(name ="userid")
    private String username;
    @Column 
    private String password; 
    
    public User(){
        
    }
    
     public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
