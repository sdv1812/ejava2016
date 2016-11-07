/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca3.view;

import ejava.ca3.business.PodBean;
import java.io.IOException;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/callback"})
public class PodServlet extends HttpServlet{
    @EJB PodBean podBean;
    @Resource (mappedName ="concurrent/Deadpool2")
    private ManagedExecutorService executors; 
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
   String ackId = req.getParameter("ackId");
   String podId = req.getParameter("podId");
   if(ackId != null){
            System.out.println("getting parameters from request" +req.getParameter("ackId"));
            System.out.println("Getting pod ID >>> " +podId);
            podBean.writeAck(ackId,podId);  
   }
         
}
}
