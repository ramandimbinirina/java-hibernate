/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import bean.Prof;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import manager.ProfManager;

/**
 *
 * @author Angela
 */
public class ProfForm extends javax.swing.JFrame {

    private ProfTableModel profTableModel;
    private DefaultTableModel profTableMode;
    private ProfManager profManager = new ProfManager();
    private DefaultTableModel tableModel;
    private String codeprof;
    public static int id;

    /**
     * Creates new form ProfForm
     */
    public ProfForm() {
        initComponents();
        initialiser();
        updateTable();

        profTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int Row = profTable.getSelectedRow();

                    if (Row != -1) {

                        Object codeprof = profTable.getValueAt(Row, 0);
                        Object nom = profTable.getValueAt(Row, 1);
                        Object prenom = profTable.getValueAt(Row, 2);
                        Object grade = profTable.getValueAt(Row, 3);

                        String codeprof1 = (codeprof != null) ? codeprof.toString() : "";
                        String nom1 = (nom != null) ? nom.toString() : "";
                        String prenom1 = (prenom != null) ? prenom.toString() : "";
                        String grade1 = (grade != null) ? grade.toString() : "";

                        // Affichez les données du Prof dans votre formulaire de modification
                        textCodeprof.setText(codeprof1);
                        textNom.setText(nom1);
                        textPrenom.setText(prenom1);
                        textGrade.setText(grade1);
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

    private void onSearchFieldChanged() {
        String key = Search.getText();
        if (key.isEmpty()) {
            profTableMode.setRowCount(0);
            List<Prof> profs = profManager.getAllProf();
            for (Prof prof : profs) {
                profTableMode.addRow(new Object[]{prof.getCodeprof(), prof.getNom(), prof.getPrenom(), prof.getGrade()});
            }
        } else {
            profTableMode.setRowCount(0);
            List<Prof> profs = profManager.searchprof(key);
            for (Prof prof : profs) {
                profTableMode.addRow(new Object[]{prof.getCodeprof(), prof.getNom(), prof.getPrenom(), prof.getGrade()});
            }
        }
    }

    private void calculateTotalEffectifs() {
        int totalEffectifs = profTable.getRowCount();
        jLabel7.setText(Integer.toString(totalEffectifs));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        Search = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        profTable = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        profTitre = new javax.swing.JLabel();
        textGrade = new javax.swing.JTextField();
        textPrenom = new javax.swing.JTextField();
        textNom = new javax.swing.JTextField();
        textCodeprof = new javax.swing.JTextField();
        btnAjouter = new javax.swing.JButton();
        btnAnnuler = new javax.swing.JButton();
        btnModifier = new javax.swing.JButton();
        btnSupprimer = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        accueil = new javax.swing.JLabel();
        salle = new javax.swing.JLabel();
        occupation = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GESTION DE SALLE DE CLASSE - PROFESSEUR");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(0, 102, 102));

        Search.setBackground(new java.awt.Color(0, 102, 102));
        Search.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Search.setForeground(new java.awt.Color(255, 255, 255));
        Search.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Recherche ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchActionPerformed(evt);
            }
        });

