/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plan;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Rafal Tkaczyk
 */
public class PlanTableModel extends AbstractTableModel{

    private Class[] classes = {Integer.class, String.class, String.class, String.class, String.class, Integer.class};
    private String[] headers = {"Id.", "Czas","Nazwa", "Typ", "Wykładowca", "Sala"};
    private Object data[][];
    
    int rows;
    final int cols = 10;
    
    public PlanTableModel(){
	data = null;
        rows = 0;
    }
    
    public PlanTableModel(int size){
	data = new Object[size][cols];
    }
    
    @Override
    public int getRowCount() {
	return rows;
    }

    @Override
    public int getColumnCount() {
	return headers.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
	return headers[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
	return classes[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
	return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
	return data[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	data[rowIndex][columnIndex] = aValue;
	fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public void setValueAt(ArrayList<PlanData> newData) {
        rows = newData.size();
        data = new Object[rows][cols];
        for(int i = 0; i < rows; i++) {
            data[i][0] = i+1;
            data[i][1] = newData.get(i).getTime();
            data[i][2] = newData.get(i).getName();
            data[i][3] = newData.get(i).getType();
            data[i][4] = newData.get(i).getTeacher();
            data[i][5] = newData.get(i).getRoom();
        }
        fireTableDataChanged();
    }
}
