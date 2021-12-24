package ru.ramanpan.desktop.util;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.List;

public class CustomTableModel<T> extends AbstractTableModel {
    private List<T> rows;
    private Class<T> cls;
    private String[] nameCol;


    public CustomTableModel(List<T> rows, Class<T> cls, String[] nameCol) {
        this.rows = rows;
        this.cls = cls;
        this.nameCol = nameCol;
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public String getColumnName(int column) {
        return nameCol[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return cls.getDeclaredFields()[columnIndex].getType();
    }

    @Override
    public int getColumnCount() {
        return cls.getDeclaredFields().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Field field = cls.getDeclaredFields()[columnIndex];
            field.setAccessible(true);
            return field.get(rows.get(rowIndex));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Class<T> getCls() {
        return cls;
    }

    public void setCls(Class<T> cls) {
        this.cls = cls;
    }

    public String[] getNameCol() {
        return nameCol;
    }

    public void setNameCol(String[] nameCol) {
        this.nameCol = nameCol;
    }
}
