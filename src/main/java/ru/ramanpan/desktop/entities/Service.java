package ru.ramanpan.desktop.entities;

import lombok.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

@Data
public class Service {
    private int id;
    private String title;
    private double cost;
    private int durationInSeconds;
    private String description;
    private double discount;
    private String mainImagePath;

    private ImageIcon image;
    public Service(int id, String title, double cost, int durationInSeconds, String description, double discount, String mainImagePath) {
        this.id = id;
        this.title = title;
        this.cost = cost;
        this.durationInSeconds = durationInSeconds;
        this.description = description;
        this.discount = discount;
        this.mainImagePath = mainImagePath;
        try {
            this.image = new ImageIcon(ImageIO.read(Service.class.getClassLoader().getResource(mainImagePath)).
                    getScaledInstance(50,50, Image.SCALE_DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Service(String title, double cost, int durationInSeconds, String description, double discount, String mainImagePath) {
        this.id = -1;
        this.title = title;
        this.cost = cost;
        this.discount = discount;
        this.durationInSeconds = durationInSeconds;
        this.description = description;
        this.mainImagePath = mainImagePath;
    }
}
