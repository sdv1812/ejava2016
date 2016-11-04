/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca2.web;

import ejava.ca2.model.Notes;
import ejava.ca2.model.NotesPK;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


@Stateless
public class NoteBean {
     @PersistenceContext private EntityManager em;
   
    
    public void createNote(String title, String category, String content){
        Notes notes= new Notes();
        NotesPK notesPK = new NotesPK();
        notes.setCategory(category);
        notes.setTitle(title);
        notesPK.setUserid(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().toString());
        notesPK.setDateTime(new Date());
        notes.setContent(content);
        notes.setNotesPK(notesPK);
        em.persist(notes);
        
    }
    
     public List<Notes> showPostedNotes(){
         String user = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().toString();
         TypedQuery<Notes> query = em.createNamedQuery(
				"Notes.findByUserid", Notes.class);
		query.setParameter("userid", user);
                
                return (query.getResultList());
     }
     
     public String toJSON(String category){
          TypedQuery<Notes> note = null;
          if(category.equals("All")){
        note = em.createNamedQuery("Notes.findAll",Notes.class); 
        }
        else{
        note = em.createNamedQuery("Notes.findByCategory", Notes.class);
        note.setParameter("category", category.trim());
        }

        JsonArrayBuilder array = Json.createArrayBuilder();
        note.getResultList().forEach(n -> {
            array.add(Json.createObjectBuilder()
                    .add("content", n.getContent())
                    .add("title", n.getTitle())
                    .add("category", n.getCategory())
                    .add("timestamp", n.getNotesPK().getDateTime().toString())
                    .add("user", n.getNotesPK().getUserid())
                    .build()
            );
        });
          return array.build().toString();
     }
}
