package ru.ramanpan.desktop.ui;

import ru.ramanpan.desktop.entities.Service;
import ru.ramanpan.desktop.manager.ServiceManager;
import ru.ramanpan.desktop.util.BaseForm;
import ru.ramanpan.desktop.util.CustomTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class ServiceTableForm extends BaseForm {
    private JPanel mainPanel;
    private JTable table;
    private JButton addButton;

    CustomTableModel<Service> model;
    public ServiceTableForm() {
        super();
        setContentPane(mainPanel);
        initTable();
        initButtons();
        setVisible(true);
    }

    private void initTable() {
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(50);
        try {
            model = new CustomTableModel<>(ServiceManager.selectAll(),
                    Service.class,
                    new String[]{"ID","Название", "Цена","Длительность(в сек)","описание","скидка","путь до картинки", "картинка"});
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            showError(this,"Ошибка в БД");
        }
        table.setModel(model);
        TableColumn column = table.getColumn("путь до картинки");
        column.setMaxWidth(0);
        column.setMinWidth(0);
        column.setPreferredWidth(0);


        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        dispose();
                        new UpdateServiceForm(model.getRows().get(row).getId());
                    }
                }
            }
        });

    }


    private void initButtons() {
        addButton.addActionListener(e -> {
            dispose();
            new AddServiceForm();
        });
    }
}
