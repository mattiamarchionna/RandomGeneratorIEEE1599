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



        JFrame jframe = new JFrame("Generatore random file IEEE1599");

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
        //jframe.setSize(1100, 765);
        jframe.setSize(1100, 785);

        //System.out.println(dim.width);
        //System.out.println(dim.height);

        //jframe.setSize((int)(dim.width/1.75), (int)(dim.height/1.38));

        jframe.setLocation(dim.width/2-jframe.getSize().width/2, dim.height/2-jframe.getSize().height/2);

        try {
            Thread.sleep(3000);
            splashFrame.setVisible(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        jframe.setVisible(true);
    }
}
