/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca2.views;

import ejava.ca2.model.Notes;
import java.util.ArrayList;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Sanskar
 */
@RequestScoped
@Named
public class NoteView {
    private String title;
    private String category;
    private String content;
    private ArrayList<Notes> notes; 

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

    public ArrayList<Notes> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Notes> notes) {
        this.notes = notes;
    }
    
    

}