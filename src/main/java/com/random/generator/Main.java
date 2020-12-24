package com.random.generator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Main {

    static CustomGrid grid;
    static JButton switchB;

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

        switchB = new JButton();
        switchB.setBorder(BorderFactory.createEmptyBorder(5,10,5,50));
        final int[] flagMode = {1}; // 1 for dark blue mode, 0 for dark yellow mode
        switchB.setContentAreaFilled(false);
        BufferedImage switchImg = ImageIO.read(ClassLoader.getSystemResource("exchange.png"));
        ImageIcon switchIcon = new ImageIcon(switchImg);
        switchB.setIcon(switchIcon);
        switchB.setToolTipText("Cambia tema");

        switchB.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                switchB.setContentAreaFilled(false);
                try {
                    BufferedImage switchImg = ImageIO.read(ClassLoader.getSystemResource("dark_exchange.png"));
                    ImageIcon switchIcon = new ImageIcon(switchImg);
                    switchB.setIcon(switchIcon);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                switchB.setContentAreaFilled(false);
                try {
                    BufferedImage switchImg = ImageIO.read(ClassLoader.getSystemResource("exchange.png"));
                    ImageIcon switchIcon = new ImageIcon(switchImg);
                    switchB.setIcon(switchIcon);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        grid.toolbar1.add(switchB);

        switchB.setBorder(BorderFactory.createEmptyBorder());


        darkYellowTheme(grid);

        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());

        jframe.add(grid.mainPanel);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setSize(1100, 730);
        jframe.setVisible(true);


        switchB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(flagMode[0] == 1) {
                    flagMode[0] = 0;
                    darkBlueTheme(grid);
                }
                else{
                    flagMode[0] = 1;
                    darkYellowTheme(grid);
                }
            }
        });
    }

    static private void darkBlueTheme(CustomGrid g){
        switchB.setToolTipText("Tema chiaro");

        Color background = new Color(33, 37, 43);
        Color c = new Color(40, 44, 52);
        g.mainPanel.setBackground(background);
        g.labelDestinazione.setForeground(Color.WHITE);
        g.setColorOfLabel(Color.WHITE);
        switchB.setForeground(Color.white);
        g.panelNote.setBackground(c);
        g.authorPanel.setBackground(background);
        g.labelAuthor.setForeground(Color.WHITE);
        g.panelNumeroStrumenti.setBackground(c);
        g.panelNotePause.setBackground(c);
        g.panelLunghezzaBrano.setBackground(c);
        g.panelAltezza.setBackground(c);
        g.panelDurata.setBackground(c);
        g.parentA.setBackground(c);
        g.parentAltezzaMassima.setBackground(c);
        g.parentAltezzaMinima.setBackground(c);
        g.parentB.setBackground(c);
        g.parentC.setBackground(c);
        g.parentD.setBackground(c);
        g.parentDurataMassima.setBackground(c);
        g.parentDurataMinima.setBackground(c);
        g.parentE.setBackground(c);
        g.parentEntrambe.setBackground(c);
        g.parentF.setBackground(c);
        g.parentG.setBackground(c);
        g.parentLunghezzaBrano.setBackground(c);
        g.parentNumeroStrumenti.setBackground(c);
        g.parentSoloNote.setBackground(c);
        g.parentSoloPause.setBackground(c);
        g.panelSalvataggio.setBackground(background);
        g.panelButton.setBackground(background);

        g.textField4.setBackground(background);
        g.textField4.setForeground(Color.white);
        g.textField4.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));

        g.cartellaDiDestinazioneButton.setBackground(new Color(40, 44, 52));
        g.generaIEEE1599Button.setBackground(new Color(40, 44, 52));
        g.cartellaDiDestinazioneButton.setForeground(Color.WHITE);
        g.generaIEEE1599Button.setForeground(Color.WHITE);

        /*g.spinner8.getEditor().getComponent(0).setForeground(background);
        g.spinner9.getEditor().getComponent(0).setForeground(background);

        g.spinnerLunghezzaBrano.getEditor().getComponent(0).setForeground(background);
        g.spinnerNumeroStrumenti.getEditor().getComponent(0).setForeground(background);

        g.comboBox1.getEditor().getEditorComponent().setForeground(background);
        g.comboBox2.getEditor().getEditorComponent().setForeground(background);*/
    }


    static private void darkYellowTheme(CustomGrid g){
        switchB.setToolTipText("Tema scuro");
        g.mainPanel.setBackground(Color.WHITE);
        Color c = new Color(206, 124, 4); // 248, 148, 7
        //UIManager.put( "text", Color.BLACK);
        switchB.setForeground(Color.BLACK);
        g.labelDestinazione.setForeground(Color.BLACK);
        g.setColorOfLabel(Color.BLACK);
        g.panelNote.setBackground(c);
        g.authorPanel.setBackground(Color.WHITE);
        g.labelAuthor.setForeground(Color.BLACK);
        g.panelNumeroStrumenti.setBackground(c);
        g.panelNotePause.setBackground(c);
        g.panelLunghezzaBrano.setBackground(c);
        g.panelAltezza.setBackground(c);
        g.panelDurata.setBackground(c);
        g.parentA.setBackground(c);
        g.parentAltezzaMassima.setBackground(c);
        g.parentAltezzaMinima.setBackground(c);
        g.parentB.setBackground(c);
        g.parentC.setBackground(c);
        g.parentD.setBackground(c);
        g.parentDurataMassima.setBackground(c);
        g.parentDurataMinima.setBackground(c);
        g.parentE.setBackground(c);
        g.parentEntrambe.setBackground(c);
        g.parentF.setBackground(c);
        g.parentG.setBackground(c);
        g.parentLunghezzaBrano.setBackground(c);
        g.parentNumeroStrumenti.setBackground(c);
        g.parentSoloNote.setBackground(c);
        g.parentSoloPause.setBackground(c);
        g.panelSalvataggio.setBackground(Color.white);
        g.panelButton.setBackground(Color.white);
        g.textField4.setBackground(Color.white);
        g.textField4.setForeground(Color.black);
        g.textField4.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        g.cartellaDiDestinazioneButton.setBackground(Color.LIGHT_GRAY);
        g.generaIEEE1599Button.setBackground(Color.LIGHT_GRAY);
        g.cartellaDiDestinazioneButton.setForeground(Color.BLACK);
        g.generaIEEE1599Button.setForeground(Color.BLACK);
    }

}
