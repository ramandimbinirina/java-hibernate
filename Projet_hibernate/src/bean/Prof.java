/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

import javax.persistence.Column;

/**
 *
 * @author Angela
 */
public class Prof {

    public static void add(Prof professeur) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private String codeprof;
    private String nom;
    private String prenom;
    private String grade;

    public Prof() {
    }

    ;

    public Prof(String codeprof, String nom, String prenom, String grade) {
        this.codeprof = codeprof;
        this.nom = nom;
        this.prenom = prenom;
        this.grade = grade;
    }

    public String getCodeprof() {
        return codeprof;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getGrade() {
        return grade;
    }

    public void setCodeprof(String codeprof) {
        this.codeprof = codeprof;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

}
