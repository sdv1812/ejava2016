/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca2.views;

import ejava.ca2.model.Notes;
import ejava.ca2.web.Categories;
import ejava.ca2.web.NoteBean;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;


@RequestScoped
@Named
public class NoteView {
    private String title;
    private String category;
    private String content;
    private List<Notes> notes;

    public List<Notes> getNotes() {
        return notes;
    }

    public void setNotes(List<Notes> notes) {
        this.notes = notes;
    }
    
    @EJB private NoteBean noteBean;
    @Inject private Categories categories; 

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public void createNote(){
        noteBean.createNote(title,category,content);
        FacesMessage message = new FacesMessage("Note is Created!!!");
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("noteForm", message);
        categories.broadcast(category);
    }
    
    public String showPostedNotes(){
       notes = noteBean.showPostedNotes();
       return ("postedNotes?send-redirect=true");
    }

}