package com.random.generator;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

public class CustomGrid {
    public JPanel mainPanel;
    private JButton generaIEEE1599Button; private JButton cartellaDiDestinazioneButton;
    private JTextField textField4;
    private JCheckBox onlyNoteCheckBox; private JCheckBox onlyRestCheckBox; private JCheckBox bothRestNoteCheckBox;
    private JComboBox comboBox1; private JComboBox comboBox2;
    private JToolBar toolbar1;
    private Parameter configuration = new Parameter();

    private JSpinner spinnerLunghezzaBrano; private JSpinner spinnerNumeroStrumenti; private JSpinner spinner8; private JSpinner spinner9;

    private JPanel panelLunghezzaBrano; private JPanel panelNumeroStrumenti; private JPanel panelNote; private JPanel panelAltezza;
    private JPanel panelNotePause; private JPanel panelSalvataggio; private JPanel panelDurata;

    private JLabel totalPercentage;

    private JPanel parentLunghezzaBrano; private JPanel parentNumeroStrumenti;
    private JPanel parentC; private JPanel parentD; private JPanel parentE; private JPanel parentF;
    private JPanel parentG; private JPanel parentA; private JPanel parentB;
    private JPanel parentDurataMinima; private JPanel parentDurataMassima; private JPanel parentAltezzaMinima;
    private JPanel parentAltezzaMassima; private JPanel parentSoloNote; private JPanel parentSoloPause; private JPanel parentEntrambe;
    private JPanel panelButton; private JPanel authorPanel;

    private JLabel labelDestinazione;

    private JLabel labelAuthor;
    private JLabel label1; private JLabel label2; private JLabel label3; private JLabel label4;
    private JLabel label5; private JLabel label6; private JLabel label7; private JLabel label8;
    private JLabel label9; private JLabel label10; private JLabel label11; private JLabel label12; private JLabel label13;

    private JSlider slider1; private JSlider slider2; private JSlider slider3; private JSlider slider4;
    private JSlider slider5; private JSlider slider6; private JSlider slider7;
    private JLabel errorLunghezzaBrano;
    private JLabel errorNumeroStrumenti;
    private JLabel errorDurata;
    private JLabel errorNotePause;
    private JLabel errorAltezza;
    private JLabel errorDestinazione;
    private JPanel afterToolbarPanel;
    private JPanel afterNotePausePanel;

    private JButton switchB;
    private Random r = new Random();


    private void setClientProperty(){
        mainPanel.putClientProperty("slider1", slider1); mainPanel.putClientProperty("slider2", slider2); mainPanel.putClientProperty("slider3", slider3);

        mainPanel.putClientProperty("slider4", slider4); mainPanel.putClientProperty("slider5", slider5); mainPanel.putClientProperty("slider6", slider6); mainPanel.putClientProperty("slider7", slider7);
        mainPanel.putClientProperty("spinner8", spinner8); mainPanel.putClientProperty("spinner9", spinner9);

        mainPanel.putClientProperty("comboBox1", comboBox1); mainPanel.putClientProperty("comboBox2", comboBox2);
        mainPanel.putClientProperty("spinnerLunghezzaBrano", spinnerLunghezzaBrano);
        mainPanel.putClientProperty("spinnerNumeroStrumenti", spinnerNumeroStrumenti);

        mainPanel.putClientProperty("onlyNoteCheckBox", onlyNoteCheckBox);
        mainPanel.putClientProperty("onlyRestCheckBox", onlyRestCheckBox);
        mainPanel.putClientProperty("bothRestNoteCheckBox", bothRestNoteCheckBox);
    }

