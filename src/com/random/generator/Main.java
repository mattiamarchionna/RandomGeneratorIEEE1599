package com.random.generator;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args){
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

    }
}
