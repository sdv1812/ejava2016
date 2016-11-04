/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca2.views;

import ejava.ca2.web.Categories;
import ejava.ca2.web.NoteBean;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@RequestScoped
@ServerEndpoint("/displayNotes")
public class NoteDisplay {

    @Inject
    private Categories categories;
    private String category;
    @EJB private NoteBean noteBean;

    @Resource(lookup = "concurrent/Deadpool2")
    private ManagedExecutorService executor;

    @OnOpen
    public void open(Session session) {
        System.out.println(">>> new session: " + session.getId());
    }

    @OnMessage
    public void message(final Session session, final String msg) {
        System.out.println(">>> message: " + msg);
        this.category = msg;
        
        categories.lock(() -> {
            categories.addSession(session, category);
        });

                 String allNotes=noteBean.toJSON(category);
                 System.out.println("What is in all notes" +allNotes);
                    try{
                session.getBasicRemote().sendText(allNotes);
                    }catch (IOException ex) {
                try {
                    session.close();
                } catch (IOException e) {
                        }
                    }

        System.out.println(">>> exiting message");
    }

    @OnClose
    public void close(Session session) {
        System.out.println("closing connection");
        categories.lock(() -> {
            categories.remove(category, session);
        });
    }

}
