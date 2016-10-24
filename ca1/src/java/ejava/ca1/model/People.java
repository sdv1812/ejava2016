/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca1.model;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sanskar
 */

@Entity
@Table(name = "appointment")
public class People implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CUSTOMER_ID")    
    private int appId;
        
    private String desciption;
    private Timestamp apptDate;
    private String peopleId;

    public int getAppId() {
        return appId;
    }
    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getDesciption() {
        return desciption;
    }
    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public Timestamp getApptDate() {
        return apptDate;
    }
    public void setApptDate(Timestamp apptDate) {
        this.apptDate = apptDate;
    }

    public String getPeopleId() {
        return peopleId;
    }
    public void setPeopleId(String peopleId) {
        this.peopleId = peopleId;
    }
         
}
