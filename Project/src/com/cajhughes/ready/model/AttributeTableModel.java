package com.cajhughes.ready.model;

import java.util.Map;
import java.util.Set;
import javax.swing.table.AbstractTableModel;

public class AttributeTableModel extends AbstractTableModel {
    private static final String[] columnNames = new String[] {"Column", "Distinct Values"};
    private AttributeTableRow[] data;

    public AttributeTableModel(final Map<String, Set<String>> results) {
        super();
        Object[] keys = results.keySet().toArray();
        int size = keys.length;
        data = new AttributeTableRow[size];
        for(int i=0; i<size; i++) {
            data[i] = new AttributeTableRow((String)keys[i], results.get(keys[i]).size());
        }
    }

    public int getRowCount() {
        return data.length;
    }

    public Class getColumnClass(final int column) {
        return getValueAt(0, column).getClass();
    }

    public int getColumnCount() {
        return 2;
    }

    public String getColumnName(final int column) {
        return columnNames[column];
    }

    public Object getValueAt(int row, int column) {
        if(column == 0) {
            return data[row].getName();
        }
        else {
            return data[row].getCount();
        }
    }
}
