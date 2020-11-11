package com.random.generator;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException {
        Parameter p = new Parameter();
        p.setLenght(2000);
        p.setMin_max_height(3, 8);
        p.setMin_max_numerator(1, 5);
        p.setMin_max_denominator(1, 5);

        // SHARP, FLAT, NATURAL, DOUBLESHARP, DOUBLEFLAT, UNDEFINED
        p.setAccidentals(new ArrayList<>(Arrays.asList("NATURAL", "DOUBLEFLAT", "FLAT", "SHARP")));

        p.setStruments(new ArrayList<>(Arrays.asList("Violino1", "Piano1", "Piano2", "Violino2")));


        TreeMap<String, Integer> notes_freq = new TreeMap<>();
        notes_freq.put("C", 10); notes_freq.put("D", 20); notes_freq.put("E", 5);
        notes_freq.put("F", 5); notes_freq.put("G", 40); notes_freq.put("A", 10);
        notes_freq.put("B", 20);
        p.setNotes(notes_freq);


        GeneratorIEEE1599 g1 = new GeneratorIEEE1599("", "ieee1599.xml", p);
        g1.generate_file();


    }
}
