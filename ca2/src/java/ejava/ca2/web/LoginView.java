/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca2.web;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import javax.faces.context.FacesContext;
//import javax.inject.Named;
////import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name = "loginView")
@RequestScoped
public class LoginView implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    @EJB
    private UserBean userbean;

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

    public String login() {
        System.out.println("jsdfashdjkhsfdj");
        System.out.println("faces context");
        FacesContext fc = FacesContext.getCurrentInstance();
        System.out.println("faces context" + fc);
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        try {
            System.out.println("before authentication" + username + "." + password);
            req.login(username, password);
            System.out.println("inside login ");
        } catch (ServletException e) {
            fc.addMessage(username, new FacesMessage("Incorrect Login"));
            return (null);
        }
        return ("Menu");
    }

    public void register() {
        try {
            userbean.register(username, password);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
