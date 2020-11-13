package com.random.generator;

import javafx.scene.control.Separator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CustomGrid {
    public JPanel mainPanel;
    private JButton generaIEEE1599Button;
    private JButton cartellaDiDestinazioneButton;
    private JTextField textField4;
    private JCheckBox soloNoteCheckBox;
    private JCheckBox soloPauseCheckBox;
    private JCheckBox entrambeCheckBox;
    private JSpinner spinner1; private JSpinner spinner2; private JSpinner spinner3;
    private JSpinner spinner4; private JSpinner spinner5; private JSpinner spinner6; private JSpinner spinner7;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JToolBar toolbar1;
    private JButton saveButton;
    private JSpinner spinnerLunghezzaBrano;
    private JSpinner spinnerNumeroStrumenti;
    private JButton buttonOpen;
    private Random r = new Random();

    public CustomGrid() throws IOException {
        // parametro strumenti = numero eventi
        toolbar1.setFloatable(false);
        toolbar1.setMargin(new Insets(10, 10, 5, 0));
        textField4.setBackground(new Color(214, 217, 223));
        //toolbar1.addSeparator(new Dimension(10, 10));
        JButton save = new JButton();
        save.setIcon(new ImageIcon("C:\\Users\\matti\\Desktop\\RandomGeneratorIEEE1599\\src\\com\\random\\generator\\Save-icon.png"));
        save.setToolTipText("Salva configurazione");
        toolbar1.add(save);
        toolbar1.addSeparator(new Dimension(15, 10));

        JButton open = new JButton();
        open.setToolTipText("Apri configurazione");
        open.setIcon(new ImageIcon("C:\\Users\\matti\\Desktop\\RandomGeneratorIEEE1599\\src\\com\\random\\generator\\open.png"));
        toolbar1.add(open);

        open.setBorder(BorderFactory.createEmptyBorder());
        save.setBorder(BorderFactory.createEmptyBorder());

        spinner1.setValue(r.nextInt(20)); spinner2.setValue(r.nextInt(20)); spinner3.setValue(r.nextInt(20));
        spinner4.setValue(r.nextInt(20)); spinner5.setValue(r.nextInt(20)); spinner6.setValue(r.nextInt(20));
        spinner7.setValue(r.nextInt(20));
        textField4.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        textField4.setEditable(false);
        //textField4.setOpaque(false);
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                String s = i + "/" + j;
                comboBox1.addItem(s); comboBox2.addItem(s);
                comboBox3.addItem(s); comboBox4.addItem(s);
            }
        }



        cartellaDiDestinazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    JFileChooser fs = new JFileChooser(new File("C:\\"));
                    fs.setFileFilter( new FolderFilter() );
                    fs.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    fs.setDialogTitle("Seleziona cartella destinazione");
                    fs.showOpenDialog(mainPanel);
                    File fi = fs.getSelectedFile();
                    //textField4.setBackground(Color.lightGray);
                    if(fi != null) {
                        textField4.setText(fi.getPath());
                        //textField4.setOpaque(true);
                        System.out.println(fi.getPath());
                    }
            }
        });


        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame parentFrame = new JFrame();

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specifica dove salvare il file di configurazione");

                int userSelection = fileChooser.showSaveDialog(parentFrame);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();

                    File params = new File(fileToSave.getAbsolutePath());
                    FileWriter myWriter;
                    try {
                        myWriter = new FileWriter(fileToSave.getAbsolutePath());

                        String p1 = "spinner1:"+spinner1.getValue(); String p2 = "spinner2:"+spinner2.getValue();
                        String p3 = "spinner3:"+spinner3.getValue(); String p4 = "spinner4:"+spinner4.getValue();
                        String p5 = "spinner5:"+spinner5.getValue(); String p6 = "spinner6:"+spinner6.getValue();
                        String p7 = "spinner7:"+spinner7.getValue();
                        String p8 = "spinnerLunghezzaBrano:"+spinnerLunghezzaBrano.getValue()+"\n";
                        String p9 = "spinnerNumeroStrumenti:"+spinnerNumeroStrumenti.getValue()+"\n";
                        String p10 = "comboBox1:"+ comboBox1.getSelectedItem()+"\n";
                        String p11 = "comboBox2:"+ comboBox2.getSelectedItem()+"\n";
                        String p12 = "comboBox3:"+ comboBox3.getSelectedItem()+"\n";
                        String p13 = "comboBox4:"+ comboBox4.getSelectedItem()+"\n";
                        String p14 = "soloNoteCheckBox:"+soloNoteCheckBox.isSelected()+"\n";
                        String p15 = "soloPauseCheckBox:"+soloPauseCheckBox.isSelected()+"\n";
                        String p16 = "entrambeCheckBox:"+entrambeCheckBox.isSelected();


                        String[] ps = {p1+"\n", p2+"\n", p3+"\n", p4+"\n", p5+"\n", p6+"\n", p7+"\n", p8, p9, p10, p11, p12, p13, p14, p15, p16};

                        for(String p : ps) {
                            myWriter.write(p);
                        }
                        myWriter.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }


                    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                }
            }
        });
    }


}
