package com.random.generator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Main {

    static CustomGrid grid;

    public static void main(String[] args) throws IOException {
        System.setProperty("sun.java2d.uiScale", "1.0");

        JFrame jframe = new JFrame("Generatore random IEEE1599");

        BufferedImage ieee1599Img = ImageIO.read(ClassLoader.getSystemResource("ieee1599.png"));
        ImageIcon ieee1599Icon = new ImageIcon(ieee1599Img);
        jframe.setIconImage(ieee1599Icon.getImage());

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        grid = new CustomGrid();

        grid.darkYellowTheme();

        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());

        jframe.add(grid.mainPanel);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setSize(1100, 750);
        jframe.setVisible(true);
    }
}
