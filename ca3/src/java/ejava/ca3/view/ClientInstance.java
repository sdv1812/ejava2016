/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca3.view;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

@ApplicationScoped
public class ClientInstance {
    private Client client;
    
    @PostConstruct
    private void init(){
        client = ClientBuilder.newClient();
    }
    
    @PreDestroy
    private void destroy(){
        client.close();
           }
    
   public WebTarget target(String url){
       return client.target(url);
   }
}
