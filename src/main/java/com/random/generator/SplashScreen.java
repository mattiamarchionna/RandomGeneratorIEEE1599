package com.random.generator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SplashScreen {
    public JPanel Splash;
    private JPanel imageBackground;


    public SplashScreen() throws IOException {
        BufferedImage backgroundImg = ImageIO.read(ClassLoader.getSystemResource("backgroundIEEE1599.png"));
        JLabel picLabel = new JLabel(new ImageIcon(backgroundImg));
        imageBackground.add(picLabel);
    }


}
