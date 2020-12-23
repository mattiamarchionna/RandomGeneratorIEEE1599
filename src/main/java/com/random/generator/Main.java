package com.random.generator;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Main {

    static CustomGrid grid;
    static JToggleButton switchB;

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

        grid = new CustomGrid();

        switchB = new JToggleButton("Tema chiaro");
        switchB.setBorder(BorderFactory.createEmptyBorder(5,10,5,50));
        switchB.setPreferredSize(new Dimension(140, 40));
        final int[] flagMode = {1}; // 1 for dark blue mode, 0 for dark yellow mode
        BufferedImage switchImg = ImageIO.read(ClassLoader.getSystemResource("switch_on.png"));
        ImageIcon switchIcon = new ImageIcon(switchImg);
        switchB.setIcon(switchIcon);
        switchB.setToolTipText("Cambia tema");
        grid.toolbar1.add(switchB);

        switchB.setBorder(BorderFactory.createEmptyBorder());


        darkYellowTheme(grid);

        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());

        //darkBlueTheme(grid);
        jframe.add(grid.mainPanel);
        //jframe.add(p);
        //jframe.setContentPane(grid.mainPanel);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setSize(1100, 720);
        jframe.setVisible(true);


        switchB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(flagMode[0] == 1) {
                        BufferedImage switchImg = ImageIO.read(ClassLoader.getSystemResource("switch_off.png"));
                        ImageIcon switchIcon = new ImageIcon(switchImg);
                        switchB.setIcon(switchIcon);
                        flagMode[0] = 0;
                        darkBlueTheme(grid);
                    }
                    else{
                        BufferedImage switchImg = ImageIO.read(ClassLoader.getSystemResource("switch_on.png"));
                        ImageIcon switchIcon = new ImageIcon(switchImg);
                        switchB.setIcon(switchIcon);
                        flagMode[0] = 1;
                        darkYellowTheme(grid);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

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
        switchB.setText("Tema scuro");

        Color background = new Color(33, 37, 43);
        g.mainPanel.setBackground(background);

        //UIManager.put( "control", new Color(40, 44, 52)); // 61 61 61
        //UIManager.put( "info", new Color(65,65,65) );
        //UIManager.put( "nimbusBase", new Color( 33, 37, 43) );
        //UIManager.put( "nimbusAlertYellow", new Color( 248, 187, 0) );
        //UIManager.put( "nimbusDisabledText", new Color( 128, 128, 128) );
        //UIManager.put( "nimbusFocus", new Color(115,164,209) );
        //UIManager.put( "nimbusGreen", new Color(176,179,50) );
        //UIManager.put( "nimbusInfoBlue", new Color( 66, 139, 221) );
        //UIManager.put( "nimbusLightBackground", new Color( 33, 37, 43) ); // ok
        //UIManager.put( "nimbusOrange", new Color(191,98,4) );
        //UIManager.put( "nimbusRed", new Color(169,46,34) );
        //UIManager.put( "nimbusSelectedText", new Color( 255, 255, 255) ); // ok
        //UIManager.put( "nimbusSelectionBackground", new Color( 66, 139, 221) ); // ok

        //Color c = new Color(120, 155, 254);
        Color c = new Color(40, 44, 52);

        g.labelDestinazione.setForeground(Color.WHITE);

        g.setColorOfLabel(Color.WHITE);

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

        /*g.comboBox1.setBackground(new Color(25, 27, 32));
        g.comboBox1.setForeground(Color.BLACK);
        g.comboBox2.setBackground(new Color(25, 27, 32));
        g.comboBox2.setForeground(Color.BLACK);*/

        g.cartellaDiDestinazioneButton.setBackground(new Color(40, 44, 52));
        g.generaIEEE1599Button.setBackground(new Color(40, 44, 52));
        g.cartellaDiDestinazioneButton.setForeground(Color.WHITE);
        g.generaIEEE1599Button.setForeground(Color.WHITE);

        //UIManager.put("text", Color.WHITE);

        g.spinner1.getEditor().getComponent(0).setForeground(background);
        g.spinner2.getEditor().getComponent(0).setForeground(background);
        g.spinner3.getEditor().getComponent(0).setForeground(background);
        g.spinner4.getEditor().getComponent(0).setForeground(background);
        g.spinner5.getEditor().getComponent(0).setForeground(background);
        g.spinner6.getEditor().getComponent(0).setForeground(background);
        g.spinner7.getEditor().getComponent(0).setForeground(background);
        g.spinner8.getEditor().getComponent(0).setForeground(background);
        g.spinner9.getEditor().getComponent(0).setForeground(background);


        g.spinnerLunghezzaBrano.getEditor().getComponent(0).setForeground(background);
        g.spinnerNumeroStrumenti.getEditor().getComponent(0).setForeground(background);

        g.comboBox1.getEditor().getEditorComponent().setForeground(background);
        g.comboBox2.getEditor().getEditorComponent().setForeground(background);


        /*PopupMenuListener popList = new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                UIManager.put("text", background);
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                UIManager.put("text", Color.WHITE);
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                UIManager.put("text", background);

            }
        };*/

        //g.comboBox1.addPopupMenuListener(popList);

        //g.comboBox2.addPopupMenuListener(popList);


    }


    static private void darkYellowTheme(CustomGrid g){
        switchB.setText("Tema chiaro");
        g.mainPanel.setBackground(Color.WHITE);
        Color c = new Color(219, 130, 7); // 248, 148, 7
        //UIManager.put( "control", Color.WHITE); // 61 61 61
        //UIManager.put( "info", Color.WHITE );
        //UIManager.put( "nimbusBase", new Color(128, 128, 128));

        //UIManager.put( "nimbusAlertYellow", new Color( 248, 187, 0) );
        //UIManager.put( "nimbusDisabledText", new Color( 128, 128, 128) );
        //UIManager.put( "nimbusFocus", new Color(115,164,209) );
        //UIManager.put( "nimbusGreen", new Color(176,179,50) );
        //UIManager.put( "nimbusInfoBlue", new Color( 66, 139, 221) );
        //UIManager.put( "nimbusLightBackground", Color.WHITE ); // ok
        //UIManager.put( "nimbusOrange", new Color(191,98,4) );
        //UIManager.put( "nimbusRed", new Color(169,46,34) );
        //UIManager.put( "nimbusSelectedText", new Color( 255, 255, 255) ); // ok

        //UIManager.put( "nimbusSelectionBackground", new Color( 66, 139, 221) ); // ok
        UIManager.put( "text", Color.BLACK);

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
