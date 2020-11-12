package com.random.generator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

public class CustomGrid {
    public JPanel mainPanel;
    private JButton generaIEEE1599Button;
    private JButton cartellaDiDestinazioneButton;
    private JTextArea textArea4;
    private JCheckBox soloNoteCheckBox;
    private JCheckBox soloPauseCheckBox;
    private JCheckBox entrambeCheckBox;
    private JSpinner spinner1; private JSpinner spinner2; private JSpinner spinner3;
    private JSpinner spinner4; private JSpinner spinner5; private JSpinner spinner6; private JSpinner spinner7;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private Random r = new Random();

    public CustomGrid() {

        spinner1.setValue(r.nextInt(20)); spinner2.setValue(r.nextInt(20)); spinner3.setValue(r.nextInt(20));
        spinner4.setValue(r.nextInt(20)); spinner5.setValue(r.nextInt(20)); spinner6.setValue(r.nextInt(20));
        spinner7.setValue(r.nextInt(20));



        cartellaDiDestinazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    JFileChooser fs = new JFileChooser(new File("C:\\"));
                    fs.setDialogTitle("Selezione cartella destinazione");
                    fs.showOpenDialog(mainPanel);
                    File fi = fs.getSelectedFile();
                    textArea4.setText(fi.getPath());
                    System.out.println(fi.getPath());
            }
        });
    }


}