        profTable.setBackground(new java.awt.Color(0, 102, 102));
        profTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        profTable.setForeground(new java.awt.Color(255, 255, 255));
        profTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "CodeProf", "Nom", "Prenom", "Grade"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        profTable.setSelectionBackground(new java.awt.Color(204, 255, 255));
        profTable.setSelectionForeground(new java.awt.Color(0, 102, 102));
        profTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(profTable);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("LISTE PROFESSESURS EXISTANT");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(155, 155, 155)
                        .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setText("EFFECTIFS : ");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("total");

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        profTitre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        profTitre.setForeground(new java.awt.Color(255, 255, 255));
        profTitre.setText("NOUVEAU ");
        jPanel1.add(profTitre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 400, -1));

        textGrade.setBackground(new java.awt.Color(0, 102, 102));
        textGrade.setForeground(new java.awt.Color(255, 255, 255));
        textGrade.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Grade", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel1.add(textGrade, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, 310, 70));

        textPrenom.setBackground(new java.awt.Color(0, 102, 102));
        textPrenom.setForeground(new java.awt.Color(255, 255, 255));
        textPrenom.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Prénom", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel1.add(textPrenom, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 310, 70));

        textNom.setBackground(new java.awt.Color(0, 102, 102));
        textNom.setForeground(new java.awt.Color(255, 255, 255));
        textNom.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nom", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        textNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textNomActionPerformed(evt);
            }
        });
        jPanel1.add(textNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 310, 70));

        textCodeprof.setBackground(new java.awt.Color(0, 102, 102));
        textCodeprof.setForeground(new java.awt.Color(255, 255, 255));
        textCodeprof.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CodeProf", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        textCodeprof.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textCodeprofActionPerformed(evt);
            }
        });
        jPanel1.add(textCodeprof, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 310, 60));

        btnAjouter.setBackground(new java.awt.Color(0, 102, 102));
        btnAjouter.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAjouter.setForeground(new java.awt.Color(255, 255, 255));
        btnAjouter.setText("Enregistrer");
        btnAjouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjouterActionPerformed(evt);
            }
        });
        jPanel1.add(btnAjouter, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 450, 110, 42));

        btnAnnuler.setBackground(new java.awt.Color(0, 102, 102));
        btnAnnuler.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAnnuler.setForeground(new java.awt.Color(255, 255, 255));
        btnAnnuler.setText("X");
        btnAnnuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnnulerActionPerformed(evt);
            }
        });
        jPanel1.add(btnAnnuler, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 40, -1));

        btnModifier.setBackground(new java.awt.Color(0, 102, 102));
        btnModifier.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnModifier.setForeground(new java.awt.Color(255, 255, 255));
        btnModifier.setText("Modifier");
        btnModifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifierActionPerformed(evt);
            }
        });
        jPanel1.add(btnModifier, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 450, 110, 45));

        btnSupprimer.setBackground(new java.awt.Color(0, 102, 102));
        btnSupprimer.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSupprimer.setForeground(new java.awt.Color(255, 255, 255));
        btnSupprimer.setText("Supprimer");
        btnSupprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupprimerActionPerformed(evt);
            }
        });
        jPanel1.add(btnSupprimer, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 450, 110, 47));

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setFont(new java.awt.Font("Bookman Old Style", 0, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PROFESSEUR ....");

        accueil.setBackground(new java.awt.Color(255, 255, 255));
        accueil.setFont(new java.awt.Font("Freestyle Script", 0, 36)); // NOI18N
        accueil.setForeground(new java.awt.Color(255, 255, 255));
        accueil.setText("Accueil");
        accueil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accueilMouseClicked(evt);
            }
        });

        salle.setBackground(new java.awt.Color(255, 255, 255));
        salle.setFont(new java.awt.Font("Freestyle Script", 0, 36)); // NOI18N
        salle.setForeground(new java.awt.Color(255, 255, 255));
        salle.setText("Salle");
        salle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salleMouseClicked(evt);
            }
        });

        occupation.setBackground(new java.awt.Color(255, 255, 255));
        occupation.setFont(new java.awt.Font("Freestyle Script", 0, 36)); // NOI18N
        occupation.setForeground(new java.awt.Color(255, 255, 255));
        occupation.setText("Occupation");
        occupation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                occupationMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(accueil, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(salle, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(occupation, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salle)
                    .addComponent(accueil)
                    .addComponent(occupation)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addGap(308, 308, 308))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void textNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textNomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textNomActionPerformed

    public class ProfTableModel extends AbstractTableModel {

        private List<Prof> profList;

        public ProfTableModel(List<Prof> profList) {
            this.profList = profList;
        }

        public void ajouterProfesseur(Prof professeur) {
            profList.add(professeur);

            fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
        }

        public Prof getProfAt(int rowIndex) {
            return profList.get(rowIndex);
        }

        public void removeProf(int rowIndex) {
            profList.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }

        public void setProfesseurs(List<Prof> professeurs) {
            this.profList = professeurs;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return profList.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Prof prof = profList.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return prof.getCodeprof();
                case 1:
                    return prof.getNom();
                case 2:
                    return prof.getPrenom();
                case 3:
                    return prof.getGrade();
                default:
                    return null;
            }
        }
    }

    public void initialiser() {
        profTableMode = new DefaultTableModel();
        profTableMode.addColumn("CodeProf");
        profTableMode.addColumn("Nom");
        profTableMode.addColumn("Prenom");
        profTableMode.addColumn("Grade");

        profTable.setModel(profTableMode);
    }

    public void updateTable() {
        profTableMode.setRowCount(0);
        List<Prof> profs = profManager.getAllProf();
        for (Prof proff : profs) {
            profTableMode.addRow(new Object[]{proff.getCodeprof(), proff.getNom(), proff.getPrenom(), proff.getGrade()});
        }
        // Calculer et afficher les effectifs totaux
        calculateTotalEffectifs();

        // Récupérer le codeprof et le mettre à jour dans le champ texte
        getMax();
        textCodeprof.setText(codeprof);
    }

    public void getMax() {
        String dernierCodeprof = profManager.getLastId();

        if (dernierCodeprof != null && dernierCodeprof.length() >= 2) {
            int dernierNumero = Integer.parseInt(dernierCodeprof.substring(2));
            dernierNumero++;
            codeprof = "CP" + dernierNumero;

        } else {
            // Gérer le cas où dernierCodeprof est null ou de longueur inférieure à 2
            // Peut-être afficher un message d'erreur ou prendre une autre action appropriée
            codeprof = "CP1"; // Initialiser codeprof à CP1 si aucun code n'existe encore
        }
    }


    private void btnAjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjouterActionPerformed
        // TODO add your handling code here:

        String nom = textNom.getText();
        String prenom = textPrenom.getText();
        String grade = textGrade.getText();

        if (nom.isEmpty() && prenom.isEmpty() && grade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Champs vides", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                ProfManager pm = new ProfManager();
                Prof nouveauProf = pm.ajouterProf(codeprof, nom, prenom, grade); // Ajout et récupération du nouveau professeur

                JOptionPane.showMessageDialog(this, "Proffesseurs ajoute avec succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);
                updateTable();

                // Efface les champs de saisie après l'ajout
                textNom.setText("");
                textPrenom.setText("");
                textGrade.setText("");
                textCodeprof.setText(codeprof);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du professeur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnAjouterActionPerformed


    private void btnModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifierActionPerformed
        // TODO add your handling code here:
        int selectedRow = profTable.getSelectedRow();

        if (selectedRow != -1) {
            try {
                String codeprof = textCodeprof.getText();
                String nom = textNom.getText();
                String prenom = textPrenom.getText();
                String grade = textGrade.getText();

                if (nom.isEmpty() && prenom.isEmpty() && grade.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Champs vides", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        ProfManager pm = new ProfManager();
                        pm.modifierProf(codeprof, nom, prenom, grade); // Ajout et récupération du nouveau professeur

                        JOptionPane.showMessageDialog(this, "Modification proffesseurs avec succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);
                        updateTable();

                        // Efface les champs de saisie après l'ajout
                        textNom.setText("");
                        textPrenom.setText("");
                        textGrade.setText("");

                    } catch (Exception ex) {
                        //                e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Erreur de modification du professeur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception e) {
                // Géstion des autres erreurs éventuelles
                JOptionPane.showMessageDialog(this, "Une erreur s'est produite : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne à modifier.", "Aucune ligne sélectionnée", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnModifierActionPerformed

    private void btnSupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupprimerActionPerformed
        int selectedRow = profTable.getSelectedRow();

        if (selectedRow != -1) {
            // Récupération du codeprof du professeur sélectionné
            Object codeprofObj = profTable.getValueAt(selectedRow, 0);
            String codeprof = (codeprofObj != null) ? codeprofObj.toString() : "";

            if (!codeprof.isEmpty()) {
                try {
                    ProfManager profManager = new ProfManager(); // Création d'une instance de la classe ProfManager

                    if (profManager.supprimerProf(codeprof)) {
                        JOptionPane.showMessageDialog(this, "Suppression du professeur réussie.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        updateTable();

                        // Effacer les champs de saisie après la suppression
                        textNom.setText("");
                        textPrenom.setText("");
                        textGrade.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du professeur de la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Une erreur s'est produite : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Codeprof invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un professeur à supprimer.", "Avertissement", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnSupprimerActionPerformed

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchActionPerformed

    private void btnAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnnulerActionPerformed
        // TODO add your handling code here:
        btnModifier.setVisible(false);
        btnAjouter.setVisible(true);
        btnSupprimer.setVisible(false);

        profTitre.setText("NOUVEAU");

        textCodeprof.setText(codeprof);
        textNom.setText("");
        textPrenom.setText("");
        textGrade.setText("");

        JOptionPane.showMessageDialog(this, "Action annulée", "Succes", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_btnAnnulerActionPerformed

    private void textCodeprofActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textCodeprofActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textCodeprofActionPerformed

    private void profTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profTableMouseClicked
        // TODO add your handling code here:
        btnModifier.setVisible(true);
        btnAjouter.setVisible(false);
        btnSupprimer.setVisible(true);

        profTitre.setText("INFORMATIONS SUR LE PROFESSEUR");

    }//GEN-LAST:event_profTableMouseClicked

    private void accueilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accueilMouseClicked
        // TODO add your handling code here:
        HomeForm hf = new HomeForm();
        hf.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_accueilMouseClicked

    private void salleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salleMouseClicked
        // TODO add your handling code here:
        SalleForm sf = new SalleForm();
        sf.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_salleMouseClicked

    private void occupationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_occupationMouseClicked
        // TODO add your handling code here:
        OccuperForm of = new OccuperForm();
        of.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_occupationMouseClicked

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
            java.util.logging.Logger.getLogger(ProfForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProfForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProfForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProfForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProfForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Search;
    private javax.swing.JLabel accueil;
    private javax.swing.JButton btnAjouter;
    private javax.swing.JButton btnAnnuler;
    private javax.swing.JButton btnModifier;
    private javax.swing.JButton btnSupprimer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel occupation;
    private javax.swing.JTable profTable;
    private javax.swing.JLabel profTitre;
    private javax.swing.JLabel salle;
    private javax.swing.JTextField textCodeprof;
    private javax.swing.JTextField textGrade;
    private javax.swing.JTextField textNom;
    private javax.swing.JTextField textPrenom;
    // End of variables declaration//GEN-END:variables
}