    public CustomGrid() throws IOException {
        BufferedImage playImg = ImageIO.read(ClassLoader.getSystemResource("play.png"));
        ImageIcon playIcon = new ImageIcon(playImg);
        generaIEEE1599Button.setIcon(playIcon);

        labelAuthor.setText("Corso \"Programmazione per la musica\" - a.a. 2020-2021 - Docente: Barate' Adriano");
        toolbar1.setFloatable(false);
        toolbar1.setMargin(new Insets(10, 10, 5, 0));
        textField4.setBackground(new Color(40, 44, 52));
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


        switchB = new JButton();
        switchB.setBorder(BorderFactory.createEmptyBorder(5,10,5,50));
        final int[] flagMode = {1}; // 1 for dark blue mode, 0 for dark yellow mode
        switchB.setContentAreaFilled(false);
        BufferedImage switchImg = ImageIO.read(ClassLoader.getSystemResource("dark_mode_on.png"));
        ImageIcon switchIcon = new ImageIcon(switchImg);
        switchB.setIcon(switchIcon);
        switchB.setToolTipText("Cambia tema");


        switchB.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                switchB.setContentAreaFilled(false);
                try {
                    if(flagMode[0] == 1) {
                        BufferedImage switchImg = ImageIO.read(ClassLoader.getSystemResource("dark_mode_off.png"));
                        ImageIcon switchIcon = new ImageIcon(switchImg);
                        switchB.setIcon(switchIcon);
                    }
                    else {
                        BufferedImage switchImg = ImageIO.read(ClassLoader.getSystemResource("dark_mode_on_white.png"));
                        ImageIcon switchIcon = new ImageIcon(switchImg);
                        switchB.setIcon(switchIcon);
                    }

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }


            @Override
            public void mouseExited(MouseEvent e) {
                switchB.setContentAreaFilled(false);
                try {
                    if(flagMode[0] == 1) {
                        BufferedImage switchImg = ImageIO.read(ClassLoader.getSystemResource("dark_mode_on.png"));
                        ImageIcon switchIcon = new ImageIcon(switchImg);
                        switchB.setIcon(switchIcon);
                    }
                    else{
                        BufferedImage switchImg = ImageIO.read(ClassLoader.getSystemResource("dark_mode_off_white.png"));
                        ImageIcon switchIcon = new ImageIcon(switchImg);
                        switchB.setIcon(switchIcon);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        toolbar1.add(switchB);

        switchB.addActionListener(e -> {
            if(flagMode[0] == 1) {
                flagMode[0] = 0;
                darkBlueTheme();
            }
            else{
                flagMode[0] = 1;
                darkYellowTheme();
            }
        });

        switchB.setBorder(BorderFactory.createEmptyBorder());
        open.setBorder(BorderFactory.createEmptyBorder());
        save.setBorder(BorderFactory.createEmptyBorder());
        setClientProperty();

        slider1.setValue(r.nextInt(20)); changeLabelValueSlider1(parentC, slider1);
        slider2.setValue(r.nextInt(20)); changeLabelValueSlider1(parentD, slider2);
        slider3.setValue(r.nextInt(20)); changeLabelValueSlider1(parentE, slider3);
        slider4.setValue(r.nextInt(20)); changeLabelValueSlider1(parentF, slider4);
        slider5.setValue(r.nextInt(20)); changeLabelValueSlider1(parentG, slider5);
        slider6.setValue(r.nextInt(20)); changeLabelValueSlider1(parentA, slider6);
        slider7.setValue(r.nextInt(20)); changeLabelValueSlider1(parentB, slider7);

        int width = 80;
        int height = 20;
        int widthParent = 120;
        int heightParent = 60;

        parentC.setPreferredSize(new Dimension(widthParent, heightParent));
        parentD.setPreferredSize(new Dimension(widthParent, heightParent));
        parentE.setPreferredSize(new Dimension(widthParent, heightParent));
        parentF.setPreferredSize(new Dimension(widthParent, heightParent));
        parentG.setPreferredSize(new Dimension(widthParent, heightParent));
        parentA.setPreferredSize(new Dimension(widthParent, heightParent));
        parentB.setPreferredSize(new Dimension(widthParent, heightParent));

        slider1.setPreferredSize(new Dimension(width, height));
        slider2.setPreferredSize(new Dimension(width, height));
        slider3.setPreferredSize(new Dimension(width, height));
        slider4.setPreferredSize(new Dimension(width, height));
        slider5.setPreferredSize(new Dimension(width, height));
        slider6.setPreferredSize(new Dimension(width, height));
        slider7.setPreferredSize(new Dimension(width, height));

        totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");

        textField4.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        textField4.setEditable(false);


        HashMap<String, Double> durationValue = new HashMap<>();

        for (int i = 1; i < 65; i++) {
            for (int j = 1; j < 65; j++) {
                String s = i + "/" + j;
                durationValue.put(s, ((double) i)/ ((double) j));
            }
        }

        durationValue = sortByValue(durationValue);

        for (String name: durationValue.keySet()){
            comboBox1.addItem(name);
            comboBox2.addItem(name);
        }


        cartellaDiDestinazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorDestinazione.setText(""); errorDestinazione.setIcon(null); textField4.setText("");
                if(flagMode[0] == 1) textField4.setForeground(Color.BLACK);
                else textField4.setForeground(Color.WHITE);
                JFileChooser fs = new JFileChooser(new File(System.getProperty("user.home")));
                UIManager.put("text", Color.BLACK);
                fs.setFileFilter(new FolderFilter());
                fs.setFileSelectionMode(JFileChooser.APPROVE_OPTION);
                fs.setDialogTitle("Seleziona cartella destinazione");
                fs.showSaveDialog(mainPanel);
                File fi = fs.getSelectedFile();
                if (fi != null) {
                    textField4.setText(fi.getPath());
                    System.out.println(fi.getPath());
                }
            }
        });


        open.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                open.setContentAreaFilled(false);
                try {
                    BufferedImage openImg = ImageIO.read(ClassLoader.getSystemResource("dark_open.png"));
                    ImageIcon openIcon = new ImageIcon(openImg);
                    open.setIcon(openIcon);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    BufferedImage openImg = ImageIO.read(ClassLoader.getSystemResource("open.png"));
                    ImageIcon openIcon = new ImageIcon(openImg);
                    open.setIcon(openIcon);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });


