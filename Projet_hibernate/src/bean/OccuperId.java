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

public class OccuperId implements Serializable {
    private Prof codeprof;
    private Salle codesal;

    public OccuperId(){}
    
     
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        OccuperId occuperId = (OccuperId) o;
//
//        if (codeprof != occuperId.codeprof) return false;
//        return codesal == occuperId.codesal;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = codeprof;
//        result = 31 * result + codesal;
//        return result;
//    }

    public OccuperId(Prof codeprof, Salle codesal) {
        this.codeprof = codeprof;
        this.codesal = codesal;
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
    
}

