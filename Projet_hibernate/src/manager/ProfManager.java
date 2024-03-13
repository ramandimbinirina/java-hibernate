/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import bean.Prof;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import util.hibernateUtil;

/**
 *
 * @author Angela
 */
public class ProfManager {

    public List<Prof> getAllProf() {
        List<Prof> profs = new ArrayList<>();
        Session session = null;

        try {
            session = hibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            Query query = session.createQuery("FROM Prof");
            profs = query.list();

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return profs;
    }

    public String getLastId() {
        String lastId = "";
        Session session = null;

        try {
            session = hibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            Query query = session.createQuery("select max(codeprof) FROM Prof");
            lastId = (String) query.uniqueResult();

            if (lastId != null) {
                // Si lastId n'est pas null, on l'incrémente de 1
                lastId = String.valueOf(Integer.parseInt(lastId) + 1);
            } else {
                // Si aucun codeProf n'existe encore, on initialise à 1
                lastId = "1";
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return lastId;
    }

    public Prof getProf(String codeprof) {
        Prof prof = null;
        Session session = null;

        try {
            session = hibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            prof = (Prof) session.get(Prof.class, codeprof);
            session.beginTransaction().commit();

        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.beginTransaction().rollback();
            }
            e.printStackTrace();
        }
        return prof;
    }

    public Prof ajouterProf(String codeprof, String nom, String prenom, String grade) {
        Session session = hibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Prof p = new Prof();
        p.setCodeprof(codeprof);
        p.setNom(nom);
        p.setPrenom(prenom);
        p.setGrade(grade);
        session.save(p);
        session.getTransaction().commit();

        return p;
    }

    public void modifierProf(String codeprof, String nom, String prenom, String grade) {
        Session session = hibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Prof p = (Prof) session.load(Prof.class, codeprof);
        p.setCodeprof(codeprof);
        p.setNom(nom);
        p.setPrenom(prenom);
        p.setGrade(grade);
        session.update(p);
        session.getTransaction().commit();
    }

    public boolean supprimerProf(String codeprof) {
        try {
            Session session = hibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Prof p = (Prof) session.load(Prof.class, codeprof);
            session.delete(p);
            session.getTransaction().commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Prof> searchprof(String keyword) {
        List<Prof> prof = null;
        Session session = null;
        try {
            session = hibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            // Convertir le mot-clé en minuscules
            keyword = "%" + keyword.toLowerCase() + "%";

            // Utiliser LOWER() dans la requête pour rendre la recherche insensible à la casse
            Query query = session.createQuery("FROM Prof WHERE lower(codeprof) LIKE :keyword OR lower(nom) LIKE :keyword OR lower(prenom) LIKE :keyword OR lower(grade) LIKE :keyword");
            query.setParameter("keyword", keyword);

            prof = query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return prof;
    }
}
