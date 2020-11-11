package com.random.generator;


import sun.reflect.generics.tree.Tree;

import java.lang.reflect.Array;
import java.util.*;

// classe che riceverà i vari parametri per la generazione del file
public class Parameter {

    private int lenght;
    private int[] min_max_height = new int[2];
    private TreeMap<String, Integer> notes;

    public TreeMap<String, Integer> getNotes() {
        return notes;
    }

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

    public void setMin_max_height(int min, int max){
        min_max_height[0] = min; min_max_height[1] = max;
    }

    public void setNotes(TreeMap<String,Integer> notes){
        this.notes = new TreeMap<String, Integer>(notes);
    }

    public String generate_note_with_probability(){
        GFG<String> g = new GFG<>();
        ArrayList<String> arr = new ArrayList<>();
        ArrayList<Integer> freq = new ArrayList<>();
        Set<Map.Entry<String, Integer>> entrySet = this.notes.entrySet();
        List<Map.Entry<String, Integer>> listEntries = new ArrayList<>(entrySet);

        for(Map.Entry<String, Integer> entry : listEntries ){
            arr.add(entry.getKey());
            freq.add(entry.getValue());
        }

        int i, n = arr.size();
        return g.myRand(arr.toArray(new String[arr.size()]), freq.toArray(new Integer[freq.size()]), n);
    }
}
