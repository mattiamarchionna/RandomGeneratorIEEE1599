package com.random.generator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {

        JFrame jframe = new JFrame("Generatore random IEEE1599");


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

        jframe.setContentPane(grid.mainPanel);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.pack();
        //jframe.setResizable(false);
        jframe.setSize(1000, 850);
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

}
