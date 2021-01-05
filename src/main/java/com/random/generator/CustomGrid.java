package com.random.generator;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class CustomGrid {
    public JPanel mainPanel;
    private JButton generaIEEE1599Button; private JButton cartellaDiDestinazioneButton;
    private JTextField textFieldDestination;
    private JCheckBox onlyNoteCheckBox; private JCheckBox onlyRestCheckBox; private JCheckBox bothRestNoteCheckBox;
    private JComboBox comboBoxDurMin; private JComboBox comboBoxDurMax;
    private JToolBar toolbar1;
    private Parameter configuration = new Parameter();

    private JSpinner spinnerLunghezzaBrano; private JSpinner spinnerNumeroStrumenti; private JSpinner spinnerMinH; private JSpinner spinnerMaxH;

    private JPanel panelLunghezzaBrano; private JPanel panelNumeroStrumenti; private JPanel panelNote; private JPanel panelAltezza;
    private JPanel panelNotePause; private JPanel panelSalvataggio; private JPanel panelDurata;

    private JLabel totalPercentage;

    private JPanel parentLunghezzaBrano; private JPanel parentNumeroStrumenti;
    private JPanel parentC; private JPanel parentD; private JPanel parentE; private JPanel parentF;
    private JPanel parentG; private JPanel parentA; private JPanel parentB;
    private JPanel parentDurataMinima; private JPanel parentDurataMassima; private JPanel parentAltezzaMinima;
    private JPanel parentAltezzaMassima; private JPanel parentSoloNote; private JPanel parentSoloPause; private JPanel parentEntrambe;
    private JPanel panelButton; private JPanel authorPanel;

    private JLabel labelDestinazione; private JLabel labelAuthor;
    private JLabel label1; private JLabel label2; private JLabel label3; private JLabel label4;
    private JLabel label5; private JLabel label6; private JLabel label7; private JLabel label8;
    private JLabel label9; private JLabel label10; private JLabel label11; private JLabel label12; private JLabel label13;

    private JSlider sliderC; private JSlider sliderD; private JSlider sliderE; private JSlider sliderF;
    private JSlider sliderG; private JSlider sliderA; private JSlider sliderB;

    private JLabel errorLunghezzaBrano; private JLabel errorNumeroStrumenti;
    private JLabel errorDurata; private JLabel errorNotePause;
    private JLabel errorAltezza; private JLabel errorDestinazione;

    private JPanel afterToolbarPanel; private JPanel afterNotePausePanel;

    private JButton switchB;
    private Random r = new Random();

    private boolean flagTheme = true; // true for dark mode, false for light mode

    public CustomGrid() throws IOException {
        BufferedImage playImg = ImageIO.read(ClassLoader.getSystemResource("play.png"));
        ImageIcon playIcon = new ImageIcon(playImg);
        generaIEEE1599Button.setIcon(playIcon);

        setListenersToSliders();
        initializeToolbar();
        initializeJTextFieldDestination();
        setClientProperty();
        initializeSliders();
        setSizeOfSlidersAndPanelParent();
        setDurationComponents();
        setListenersToDestinationFolder();
        setListenersToSpinners();
        setListenersToCheckBoxes();
        setListenersToComboBoxes();
        setListenersToGenerateIEEEButton();
        setFontToComponent("Microsoft JhengHey");
    }

    private void initializeJTextFieldDestination(){
        textFieldDestination.setBackground(new Color(40, 44, 52));
        textFieldDestination.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        textFieldDestination.setEditable(false);
    }

    private void setClientProperty(){
        mainPanel.putClientProperty("sliderC", sliderC); mainPanel.putClientProperty("sliderD", sliderD); mainPanel.putClientProperty("sliderE", sliderE);

        mainPanel.putClientProperty("sliderF", sliderF); mainPanel.putClientProperty("sliderG", sliderG); mainPanel.putClientProperty("sliderA", sliderA); mainPanel.putClientProperty("sliderB", sliderB);
        mainPanel.putClientProperty("spinnerMinH", spinnerMinH); mainPanel.putClientProperty("spinnerMaxH", spinnerMaxH);

        mainPanel.putClientProperty("comboBoxDurMin", comboBoxDurMin); mainPanel.putClientProperty("comboBoxDurMax", comboBoxDurMax);
        mainPanel.putClientProperty("spinnerLunghezzaBrano", spinnerLunghezzaBrano);
        mainPanel.putClientProperty("spinnerNumeroStrumenti", spinnerNumeroStrumenti);

        mainPanel.putClientProperty("onlyNoteCheckBox", onlyNoteCheckBox);
        mainPanel.putClientProperty("onlyRestCheckBox", onlyRestCheckBox);
        mainPanel.putClientProperty("bothRestNoteCheckBox", bothRestNoteCheckBox);
    }

    private void setListenersToDestinationFolder(){
        cartellaDiDestinazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorDestinazione.setText(""); errorDestinazione.setIcon(null); textFieldDestination.setText("");
                if(flagTheme) textFieldDestination.setForeground(Color.BLACK);
                else textFieldDestination.setForeground(Color.WHITE);
                JFileChooser fs = new JFileChooser(new File(System.getProperty("user.home")));
                UIManager.put("text", Color.BLACK);
                fs.setFileFilter(new FolderFilter());
                fs.setFileSelectionMode(JFileChooser.APPROVE_OPTION);
                fs.setDialogTitle("Seleziona cartella destinazione");
                fs.showSaveDialog(mainPanel);
                File fi = fs.getSelectedFile();
                if (fi != null) {
                    textFieldDestination.setText(fi.getPath());
                    System.out.println(fi.getPath());
                }
            }
        });
    }

    private void setListenersToGenerateIEEEButton(){
        generaIEEE1599Button.addActionListener(e -> {
            if (checkValidityOfInput()) {

                configuration.setLenght((int) spinnerLunghezzaBrano.getValue());
                configuration.setNumber_struments((int) spinnerNumeroStrumenti.getValue());

                TreeMap<String, Integer> notes_freq = new TreeMap<>();
                notes_freq.put("C", sliderC.getValue());
                notes_freq.put("D", sliderD.getValue());
                notes_freq.put("E", sliderE.getValue());
                notes_freq.put("F", sliderF.getValue());
                notes_freq.put("G", sliderG.getValue());
                notes_freq.put("A", sliderA.getValue());
                notes_freq.put("B", sliderB.getValue());
                configuration.setNotes(notes_freq);

                configuration.setMin_duration(String.valueOf(comboBoxDurMin.getSelectedItem()));
                configuration.setMax_duration(String.valueOf(comboBoxDurMax.getSelectedItem()));
                configuration.setMin_height((int) spinnerMinH.getValue());
                configuration.setMax_height((int) spinnerMaxH.getValue());

                configuration.setPath(textFieldDestination.getText());

                configuration.setOnlyRest(onlyRestCheckBox.isSelected());
                configuration.setOnlyNote(onlyNoteCheckBox.isSelected());
                configuration.setBothNoteRest(bothRestNoteCheckBox.isSelected());

                String path = configuration.getPath(); // e.g: "C:\\Users\\mattia\\Desktop\\"
                IEEE1599Generator g1 = new IEEE1599Generator(path, "", configuration);
                g1.generate_file();
                JOptionPane.showMessageDialog(mainPanel, "File IEEE1599 generato con successo!", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Configurare correttamente i parametri!", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
            }
        });

    }

    private void setListenersToComboBoxes(){
        comboBoxDurMin.addActionListener(e -> {
            errorDurata.setIcon(null);
        });

        comboBoxDurMax.addActionListener(e -> {
            errorDurata.setIcon(null);
        });

        comboBoxDurMin.addItemListener(e -> {
            comboBoxDurMax.removeAllItems();
            for(int j = 1; j < 5; j++){
                for(int i = 0; i < 8; i++) {
                    String s = j + "/" + (int) Math.pow(2, i);
                    String s1 = (String) comboBoxDurMin.getSelectedItem();
                    //comboBoxDurMin.addItem(s);
                    if (fromFractionToDecimal(s) > fromFractionToDecimal(s1)) {
                        comboBoxDurMax.addItem(s);
                    }
                }
            }
        });
    }

    private void setListenersToCheckBoxes(){
        onlyNoteCheckBox.addActionListener(e -> {
            onlyRestCheckBox.setSelected(false);
            bothRestNoteCheckBox.setSelected(false);
            errorNotePause.setIcon(null);
        });

        onlyRestCheckBox.addActionListener(e -> {
            onlyNoteCheckBox.setSelected(false);
            bothRestNoteCheckBox.setSelected(false);
            errorNotePause.setIcon(null);
        });

        bothRestNoteCheckBox.addActionListener(e -> {
            onlyRestCheckBox.setSelected(false);
            onlyNoteCheckBox.setSelected(false);
            errorNotePause.setIcon(null);
        });
    }

    private void setListenersToSpinners(){
        spinnerLunghezzaBrano.addChangeListener(e -> {
            errorLunghezzaBrano.setIcon(null);
        });

        spinnerNumeroStrumenti.addChangeListener(e ->{
            errorNumeroStrumenti.setIcon(null);
        });

        spinnerMinH.addChangeListener(e -> {
            errorAltezza.setIcon(null);
        });

        spinnerMaxH.addChangeListener(e -> {
            errorAltezza.setIcon(null);
        });
    }

    private void setListenersToSliders(){
        sliderC.addChangeListener(e -> {
            changeLabelValueSlider(parentC, sliderC);
            jSliderStateChanged(sliderD, sliderE, sliderF, sliderG, sliderA, sliderB);
            totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");
        });

        sliderD.addChangeListener(e -> {
            changeLabelValueSlider(parentD, sliderD);
            jSliderStateChanged(sliderC, sliderE, sliderF, sliderG, sliderA, sliderB);
            totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");
        });

        sliderE.addChangeListener(e -> {
            changeLabelValueSlider(parentE, sliderE);
            jSliderStateChanged(sliderD, sliderC, sliderF, sliderG, sliderA, sliderB);
            totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");
        });

        sliderF.addChangeListener(e -> {
            changeLabelValueSlider(parentF, sliderF);
            jSliderStateChanged(sliderD, sliderE, sliderC, sliderG, sliderA, sliderB);
            totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");
        });

        sliderG.addChangeListener(e -> {
            changeLabelValueSlider(parentG, sliderG);
            jSliderStateChanged(sliderD, sliderE, sliderF, sliderC, sliderA, sliderB);
            totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");
        });

        sliderA.addChangeListener(e -> {
            changeLabelValueSlider(parentA, sliderA);
            jSliderStateChanged(sliderD, sliderE, sliderF, sliderG, sliderC, sliderB);
            totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");
        });

        sliderB.addChangeListener(e -> {
            changeLabelValueSlider(parentB, sliderB);
            jSliderStateChanged(sliderD, sliderE, sliderF, sliderG, sliderA, sliderC);
            totalPercentage.setText("Totale: " + getSumOfPitchSpinners() + "%");
        });
    }

    private void setSizeOfSlidersAndPanelParent(){
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

        sliderC.setPreferredSize(new Dimension(width, height));
        sliderD.setPreferredSize(new Dimension(width, height));
        sliderE.setPreferredSize(new Dimension(width, height));
        sliderF.setPreferredSize(new Dimension(width, height));
        sliderG.setPreferredSize(new Dimension(width, height));
        sliderA.setPreferredSize(new Dimension(width, height));
        sliderB.setPreferredSize(new Dimension(width, height));
    }

    private void initializeSliders(){
        sliderC.setValue(r.nextInt(20)); changeLabelValueSlider(parentC, sliderC);
        sliderD.setValue(r.nextInt(20)); changeLabelValueSlider(parentD, sliderD);
        sliderE.setValue(r.nextInt(20)); changeLabelValueSlider(parentE, sliderE);
        sliderF.setValue(r.nextInt(20)); changeLabelValueSlider(parentF, sliderF);
        sliderG.setValue(r.nextInt(20)); changeLabelValueSlider(parentG, sliderG);
        sliderA.setValue(r.nextInt(20)); changeLabelValueSlider(parentA, sliderA);
        sliderB.setValue(r.nextInt(20)); changeLabelValueSlider(parentB, sliderB);
    }

    private void setFontToComponent(String fontName){
        int size1 = 19;
        Font f1 = new Font(fontName, Font.PLAIN, size1);

        spinnerLunghezzaBrano.setFont(f1);
        spinnerNumeroStrumenti.setFont(f1);
        spinnerMinH.setFont(f1);
        spinnerMaxH.setFont(f1);

        Font f2 = new Font(fontName, Font.PLAIN, size1 - 1);
        comboBoxDurMin.setFont(f2);
        comboBoxDurMax.setFont(f2);

        totalPercentage.setFont(new Font(fontName, Font.PLAIN, 18));

        Font f3 = new Font("Montserrat", Font.PLAIN, 14);
        generaIEEE1599Button.setFont(f3);
        cartellaDiDestinazioneButton.setFont(f3);


        Font f4 = new Font(fontName, Font.BOLD, 17);
        Font f5 = new Font(fontName, Font.BOLD, 16);

        bothRestNoteCheckBox.setFont(f4);
        onlyNoteCheckBox.setFont(f4);
        onlyRestCheckBox.setFont(f4);
        label1.setFont(f4);
        label2.setFont(f4);
        label3.setFont(f5);
        label4.setFont(f5);
        label5.setFont(f5);
        label6.setFont(f5);
        label7.setFont(f5);
        label8.setFont(f5);
        label9.setFont(f5);
        label10.setFont(f4);
        label11.setFont(f4);
        label12.setFont(f4);
        label13.setFont(f4);

    }

    private void changeLabelValueSlider(JPanel p, JSlider s){
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
            String minDuration = (String) comboBoxDurMin.getSelectedItem();
            String maxDuration = (String) comboBoxDurMax.getSelectedItem();

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
            boolean checkToMinAltezza = ((int) spinnerMinH.getValue() > 0);
            boolean checkToMinMaxAltezza = ((int) spinnerMaxH.getValue() > (int) spinnerMinH.getValue());
            boolean checkToCheckBoxes = (onlyNoteCheckBox.isSelected() || onlyRestCheckBox.isSelected() || bothRestNoteCheckBox.isSelected());
            boolean checkToMinMaxDurata = (fromFractionToDecimal((String) comboBoxDurMin.getSelectedItem()) < fromFractionToDecimal((String) comboBoxDurMax.getSelectedItem()));
            boolean checkToDestinazioneSpecificata = !textFieldDestination.getText().equals("");


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
                //textFieldDestination.setText(" Specificare la destinazione");
                //textFieldDestination.setForeground(new Color(191, 0, 40));
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
        resetValueOfJSlider();
        try {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(pathname));
                String line = reader.readLine();
                while (line != null) {
                    String[] id_value = line.split(":");
                    //System.out.println(id_value[0] + "  " + id_value[1]);
                    if(!setValueFromConfig(id_value[0], id_value[1])) break;
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(mainPanel, "File di configurazione non corretto!", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(mainPanel, "File di configurazione non corretto!", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean setValueFromConfig(String id, String value) {
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
            return true;
        } catch(Exception e){
            JOptionPane.showMessageDialog(mainPanel, "File di configurazione non corretto!", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
    }

    private int getSumOfPitchSpinners(){
        return (sliderC.getValue()) + (sliderD.getValue()) + (sliderE.getValue()) + (sliderF.getValue()) +
        (sliderG.getValue()) + (sliderA.getValue()) + (sliderB.getValue());
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

    private void jSliderStateChanged(JSlider s1, JSlider s2, JSlider s3, JSlider s4, JSlider s5, JSlider s6) {
        if(flagTheme) totalPercentage.setForeground(Color.BLACK);
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
        textFieldDestination.setBackground(background);
        textFieldDestination.setForeground(Color.white);
        textFieldDestination.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
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
             textFieldDestination.setForeground(new Color(191, 0, 40));
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
        textFieldDestination.setBackground(Color.white);
        textFieldDestination.setForeground(Color.black);
        textFieldDestination.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        cartellaDiDestinazioneButton.setBackground(Color.LIGHT_GRAY);
        generaIEEE1599Button.setBackground(Color.LIGHT_GRAY);
        cartellaDiDestinazioneButton.setForeground(Color.BLACK);
        generaIEEE1599Button.setForeground(Color.BLACK);

        if(totalPercentage.getIcon() != null){
            totalPercentage.setForeground(new Color(191, 0, 40));
        }


        /*panelSalvataggio.setBackground(c);
        panelButton.setBackground(c);
        textFieldDestination.setBackground(c);*/

        authorPanel.setBackground(new Color(243, 240, 240));
        //labelAuthor.setText("");

        changeBorderType();


        /*if(errorDestinazione.getIcon() != null){
            textFieldDestination.setForeground(new Color(191, 0, 40));
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


    /*private HashMap<String, Double> sortByValue(HashMap<String, Double> hm)
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
    }*/


    private void setDurationComponents(){
        /*HashMap<String, Double> durationValue = new HashMap<>();
        for (int i = 1; i < 65; i++) {
            for (int j = 1; j < 65; j++) {
                String s = i + "/" + j;
                durationValue.put(s, ((double) i)/ ((double) j));
            }
        }
        durationValue = sortByValue(durationValue);
        for (String name: durationValue.keySet()){
            comboBoxDurMin.addItem(name);
            comboBoxDurMax.addItem(name);
        } */
        String s1 = "1/1";
        for(int j = 1; j < 5; j++) {
            for (int i = 0; i < 8; i++) {
                String s = j + "/" + (int) Math.pow(2, i);
                comboBoxDurMin.addItem(s);
                if(fromFractionToDecimal(s) > fromFractionToDecimal(s1))  comboBoxDurMax.addItem(s);
            }
        }
    }

    private void resetValueOfJSlider(){
        sliderC.setValue(0); //changeLabelValueSlider(parentC, sliderC);
        sliderD.setValue(0); //changeLabelValueSlider(parentD, sliderD);
        sliderE.setValue(0); //changeLabelValueSlider(parentE, sliderE);
        sliderF.setValue(0); //changeLabelValueSlider(parentF, sliderF);
        sliderG.setValue(0); //changeLabelValueSlider(parentG, sliderG);
        sliderA.setValue(0); //changeLabelValueSlider(parentA, sliderA);
        sliderB.setValue(0); //changeLabelValueSlider(parentB, sliderB);
    }


    private void initializeToolbar() throws IOException {
        toolbar1.setFloatable(false);
        toolbar1.setMargin(new Insets(10, 10, 5, 0));

        JButton save = new JButton();
        BufferedImage saveImg = ImageIO.read(ClassLoader.getSystemResource("disk.png"));
        ImageIcon saveIcon = new ImageIcon(saveImg);
        save.setIcon(saveIcon);

        save.setToolTipText("Salva configurazione parametri");
        toolbar1.add(save);
        toolbar1.addSeparator(new Dimension(15, 10));

        JButton open = new JButton();
        open.setToolTipText("Apri configurazione parametri");

        BufferedImage openImg = ImageIO.read(ClassLoader.getSystemResource("open.png"));
        ImageIcon openIcon = new ImageIcon(openImg);
        open.setIcon(openIcon);
        toolbar1.add(open);
        toolbar1.addSeparator(new Dimension(15, 10));

        switchB = new JButton();
        switchB.setBorder(BorderFactory.createEmptyBorder(5,10,5,50));
        switchB.setContentAreaFilled(false);

        BufferedImage switchImg = ImageIO.read(ClassLoader.getSystemResource("dark_mode_on.png"));
        ImageIcon switchIcon = new ImageIcon(switchImg);
        switchB.setIcon(switchIcon);
        switchB.setToolTipText("Cambia tema");

        toolbar1.add(switchB);

        switchB.setBorder(BorderFactory.createEmptyBorder());
        open.setBorder(BorderFactory.createEmptyBorder());
        save.setBorder(BorderFactory.createEmptyBorder());

        addListenersToSwitchButton(switchB);
        addListenersToOpenButton(open);
        addListenersToSaveButton(save);

    }

    private void addListenersToSaveButton(JButton save){
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
                    String p1 = "sliderC:" + sliderC.getValue();
                    String p2 = "sliderD:" + sliderD.getValue();
                    String p3 = "sliderE:" + sliderE.getValue();
                    String p4 = "sliderF:" + sliderF.getValue();
                    String p5 = "sliderG:" + sliderG.getValue();
                    String p6 = "sliderA:" + sliderA.getValue();
                    String p7 = "sliderB:" + sliderB.getValue();
                    String p8 = "spinnerLunghezzaBrano:" + spinnerLunghezzaBrano.getValue() + "\n";
                    String p9 = "spinnerNumeroStrumenti:" + spinnerNumeroStrumenti.getValue() + "\n";
                    String p10 = "comboBoxDurMin:" + comboBoxDurMin.getSelectedItem() + "\n";
                    String p11 = "comboBoxDurMax:" + comboBoxDurMax.getSelectedItem() + "\n";
                    String p12 = "spinnerMinH:" + spinnerMinH.getValue() + "\n";
                    String p13 = "spinnerMaxH:" + spinnerMaxH.getValue() + "\n";
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
                JOptionPane.showMessageDialog(mainPanel, "Configurazione salvata correttamente!", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void addListenersToOpenButton(JButton open){
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
    }


    private void addListenersToSwitchButton(JButton switchB){
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
                    if(flagTheme) {
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
                    if(flagTheme) {
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

        switchB.addActionListener(e -> {
            if(flagTheme) {
                flagTheme = false;
                darkBlueTheme();
            }
            else{
                flagTheme = true;
                darkYellowTheme();
            }
        });
    }
}
