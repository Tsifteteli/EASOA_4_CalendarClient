/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controler.ClientControler;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import model.CanvasEvent;

/**
 *
 * @author Caroline
 */
public class ClientGUI extends javax.swing.JFrame {

    //2D array för data til JTable (Tablemodel)
    private Object[][] data;
    private DefaultTableModel tblModel;
    private String details;
    
    private ClientControler clientControler = new ClientControler();

    /**
     * Creates new form ClientGUI
     */
    public ClientGUI() {
        initComponents();
        pnlEditEvent.setVisible(false);
    }

    private void loadEventsToJTable(CanvasEvent[] canvasEventsArray) {

        //Specifiera storlek på en annan array (kallad data) som används för att föra över datat till min JTable sen.
        int rows = canvasEventsArray.length;
        data = new Object[rows][6];
        int row = 0;
        for (CanvasEvent canvasEvent : canvasEventsArray) {
            //test
//         System.out.println(canvasEvent.getTitle() + canvasEvent.getLocationName() + canvasEvent.getLocationAddress() + canvasEvent.getDescription());
            //Ladda JTable
            data[row][0] = canvasEvent.getTitle();
            data[row][1] = canvasEvent.getLocationName();
            data[row][2] = canvasEvent.getLocationAddress();
            data[row][3] = canvasEvent.getDescription();
            data[row][4] = canvasEvent.getStartAt();
            data[row][5] = canvasEvent.getEndAt();
            row++;
        }
        initTable();
    }

