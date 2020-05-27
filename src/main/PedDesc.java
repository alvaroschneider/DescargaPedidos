/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PedNoDesc.java
 *
 * Created on 20/12/2011, 22:56:50
 */
package main;

import controller.BajadaController;
import database.PersistData;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Admin
 */
public class PedDesc extends javax.swing.JInternalFrame {

    /** Creates new form PedNoDesc */
    public PedDesc() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtCabeceras = new javax.swing.JTable();
        jbSalir = new javax.swing.JButton();
        jbRefrescar = new javax.swing.JButton();
        jbBajaPedidos1 = new javax.swing.JButton();
        jbDescargaPedidosSeleccionados = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 204, 153));
        setTitle("Pedidos Descargados");

        jtCabeceras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jtCabeceras);

        jbSalir.setText("Salir");
        jbSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalirActionPerformed(evt);
            }
        });

        jbRefrescar.setText("Refrescar");
        jbRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRefrescarActionPerformed(evt);
            }
        });

        jbBajaPedidos1.setText("Descargar todos los pedidos");
        jbBajaPedidos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBajaPedidos1ActionPerformed(evt);
            }
        });

        jbDescargaPedidosSeleccionados.setText("Descargar Pedidos Seleccionados");
        jbDescargaPedidosSeleccionados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDescargaPedidosSeleccionadosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbBajaPedidos1)
                        .addGap(18, 18, 18)
                        .addComponent(jbDescargaPedidosSeleccionados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jbRefrescar)
                            .addComponent(jbSalir))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbRefrescar)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbBajaPedidos1)
                    .addComponent(jbSalir)
                    .addComponent(jbDescargaPedidosSeleccionados))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
}//GEN-LAST:event_jbSalirActionPerformed

    private void jbRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRefrescarActionPerformed
        try {
            // TODO add your handling code here:
            PersistData pd = new PersistData();
            jtCabeceras.setModel(pd.getModeloTodosLosPedidos());
        //jtCabeceras.updateUI();
        } catch (Exception ex) {
            Logger.getLogger(VentanaMaestra.class.getName()).log(Level.SEVERE, null, ex);
        }

}//GEN-LAST:event_jbRefrescarActionPerformed

    private void jbBajaPedidos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBajaPedidos1ActionPerformed
        // TODO add your handling code here:
        BajadaController bc = new BajadaController();
        try {
            System.out.println("descargando");
            bc.bajaTodosLosPedidos();
        } catch (Exception ex) {
            Logger.getLogger(VentanaMaestra.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_jbBajaPedidos1ActionPerformed

    private void jbDescargaPedidosSeleccionadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDescargaPedidosSeleccionadosActionPerformed
        // TODO add your handling code here:
        int nroP = 0;
        Vector nros = new Vector();
        int[] indice = jtCabeceras.getSelectedRows();

        for (int i = 0; i < indice.length; i++) {
            //System.out.println("indice: "+indice[i]);
            //nroP = String.valueOf(jtCabeceras.getModel().getValueAt(indice[i], 1));
            nroP = Integer.parseInt(String.valueOf(jtCabeceras.getModel().getValueAt(indice[i], 1)));
            //System.out.println("nro: "+nroP);
            nros.add(nroP);            
        }

        BajadaController bc = new BajadaController();
        try {
            bc.bajaPedidosSeleccionados(nros);
        } catch (Exception ex) {
            Logger.getLogger(PedDesc.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jbDescargaPedidosSeleccionadosActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbBajaPedidos1;
    private javax.swing.JButton jbDescargaPedidosSeleccionados;
    private javax.swing.JButton jbRefrescar;
    private javax.swing.JButton jbSalir;
    private javax.swing.JTable jtCabeceras;
    // End of variables declaration//GEN-END:variables
}