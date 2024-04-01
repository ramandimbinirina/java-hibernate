/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import bean.DateBean;
import bean.Occuper;
import bean.Prof;
import bean.Salle;
import com.raven.datechooser.EventDateChooser;
import com.raven.datechooser.SelectedAction;
import com.raven.datechooser.SelectedDate;
import java.awt.event.ActionEvent;
import manager.ProfManager;
import manager.SalleManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import manager.OccuperManager;
import manager.ProfManager;
import org.hibernate.Query;
import org.hibernate.Session;
import util.hibernateUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import manager.DateManager;

/**
 *
 * @author Angela
 */
public class OccuperForm extends javax.swing.JFrame {

    private OccuperTableModel occuperTableModel;
    private DefaultTableModel occuperTableMode;
    private OccuperManager occuperManager = new OccuperManager();
    private DefaultTableModel tableModel;
    public static int x;
    public static String p;
    public static String s;
    private ProfManager profManager = new ProfManager();
    private SalleManager salleManager = new SalleManager();
    private List<Salle> listeDesSalles;

    public Date date = new Date();

    /**
     * Creates new form OccuperForm
     */
    public OccuperForm() {
        initComponents();

        initialiser();
        afficherProf();
        afficherSal();
        updateTable();

        dateChooser1.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate date) {
                dateCalendar.setText(date.getDay() + "-" + date.getMonth() + "-" + date.getYear());
                if (action.getAction() == SelectedAction.DAY_SELECTED) {
                    dateChooser1.hidePopup();
                }
            }
        });
        heure.setText(timepicker.getSelectedTime());
        dateCalendar.setText(
                dateChooser1.getSelectedDate().getDay() + "-"
                + dateChooser1.getSelectedDate().getMonth() + "-"
                + dateChooser1.getSelectedDate().getYear());

        timepicker.addActionListener((ActionEvent e) -> {
            heure.setText(timepicker.getSelectedTime());
        });

        ProfComboBoxActionPerformed(null);
        SalleComboBoxActionPerformed(null);

        OccuperTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = OccuperTable.getSelectedRow();

                    // Vérifiez s'il y a une ligne sélectionnée
                    if (selectedRow != -1) {
                        x = (Integer) OccuperTable.getValueAt(selectedRow, 0);
                        Object prof = OccuperTable.getValueAt(selectedRow, 1);
                        Object salle = OccuperTable.getValueAt(selectedRow, 2);
                        Date date = (Date) OccuperTable.getValueAt(selectedRow, 3);

                        Object selectedSalle = SalleComboBox.getSelectedItem(); // Récupère l'élément sélectionné dans le JComboBox
                        List<Salle> salles = salleManager.getAllSalle();
                        for (Salle sale : salles) {
                            if (salle.equals((sale.getDesignation() + "(" + sale.getCodesal() + ")"))) {
                                SalleComboBox.setSelectedItem(sale.getCodesal());
                                s = sale.getCodesal();
                            }
                        }

                        Object selectedProf = ProfComboBox.getSelectedItem(); // Récupère l'élément sélectionné dans le JComboBox
                        List<Prof> profs = profManager.getAllProf();
                        for (Prof po : profs) {
                            if (prof.equals((po.getNom() + "(" + po.getCodeprof() + ")"))) {
                                ProfComboBox.setSelectedItem(po.getCodeprof());
                                p = po.getCodeprof();
                            }
                        }
                        dateChooser1.setSelectedDate(date);
                        timepicker.setSelectedTime(date);
                        heure.setText(timepicker.getSelectedTime());
                        remplirProfComboBox();
                        remplirSalleComboBox();
                    }
                }
            }
        });

        btnModifier.setVisible(false);
        btnSupprimer.setVisible(false);

        Search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onSearchFieldChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onSearchFieldChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    private void remplirProfComboBox() {
        ProfComboBox.removeAllItems();
        Prof pr = profManager.getProf(p);
        if (pr != null) {
            ProfComboBox.addItem(pr.getCodeprof());
        }
        List<Prof> profs = profManager.getAllProf();
        for (Prof prof : profs) {
            ProfComboBox.addItem(prof.getCodeprof());
        }
    }

    private void remplirSalleComboBox() {
        SalleComboBox.removeAllItems();
        Salle sa = salleManager.getSalle(s);
        if (sa != null) {
            SalleComboBox.addItem(sa.getCodesal());
        }
        listeDesSalles = salleManager.getAllSalle();
        for (Salle salle : listeDesSalles) {
            SalleComboBox.addItem(salle.getCodesal());
        }
    }

    private void onSearchFieldChanged() {
        String key = Search.getText().toLowerCase(); // Convertir la clé en minuscules pour une recherche insensible à la casse

        if (key.isEmpty()) {

            occuperTableMode.setRowCount(0);
            List<Occuper> oc = occuperManager.getAllOccuper();
            for (Occuper occ : oc) {
                occuperTableMode.addRow(new Object[]{occ.getId(), occ.getCodeprof().getNom(), occ.getCodesal().getDesignation(), occ.getDate()});
            }
            updateTable();

        } else {
            occuperTableMode.setRowCount(0);
            List<Occuper> oc = occuperManager.searchoccuper(key);
            for (Occuper occ : oc) {
                occuperTableMode.addRow(new Object[]{occ.getId(), occ.getCodeprof().getNom(), occ.getCodesal().getDesignation(), occ.getDate()});
            }
        }
    }

    private void calculateTotalEffectifs() {
        int totalEffectifs = OccuperTable.getRowCount();
        jLabel8.setText(Integer.toString(totalEffectifs));
    }

    // Méthode pour charger les valeurs initiales dans le combobox des professeurs
    private void chargerComboboxProf() {
        List<Prof> profs = profManager.getAllProf();
        ProfComboBox.removeAllItems();
        for (Prof prof : profs) {
            ProfComboBox.addItem(prof.getCodeprof());
        }
    }

    // Méthode pour charger les valeurs initiales dans le combobox des salles
    private void chargerComboboxSalle() {
        List<Salle> salles = salleManager.getAllSalle();
        SalleComboBox.removeAllItems();
        for (Salle salle : salles) {
            SalleComboBox.addItem(salle.getCodesal());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        timepicker = new com.raven.swing.TimePicker();
        dateChooser1 = new com.raven.datechooser.DateChooser();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        OccuperTable = new javax.swing.JTable();
        Search = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        SalleComboBox = new javax.swing.JComboBox<>();
        ProfComboBox = new javax.swing.JComboBox<>();
        occuperTitre = new javax.swing.JLabel();
        btnSupprimer = new javax.swing.JButton();
        btnModifier = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        dateCalendar = new javax.swing.JLabel();
        heure = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnAjouter = new javax.swing.JButton();
        annuler = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        salle = new javax.swing.JLabel();
        prof = new javax.swing.JLabel();
        accueil = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GESTION DE SALLE DE CLASSE - OCCUPER");

        jPanel4.setBackground(new java.awt.Color(51, 0, 0));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setForeground(new java.awt.Color(51, 0, 0));

        OccuperTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        OccuperTable.setForeground(new java.awt.Color(51, 0, 0));
        OccuperTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id", "CodeProf", "CodeSalle", "Date"
            }
        ));
        OccuperTable.setGridColor(new java.awt.Color(255, 255, 255));
        OccuperTable.setSelectionBackground(new java.awt.Color(51, 0, 0));
        OccuperTable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        OccuperTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OccuperTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(OccuperTable);

        Search.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Search.setForeground(new java.awt.Color(51, 0, 0));
        Search.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Recherche", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(51, 0, 0))); // NOI18N
        Search.setSelectedTextColor(new java.awt.Color(51, 0, 0));
        Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 0, 0));
        jLabel7.setText("LES SALLES OCCUPES PAR CHAQUE PROF");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setText("EFFECTIFS : ");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("total");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(433, 433, 433)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 813, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8))
                .addGap(107, 107, 107))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(51, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SalleComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SalleComboBox.setForeground(new java.awt.Color(51, 0, 0));
        SalleComboBox.setToolTipText("");
        SalleComboBox.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        SalleComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        SalleComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalleComboBoxActionPerformed(evt);
            }
        });
        jPanel1.add(SalleComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, 257, 50));

        ProfComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ProfComboBox.setForeground(new java.awt.Color(51, 0, 0));
        ProfComboBox.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        ProfComboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProfComboBoxMouseClicked(evt);
            }
        });
        ProfComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProfComboBoxActionPerformed(evt);
            }
        });
        jPanel1.add(ProfComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 257, 40));

        occuperTitre.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        occuperTitre.setForeground(new java.awt.Color(51, 0, 0));
        occuperTitre.setText("NOUVEAU ");
        jPanel1.add(occuperTitre, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 362, 33));

        btnSupprimer.setBackground(new java.awt.Color(255, 51, 0));
        btnSupprimer.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSupprimer.setForeground(new java.awt.Color(255, 255, 255));
        btnSupprimer.setText("Supprimer");
        btnSupprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupprimerActionPerformed(evt);
            }
        });
        jPanel1.add(btnSupprimer, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 500, 150, 60));

        btnModifier.setBackground(new java.awt.Color(0, 204, 204));
        btnModifier.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnModifier.setText("Modifier");
        btnModifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifierActionPerformed(evt);
            }
        });
        jPanel1.add(btnModifier, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 500, 150, 60));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 0, 0));
        jLabel2.setText("CodeProf");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 70, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 0, 0));
        jLabel4.setText("CodeSalle");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, -1, -1));

        dateCalendar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        dateCalendar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/calendar.png"))); // NOI18N
        dateCalendar.setText("16 mars 2024");
        dateCalendar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dateCalendarMouseClicked(evt);
            }
        });
        jPanel1.add(dateCalendar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 370, 150, -1));

        heure.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        heure.setForeground(new java.awt.Color(51, 0, 0));
        heure.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clock.png"))); // NOI18N
        heure.setText("20:30");
        heure.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                heureMouseClicked(evt);
            }
        });
        jPanel1.add(heure, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 370, 100, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 0, 0));
        jLabel3.setText("Date et heure d'occupation");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 330, -1, -1));

        btnAjouter.setBackground(new java.awt.Color(51, 153, 0));
        btnAjouter.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAjouter.setForeground(new java.awt.Color(255, 255, 255));
        btnAjouter.setText("Enregistrer");
        btnAjouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjouterActionPerformed(evt);
            }
        });
        jPanel1.add(btnAjouter, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 500, 150, 60));

        annuler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-return-16.png"))); // NOI18N
        annuler.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                annulerMouseClicked(evt);
            }
        });
        jPanel1.add(annuler, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, -1, -1));

        jPanel2.setBackground(new java.awt.Color(51, 0, 0));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Bookman Old Style", 0, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-meeting-time-40.png"))); // NOI18N
        jLabel1.setText("  OCCUPATION ....");

        salle.setFont(new java.awt.Font("Freestyle Script", 0, 36)); // NOI18N
        salle.setForeground(new java.awt.Color(255, 255, 255));
        salle.setText("Salle");
        salle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salleMouseClicked(evt);
            }
        });

        prof.setFont(new java.awt.Font("Freestyle Script", 0, 36)); // NOI18N
        prof.setForeground(new java.awt.Color(255, 255, 255));
        prof.setText("Prof");
        prof.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profMouseClicked(evt);
            }
        });

        accueil.setFont(new java.awt.Font("Freestyle Script", 0, 36)); // NOI18N
        accueil.setForeground(new java.awt.Color(255, 255, 255));
        accueil.setText("Accueil");
        accueil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accueilMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(accueil, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(prof, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(salle, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accueil)
                    .addComponent(prof)
                    .addComponent(salle)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public class OccuperTableModel extends AbstractTableModel {

        private List<Occuper> occuperList;

        public OccuperTableModel(List<Occuper> occuperList) {
            this.occuperList = occuperList;
        }

        public Occuper getOccuperAt(int rowIndex) {
            return occuperList.get(rowIndex);
        }

        public void removeOccuper(int rowIndex) {
            occuperList.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }

        public void setOccuperList(List<Occuper> occuperList) {
            this.occuperList = occuperList;
            fireTableDataChanged();
        }

        public void supprimerOccuper(Occuper occuper) {
            int index = occuperList.indexOf(occuper);
            if (index != -1) {
                occuperList.remove(index);
                fireTableRowsDeleted(index, index);
            }
        }

        @Override
        public int getRowCount() {
            return occuperList.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Occuper occuper = occuperList.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return occuper.getCodeprof().getCodeprof();
                case 1:
                    return occuper.getCodesal().getCodesal();
                case 2:
                    return occuper.getDate();
                default:
                    return null;
            }
        }
    }

    public void initialiser() {
        occuperTableMode = new DefaultTableModel();
        occuperTableMode.addColumn("Id");
        occuperTableMode.addColumn("CodeProf avec son Nom");
        occuperTableMode.addColumn("CodeSalle avec Designation");
        occuperTableMode.addColumn("Date d'occupation");
        OccuperTable.setModel(occuperTableMode);
    }

    List<Occuper> occupe;

    public void updateTable() {
        occuperTableMode.setRowCount(0);
        occupe = occuperManager.getAllOccuper();

        for (Occuper occ : occupe) {
            String codeProf = (occ.getCodeprof() != null) ? occ.getCodeprof().getNom() + "(" + occ.getCodeprof().getCodeprof() + ")" : ".....";
            String codeSal = (occ.getCodesal() != null) ? occ.getCodesal().getDesignation() + "(" + occ.getCodesal().getCodesal() + ")" : "...";

            occuperTableMode.addRow(new Object[]{occ.getId(), codeProf, codeSal, occ.getDate()});
        }

        // Calculer et afficher les effectifs totaux
        calculateTotalEffectifs();
    }

    public Date toDateTime(String date) {
        Date daten = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            daten = format.parse(date);
        } catch (ParseException e) {
            System.out.println("Erreur :" + e);
        }
        return daten;
    }

    public Date toDate(String date) {
        Date daten = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            daten = format.parse(date);
        } catch (ParseException e) {
            System.out.println("Erreur :" + e);
        }
        return daten;
    }

    public Date toTime(String date) {
        Date daten = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            daten = format.parse(date);
        } catch (ParseException e) {
            System.out.println("Erreur :" + e);
        }
        return daten;
    }

    public void ComparData() {
        LocalDateTime dateTime = null;
        LocalDateTime dateTimedb = null;

        Date datetodate = toDateTime(dateCalendar.getText() + " " + heure.getText());
        List<DateBean> datebean = new DateManager().getDate(datetodate);
        for (DateBean dateBean : datebean) {
            dateTime = LocalDateTime.of(dateBean.getYear(), dateBean.getMonth(), dateBean.getDay(), dateBean.getHour(), dateBean.getMinute());
        }

        String message = null;
        int i = 0;
        for (Occuper occuper : occupe) {
            List<DateBean> datebeandb = new DateManager().getDate(occuper.getDate());
            for (DateBean dateBean : datebeandb) {
                dateTimedb = LocalDateTime.of(dateBean.getYear(), dateBean.getMonth(), dateBean.getDay(), dateBean.getHour(), dateBean.getMinute());
            }
            if (dateTime.isAfter(dateTimedb) && dateTime.isBefore(dateTimedb.plusHours(2)) && SalleComboBox.getSelectedItem().equals(occuper.getCodesal().getCodesal())) {
                message = "Salle occupé par autre professeur en ce moment!";
                i++;
            } else {
                if (dateTime.isAfter(dateTimedb) && dateTime.isBefore(dateTimedb.plusHours(2)) && ProfComboBox.getSelectedItem().equals(occuper.getCodeprof().getCodeprof())) {
                    message = "Prof occupé dans un autre salle en ce moment!";
                    i++;
                }
            }
        }
        if (message != null) {
            JOptionPane.showMessageDialog(null, message);
        }
        if (i == 0) {
            Prof p = profManager.getProf((String) ProfComboBox.getSelectedItem());
            Salle s = salleManager.getSalle((String) SalleComboBox.getSelectedItem());
            occuperManager.ajouterOccuper(p, s, datetodate);

        JOptionPane.showMessageDialog(this, "Proffesseur ajouté avec succes.", "Succés", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void btnAjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjouterActionPerformed
        ComparData();
        updateTable();

        chargerComboboxProf();
        chargerComboboxSalle();

        dateChooser1.setSelectedDate(date);
        dateCalendar.setText(
                dateChooser1.getSelectedDate().getDay() + "-"
                + dateChooser1.getSelectedDate().getMonth() + "-"
                + dateChooser1.getSelectedDate().getYear());
        timepicker.setSelectedTime(date);
        heure.setText(timepicker.getSelectedTime());
    }//GEN-LAST:event_btnAjouterActionPerformed


    private void btnModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifierActionPerformed

        String codeProf = (String) ProfComboBox.getSelectedItem();
        String codeSal = (String) SalleComboBox.getSelectedItem();

        int selectedRow = OccuperTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne à modifier.", "Aucune ligne sélectionnée", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Remplir les combobox avec les valeurs récupérées
        ProfComboBox.setSelectedItem(codeProf);
        SalleComboBox.setSelectedItem(codeSal);
        Date datetodate = toDateTime(dateCalendar.getText() + " " + heure.getText());

        // Effectuer les actions de modification...
        if (datetodate == null) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une date.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                OccuperManager o = new OccuperManager();
                Prof p = profManager.getProf(codeProf);
                Salle s = salleManager.getSalle(codeSal);
                o.modifierOccuper(x, p, s, datetodate);

                JOptionPane.showMessageDialog(this, "Occupations modifiées avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                updateTable();

                // Recharger les valeurs dans les combobox
                chargerComboboxProf();
                chargerComboboxSalle();

                dateChooser1.setSelectedDate(date);
                dateCalendar.setText(
                        dateChooser1.getSelectedDate().getDay() + "-"
                        + dateChooser1.getSelectedDate().getMonth() + "-"
                        + dateChooser1.getSelectedDate().getYear());
                timepicker.setSelectedTime(date);
                heure.setText(timepicker.getSelectedTime());

                btnModifier.setVisible(false);
                btnAjouter.setVisible(true);
                btnSupprimer.setVisible(false);

                occuperTitre.setText("NOUVEAU OCCUPATION");

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Une erreur s'est produite lors de la modification de l'entrée OCCUPER.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnModifierActionPerformed

    private void btnSupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupprimerActionPerformed
        int selectedRow = OccuperTable.getSelectedRow();

        // Vérifier si une ligne est sélectionnée dans le tableau
        if (selectedRow != -1) {
            try {
                OccuperManager o = new OccuperManager();
                o.supprimerOccuper(x);

                JOptionPane.showMessageDialog(this, "Occupation supprimée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                updateTable();

                // Recharger les valeurs dans les combobox
                chargerComboboxProf();
                chargerComboboxSalle();

                dateChooser1.setSelectedDate(date);
                dateCalendar.setText(
                        dateChooser1.getSelectedDate().getDay() + "-"
                        + dateChooser1.getSelectedDate().getMonth() + "-"
                        + dateChooser1.getSelectedDate().getYear());
                timepicker.setSelectedTime(date);
                heure.setText(timepicker.getSelectedTime());

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de l'occupation : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une occupation à supprimer.", "Aucune occupation sélectionnée", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnSupprimerActionPerformed

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchActionPerformed

    private void SalleComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalleComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SalleComboBoxActionPerformed

    public void afficherProf() {
        Session session = hibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Récupération de la liste des profs depuis la base de données
        Query query = session.createQuery("from Prof");
        List<Prof> profs = query.list();

        // Ajout de chaque nom de professeur au modèle de liste
        for (Prof prof : profs) {
            ProfComboBox.addItem(prof.getCodeprof());
        }

        // Fermer la session Hibernate
        session.getTransaction().commit();
        session.close();
    }

    public void afficherSal() {
        // Ouvrir une nouvelle session Hibernate
        Session session = hibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Récupération de la liste des salles depuis la base de données
        Query query = session.createQuery("from Salle");
        List<Salle> salles = query.list();

        // Ajout de chaque désignation de salle au modèle de liste
        for (Salle salle : salles) {
            SalleComboBox.addItem(salle.getCodesal());
        }
        // Fermer la session Hibernate
        session.getTransaction().commit();
        session.close();

    }
    private void ProfComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProfComboBoxActionPerformed
//        // TODO add your handling code here:
    }//GEN-LAST:event_ProfComboBoxActionPerformed

    private void ProfComboBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProfComboBoxMouseClicked
        ProfComboBox.requestFocusInWindow();
    }//GEN-LAST:event_ProfComboBoxMouseClicked

    private void OccuperTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OccuperTableMouseClicked

        btnModifier.setVisible(true);
        btnAjouter.setVisible(false);
        btnSupprimer.setVisible(true);

        occuperTitre.setText("INFORMATIONS SUR CET OCCUPATION");
    }//GEN-LAST:event_OccuperTableMouseClicked

    private void salleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salleMouseClicked

        SalleForm sf = new SalleForm();
        sf.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_salleMouseClicked

    private void profMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profMouseClicked

        ProfForm pf = new ProfForm();
        pf.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_profMouseClicked

    private void accueilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accueilMouseClicked

        HomeForm hf = new HomeForm();
        hf.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_accueilMouseClicked

    private void dateCalendarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateCalendarMouseClicked
        dateChooser1.showPopup(dateCalendar, 0, dateCalendar.getHeight());
    }//GEN-LAST:event_dateCalendarMouseClicked

    private void heureMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_heureMouseClicked
        timepicker.showPopup(heure, 0, heure.getHeight());
    }//GEN-LAST:event_heureMouseClicked

    private void annulerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_annulerMouseClicked

        btnModifier.setVisible(false);
        btnAjouter.setVisible(true);
        btnSupprimer.setVisible(false);

        occuperTitre.setText("NOUVEAU OCCUPATION");

        JOptionPane.showMessageDialog(this, "Action annulée/ Actualisé", "Actualisation", JOptionPane.INFORMATION_MESSAGE);
        // Recharger les valeurs dans les combobox
        chargerComboboxProf();
        chargerComboboxSalle();

        dateChooser1.setSelectedDate(date);
        dateCalendar.setText(
                dateChooser1.getSelectedDate().getDay() + "-"
                + dateChooser1.getSelectedDate().getMonth() + "-"
                + dateChooser1.getSelectedDate().getYear());
        timepicker.setSelectedTime(date);
        heure.setText(timepicker.getSelectedTime());

        updateTable();
    }//GEN-LAST:event_annulerMouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked

        NewJFrame pf = new NewJFrame();
        pf.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel5MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OccuperForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OccuperForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OccuperForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OccuperForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OccuperForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable OccuperTable;
    private javax.swing.JComboBox<String> ProfComboBox;
    private javax.swing.JComboBox<String> SalleComboBox;
    private javax.swing.JTextField Search;
    private javax.swing.JLabel accueil;
    private javax.swing.JLabel annuler;
    private javax.swing.JButton btnAjouter;
    private javax.swing.JButton btnModifier;
    private javax.swing.JButton btnSupprimer;
    private javax.swing.JLabel dateCalendar;
    private com.raven.datechooser.DateChooser dateChooser1;
    private javax.swing.JLabel heure;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel occuperTitre;
    private javax.swing.JLabel prof;
    private javax.swing.JLabel salle;
    private com.raven.swing.TimePicker timepicker;
    // End of variables declaration//GEN-END:variables
}
