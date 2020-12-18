package com.random.generator;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        System.setProperty("sun.java2d.uiScale", "1.0");


        JFrame jframe = new JFrame("Generatore random IEEE1599");

        BufferedImage ieee1599Img = ImageIO.read(ClassLoader.getSystemResource("ieee1599.png"));
        ImageIcon ieee1599Icon = new ImageIcon(ieee1599Img);
        jframe.setIconImage(ieee1599Icon.getImage());

        /*try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("This look isn't available");
        }*/


        //darkBlueTheme();


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

        CustomGrid grid = new CustomGrid();


        darkYellowTheme(grid);

        //darkBlueTheme(grid);

        jframe.setContentPane(grid.mainPanel);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //jframe.setResizable(false);
        jframe.setSize(1000, 750);
        jframe.setVisible(true);

        //Parameter p = grid.configuration;

        /*p.setOnlyRest(true);
        p.setOnlyNote(false);
        p.setBothNoteRest(false);

        // SHARP, FLAT, NATURAL, DOUBLESHARP, DOUBLEFLAT, UNDEFINED
        p.setAccidentals(new ArrayList<>(Arrays.asList("NATURAL", "DOUBLEFLAT", "FLAT", "SHARP")));

        p.setNumber_struments(100);
        p.setLenght(500);
        p.setMin_duration("1/8"); p.setMax_duration("1/2");
        p.setMin_height(1); p.setMax_height(8);

        TreeMap<String, Integer> notes_freq = new TreeMap<>();
        notes_freq.put("C", 10); notes_freq.put("D", 20); notes_freq.put("E", 5);
        notes_freq.put("F", 5); notes_freq.put("G", 40); notes_freq.put("A", 10);
        notes_freq.put("B", 20);
        p.setNotes(notes_freq);*/
    }

    static private void darkBlueTheme(CustomGrid g){
        UIManager.put( "control", new Color(40, 44, 52)); // 61 61 61
        UIManager.put( "info", new Color(65,65,65) );
        UIManager.put( "nimbusBase", new Color( 33, 37, 43) );

        //UIManager.put( "nimbusAlertYellow", new Color( 248, 187, 0) );
        //UIManager.put( "nimbusDisabledText", new Color( 128, 128, 128) );
        //UIManager.put( "nimbusFocus", new Color(115,164,209) );
        //UIManager.put( "nimbusGreen", new Color(176,179,50) );
        //UIManager.put( "nimbusInfoBlue", new Color( 66, 139, 221) );
        UIManager.put( "nimbusLightBackground", new Color( 33, 37, 43) ); // ok
        //UIManager.put( "nimbusOrange", new Color(191,98,4) );
        //UIManager.put( "nimbusRed", new Color(169,46,34) );
        //UIManager.put( "nimbusSelectedText", new Color( 255, 255, 255) ); // ok

        UIManager.put( "nimbusSelectionBackground", new Color( 66, 139, 221) ); // ok
        UIManager.put( "text", new Color( 230, 230, 230) );

        Color c = new Color(33, 37, 43);

        g.panelNote.setBackground(c);
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
        g.textField4.setBackground(new Color(40, 44, 52));
        g.textField4.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));

        g.comboBox1.setBackground(new Color(25, 27, 32));
        g.comboBox1.setForeground(Color.BLACK);
        g.comboBox2.setBackground(new Color(25, 27, 32));
        g.comboBox2.setForeground(Color.BLACK);

        g.cartellaDiDestinazioneButton.setBackground(new Color(40, 44, 52));
        g.generaIEEE1599Button.setBackground(new Color(40, 44, 52));
        g.cartellaDiDestinazioneButton.setForeground(Color.WHITE);
        g.generaIEEE1599Button.setForeground(Color.WHITE);
    }

    static private void darkYellowTheme(CustomGrid g){
        Color c = new Color(248, 148, 7);
        UIManager.put( "control", Color.WHITE); // 61 61 61
        UIManager.put( "info", Color.WHITE );
        UIManager.put( "nimbusBase", new Color(128, 128, 128));

        //UIManager.put( "nimbusAlertYellow", new Color( 248, 187, 0) );
        //UIManager.put( "nimbusDisabledText", new Color( 128, 128, 128) );
        //UIManager.put( "nimbusFocus", new Color(115,164,209) );
        //UIManager.put( "nimbusGreen", new Color(176,179,50) );
        //UIManager.put( "nimbusInfoBlue", new Color( 66, 139, 221) );
        UIManager.put( "nimbusLightBackground", Color.WHITE ); // ok
        //UIManager.put( "nimbusOrange", new Color(191,98,4) );
        //UIManager.put( "nimbusRed", new Color(169,46,34) );
        //UIManager.put( "nimbusSelectedText", new Color( 255, 255, 255) ); // ok

        UIManager.put( "nimbusSelectionBackground", new Color( 66, 139, 221) ); // ok
        UIManager.put( "text", Color.BLACK );

        g.panelNote.setBackground(c);
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
        g.textField4.setBackground(Color.white);
        g.textField4.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        g.comboBox1.setBackground(Color.LIGHT_GRAY);
        g.comboBox1.setForeground(Color.white);
        g.comboBox2.setBackground(Color.LIGHT_GRAY);
        g.comboBox2.setForeground(Color.white);

        g.cartellaDiDestinazioneButton.setBackground(Color.LIGHT_GRAY);
        g.generaIEEE1599Button.setBackground(Color.LIGHT_GRAY);

        g.cartellaDiDestinazioneButton.setForeground(Color.BLACK);
        g.generaIEEE1599Button.setForeground(Color.BLACK);

    }

}
