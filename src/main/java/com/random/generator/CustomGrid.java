package com.random.generator;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import java.util.TreeMap;

public class CustomGrid {
    public JPanel mainPanel;
    public JButton generaIEEE1599Button; public JButton cartellaDiDestinazioneButton;
    public JTextField textField4;
    private JCheckBox onlyNoteCheckBox; private JCheckBox onlyRestCheckBox; private JCheckBox bothRestNoteCheckBox;
    public JSpinner spinner1; public JSpinner spinner2; public JSpinner spinner3;
    public JSpinner spinner4; public JSpinner spinner5; public JSpinner spinner6; public JSpinner spinner7;
    public JComboBox comboBox1; public JComboBox comboBox2;
    public JToolBar toolbar1;
    public Parameter configuration = new Parameter();
    private JButton saveButton;
    public JSpinner spinnerLunghezzaBrano; public JSpinner spinnerNumeroStrumenti; public JSpinner spinner8; public JSpinner spinner9;
    public JPanel panelLunghezzaBrano; public JPanel panelNumeroStrumenti; public JPanel panelNote; public JPanel panelAltezza;
    public JPanel panelNotePause; public JPanel panelSalvataggio; public JPanel panelDurata;
    private JLabel totalPercentage;
    public JPanel parentLunghezzaBrano;
    public JPanel parentNumeroStrumenti;
    public JPanel parentC;
    public JPanel parentD;
    public JPanel parentE;
    public JPanel parentF;
    public JPanel parentG;
    public JPanel parentA;
    public JPanel parentB;
    public JPanel parentDurataMinima;
    public JPanel parentDurataMassima;
    public JPanel parentAltezzaMinima;
    public JPanel parentAltezzaMassima;
    public JPanel parentSoloNote;
    public JPanel parentSoloPause;
    public JPanel parentEntrambe;
    public JPanel panelButton;
    public JLabel label1;
    public JLabel labelDestinazione;
    public JPanel authorPanel;
    public JLabel labelAuthor;
    public JLabel label2;
    public JLabel label3;
    public JLabel label4;
    public JLabel label5;
    public JLabel label6;
    public JLabel label7;
    public JLabel label8;
    public JLabel label9;
    public JLabel label10;
    public JLabel label11;
    public JLabel label12;
    public JLabel label13;
    public JButton buttonOpen;
    private Random r = new Random();


    private void setClientProperty(){
        mainPanel.putClientProperty("spinner1", spinner1); mainPanel.putClientProperty("spinner2", spinner2); mainPanel.putClientProperty("spinner3", spinner3);
        mainPanel.putClientProperty("spinner4", spinner4); mainPanel.putClientProperty("spinner5", spinner5); mainPanel.putClientProperty("spinner6", spinner6); mainPanel.putClientProperty("spinner7", spinner7);
        mainPanel.putClientProperty("spinner8", spinner8); mainPanel.putClientProperty("spinner9", spinner9);

        mainPanel.putClientProperty("comboBox1", comboBox1); mainPanel.putClientProperty("comboBox2", comboBox2);
        mainPanel.putClientProperty("spinnerLunghezzaBrano", spinnerLunghezzaBrano);
        mainPanel.putClientProperty("spinnerNumeroStrumenti", spinnerNumeroStrumenti);

        mainPanel.putClientProperty("onlyNoteCheckBox", onlyNoteCheckBox);
        mainPanel.putClientProperty("onlyRestCheckBox", onlyRestCheckBox);
        mainPanel.putClientProperty("bothRestNoteCheckBox", bothRestNoteCheckBox);
    }

