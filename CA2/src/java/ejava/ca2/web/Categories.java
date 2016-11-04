/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca2.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.websocket.Session;

@ApplicationScoped
public class Categories {
    private final Lock lock = new ReentrantLock();
    @EJB private NoteBean noteBean ;
    @PersistenceContext
    private EntityManager em;
    final private HashMap<String, List<Session>> categorySessions = new HashMap<>();

    public void addSession(Session session, String Category) {
        System.out.println("Adding Sessionsssssss");
        List<Session> sessions = categorySessions.computeIfAbsent(Category, s -> new LinkedList<>());
        System.out.println("category present" +Category);
        sessions.add(session);
        System.out.println(sessions.size()) ;
    }

    public void broadcast(String category) {
        
        final String message = noteBean.toJSON(category);             
        if(categorySessions.get(category) != null){
        categorySessions.get(category).stream().forEach(s -> {
            try{
                 s.getBasicRemote().sendText(message);
            }catch (IOException ex) {
                try {
                    s.close();
                } catch (IOException e) {
                }
            }
            
        });
        }
        
    }
    
    public void lock(Runnable block){
        lock.lock();
        try{
            block.run();
        }finally {
            lock.unlock();
        
        }
    }
   
    public void remove(String category, Session session){
     categorySessions.get(category).remove(categorySessions.get(category).indexOf(session));  
    }

}
