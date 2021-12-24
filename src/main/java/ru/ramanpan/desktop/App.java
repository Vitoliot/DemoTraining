package ru.ramanpan.desktop;

import ru.ramanpan.desktop.ui.ServiceTableForm;


import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

public class App {
    public static boolean ADMIN_MODE = false;
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        changeAllFonts(new FontUIResource("Comic Sans MS", Font.TRUETYPE_FONT, 12));

        ADMIN_MODE = "0000".equalsIgnoreCase(JOptionPane.showInputDialog(null, "Введите пароль администратора, если знаете", "Режим администратора", JOptionPane.QUESTION_MESSAGE));

        new ServiceTableForm();
    }
    public static void changeAllFonts(Font font){
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, font);
        }
    }
}
