/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import bean.Salle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import manager.SalleManager;

/**
 *
 * @author Angela
 */
public class SalleForm extends javax.swing.JFrame {

    private SalleTableModel salleTableModel;
    private DefaultTableModel salleTableMode;
    private SalleManager salleManager = new SalleManager();
    private DefaultTableModel tableModel;
    private String codesal;
    public static int id;

    /**
     * Creates new form SalleFrom
     */
    public SalleForm() {
        initComponents();

        initialiser();
        updateTable();

        salleTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    // Obtenez l'index de la ligne sélectionnée
                    int Row = salleTable.getSelectedRow();

                    // Vérifiez s'il y a une ligne sélectionnée
                    if (Row != -1) {

                        Object codesal = salleTable.getValueAt(Row, 0);
                        Object designation = salleTable.getValueAt(Row, 1);

                        String codesal1 = (codesal != null) ? codesal.toString() : "";
                        String designation1 = (designation != null) ? designation.toString() : "";

                        // Affichez les données du Prof dans votre formulaire de modification
                        textCodesalle.setText(codesal1);
                        textDesignation.setText(designation1);
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
            salleTableMode.setRowCount(0);
            List<Salle> sall = salleManager.getAllSalle();
            for (Salle sal : sall) {
                salleTableMode.addRow(new Object[]{sal.getCodesal(), sal.getDesignation()});
            }
        } else {
            salleTableMode.setRowCount(0);
            List<Salle> sall = salleManager.searchsalle(key);
            for (Salle sal : sall) {
                salleTableMode.addRow(new Object[]{sal.getCodesal(), sal.getDesignation()});
            }
        }
    }

    private void calculateTotalEffectifs() {
        int totalEffectifs = salleTable.getRowCount();
        jLabel8.setText(Integer.toString(totalEffectifs));
    }

    @SuppressWarnings("unchecked")

    public class SalleTableModel extends AbstractTableModel {

        private List<Salle> salleList;

        public SalleTableModel(List<Salle> salleList) {
            this.salleList = salleList;
        }

        public void ajouterSalle(Salle salle) {
            salleList.add(salle);
            fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
        }

        public Salle getSalleAt(int rowIndex) {
            return salleList.get(rowIndex);
        }

        public void removeSalle(int rowIndex) {
            salleList.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }

