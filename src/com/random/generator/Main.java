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

        p.setNotes(new ArrayList<>(Arrays.asList("C", "D", "E", "F")));
        GeneratorIEEE1599 g1 = new GeneratorIEEE1599("", "ieee1599.xml", p);
        g1.generate_file();

        // example of GFG
        GFG g2 = new GFG();
        int arr[] = {1, 2, 3, 4};
        int probabilities[] = {30, 25, 25, 25}; // 30%, 25%, 25%, 25%
        int i, n = arr.length;

        // Let us generate 10 random numbers accroding to
        // given distribution
        for (i = 0; i < 5; i++)
            System.out.println(g2.myRand(arr, probabilities, n));

    }
}
