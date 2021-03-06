/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca1.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sanskar
 */
@Entity
@Table(name = "appointment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Appointment.findAll", query = "SELECT a FROM Appointment a"),
    @NamedQuery(name = "Appointment.findByApptId", query = "SELECT a FROM Appointment a WHERE a.apptId = :apptId"),
    @NamedQuery(name = "Appointment.findByApptDate", query = "SELECT a FROM Appointment a WHERE a.apptDate = :apptDate"),
    @NamedQuery(name="Appointment.findByEmail", query="select a.appointmentList from People a where  a.email like :email")})

public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "appt_id")
    private Integer apptId;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 16777215)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "appt_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date apptDate;
    @JoinColumn(name = "pid", referencedColumnName = "pid")
    @ManyToOne(optional = false)
    private People pid;

    public Appointment() {
    }

    public Appointment(Integer apptId) {
        this.apptId = apptId;
    }

    public Appointment(Integer apptId, String description, Date apptDate) {
        this.apptId = apptId;
        this.description = description;
        this.apptDate = apptDate;
    }

    public Integer getApptId() {
        return apptId;
    }

    public void setApptId(Integer apptId) {
        this.apptId = apptId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getApptDate() {
        return apptDate;
    }

    public void setApptDate(Date apptDate) {
        this.apptDate = apptDate;
    }

    public People getPid() {
        return pid;
    }

    public void setPid(People pid) {
        this.pid = pid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (apptId != null ? apptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Appointment)) {
            return false;
        }
        Appointment other = (Appointment) object;
        if ((this.apptId == null && other.apptId != null) || (this.apptId != null && !this.apptId.equals(other.apptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejava.ca1.model.Appointment[ apptId=" + apptId + " ]";
    }
    
}