        public void setSalles(List<Salle> professeurs) {
            this.salleList = professeurs;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return salleList.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Salle salle = salleList.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return salle.getCodesal();
                case 1:
                    return salle.getDesignation();
                default:
                    return null;
            }
        }
    }

    public void initialiser() {
        salleTableMode = new DefaultTableModel();
        salleTableMode.addColumn("CodeSal");
        salleTableMode.addColumn("Designation");

        salleTable.setModel(salleTableMode);
    }

    public void updateTable() {
        salleTableMode.setRowCount(0);
        List<Salle> salles = salleManager.getAllSalle();
        for (Salle sal : salles) {
            salleTableMode.addRow(new Object[]{sal.getCodesal(), sal.getDesignation()});
        }

        // Calculer et afficher les effectifs totaux
        calculateTotalEffectifs();

        // Récupérer le codeprof et le mettre à jour dans le champ texte
        getMax();
        textCodesalle.setText(codesal);
    }

    public void getMax() {
        String dernierCodesalle = salleManager.getLastId();

        if (dernierCodesalle != null && dernierCodesalle.length() >= 2) {
            int dernierNumero = Integer.parseInt(dernierCodesalle.substring(2));
            dernierNumero++;
            codesal = "CS" + dernierNumero;

        } else {
            codesal = "CS1"; // Initialiser codeprof à SP1 si aucun code n'existe encore
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        Search = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        salleTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        textCodesalle = new javax.swing.JTextField();
        textDesignation = new javax.swing.JTextField();
        salleTitre = new javax.swing.JLabel();
        btnAjouter = new javax.swing.JButton();
        btnSupprimer = new javax.swing.JButton();
        btnModifier = new javax.swing.JButton();
        annuler = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        accueil = new javax.swing.JLabel();
        prof = new javax.swing.JLabel();
        occuper = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GESTION DE SALLE DE CLASSE - SALLE");

        jPanel4.setBackground(new java.awt.Color(51, 0, 0));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setForeground(new java.awt.Color(51, 0, 0));

        Search.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        Search.setForeground(new java.awt.Color(51, 0, 0));
        Search.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Recherche par code ou désignation", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(51, 0, 0))); // NOI18N
        Search.setSelectedTextColor(new java.awt.Color(51, 0, 0));
        Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchActionPerformed(evt);
            }
        });

        salleTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        salleTable.setForeground(new java.awt.Color(51, 0, 0));
        salleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "CodeSalle", "Désignation"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        salleTable.setGridColor(new java.awt.Color(255, 255, 255));
        salleTable.setSelectionBackground(new java.awt.Color(51, 0, 0));
        salleTable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        salleTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salleTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(salleTable);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 0, 0));
        jLabel2.setText("LISTE SALLES EXISTANTS");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 0, 0));
        jLabel5.setText("EFFECTIFS : ");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 0, 0));
        jLabel8.setText("total");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(331, 331, 331)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 661, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(99, 99, 99))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8))
                .addContainerGap(174, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(51, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        textCodesalle.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textCodesalle.setForeground(new java.awt.Color(51, 0, 0));
        textCodesalle.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CodeSalle", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(51, 0, 0))); // NOI18N
        textCodesalle.setSelectedTextColor(new java.awt.Color(51, 0, 0));
        textCodesalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textCodesalleActionPerformed(evt);
            }
        });
        jPanel1.add(textCodesalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 320, 70));

        textDesignation.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textDesignation.setForeground(new java.awt.Color(51, 0, 0));
        textDesignation.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Designation", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14), new java.awt.Color(51, 0, 0))); // NOI18N
        textDesignation.setSelectedTextColor(new java.awt.Color(51, 0, 0));
        textDesignation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textDesignationActionPerformed(evt);
            }
        });
        jPanel1.add(textDesignation, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 320, 70));

        salleTitre.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        salleTitre.setForeground(new java.awt.Color(51, 0, 0));
        salleTitre.setText("NOUVEAU ");
        jPanel1.add(salleTitre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 347, -1));

        btnAjouter.setBackground(new java.awt.Color(51, 153, 0));
        btnAjouter.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAjouter.setForeground(new java.awt.Color(255, 255, 255));
        btnAjouter.setText("Enregistrer");
        btnAjouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjouterActionPerformed(evt);
            }
        });
        jPanel1.add(btnAjouter, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, 140, 60));

        btnSupprimer.setBackground(new java.awt.Color(255, 0, 0));
        btnSupprimer.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSupprimer.setForeground(new java.awt.Color(255, 255, 255));
        btnSupprimer.setText("Supprimer");
        btnSupprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupprimerActionPerformed(evt);
            }
        });
        jPanel1.add(btnSupprimer, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 360, 140, 60));

        btnModifier.setBackground(new java.awt.Color(0, 204, 204));
        btnModifier.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnModifier.setText("Modifier");
        btnModifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifierActionPerformed(evt);
            }
        });
        jPanel1.add(btnModifier, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, 140, 60));

        annuler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-return-16.png"))); // NOI18N
        annuler.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                annulerMouseClicked(evt);
            }
        });
        jPanel1.add(annuler, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, -1, -1));

        jLabel1.setFont(new java.awt.Font("Bookman Old Style", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-classroom-42.png"))); // NOI18N
        jLabel1.setText(" SALLE ....");

        accueil.setFont(new java.awt.Font("Freestyle Script", 0, 36)); // NOI18N
        accueil.setForeground(new java.awt.Color(255, 255, 255));
        accueil.setText("Accueil");
        accueil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accueilMouseClicked(evt);
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

        occuper.setFont(new java.awt.Font("Freestyle Script", 0, 36)); // NOI18N
        occuper.setForeground(new java.awt.Color(255, 255, 255));
        occuper.setText("Occupation");
        occuper.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                occuperMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(accueil, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(prof, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(occuper, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accueil)
                    .addComponent(prof)
                    .addComponent(occuper)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjouterActionPerformed
        String designation = textDesignation.getText();

        if (designation.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un designtaion.", "Champs vides", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                SalleManager pm = new SalleManager();
                Salle nouveauSalle = pm.ajouterSalle(codesal, designation);

                JOptionPane.showMessageDialog(this, "Designation de salle ajoutez avec succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);
                updateTable();

                // Efface les champs de saisie après l'ajout
                textDesignation.setText("");
                textCodesalle.setText(codesal);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du salle.");
            }
        }
    }//GEN-LAST:event_btnAjouterActionPerformed

    private void btnModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifierActionPerformed

        int selectedRow = salleTable.getSelectedRow();

        // Vérification s'il y a une ligne sélectionnée
        if (selectedRow != -1) {
            try {
                String codesal = textCodesalle.getText();
                String designation = textDesignation.getText();

                if (designation.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Champs vides", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        SalleManager sm = new SalleManager();
                        sm.modifierSalle(codesal, designation); // Ajout et récupération du nouveau professeur
                        updateTable();

                        JOptionPane.showMessageDialog(this, "Modification designation de sallle avec succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);

                        // Efface les champs de saisie après l'ajout
                        textDesignation.setText("");

                    } catch (Exception ex) {
                        //                e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Erreur de modification de designation de salle : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Une erreur s'est produite : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne à modifier.", "Aucune ligne sélectionnée", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnModifierActionPerformed

    private void btnSupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupprimerActionPerformed
        int selectedRow = salleTable.getSelectedRow();

        if (selectedRow != -1) {
            // Obtention du code de la salle sélectionnée
            Object codesalObj = salleTable.getValueAt(selectedRow, 0);
            String codesal = (codesalObj != null) ? codesalObj.toString() : "";

            if (!codesal.isEmpty()) {
                try {
                    SalleManager salleManager = new SalleManager(); // Création d'une instance de la classe SalleManager

                    if (salleManager.supprimerSalle(codesal)) {
                        JOptionPane.showMessageDialog(this, "Suppression de la salle réussie.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        updateTable();

                        // Effacer les champs de saisie après la suppression
                        textCodesalle.setText("");
                        textDesignation.setText("");

                    } else {
                        JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de la salle de la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Une erreur s'est produite : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Code de salle invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une salle à supprimer.", "Avertissement", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnSupprimerActionPerformed

    private void textDesignationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textDesignationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textDesignationActionPerformed

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchActionPerformed

    private void textCodesalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textCodesalleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textCodesalleActionPerformed

    private void accueilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accueilMouseClicked
        HomeForm hf = new HomeForm();
        hf.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_accueilMouseClicked

    private void occuperMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_occuperMouseClicked
        OccuperForm of = new OccuperForm();
        of.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_occuperMouseClicked

    private void profMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profMouseClicked
        ProfForm pf = new ProfForm();
        pf.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_profMouseClicked

    private void salleTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salleTableMouseClicked

        btnModifier.setVisible(true);
        btnAjouter.setVisible(false);
        btnSupprimer.setVisible(true);

        salleTitre.setText("INFORMATIONS SUR CETTE SALLE");
    }//GEN-LAST:event_salleTableMouseClicked

    private void annulerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_annulerMouseClicked

        btnModifier.setVisible(false);
        btnAjouter.setVisible(true);
        btnSupprimer.setVisible(false);

        salleTitre.setText("NOUVELLE SALLE");

        textCodesalle.setText(codesal);
        textDesignation.setText("");

        JOptionPane.showMessageDialog(this, "Action annulée / Actualisé ", "Actualisation", JOptionPane.INFORMATION_MESSAGE);
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
            java.util.logging.Logger.getLogger(SalleForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalleForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalleForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalleForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SalleForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Search;
    private javax.swing.JLabel accueil;
    private javax.swing.JLabel annuler;
    private javax.swing.JButton btnAjouter;
    private javax.swing.JButton btnModifier;
    private javax.swing.JButton btnSupprimer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel occuper;
    private javax.swing.JLabel prof;
    private javax.swing.JTable salleTable;
    private javax.swing.JLabel salleTitre;
    private javax.swing.JTextField textCodesalle;
    private javax.swing.JTextField textDesignation;
    // End of variables declaration//GEN-END:variables
}