    //Relaterar arrayen data till JTable(tblCalendarEvents) som visar upp innehållet   
    private void initTable() {

        String[] columnNames = {"Titel", "Lokal/plats", "Address", "Detaljer", "Starttid", "Sluttid"};
        tblModel = new DefaultTableModel(this.data, columnNames);
        tblCalendarEvents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblCalendarEvents.setModel(tblModel);
        tblCalendarEvents.getColumnModel().getColumn(0).setPreferredWidth(10);
        tblCalendarEvents.getColumnModel().getColumn(1).setPreferredWidth(10);
        tblCalendarEvents.getColumnModel().getColumn(2).setPreferredWidth(10);
        tblCalendarEvents.getColumnModel().getColumn(3).setPreferredWidth(20);
        tblCalendarEvents.getColumnModel().getColumn(4).setPreferredWidth(20);
        tblCalendarEvents.getColumnModel().getColumn(5).setPreferredWidth(20);
        tblCalendarEvents.setShowGrid(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblCalendarEvents = new javax.swing.JTable();
        btnGetSchedule = new javax.swing.JButton();
        btnEditEvent = new javax.swing.JButton();
        btnLoadToCanvas = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblCourseName = new javax.swing.JLabel();
        pnlEditEvent = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtEventDetails = new javax.swing.JTextArea();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblCalendarEvents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        jScrollPane1.setViewportView(tblCalendarEvents);

        btnGetSchedule.setText("Get schedule");
        btnGetSchedule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetScheduleActionPerformed(evt);
            }
        });

        btnEditEvent.setText("Edit selected event");
        btnEditEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditEventActionPerformed(evt);
            }
        });

        btnLoadToCanvas.setText("Load schedule to Canvas");
        btnLoadToCanvas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadToCanvasActionPerformed(evt);
            }
        });

        jTextField1.setForeground(new java.awt.Color(153, 153, 153));
        jTextField1.setText("ex D0031N");

        jTextField2.setForeground(new java.awt.Color(153, 153, 153));
        jTextField2.setText("ex 2019-09-02");

        jLabel1.setText("Course code:");

        jLabel2.setText("Sart date:");

        lblCourseName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblCourseName.setText("Course:");

        txtEventDetails.setColumns(20);
        txtEventDetails.setRows(5);
        jScrollPane2.setViewportView(txtEventDetails);

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlEditEventLayout = new javax.swing.GroupLayout(pnlEditEvent);
        pnlEditEvent.setLayout(pnlEditEventLayout);
        pnlEditEventLayout.setHorizontalGroup(
            pnlEditEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditEventLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pnlEditEventLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel)
                .addGap(101, 101, 101))
        );
        pnlEditEventLayout.setVerticalGroup(
            pnlEditEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditEventLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEditEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addGap(0, 72, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCourseName, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField1)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 754, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlEditEvent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEditEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGetSchedule, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLoadToCanvas, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(btnEditEvent)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlEditEvent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(8, 8, 8)
                        .addComponent(btnLoadToCanvas))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(btnGetSchedule))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addComponent(lblCourseName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   private void btnGetScheduleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetScheduleActionPerformed
       //Kör metoden som hämtar JSON-data från TimeEdit
       //Just nu är det hårdkodat på vår kurs, men när man har mer access till TimeEdit API kan man göra en sökning på evens som machar kursen och datum.
       //Den kör i sin tur loadEventsToJTable(CanvasEvent[] canvasEventsArray)
       //och skriver ut namne på kursen från datan i TimeEditEvents column-array.
       CanvasEvent[] canvasEvent = this.clientControler.getCanvasCalendar();
       loadEventsToJTable(canvasEvent);
   }//GEN-LAST:event_btnGetScheduleActionPerformed

   private void btnEditEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditEventActionPerformed
       // TODO add your handling code here:
       //Visa JPanel där man kan redigera "Details" för ett event
       if (tblCalendarEvents.getValueAt(tblCalendarEvents.getSelectedRow(), 3) != null) {
           pnlEditEvent.setVisible(true);
           btnEditEvent.setVisible(false);
           btnLoadToCanvas.setVisible(false);
           btnGetSchedule.setVisible(false);
           //Hårdkodat med vilken kolumn, men det ska vara detljer som ska redigeras
           txtEventDetails.setText(tblCalendarEvents.getValueAt(tblCalendarEvents.getSelectedRow(), 3).toString());
       }
   }//GEN-LAST:event_btnEditEventActionPerformed

   private void btnLoadToCanvasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadToCanvasActionPerformed
       // TODO add your handling code here:
       //POSTa canvasEvent-objekten till Canvas
       String contextCode = JOptionPane.showInputDialog( this, 
        "Enter the canvas ID number of the course to continue", 
        "Canvas ID number", 
        JOptionPane.QUESTION_MESSAGE);
       this.clientControler.setContextCode(contextCode);
       this.clientControler.setCanvasCalendar();

       //Visa pop-up som bekräftelse på om det funkat eller ej... (lista vilka som postats och vilka som misslyckats?)
   }//GEN-LAST:event_btnLoadToCanvasActionPerformed

   private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
       // TODO add your handling code here:
       details = txtEventDetails.getText();
       //Lägg till kod som sparar in de nya detailsen till motsvarande
       //canvasEvent-objekt i canvasEvent-arrayen och visar infon i tabellen
       txtEventDetails.setText("");
       pnlEditEvent.setVisible(false);
       btnEditEvent.setVisible(true);
       btnLoadToCanvas.setVisible(true);
       btnGetSchedule.setVisible(true);
   }//GEN-LAST:event_btnSaveActionPerformed

   private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
       // TODO add your handling code here:
       txtEventDetails.setText("");
       pnlEditEvent.setVisible(false);
       btnEditEvent.setVisible(true);
       btnLoadToCanvas.setVisible(true);
       btnGetSchedule.setVisible(true);
   }//GEN-LAST:event_btnCancelActionPerformed

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
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnEditEvent;
    private javax.swing.JButton btnGetSchedule;
    private javax.swing.JButton btnLoadToCanvas;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel lblCourseName;
    private javax.swing.JPanel pnlEditEvent;
    private javax.swing.JTable tblCalendarEvents;
    private javax.swing.JTextArea txtEventDetails;
    // End of variables declaration//GEN-END:variables
}
