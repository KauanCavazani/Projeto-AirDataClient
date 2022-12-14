package com.airdata.view;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.airdata.model.Server;
import com.airdata.model.User;

import com.airdata.controller.ServerController;
import com.airdata.dao.ExceptionDAO;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;

public class Dash extends javax.swing.JFrame {
    
    /** Creates new form Dash
     * @throws java.lang.ClassNotFoundException
     * @throws java.net.SocketException
     * @throws java.net.UnknownHostException
     * @throws com.airdata.dao.ExceptionDAO */
    public Dash() throws ClassNotFoundException, SocketException, UnknownHostException, ExceptionDAO {
                
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Dash.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.setLocationRelativeTo(null);
        
        TimerTask monitoramento = new TimerTask() {
            @Override
            public void run() {
                
                ServerController sc = new ServerController();

                try {
                    Server server = sc.getServer();
                    
                    List dataList = sc.saveData(server);
                    
                    titleMacAddress.setText((String) dataList.get(0));
                    
                    cpuBar.setValue((int) dataList.get(1));
                    ramBar.setValue((int) dataList.get(2));
                    diskBar.setValue((int) dataList.get(3));
                    
                } catch (SocketException | UnknownHostException | ExceptionDAO | ClassNotFoundException ex) {
                    Logger.getLogger(Dash.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Dash.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        };
        
        initComponents();
        Timer time = new Timer();
        time.scheduleAtFixedRate(monitoramento, 0, 1000);
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ramBar = new javax.swing.JProgressBar();
        cpuBar = new javax.swing.JProgressBar();
        diskBar = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        titleMacAddress = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dashboard");
        setResizable(false);

        ramBar.setBackground(new java.awt.Color(255, 255, 255));
        ramBar.setForeground(new java.awt.Color(76, 134, 200));
        ramBar.setStringPainted(true);

        cpuBar.setBackground(new java.awt.Color(255, 255, 255));
        cpuBar.setForeground(new java.awt.Color(76, 134, 200));
        cpuBar.setToolTipText("");
        cpuBar.setName(""); // NOI18N
        cpuBar.setStringPainted(true);

        diskBar.setBackground(new java.awt.Color(255, 255, 255));
        diskBar.setForeground(new java.awt.Color(76, 134, 200));
        diskBar.setStringPainted(true);

        jLabel1.setText("RAM (%)");

        jLabel2.setText("CPU (%)");

        jLabel3.setText("DISCO (%)");

        titleMacAddress.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        titleMacAddress.setText("                         ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cpuBar, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ramBar, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(diskBar, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(115, 115, 115))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(101, 101, 101))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(115, 115, 115)))
                        .addGap(71, 71, 71))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(titleMacAddress)
                        .addGap(149, 149, 149))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(titleMacAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cpuBar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ramBar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(diskBar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Dash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                Dash dash = null;
                try {
                    dash = new Dash();
                } catch (ClassNotFoundException | SocketException | UnknownHostException | ExceptionDAO ex) {
                    Logger.getLogger(Dash.class.getName()).log(Level.SEVERE, null, ex);
                }
                dash.setVisible(true);
            }
        });
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar cpuBar;
    private javax.swing.JProgressBar diskBar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JProgressBar ramBar;
    private javax.swing.JLabel titleMacAddress;
    // End of variables declaration//GEN-END:variables

}
