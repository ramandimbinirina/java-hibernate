/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

/**
 *
 * @author Angela
 */
import bean.Occuper;
import bean.OccuperId;
import bean.Prof;
import bean.Salle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import util.hibernateUtil;

public class OccuperManager {

    public List<Occuper> getAllOccuper() {
        List<Occuper> occupe = new ArrayList<>();
        Session session = null;

        try {
            session = hibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            Query query = session.createQuery("FROM Occuper");
            occupe = query.list();

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return occupe;
    }

    public boolean checkOccupation(String codeProf, String codeSal, Date date, String heure) {
        Session session = null;
        try {
            session = hibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            // Recherche d'une occupation existante pour le professeur, la salle et la date donnés
            Query query = session.createQuery("FROM Occuper WHERE codeprof.codeprof = :codeProf AND codesal.codesal = :codeSal AND date = :date AND heure =:heure ");
            query.setParameter("codeProf", codeProf);
            query.setParameter("codeSal", codeSal);
            query.setParameter("date", date);
            query.setParameter("heure", heure);

            List<Occuper> result = query.list();

            // Vérifier si une occupation existe déjà avec un professeur différent
            for (Occuper occuper : result) {
                if (!occuper.getCodeprof().getCodeprof().equals(codeProf)) {
                    return true; // Une occupation existe déjà pour cette salle, date et heure avec un professeur différent
                }
            }

            session.getTransaction().commit();

            // Si une occupation existe déjà pour cette heure spécifique, retourner true
            return false;

        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public void ajouterOccuper(Prof CodeProf, Salle codeSalle, Date date, String heure) {
        Session session = hibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Occuper occuper = new Occuper();
        occuper.setDate(date);
        occuper.setCodeprof(CodeProf);
        occuper.setCodesal(codeSalle);
        occuper.setHeure(heure);

        session.save(occuper);
        session.getTransaction().commit();
    }

    public void modifierOccuper(int id, Prof codeProf, Salle codeSalle, Date date, String heure) {
        Session session = hibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Occuper occuper = (Occuper) session.load(Occuper.class, id);
        occuper.setDate(date);
        occuper.setHeure(heure);
        occuper.setCodeprof(codeProf);
        occuper.setCodesal(codeSalle);

        session.save(occuper);
        session.getTransaction().commit();
    }

    public void supprimerOccuper(int id) {

        Session session = hibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Occuper occuper = (Occuper) session.load(Occuper.class, id);
        if (occuper != null) {
            session.delete(occuper);
            session.getTransaction().commit();
            getAllOccuper();
        } else {
            session.getTransaction().rollback();
        }

    }

    public List<Occuper> searchoccuper(String keyword) {
        List<Occuper> occuper = null;
        Session session = null;
        try {
            session = hibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            // Convertir le mot-clé en minuscules
            keyword = "%" + keyword.toLowerCase() + "%";

            // Utiliser LOWER() dans la requête pour rendre la recherche insensible à la casse
            Query query = session.createQuery("FROM Occuper WHERE lower(codeprof.nom) LIKE :keyword OR lower(codesal.designation) LIKE :keyword OR lower(codeprof.codeprof) LIKE :keyword OR lower(codesal.codesal) LIKE :keyword");
            query.setParameter("keyword", keyword);

            occuper = query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return occuper;
    }
}