        open.addActionListener(e -> {
            JFileChooser fs = new JFileChooser(new File(System.getProperty("user.home")));
            UIManager.put("text", Color.BLACK);
            fs.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fs.setDialogTitle("Seleziona file di configurazione");
            fs.showOpenDialog(mainPanel);
            File fi = fs.getSelectedFile();
            if (fi != null) {
                setParametersFromFileConfiguration(fi.getPath());
            }
        });

       save.addMouseListener(new MouseListener() {
           @Override
           public void mouseClicked(MouseEvent e) {}

           @Override
           public void mousePressed(MouseEvent e) { }

           @Override
           public void mouseReleased(MouseEvent e) { }

           @Override
           public void mouseEntered(MouseEvent e) {
               try {
                   BufferedImage saveImg = ImageIO.read(ClassLoader.getSystemResource("dark_disk.png"));
                   ImageIcon saveIcon = new ImageIcon(saveImg);
                   save.setIcon(saveIcon);
               } catch (IOException ioException) {
                   ioException.printStackTrace();
               }
           }

           @Override
           public void mouseExited(MouseEvent e) {
               try {
                   BufferedImage saveImg = ImageIO.read(ClassLoader.getSystemResource("disk.png"));
                   ImageIcon saveIcon = new ImageIcon(saveImg);
                   save.setIcon(saveIcon);
               } catch (IOException ioException) {
                   ioException.printStackTrace();
               }
           }
       });

