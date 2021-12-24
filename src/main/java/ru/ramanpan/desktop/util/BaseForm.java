package ru.ramanpan.desktop.util;

import ru.ramanpan.desktop.App;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class BaseForm extends JFrame {
    private int width = 1024;
    private int height = 720;
    public static String APP_TITLE = "Школа языков Леарн";
    public static Image APP_ICON = null;

    static {
        try {
            APP_ICON = ImageIO.read(Objects.requireNonNull(BaseForm.class.getClassLoader().getResource("school_logo.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BaseForm(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(width,height));
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - width / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - height / 2);
        setTitle(APP_TITLE + (App.ADMIN_MODE ? " [режим администратора]" : ""));
        if(APP_ICON != null) {
            setIconImage(APP_ICON);
        }
    }


    protected void showError(JFrame jFrame, String message) {
        JOptionPane.showMessageDialog(jFrame,message,"Ошибка", JOptionPane.ERROR_MESSAGE);
    }
    protected void showInfo(JFrame jFrame, String message) {
        JOptionPane.showMessageDialog(jFrame,message,"Информация", JOptionPane.INFORMATION_MESSAGE);
    }

}
