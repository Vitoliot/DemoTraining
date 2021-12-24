package ru.ramanpan.desktop.ui;

import ru.ramanpan.desktop.entities.Service;
import ru.ramanpan.desktop.manager.ServiceManager;
import ru.ramanpan.desktop.util.BaseForm;

import javax.swing.*;
import java.sql.SQLException;

public class UpdateServiceForm extends BaseForm {
    private JTextField titleField;
    private JTextField costField;
    private JTextField durationField;
    private JTextField discountField;
    private JTextField pathField;
    private JButton updateButton;
    private JPanel mainPanel;
    private JTextPane descriptionPane;
    private JButton backButton;
    private JTextField idField;
    private JButton deleteButton;

    private Service service;

    public UpdateServiceForm(int ID) {
        super();
        setContentPane(mainPanel);
        try {
            service = ServiceManager.selectByID(ID);
        } catch (SQLException throwables) {
            showError(this,"Ошибка с БД");
            throwables.printStackTrace();
        }
        initFields();
        initButtons();
        setVisible(true);

    }
    private void initFields(){
        idField.setEnabled(false);
        idField.setText(String.valueOf(service.getId()));
        titleField.setText(String.valueOf(service.getTitle()));
        costField.setText(String.valueOf(service.getCost()));
        durationField.setText(String.valueOf(service.getDurationInSeconds()));
        descriptionPane.setText(String.valueOf(service.getDescription()));
        discountField.setText(String.valueOf(service.getDiscount()));
        pathField.setText(service.getMainImagePath());
    }
    private void initButtons() {
        backButton.addActionListener(e -> {
            dispose();
            new ServiceTableForm();
        });
        deleteButton.addActionListener(e -> {
            if(JOptionPane.showConfirmDialog(this,"Вы уверен что хотите удалить услугу?","Удаление",JOptionPane.YES_NO_OPTION)
            == JOptionPane.YES_OPTION) {
                try {
                    ServiceManager.delete(service.getId());
                    showInfo(this,"Услуга успешно удалена");
                    dispose();
                    new ServiceTableForm();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    showError(this,"Ошибка с БД");
                }
            }

        });
        updateButton.addActionListener(exp -> {
        String title = titleField.getText();
        if(title.length() > 100 || title.isEmpty()){
            showError(this, "Поле заполнено неверно");
            return;
        }
        double cost = 0;
        try {
            cost = Double.parseDouble(costField.getText());
        } catch (Exception e) {
            showError(this, "Ошибка с вводом цены" + e.getMessage());
            return;
        }
        if(cost < 0 ) {
            showError(this, "Цена не может быть отрицательной");
            return;
        }
        int duration;
        try {
            duration = Integer.parseInt(durationField.getText());
        } catch (Exception e) {
            showError(this, "Ошибка с вводом длительности" + e.getMessage());
            return;
        }
        if(duration < 0) {
            showError(this, "Длительность не может быть отрицательной");
            return;
        }
        double discount = 0;
        try {
            discount = Double.parseDouble(discountField.getText());
        } catch (Exception e) {
            showError(this, "Ошибка с вводом скидки " + e.getMessage());
            return;
        }
        if(discount < 0 || discount > 100) {
            showError(this,"Ошибка с водом скидки");
            return;
        }
        String description = descriptionPane.getText();
        String pathImage = pathField.getText();
        if(pathImage.length() > 1000 ){
            showError(this, "Поле изображения заполнено неверно");
            return;
        }
        service.setId(Integer.parseInt(idField.getText()));
        service.setTitle(title);
        service.setCost(cost);
        service.setDescription(description);
        service.setDiscount(discount);
        service.setDurationInSeconds(duration);
        service.setMainImagePath(pathImage);
        try {
            ServiceManager.update(service);
            showInfo(this,"Редактирование услуги прошло успешно");
            dispose();
            new ServiceTableForm();
        }
        catch (SQLException throwables) {
            showError(this,"Ошибка с БД " + throwables.getMessage());
            throwables.printStackTrace();
        }
    });
    }
}
