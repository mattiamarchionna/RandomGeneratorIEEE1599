package com.random.generator;


import sun.reflect.generics.tree.Tree;

import java.util.*;

// classe che riceverà i vari parametri per la generazione del file
public class Parameter {

    private int lenght;
    private TreeMap<String, Integer> notes;
    private String min_duration;
    private String max_duration;

    private int min_height;
    private int max_height;

    private int number_struments;
    private ArrayList<String> accidentals;

    public Parameter(){}


    public void setMin_height(int min_height) {
        this.min_height = min_height;
    }

    public void setMax_height(int max_height) {
        this.max_height = max_height;
    }


    private String convertDecimalToFraction(double x){
        if (x < 0){
            return "-" + convertDecimalToFraction(-x);
        }
        double tolerance = 1.0E-6;
        double h1=1; double h2=0;
        double k1=0; double k2=1;
        double b = x;
        do {
            double a = Math.floor(b);
            double aux = h1; h1 = a*h1+h2; h2 = aux;
            aux = k1; k1 = a*k1+k2; k2 = aux;
            b = 1/(b-a);
        } while (Math.abs(x-h1/k1) > x*tolerance);

        return ((int)h1)+"/"+((int)k1);
    }


    public int getNumber_struments(){ return number_struments; }

    public void setNumber_struments(int n){ number_struments = n; }

    public void setAccidentals(ArrayList<String> accidental) {
        this.accidentals = accidental;
    }


    public void setLenght(int l){
        this.lenght = l;
    }

    public int getLenght() {
        return lenght;
    }

    public void setNotes(TreeMap<String,Integer> notes){
        this.notes = new TreeMap<String, Integer>(notes);
    }

    public String generate_note_with_probability(){
        RandomValueProbability<String> g = new RandomValueProbability<>();
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


    public String getMin_duration() {
        return min_duration;
    }

    public void setMin_duration(String min_duration) {
        this.min_duration = min_duration;
    }

    public String getMax_duration() {
        return max_duration;
    }

    public void setMax_duration(String max_duration) {
        this.max_duration = max_duration;
    }

    public String[] generate_duration(){
        // min_duration e.g 1/2
        String[] min_duration_split = min_duration.split("/");
        int min_num = Integer.parseInt(min_duration_split[0]);
        int min_den = Integer.parseInt(min_duration_split[1]);
        double min = (Math.round((((float)min_num)/min_den) * 10) / 10.0);


        String[] max_duration_split = max_duration.split("/");
        int max_num = Integer.parseInt(max_duration_split[0]);
        int max_den = Integer.parseInt(max_duration_split[1]);
        double max = (Math.round((((float)max_num)/max_den) * 10) / 10.0);

        Random r = new Random();
        return convertDecimalToFraction(min + (Math.round(r.nextFloat() * 10) / 10.0) * (max - min)).split("/");
    }

    public String generate_height(){
        return String.valueOf(new Random().nextInt(max_height-min_height) + min_height);
    }
}
