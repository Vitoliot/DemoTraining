package ru.ramanpan.desktop.ui;

import ru.ramanpan.desktop.entities.Service;
import ru.ramanpan.desktop.manager.ServiceManager;
import ru.ramanpan.desktop.util.BaseForm;

import javax.swing.*;
import java.sql.SQLException;

public class AddServiceForm extends BaseForm {
    private JTextField titleField;
    private JTextField costField;
    private JTextField durationField;
    private JTextField discountField;
    private JTextField pathField;
    private JButton addButton;
    private JPanel mainPanel;
    private JTextPane descriptionPane;
    private JButton backButton;

    public AddServiceForm() {
        super();
        setContentPane(mainPanel);
        initButtons();
        setVisible(true);
    }
    private void initButtons() {
        backButton.addActionListener(e -> {
            dispose();
            new ServiceTableForm();
        });
        addButton.addActionListener(exp -> {
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
        if(duration < 0 || duration > 14400) {
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
        try {
            ServiceManager.insert(new Service(
                    title,cost,duration,description,discount,pathImage));
            showInfo(this,"Добавление услуги прошло успешно");
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
