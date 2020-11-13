package com.random.generator;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        JFrame jframe = new JFrame("Generatore IEEE1599");
        CustomGrid grid = new CustomGrid();
        jframe.setContentPane(grid.mainPanel);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setResizable(false);
        //jframe.setSize(1200, 750);
        jframe.setVisible(true);

        Parameter p = new Parameter();
        p.setLenght(2000);
        p.setMin_max_height(3, 8);


        // SHARP, FLAT, NATURAL, DOUBLESHARP, DOUBLEFLAT, UNDEFINED
        p.setAccidentals(new ArrayList<>(Arrays.asList("NATURAL", "DOUBLEFLAT", "FLAT", "SHARP")));

        p.setStruments(new ArrayList<>(Arrays.asList("Violino1", "Piano1", "Piano2", "Violino2")));


        TreeMap<String, Integer> notes_freq = new TreeMap<>();
        notes_freq.put("C", 10); notes_freq.put("D", 20); notes_freq.put("E", 5);
        notes_freq.put("F", 5); notes_freq.put("G", 40); notes_freq.put("A", 10);
        notes_freq.put("B", 20);
        p.setNotes(notes_freq);


        TreeMap<String, Integer> durations_freq = new TreeMap<>();
        for(int i = 1; i < 8; i++){
            for(int j = 1; j < 8; j++){
                durations_freq.put(i + "/" + j, new Random().nextInt(10));
            }
        }
        p.setDurations(durations_freq);



        String path = ""; // e.g: "C:\\Users\\matti\\Desktop\\"
        IEEE1599Generator g1 = new IEEE1599Generator(path, "example_IEEE1599.xml", p);
        g1.generate_file();


    }
}
