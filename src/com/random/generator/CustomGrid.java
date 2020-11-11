package com.random.generator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class CustomGrid {
    public JPanel mainPanel;
    private JButton generaIEEE1599Button;
    private JButton cartellaDiDestinazioneButton;
    private JTextArea textArea4;
    private JCheckBox soloNoteCheckBox;
    private JCheckBox soloPauseCheckBox;
    private JCheckBox entrambeCheckBox;
    private JSpinner spinner1;

    public CustomGrid() {
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
