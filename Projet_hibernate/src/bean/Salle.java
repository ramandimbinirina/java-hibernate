/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

/**
 *
 * @author Angela
 */
public class Salle {

    private String codesal;

    public Salle() {
    }

    public void setCodesal(String codesal) {
        this.codesal = codesal;
    }

    public String getCodesal() {
        return codesal;
    }

    public Salle(String codesal, String designation) {

        this.codesal = codesal;
        this.designation = designation;
    }
    private String designation;

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