        save.addActionListener(e -> {
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
                    String p1 = "slider1: " + slider1.getValue();
                    String p2 = "slider2:" + slider2.getValue();
                    String p3 = "slider3:" + slider3.getValue();
                    String p4 = "slider4:" + slider4.getValue();
                    String p5 = "slider5:" + slider5.getValue();
                    String p6 = "slider6:" + slider6.getValue();
                    String p7 = "slider7:" + slider7.getValue();
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


        slider1.addChangeListener(e -> {
            changeLabelValueSlider1(parentC, slider1);
            jSliderStateChanged(flagMode[0], slider2, slider3, slider4, slider5, slider6, slider7);
            totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");
        });

        slider2.addChangeListener(e -> {
            changeLabelValueSlider1(parentD, slider2);
            jSliderStateChanged(flagMode[0], slider1, slider3, slider4, slider5, slider6, slider7);
            totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");
        });

        slider3.addChangeListener(e -> {
            changeLabelValueSlider1(parentE, slider3);
            jSliderStateChanged(flagMode[0], slider2, slider1, slider4, slider5, slider6, slider7);
            totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");
        });

        slider4.addChangeListener(e -> {
            changeLabelValueSlider1(parentF, slider4);
            jSliderStateChanged(flagMode[0], slider2, slider3, slider1, slider5, slider6, slider7);
            totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");
        });

        slider5.addChangeListener(e -> {
            changeLabelValueSlider1(parentG, slider5);
            jSliderStateChanged(flagMode[0], slider2, slider3, slider4, slider1, slider6, slider7);
            totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");
        });

        slider6.addChangeListener(e -> {
            changeLabelValueSlider1(parentA, slider6);
            jSliderStateChanged(flagMode[0], slider2, slider3, slider4, slider5, slider1, slider7);
            totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");
        });

        slider7.addChangeListener(e -> {
            changeLabelValueSlider1(parentB, slider7);
            jSliderStateChanged(flagMode[0], slider2, slider3, slider4, slider5, slider6, slider1);
            totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");
        });

        spinnerLunghezzaBrano.addChangeListener(e -> {
            //errorLunghezzaBrano.setText("");
            errorLunghezzaBrano.setIcon(null);
        });

        spinnerNumeroStrumenti.addChangeListener(e ->{
            //errorNumeroStrumenti.setText("");
            errorNumeroStrumenti.setIcon(null);
        });

        spinner8.addChangeListener(e -> {
            //errorAltezza.setText("");
            errorAltezza.setIcon(null);
        });

        spinner9.addChangeListener(e -> {
            //errorAltezza.setText("");
            errorAltezza.setIcon(null);
        });

        comboBox1.addActionListener(e -> {
            //errorDurata.setText("");
            errorDurata.setIcon(null);
        });

        comboBox2.addActionListener(e -> {
            //errorDurata.setText("");
            errorDurata.setIcon(null);
        });

        onlyRestCheckBox.addActionListener(e -> {
            //errorNotePause.setText("");
            errorNotePause.setIcon(null);
        });

        onlyNoteCheckBox.addActionListener(e -> {
            //errorNotePause.setText("");
            errorNotePause.setIcon(null);
        });

        bothRestNoteCheckBox.addActionListener(e -> {
            //errorNotePause.setText("");
            errorNotePause.setIcon(null);
        });


        generaIEEE1599Button.addActionListener(e -> {
            if (checkValidityOfInput()) {

                configuration.setLenght((int) spinnerLunghezzaBrano.getValue());
                configuration.setNumber_struments((int) spinnerNumeroStrumenti.getValue());

                TreeMap<String, Integer> notes_freq = new TreeMap<>();
                notes_freq.put("C", slider1.getValue());
                notes_freq.put("D", slider2.getValue());
                notes_freq.put("E", slider3.getValue());
                notes_freq.put("F", slider4.getValue());
                notes_freq.put("G", slider5.getValue());
                notes_freq.put("A", slider6.getValue());
                notes_freq.put("B", slider7.getValue());
                configuration.setNotes(notes_freq);

                configuration.setMin_duration(String.valueOf(comboBox1.getSelectedItem()));
                configuration.setMax_duration(String.valueOf(comboBox2.getSelectedItem()));
                configuration.setMin_height((int) spinner8.getValue());
                configuration.setMax_height((int) spinner9.getValue());

                configuration.setPath(textField4.getText());

                configuration.setOnlyRest(onlyRestCheckBox.isSelected());
                configuration.setOnlyNote(onlyNoteCheckBox.isSelected());
                configuration.setBothNoteRest(bothRestNoteCheckBox.isSelected());

                String path = configuration.getPath(); // e.g: "C:\\Users\\mattia\\Desktop\\"
                IEEE1599Generator g1 = new IEEE1599Generator(path, "", configuration);
                g1.generate_file();

                JOptionPane.showMessageDialog(mainPanel, "File IEEE1599 generato con successo!");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Configurare correttamente i parametri!");
            }
        });
    }

    private void changeLabelValueSlider1(JPanel p, JSlider s){
        Color c = ((TitledBorder) p.getBorder()).getTitleColor();
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), s.getValue() + "%", TitledBorder.CENTER, TitledBorder.CENTER));
        ((TitledBorder) p.getBorder()).setTitleColor(c);
    }


    private double fromFractionToDecimal(String s){
        s = s.trim();
        String[] digits = s.split("/");
        return Double.parseDouble(digits[0]) / Double.parseDouble(digits[1]);
    }

    private boolean checkValidityOfInput() {
        boolean correct_format_duration = true;
        BufferedImage errorImg = null;
        try {
            errorImg = ImageIO.read(ClassLoader.getSystemResource("error.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon errorIcon = new ImageIcon(errorImg);

        try {
            String minDuration = (String) comboBox1.getSelectedItem();
            String maxDuration = (String) comboBox2.getSelectedItem();

            String[] array1 = minDuration.split("/");
            String[] array2 = maxDuration.split("/");

            try{
                int minDurationNumInt = Integer.parseInt(array1[0]);
                int minDurationDenInt = Integer.parseInt(array1[1]);
                int maxDurationNumInt = Integer.parseInt(array2[0]);
                int maxDurationDenInt = Integer.parseInt(array2[1]);
            }
            catch(NumberFormatException e){
                errorDurata.setIcon(errorIcon);
                errorDurata.setToolTipText("Il formato della durata specificato non è corretto");
                correct_format_duration = false;
            }

            
            boolean checkToSumOfPitchSlider = getSumOfPitchSpinners() == 100;
            boolean checkToLunghezzaBrano = ((int) spinnerLunghezzaBrano.getValue() > 0);
            boolean checkToNumeroStrumenti = ((int) spinnerNumeroStrumenti.getValue() > 0);
            boolean checkToMinAltezza = ((int) spinner8.getValue() > 0);
            boolean checkToMinMaxAltezza = ((int) spinner9.getValue() > (int) spinner8.getValue());
            boolean checkToCheckBoxes = (onlyNoteCheckBox.isSelected() || onlyRestCheckBox.isSelected() || bothRestNoteCheckBox.isSelected());
            boolean checkToMinMaxDurata = (fromFractionToDecimal((String) comboBox1.getSelectedItem()) < fromFractionToDecimal((String) comboBox2.getSelectedItem()));
            boolean checkToDestinazioneSpecificata = !textField4.getText().equals("");


            if(!checkToLunghezzaBrano){
                errorLunghezzaBrano.setToolTipText("\"Lunghezza del brano\" deve essere maggiore di 0");
                errorLunghezzaBrano.setIcon(errorIcon);
                //errorLunghezzaBrano.setText(" \"Lunghezza del brano\" deve essere maggiore di 0");
            }

            if(!checkToNumeroStrumenti){
                errorNumeroStrumenti.setIcon(errorIcon);
                errorNumeroStrumenti.setToolTipText("\"Numero strumenti\" deve essere maggiore di 0");
                //errorNumeroStrumenti.setText("\"Numero strumenti\" deve essere maggiore di 0");
            }

            if(!checkToMinAltezza || !checkToMinMaxAltezza){
                errorAltezza.setIcon(errorIcon);
                errorAltezza.setToolTipText("\"Altezza minima\" deve essere maggiore di 0 e minore di \"Altezza massima\"");
                //errorAltezza.setText(" \"Altezza minima\" deve essere maggiore di 0 e minore di \"Altezza massima\"");
            }

            if(!checkToMinMaxDurata){
                errorDurata.setIcon(errorIcon);
                errorDurata.setToolTipText("\"Durata minima\" deve essere minore di \"Durata massima\"");
                //errorDurata.setText(" \"Durata minima\" deve essere minore di \"Durata massima\"");
            }

            if(!checkToCheckBoxes){
                errorNotePause.setIcon(errorIcon);
                errorNotePause.setToolTipText("Selezionare un'opzione");
                //errorNotePause.setText(" Selezionare un'opzione");
            }

            if(!checkToDestinazioneSpecificata){
                //textField4.setText(" Specificare la destinazione");
                //textField4.setForeground(new Color(191, 0, 40));
                errorDestinazione.setIcon(errorIcon);
                errorDestinazione.setToolTipText("Specificare la destinazione");
            }

            if(!checkToSumOfPitchSlider){
                totalPercentage.setIcon(errorIcon);
                totalPercentage.setToolTipText("Il totale deve essere pari al 100%");
                totalPercentage.setForeground(new Color(191, 0, 40));
            }

            return correct_format_duration && checkToSumOfPitchSlider && checkToLunghezzaBrano && checkToNumeroStrumenti &&
                   checkToMinAltezza && checkToMinMaxAltezza && checkToCheckBoxes &&
                   checkToMinMaxDurata && checkToDestinazioneSpecificata;

        } catch(Exception e){
            return false;
        }
    }

    private void setParametersFromFileConfiguration(String pathname){
        try {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(pathname));
                String line = reader.readLine();
                while (line != null) {
                    String[] id_value = line.split(":");
                    setValueFromConfig(id_value[0], id_value[1]);
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(mainPanel, "File di configurazione non corretto!");
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(mainPanel, "File di configurazione non corretto!");
        }
    }

    private void setValueFromConfig(String id, String value) {
        try {
            value = value.trim();
            String type = mainPanel.getClientProperty(id).getClass().getName();
            Object component = mainPanel.getClientProperty(id);
            if (type.equals("javax.swing.JSpinner")) {
                ((JSpinner) component).setValue(Integer.valueOf(value));
            } else if (type.equals("javax.swing.JComboBox")) {
                ((JComboBox) component).setSelectedItem(value);
            }
            else if(type.equals("javax.swing.JSlider")){
                //System.out.println(type);
                ((JSlider) component).setValue(Integer.parseInt(value));
            }
            else if (type.equals("javax.swing.JCheckBox")) {
                if (value.equals("true")) ((JCheckBox) component).setSelected(true);
                else ((JCheckBox) component).setSelected(false);
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(mainPanel, "File di configurazione non corretto!");
        }
    }

    private int getSumOfPitchSpinners(){
        return (slider1.getValue()) + (slider2.getValue()) + (slider3.getValue()) + (slider4.getValue()) +
        (slider5.getValue()) + (slider6.getValue()) + (slider7.getValue());
    }

    public void setColorOfLabel(Color c){
        label1.setForeground(c); label2.setForeground(c); label3.setForeground(c);
        label4.setForeground(c); label5.setForeground(c); label6.setForeground(c);
        label7.setForeground(c); label8.setForeground(c); label9.setForeground(c);
        label10.setForeground(c); label11.setForeground(c); label12.setForeground(c); label13.setForeground(c);
        onlyNoteCheckBox.setForeground(c); onlyRestCheckBox.setForeground(c); bothRestNoteCheckBox.setForeground(c);
        totalPercentage.setForeground(c);

        changeColorOfTitleBorder(panelDurata, c); changeColorOfTitleBorder(panelNumeroStrumenti, c); changeColorOfTitleBorder(panelNote, c);
        changeColorOfTitleBorder(panelAltezza, c); changeColorOfTitleBorder(panelLunghezzaBrano, c); changeColorOfTitleBorder(panelNotePause, c);
        changeColorOfTitleBorder(parentA, c); changeColorOfTitleBorder(parentB, c); changeColorOfTitleBorder(parentC, c);
        changeColorOfTitleBorder(parentD, c); changeColorOfTitleBorder(parentE, c); changeColorOfTitleBorder(parentF, c);
        changeColorOfTitleBorder(parentG, c);
    }

    private void changeColorOfTitleBorder(JPanel p, Color c){
        TitledBorder b = (TitledBorder) (p.getBorder());
        b.setTitleColor(c);
    }

    private void jSliderStateChanged(int mode, JSlider s1, JSlider s2, JSlider s3, JSlider s4, JSlider s5, JSlider s6) {
        if(mode == 1) totalPercentage.setForeground(Color.BLACK);
        else totalPercentage.setForeground(Color.WHITE);
        totalPercentage.setIcon(null);
        totalPercentage.setToolTipText("Percentuale totale specificata");
        while (getSumOfPitchSpinners() > 100) {
            s1.setValue(s1.getValue() - 1);
            s2.setValue(s2.getValue() - 1);
            s3.setValue(s3.getValue() - 1);
            s4.setValue(s4.getValue() - 1);
            s5.setValue(s5.getValue() - 1);
            s6.setValue(s6.getValue() - 1);
        }
    }


     private void darkBlueTheme(){
        switchB.setToolTipText("Tema chiaro");

        try {
            BufferedImage switchImg = ImageIO.read(ClassLoader.getSystemResource("dark_mode_off_white.png"));
            ImageIcon switchIcon = new ImageIcon(switchImg);
            switchB.setIcon(switchIcon);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        Color background = new Color(33, 37, 43);
        Color c = new Color(40, 44, 52);
        mainPanel.setBackground(background);
        afterToolbarPanel.setBackground(background);
        afterNotePausePanel.setBackground(background);

        labelDestinazione.setForeground(Color.WHITE);

        setColorOfLabel(Color.WHITE);
        switchB.setForeground(Color.white);
        authorPanel.setBackground(background);
        labelAuthor.setForeground(Color.WHITE);

        changeBackgroundOfComponent(c);

        panelSalvataggio.setBackground(background);
        panelButton.setBackground(background);
        textField4.setBackground(background);
        textField4.setForeground(Color.white);
        textField4.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        cartellaDiDestinazioneButton.setBackground(c);
        generaIEEE1599Button.setBackground(c);
        cartellaDiDestinazioneButton.setForeground(Color.WHITE);
        generaIEEE1599Button.setForeground(Color.WHITE);

         authorPanel.setBackground(c);
         //labelAuthor.setText("");

         if(totalPercentage.getIcon() != null){
             totalPercentage.setForeground(new Color(191, 0, 40));
         }

         /*if(errorDestinazione.getIcon() != null){
             textField4.setForeground(new Color(191, 0, 40));
         }*/
    }


    public void darkYellowTheme(){
        switchB.setToolTipText("Tema scuro");

        try {
            BufferedImage switchImg = ImageIO.read(ClassLoader.getSystemResource("dark_mode_on.png"));
            ImageIcon switchIcon = new ImageIcon(switchImg);
            switchB.setIcon(switchIcon);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        Color c = new Color(248, 148, 7); // 206, 124, 4

        mainPanel.setBackground(Color.WHITE);

        afterToolbarPanel.setBackground(Color.WHITE);
        afterNotePausePanel.setBackground(Color.WHITE);


        changeBackgroundOfComponent(c);

        switchB.setForeground(Color.BLACK);
        labelDestinazione.setForeground(Color.BLACK);

        setColorOfLabel(Color.BLACK);

        authorPanel.setBackground(Color.WHITE);
        labelAuthor.setForeground(Color.BLACK);

        panelSalvataggio.setBackground(Color.white);
        panelButton.setBackground(Color.white);
        textField4.setBackground(Color.white);
        textField4.setForeground(Color.black);
        textField4.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        cartellaDiDestinazioneButton.setBackground(Color.LIGHT_GRAY);
        generaIEEE1599Button.setBackground(Color.LIGHT_GRAY);
        cartellaDiDestinazioneButton.setForeground(Color.BLACK);
        generaIEEE1599Button.setForeground(Color.BLACK);

        if(totalPercentage.getIcon() != null){
            totalPercentage.setForeground(new Color(191, 0, 40));
        }


        /*panelSalvataggio.setBackground(c);
        panelButton.setBackground(c);
        textField4.setBackground(c);*/

        authorPanel.setBackground(new Color(243, 240, 240));
        //labelAuthor.setText("");

        changeBorderType();


        /*if(errorDestinazione.getIcon() != null){
            textField4.setForeground(new Color(191, 0, 40));
        }*/
    }

    private void changeBorderType(){
        parentAltezzaMinima.setBorder(BorderFactory.createEtchedBorder());
        parentAltezzaMassima.setBorder(BorderFactory.createEtchedBorder());
        parentSoloNote.setBorder(BorderFactory.createEtchedBorder());
        parentEntrambe.setBorder(BorderFactory.createEtchedBorder());
        parentSoloPause.setBorder(BorderFactory.createEtchedBorder());
        parentNumeroStrumenti.setBorder(BorderFactory.createEtchedBorder());
        parentLunghezzaBrano.setBorder(BorderFactory.createEtchedBorder());
        parentDurataMinima.setBorder(BorderFactory.createEtchedBorder());
        parentDurataMassima.setBorder(BorderFactory.createEtchedBorder());
    }

    private void changeBackgroundOfComponent(Color c){
        panelNote.setBackground(c);
        panelNumeroStrumenti.setBackground(c);
        panelNotePause.setBackground(c);
        panelLunghezzaBrano.setBackground(c);
        panelAltezza.setBackground(c);
        panelDurata.setBackground(c);
        parentA.setBackground(c);
        parentAltezzaMassima.setBackground(c);
        parentAltezzaMinima.setBackground(c);
        parentB.setBackground(c);
        parentC.setBackground(c);
        parentD.setBackground(c);
        parentDurataMassima.setBackground(c);
        parentDurataMinima.setBackground(c);
        parentE.setBackground(c);
        parentEntrambe.setBackground(c);
        parentF.setBackground(c);
        parentG.setBackground(c);
        parentLunghezzaBrano.setBackground(c);
        parentNumeroStrumenti.setBackground(c);
        parentSoloNote.setBackground(c);
        parentSoloPause.setBackground(c);
    }


    private HashMap<String, Double> sortByValue(HashMap<String, Double> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Double> > list =
                new LinkedList<Map.Entry<String, Double>>(hm.entrySet());

        // Sort the list
        Collections.sort(list, (o1, o2) -> (o1.getValue()).compareTo(o2.getValue()));

        // put data from sorted list to hashmap
        HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

}
