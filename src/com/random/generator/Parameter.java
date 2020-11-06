package com.random.generator;


import java.util.ArrayList;

// classe che riceverà i vari parametri per la generazione del file
public class Parameter {

    private int lenght;
    private int[] min_max_height = new int[2];
    private ArrayList<String> notes;
    private ArrayList<String> accidentals;
    private ArrayList<String> struments;
    private int[] min_max_numerator = new int[2];
    private int[] min_max_denominator = new int[2];

    public int[] getMin_max_numerator() {
        return min_max_numerator;
    }

    public void setMin_max_numerator(int min, int max) {
        this.min_max_numerator[0] = min; this.min_max_numerator[1] = max;
    }

    public int[] getMin_max_denominator() {
        return min_max_denominator;
    }

    public void setMin_max_denominator(int min, int max) {
        this.min_max_denominator[0] = min; this.min_max_denominator[1] = max;
    }

    public ArrayList<String> getStruments() {
        return struments;
    }

    public void setStruments(ArrayList<String> struments) {
        this.struments = struments;
    }

    public ArrayList<String> getAccidentals() {
        return new ArrayList<>(accidentals);
    }

    public void setAccidentals(ArrayList<String> accidental) {
        this.accidentals = accidental;
    }

    public Parameter(){}

    public void setLenght(int l){
        this.lenght = l;
    }

    public int getLenght() {
        return lenght;
    }

    public int[] getMin_max_height() {
        return min_max_height;
    }

    public ArrayList<String> getNotes() {
        return new ArrayList<>(notes);
    }

    public void setMin_max_height(int min, int max){
        min_max_height[0] = min; min_max_height[1] = max;
    }

    public void setNotes(ArrayList<String> notes){
        this.notes = new ArrayList<>(notes);
    }
}
