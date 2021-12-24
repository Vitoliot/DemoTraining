package ru.ramanpan.desktop.manager;

import ru.ramanpan.desktop.entities.Service;
import ru.ramanpan.desktop.util.Link;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceManager {
    public static void insert(Service service) throws SQLException {
        try(Connection c = Link.getConnection()) {
            String sql = "INSERT INTO service(Title, Cost,DurationInSeconds, Description, Discount, MainImagePath) VALUES(?,?,?,?,?,?);";
            PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            p.setString(1,service.getTitle());
            p.setDouble(2,service.getCost());
            p.setInt(3,service.getDurationInSeconds());
            p.setString(4,service.getDescription());
            p.setDouble(5,service.getDiscount());
            p.setString(6,service.getMainImagePath());
            p.executeUpdate();
            ResultSet keys = p.getGeneratedKeys();
            if(keys.next()){
                service.setId(keys.getInt(1));
                return;
            }
            throw new SQLException();
        }
    }
    public static void update(Service service) throws SQLException {
        try(Connection c = Link.getConnection()) {
            String sql = "UPDATE Service SET Title = ?, Cost = ?,DurationInSeconds = ?,Description = ?,Discount = ?,MainImagePath = ? WHERE ID = ?;";
            PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            p.setString(1,service.getTitle());
            p.setDouble(2,service.getCost());
            p.setInt(3,service.getDurationInSeconds());
            p.setString(4,service.getDescription());
            p.setDouble(5,service.getDiscount());
            p.setString(6,service.getMainImagePath());
            p.setInt(7,service.getId());
            p.executeUpdate();
        }
    }
    public static void delete(int ID) throws SQLException{
        try (Connection c = Link.getConnection()){
            String sql = "DELETE FROM Service WHERE ID = ?;";
            PreparedStatement p = c.prepareStatement(sql);
            p.setInt(1,ID);
            p.executeUpdate();
        }
    }
    public static Service selectByID(int ID) throws SQLException{
        try(Connection c = Link.getConnection()) {
            String sql = "SELECT * FROM service WHERE id = ?;";
            PreparedStatement p = c.prepareStatement(sql);
            p.setInt(1,ID);
            ResultSet keys = p.executeQuery();
            if(keys.next()){
                return new Service(keys.getInt("ID"),
                        keys.getString("Title"),
                        keys.getDouble("Cost"),
                        keys.getInt("DurationInSeconds"),
                        keys.getString("Description"),
                        keys.getDouble("Discount"),
                        keys.getString("MainImagePath"));
            }
            return null;
        }
    }
    public static List<Service> selectAll() throws SQLException{
        List<Service> list = new ArrayList<>();
        try(Connection c = Link.getConnection()) {
            String sql = "SELECT * FROM service;";
            Statement s = c.createStatement();
            ResultSet keys = s.executeQuery(sql);
            while(keys.next()){
                list.add(new Service(keys.getInt("ID"),
                        keys.getString("Title"),
                        keys.getDouble("Cost"),
                        keys.getInt("DurationInSeconds"),
                        keys.getString("Description"),
                        keys.getDouble("Discount"),
                        keys.getString("MainImagePath")));
            }
            return list;
        }
    }
}