    public CustomGrid() throws IOException {

    /*  mainPanel.setBackground(new java.awt.Color(51, 51, 51));
        panelAltezza.setBackground(new java.awt.Color(51, 51, 51));
        panelDurata.setBackground(new java.awt.Color(51, 51, 51));
        panelLunghezzaBrano.setBackground(new java.awt.Color(51, 51, 51));
        panelNote.setBackground(new java.awt.Color(51, 51, 51));
        panelNotePause.setBackground(new java.awt.Color(51, 51, 51));
        panelSalvataggio.setBackground(new java.awt.Color(51, 51, 51));
        panelNumeroStrumenti.setBackground(new java.awt.Color(51, 51, 51)); */


        BufferedImage playImg = ImageIO.read(ClassLoader.getSystemResource("play.png"));
        ImageIcon playIcon = new ImageIcon(playImg);
        generaIEEE1599Button.setIcon(playIcon);

        // parametro strumenti = numero eventi
        toolbar1.setFloatable(false);
        toolbar1.setMargin(new Insets(10, 10, 5, 0));
        textField4.setBackground(new Color(40, 44, 52));
        //toolbar1.addSeparator(new Dimension(10, 10));
        JButton save = new JButton();

        BufferedImage saveImg = ImageIO.read(ClassLoader.getSystemResource("disk.png"));
        ImageIcon saveIcon = new ImageIcon(saveImg);
        save.setIcon(saveIcon);

        save.setToolTipText("Salva configurazione");
        toolbar1.add(save);
        toolbar1.addSeparator(new Dimension(15, 10));

        JButton open = new JButton();
        open.setToolTipText("Apri configurazione");

        BufferedImage openImg = ImageIO.read(ClassLoader.getSystemResource("open.png"));
        ImageIcon openIcon = new ImageIcon(openImg);
        open.setIcon(openIcon);
        toolbar1.add(open);
        toolbar1.addSeparator(new Dimension(15, 10));


        open.setBorder(BorderFactory.createEmptyBorder());
        save.setBorder(BorderFactory.createEmptyBorder());
        setClientProperty();

        spinner1.setValue(r.nextInt(20));
        spinner2.setValue(r.nextInt(20));
        spinner3.setValue(r.nextInt(20));
        spinner4.setValue(r.nextInt(20));
        spinner5.setValue(r.nextInt(20));
        spinner6.setValue(r.nextInt(20));
        spinner7.setValue(r.nextInt(20));

       // authorPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));

        totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");

        textField4.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        textField4.setEditable(false);
        //textField4.setOpaque(false);

        for (int i = 1; i < 65; i++) {
            for (int j = 1; j < 65; j++) {
                String s = i + "/" + j;
                comboBox1.addItem(s);
                comboBox2.addItem(s);
                //comboBox3.addItem(i); comboBox4.addItem(i);
            }
        }

        //comboBox1.getEditor().getEditorComponent().setForeground(Color.black);
        //comboBox2.getEditor().getEditorComponent().setForeground(Color.black);


        cartellaDiDestinazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fs = new JFileChooser(new File(System.getProperty("user.home")));
                UIManager.put("text", Color.BLACK);

                fs.setFileFilter(new FolderFilter());
                fs.setFileSelectionMode(JFileChooser.APPROVE_OPTION);
                fs.setDialogTitle("Seleziona cartella destinazione");
                fs.showSaveDialog(mainPanel);
                // fs.showOpenDialog(mainPanel);
                File fi = fs.getSelectedFile();
                //textField4.setBackground(Color.lightGray);
                if (fi != null) {
                    textField4.setText(fi.getPath());
                    //textField4.setOpaque(true);
                    System.out.println(fi.getPath());
                }
            }
        });

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fs = new JFileChooser(new File(System.getProperty("user.home")));
                //fs.setFileFilter(new FolderFilter());
                UIManager.put("text", Color.BLACK);

                fs.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fs.setDialogTitle("Seleziona file di configurazione");
                fs.showOpenDialog(mainPanel);
                File fi = fs.getSelectedFile();
                //textField4.setBackground(Color.lightGray);
                if (fi != null) {
                    setParametersFromFileConfiguration(fi.getPath());
                    //textField4.setText(fi.getPath());
                    //textField4.setOpaque(true);
                    //System.out.println(fi.getPath());
                }
            }
        });


        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame parentFrame = new JFrame();

                UIManager.put("text", Color.BLACK);

                JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.home")));
                fileChooser.setDialogTitle("Specifica dove salvare il file di configurazione");

                int userSelection = fileChooser.showSaveDialog(parentFrame);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();

                    File params = new File(fileToSave.getAbsolutePath());
                    FileWriter myWriter;
                    try {
                        myWriter = new FileWriter(fileToSave.getAbsolutePath());

                        String p1 = "spinner1:" + spinner1.getValue();
                        String p2 = "spinner2:" + spinner2.getValue();
                        String p3 = "spinner3:" + spinner3.getValue();
                        String p4 = "spinner4:" + spinner4.getValue();
                        String p5 = "spinner5:" + spinner5.getValue();
                        String p6 = "spinner6:" + spinner6.getValue();
                        String p7 = "spinner7:" + spinner7.getValue();
                        String p8 = "spinnerLunghezzaBrano:" + spinnerLunghezzaBrano.getValue() + "\n";
                        String p9 = "spinnerNumeroStrumenti:" + spinnerNumeroStrumenti.getValue() + "\n";
                        String p10 = "comboBox1:" + comboBox1.getSelectedItem() + "\n";
                        String p11 = "comboBox2:" + comboBox2.getSelectedItem() + "\n";
                        String p12 = "spinner8:" + spinner8.getValue() + "\n";
                        String p13 = "spinner9:" + spinner9.getValue() + "\n";
                        String p14 = "onlyNoteCheckBox:" + onlyNoteCheckBox.isSelected() + "\n";
                        String p15 = "onlyRestCheckBox:" + onlyRestCheckBox.isSelected() + "\n";
                        String p16 = "bothRestNoteCheckBox:" + bothRestNoteCheckBox.isSelected();

                        String[] ps = {p1 + "\n", p2 + "\n", p3 + "\n", p4 + "\n", p5 + "\n", p6 + "\n", p7 + "\n", p8, p9, p10, p11, p12, p13, p14, p15, p16};

                        for (String p : ps) {
                            myWriter.write(p);
                        }
                        myWriter.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }


                    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                    JOptionPane.showMessageDialog(mainPanel, "Configurazione salvata correttamente!");
                }
            }
        });


        onlyNoteCheckBox.addActionListener(e -> {
            onlyRestCheckBox.setSelected(false);
            bothRestNoteCheckBox.setSelected(false);
        });

        onlyRestCheckBox.addActionListener(e -> {
            onlyNoteCheckBox.setSelected(false);
            bothRestNoteCheckBox.setSelected(false);
        });

        bothRestNoteCheckBox.addActionListener(e -> {
            onlyRestCheckBox.setSelected(false);
            onlyNoteCheckBox.setSelected(false);
        });


        spinner1.addChangeListener(e -> {
            if(getSumOfPitchSpinners() > 100){
                spinner7.setValue((int) spinner7.getValue() - 1);
            }
            totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");
        });

        spinner2.addChangeListener(e -> {
            if(getSumOfPitchSpinners() > 100){
                spinner1.setValue((int) spinner1.getValue() - 1);
            }
            totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");
        });

        spinner3.addChangeListener(e -> {
            if(getSumOfPitchSpinners() > 100){
                spinner2.setValue((int) spinner2.getValue() - 1);
            }
            totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");
        });

        spinner4.addChangeListener(e -> {
            if(getSumOfPitchSpinners() > 100){
                spinner3.setValue((int) spinner3.getValue() - 1);
            }
            totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");
        });

        spinner5.addChangeListener(e -> {
            if(getSumOfPitchSpinners() > 100){
                spinner4.setValue((int) spinner4.getValue() - 1);
            }
            totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");
        });

        spinner6.addChangeListener(e -> {
            if(getSumOfPitchSpinners() > 100){
                spinner5.setValue((int) spinner5.getValue() - 1);
            }
            totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");
        });

        spinner7.addChangeListener(e -> {
            if(getSumOfPitchSpinners() > 100){
                spinner6.setValue((int) spinner6.getValue() - 1);
            }
            totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");
        });

        generaIEEE1599Button.addActionListener(e -> {
            if (checkValidityOfInput()) {

                configuration.setLenght((int) spinnerLunghezzaBrano.getValue());
                configuration.setNumber_struments((int) spinnerNumeroStrumenti.getValue());

                TreeMap<String, Integer> notes_freq = new TreeMap<>();
                notes_freq.put("C", (int) spinner1.getValue());
                notes_freq.put("D", (int) spinner2.getValue());
                notes_freq.put("E", (int) spinner3.getValue());
                notes_freq.put("F", (int) spinner4.getValue());
                notes_freq.put("G", (int) spinner5.getValue());
                notes_freq.put("A", (int) spinner6.getValue());
                notes_freq.put("B", (int) spinner7.getValue());
                configuration.setNotes(notes_freq);

                configuration.setMin_duration(String.valueOf(comboBox1.getSelectedItem()));
                configuration.setMax_duration(String.valueOf(comboBox2.getSelectedItem()));
                configuration.setMin_height((int) spinner8.getValue());
                configuration.setMax_height((int) spinner9.getValue());

                configuration.setPath(textField4.getText());

                configuration.setOnlyRest(onlyRestCheckBox.isSelected());
                configuration.setOnlyNote(onlyNoteCheckBox.isSelected());
                configuration.setBothNoteRest(bothRestNoteCheckBox.isSelected());

                String path = configuration.getPath(); // e.g: "C:\\Users\\matti\\Desktop\\"
                IEEE1599Generator g1 = new IEEE1599Generator(path, "", configuration);
                g1.generate_file();

                JOptionPane.showMessageDialog(mainPanel, "File IEEE1599 generato con successo!");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Configurare correttamente i parametri!");
            }
        });
    }


    private boolean checkValidityOfInput(){
        try {
            return getSumOfPitchSpinners() == 100 && ((int) spinnerLunghezzaBrano.getValue() > 0) && ((int) spinnerNumeroStrumenti.getValue() > 0) &&
                    ((int) spinner8.getValue() > 0) && ((int) spinner9.getValue() > (int) spinner8.getValue()) &&
                    (onlyNoteCheckBox.isSelected() || onlyRestCheckBox.isSelected() || bothRestNoteCheckBox.isSelected()) &&
                    (comboBox1.getSelectedIndex() <= comboBox2.getSelectedIndex()) && (!textField4.getText().equals(""));
        }
        catch(Exception ex){
            return false;
        }
    }

    private void setParametersFromFileConfiguration(String pathname){
        try {
            //System.out.println("Reading lines...");
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(pathname));
                String line = reader.readLine();
                while (line != null) {
                    String[] id_value = line.split(":");
                    setValueFromConfig(id_value[0], id_value[1]);
                    //System.out.println(id_value[0] + " --- " + id_value[1]);
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                //System.out.println("File di configurazione non corretto!")
                JOptionPane.showMessageDialog(mainPanel, "File di configurazione non corretto!");
                //e.printStackTrace();
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(mainPanel, "File di configurazione non corretto!");
        }
    }

    private void setValueFromConfig(String id, String value) {
        try {
            //value = value.replace("\n", "");
            String type = mainPanel.getClientProperty(id).getClass().getName();
            Object component = mainPanel.getClientProperty(id);
            if (type.equals("javax.swing.JSpinner")) {
                ((JSpinner) component).setValue(Integer.valueOf(value));
            } else if (type.equals("javax.swing.JComboBox")) {
                ((JComboBox) component).setSelectedItem(value);
            } else if (type.equals("javax.swing.JCheckBox")) {
                if (value.equals("true")) ((JCheckBox) component).setSelected(true);
                else ((JCheckBox) component).setSelected(false);
            }
            //System.out.println(type);
        } catch(Exception e){
            JOptionPane.showMessageDialog(mainPanel, "File di configurazione non corretto!");
        }
    }

    private int getSumOfPitchSpinners(){
        return (int)(spinner1.getValue()) + (int)(spinner2.getValue()) + (int)(spinner3.getValue()) + (int)(spinner4.getValue()) +
        (int)(spinner5.getValue()) + (int)(spinner6.getValue()) + (int)(spinner7.getValue());
    }

    public void setColorOfLabel(Color c){
        label1.setForeground(c);
        label2.setForeground(c);
        label3.setForeground(c);
        label4.setForeground(c);
        label5.setForeground(c);
        label6.setForeground(c);
        label7.setForeground(c);
        label8.setForeground(c);
        label9.setForeground(c);
        label10.setForeground(c);
        label11.setForeground(c);
        label12.setForeground(c);
        label13.setForeground(c);
        onlyNoteCheckBox.setForeground(c);
        onlyRestCheckBox.setForeground(c);
        bothRestNoteCheckBox.setForeground(c);
        totalPercentage.setForeground(c);

        TitledBorder b = (TitledBorder) (panelDurata.getBorder());
        b.setTitleColor(c);

        TitledBorder b1 = (TitledBorder) (panelNumeroStrumenti.getBorder());
        b1.setTitleColor(c);

        TitledBorder b2 = (TitledBorder) (panelNote.getBorder());
        b2.setTitleColor(c);

        TitledBorder b3 = (TitledBorder) (panelAltezza.getBorder());
        b3.setTitleColor(c);

        TitledBorder b4 = (TitledBorder) (panelLunghezzaBrano.getBorder());
        b4.setTitleColor(c);

        TitledBorder b5 = (TitledBorder) (panelNotePause.getBorder());
        b5.setTitleColor(c);
    }
}
