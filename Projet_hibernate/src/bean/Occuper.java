/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

/**
 *
 * @author Angela
 */
import java.io.Serializable;
import java.util.Date;

public class Occuper implements Serializable {

    private int id;
    private Date date;
    private Prof codeprof;
    private Salle codesal;
    private String heure;

    public Occuper() {
    }

    public Occuper(Date date, Prof codeprof, Salle codesal, String heure) {
        this.date = date;
        this.codeprof = codeprof;
        this.codesal = codesal;
        this.heure = heure;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Prof getCodeprof() {
        return codeprof;
    }

    public void setCodeprof(Prof codeprof) {
        this.codeprof = codeprof;
    }

    public Salle getCodesal() {
        return codesal;
    }

    public void setCodesal(Salle codesal) {
        this.codesal = codesal;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }
    
}
