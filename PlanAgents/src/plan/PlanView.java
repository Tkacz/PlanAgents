/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PlanView.java
 *
 * Created on 2012-05-28, 16:03:29
 */
package plan;

import java.util.ArrayList;
import javax.swing.table.TableColumn;

/**
 *
 * @author Rafał Tkaczyk
 */
public class PlanView extends javax.swing.JFrame {

    /** Creates new form PlanView */
    public PlanView(ArrayList<PlanData> data) {
        tabModelD1 = new PlanTableModel();
        tabModelD2 = new PlanTableModel();
        tabModelD3 = new PlanTableModel();
        initComponents();
        initTableModel();
        insertData(data);
    }
    
    private void initTableModel(){
	TableColumn col;
	col = tableD1.getColumnModel().getColumn(0);
	col.setPreferredWidth(50);
	col.setMaxWidth(50);
        col = tableD1.getColumnModel().getColumn(1);
	col.setPreferredWidth(100);
	col.setMaxWidth(100);
	col = tableD1.getColumnModel().getColumn(2);
	col.setPreferredWidth(275);
	col.setMaxWidth(275);
	col = tableD1.getColumnModel().getColumn(3);
	col.setPreferredWidth(50);
	col.setMaxWidth(50);
        col = tableD1.getColumnModel().getColumn(4);
	col.setPreferredWidth(275);
	col.setMaxWidth(275);
	col = tableD1.getColumnModel().getColumn(5);
	col.setPreferredWidth(50);
	col.setMaxWidth(50);
        
        col = tableD2.getColumnModel().getColumn(0);
	col.setPreferredWidth(50);
	col.setMaxWidth(50);
        col = tableD2.getColumnModel().getColumn(1);
	col.setPreferredWidth(100);
	col.setMaxWidth(100);
	col = tableD2.getColumnModel().getColumn(2);
	col.setPreferredWidth(275);
	col.setMaxWidth(275);
        col = tableD2.getColumnModel().getColumn(3);
	col.setPreferredWidth(50);
	col.setMaxWidth(50);
	col = tableD2.getColumnModel().getColumn(4);
	col.setPreferredWidth(275);
	col.setMaxWidth(275);
	col = tableD2.getColumnModel().getColumn(5);
	col.setPreferredWidth(50);
	col.setMaxWidth(50);
        
        col = tableD3.getColumnModel().getColumn(0);
	col.setPreferredWidth(50);
	col.setMaxWidth(50);
        col = tableD3.getColumnModel().getColumn(1);
	col.setPreferredWidth(100);
	col.setMaxWidth(100);
	col = tableD3.getColumnModel().getColumn(2);
	col.setPreferredWidth(275);
	col.setMaxWidth(275);
	col = tableD3.getColumnModel().getColumn(3);
	col.setPreferredWidth(50);
	col.setMaxWidth(50);
        col = tableD3.getColumnModel().getColumn(4);
	col.setPreferredWidth(275);
	col.setMaxWidth(275);
	col = tableD3.getColumnModel().getColumn(5);
	col.setPreferredWidth(50);
	col.setMaxWidth(50);
        
        tableD1.setAutoCreateRowSorter(true);
        tableD2.setAutoCreateRowSorter(true);
        tableD3.setAutoCreateRowSorter(true);
    }
    
    private void insertData(ArrayList<PlanData> data) {
        ArrayList<PlanData> d1 = new ArrayList<PlanData>();
        ArrayList<PlanData> d2 = new ArrayList<PlanData>();
        ArrayList<PlanData> d3 = new ArrayList<PlanData>();
        
        for(int i = 0, size = data.size(); i < size; i++) {
            PlanData temp = data.get(i);
            if(temp.getDay() == 0) {
                d1.add(temp);
            } else if(temp.getDay() == 1) {
                d2.add(temp);
            } else if(temp.getDay() == 2) {
                d3.add(temp);
            }
        }
        
        tabModelD1.setValueAt(d1);
        tabModelD2.setValueAt(d2);
        tabModelD3.setValueAt(d3);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelD1 = new javax.swing.JScrollPane();
        tableD1 = new javax.swing.JTable();
        panelD2 = new javax.swing.JScrollPane();
        tableD2 = new javax.swing.JTable();
        panelD3 = new javax.swing.JScrollPane();
        tableD3 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Plan");
        setResizable(false);

        panelD1.setBorder(javax.swing.BorderFactory.createTitledBorder("Dzień 1"));
        panelD1.setMaximumSize(new java.awt.Dimension(800, 200));
        panelD1.setMinimumSize(new java.awt.Dimension(800, 200));
        panelD1.setPreferredSize(new java.awt.Dimension(800, 200));

        tableD1.setModel(tabModelD1);
        panelD1.setViewportView(tableD1);

        panelD2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dzień 2", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, java.awt.Color.black));
        panelD2.setMaximumSize(new java.awt.Dimension(800, 200));
        panelD2.setMinimumSize(new java.awt.Dimension(800, 200));
        panelD2.setPreferredSize(new java.awt.Dimension(800, 200));

        tableD2.setModel(tabModelD2);
        panelD2.setViewportView(tableD2);

        panelD3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dzień 3", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, java.awt.Color.black));
        panelD3.setMaximumSize(new java.awt.Dimension(800, 200));
        panelD3.setMinimumSize(new java.awt.Dimension(800, 200));
        panelD3.setPreferredSize(new java.awt.Dimension(800, 200));

        tableD3.setModel(tabModelD3);
        panelD3.setViewportView(tableD3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelD1, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
            .addComponent(panelD2, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
            .addComponent(panelD3, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelD1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelD2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelD3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private PlanTableModel tabModelD1;
    private PlanTableModel tabModelD2;
    private PlanTableModel tabModelD3;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane panelD1;
    private javax.swing.JScrollPane panelD2;
    private javax.swing.JScrollPane panelD3;
    private javax.swing.JTable tableD1;
    private javax.swing.JTable tableD2;
    private javax.swing.JTable tableD3;
    // End of variables declaration//GEN-END:variables
}
