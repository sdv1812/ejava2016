/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca2.views;

import ejava.ca2.web.*;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Named
@RequestScoped
public class LoginView{

    @EJB private UserBean userbean;
    
    private String username;
    private String password;
    
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
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        try {
            req.login(username, password);
        } catch (ServletException e) {
            fc.addMessage(username, new FacesMessage("Incorrect Login"));
            return (null);
        }
        return ("/secure/menu?faces-redirect=true");
    }

    public String register() {
            FacesContext fc = FacesContext.getCurrentInstance();
            int message = userbean.register(username, password);
            if(message == -1){
                  fc.addMessage(username, new FacesMessage("User Already Exists"));
                  return null;
            }else{
                  fc.addMessage(username, new FacesMessage("Successfully Registered"));
                  return ("login?send-redirect=true");
            }
    }
    
    public String logout() throws ServletException{
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        req.getSession().invalidate();
        req.logout();
        return ("/login?send-redirect=true");
        
    }

}
