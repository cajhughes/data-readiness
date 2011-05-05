package com.cajhughes.ready.model;

import java.util.Map;
import java.util.Set;
import javax.swing.table.AbstractTableModel;

public class QuantityPriceTableModel extends AbstractTableModel {
    private static final String[][] categoryMatrix = {
        {"label+ve", "+ve+ve", "+ve0", "+ve-ve"},
        {"label0", "0+ve", "00", "0-ve"},
        {"label-ve", "-ve+ve", "-ve0", "-ve-ve"},
        {"labelQuantity NaN", "QuantityNaN", "label", "label"},
        {"labelPrice NaN", "PriceNaN", "label", "label"}
    };
    private static final String[] columnNames = new String[] {"", "+ve", "0", "-ve"};
    private Map<String, Set<Integer>> results = null;

    public QuantityPriceTableModel(final Map<String, Set<Integer>> results) {
        super();
        this.results = results;
    }

    public String getCategory(final int row, final int column) {
        String value = null;
        String matrixValue = categoryMatrix[row][column];
        if(matrixValue != null && !matrixValue.startsWith("label")) {
            value = matrixValue;
        }
        return value;
    }

    @Override
    public Class getColumnClass(final int column) {
        if(column > 0) {
            return Integer.class;
        }
        else {
            return String.class;
        }
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(final int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Object value = null;
        String matrixValue = categoryMatrix[row][column];
        if(matrixValue != null && !matrixValue.equals("")) {
            if(!matrixValue.startsWith("label")) {
                Set<Integer> set = results.get(matrixValue);
                if(set != null) {
                    value = Integer.valueOf(set.size());
                }
                else {
                    value = Integer.valueOf(0);
                }
            }
            else {
                value = matrixValue.substring(5);
            }
        }
        return value;
    }
}
