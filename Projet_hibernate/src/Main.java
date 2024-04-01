/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Angela
 */
import bean.Occuper;
import bean.Prof;
import bean.Salle;
import bean.DateBean;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import javax.swing.*;
import java.util.List;
import views.ProfForm;
import javax.swing.SwingUtilities;
import views.HomeForm;
import views.OccuperForm;
import views.SalleForm;

public class Main {

    public static void main(String[] args) {
        // Initialisation de la configuration Hibernate à partir U fichier hibernate.cfg.xml
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        // Création la session factory à partir de la configuration
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        // Ouvrerture une session Hibernate
        Session session = sessionFactory.openSession();

        List<Occuper> occupe = (List<Occuper>) session.createQuery("FROM Occuper").list();
        // Fermeture de la session Hibernate
        session.close();

        // Fermeture de la session factory à la fin du programme
        sessionFactory.close();

        // Lancement de l'interface graphique
        SwingUtilities.invokeLater(() -> {
            // Création et affichage de l'interface graphique (par exemple, ProfForm)
            OccuperForm occuperForm = new OccuperForm();

            // Mise à jour des données de la table dans l'interface graphique
            occuperForm.updateTable();

            // Affichage de l'interface graphique
            occuperForm.setVisible(true);
        });
    }
}
