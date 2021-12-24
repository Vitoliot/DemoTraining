package ru.ramanpan.desktop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Link {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/demoexam1", "root", "1234");
    }
}
