/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import bean.Salle;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import util.hibernateUtil;

/**
 *
 * @author Angela
 */
public class SalleManager {

    public List<Salle> getAllSalle() {
        List<Salle> salles = new ArrayList<>();
        Session session = null;

        try {
            session = hibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            Query query = session.createQuery("FROM Salle");
            salles = query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return salles;
    }

    public String getLastId() {
        String lastId = "";
        Session session = null;

        try {
            session = hibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            Query query = session.createQuery("select max(codesal) FROM Salle");
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

    public Salle getSalle(String codesal) {
        Salle salle = null;
        Session session = null;

        try {
            session = hibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            salle = (Salle) session.get(Salle.class, codesal);
            session.beginTransaction().commit();

        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.beginTransaction().rollback();
            }
            e.printStackTrace();
        }
        return salle;
    }

    public Salle ajouterSalle(String codesal, String designation) {
        Session session = hibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Salle s = new Salle();
        s.setCodesal(codesal);
        s.setDesignation(designation);

        session.save(s);
        session.getTransaction().commit();

        return s;
    }

    public void modifierSalle(String codesal, String designation) {
        Session session = hibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Salle s = (Salle) session.load(Salle.class, codesal);
        s.setCodesal(codesal);
        s.setDesignation(designation);
        session.update(s);
        session.getTransaction().commit();
    }

    public boolean supprimerSalle(String codesal) {
        try {
            Session session = hibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Salle s = (Salle) session.load(Salle.class, codesal);
            session.delete(s);
            session.getTransaction().commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Il y a eu une erreur lors de la suppression
        }
    }

    public List<Salle> searchsalle(String keyword) {
        List<Salle> salle = null;
        Session session = null;
        try {
            session = hibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            // Convertir le mot-clé en minuscules
            keyword = "%" + keyword.toLowerCase() + "%";

            // Utiliser LOWER() dans la requête pour rendre la recherche insensible à la casse
            Query query = session.createQuery("FROM Salle WHERE lower(codesal) LIKE :keyword OR lower(designation) LIKE :keyword");
            query.setParameter("keyword", keyword);
            salle = query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return salle;
    }
}
