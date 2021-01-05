package com.random.generator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        System.setProperty("sun.java2d.uiScale", "1.0");

        CustomGrid grid;

        // SPLASH SCREEN //
        SplashScreen splash = new SplashScreen();

        JFrame splashFrame = new JFrame();
        splashFrame.setUndecorated(true);
        splashFrame.add(splash.Splash);
        splashFrame.setVisible(true);
        splashFrame.setSize(600, 400);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        splashFrame.setLocation(dim.width/2-splashFrame.getSize().width/2, dim.height/2-splashFrame.getSize().height/2);
        splashFrame.setResizable(false);

        // MAIN FRAME
        JFrame mainFrame = new JFrame("Generatore random file IEEE1599");

        BufferedImage ieee1599Img = ImageIO.read(ClassLoader.getSystemResource("ieee1599.png"));
        ImageIcon ieee1599Icon = new ImageIcon(ieee1599Img);
        mainFrame.setIconImage(ieee1599Icon.getImage());

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

        mainFrame.add(grid.mainPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setSize(1110, 785);

        //mainFrame.setSize((int)(dim.width/1.75), (int)(dim.height/1.38));

        mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);

        try {
            Thread.sleep(3000);
            splashFrame.setVisible(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mainFrame.setVisible(true);
    }
}
