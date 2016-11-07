/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/

package ejava.ca3.view;

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

@WebListener
public class Uploadtask implements ServletContextListener{
    @Resource(lookup = "concurrent/Deadpool")
    private ManagedScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("ready");
        scheduler.scheduleAtFixedRate(new Runnable() {
                        @Override
                                           public void run() {
                            Client client = ClientBuilder.newBuilder().build();
                            WebTarget target = client.target("http://10.10.2.80:8080/ca3/callback");
                            Invocation.Builder inv = target.request();
                            try{
                            inv.get();
                            }catch(Throwable t ){
                                System.out.println("Not found");
                            }
                        }
        }, 30, 20, TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
        System.out.println("removed");
    }
}

