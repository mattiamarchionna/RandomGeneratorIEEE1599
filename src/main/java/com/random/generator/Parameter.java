package com.random.generator;

import java.util.*;

// classe che riceverà i vari parametri per la generazione del file
public class Parameter {

    private int lenght;
    private TreeMap<String, Integer> notes;
    private String min_duration;
    private String max_duration;
    private String path;
    private boolean onlyNote;
    private boolean onlyRest;
    private boolean bothNoteRest;

    private ArrayList<String> durations = new ArrayList<>();

    public boolean isOnlyNote() {
        return onlyNote;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isOnlyRest() {
        return onlyRest;
    }

    public boolean isBothNoteRest() {
        return bothNoteRest;
    }

    public void setOnlyNote(boolean onlyNote) {
        this.onlyNote = onlyNote;
    }

    public void setOnlyRest(boolean onlyRest) {
        this.onlyRest = onlyRest;
    }

    public void setBothNoteRest(boolean bothNoteRest) {
        this.bothNoteRest = bothNoteRest;
    }

    private int min_height;
    private int max_height;

    private int number_struments;
    private ArrayList<String> accidentals;

    public Parameter(){
        for(int j = 1; j < 5; j++) {
            for (int i = 0; i < 24; i++) {
                String s = j + "/" + (int) Math.pow(2, i);
                durations.add(s);
            }
        }
    }


    public void setMin_height(int min_height) {
        this.min_height = min_height;
    }

    public void setMax_height(int max_height) {
        this.max_height = max_height;
    }

    /*private String convertDecimalToFraction(double x){
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
    }*/

    public int getNumber_struments(){
        return number_struments;
    }

    public void setNumber_struments(int n){
        number_struments = n;
    }

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
        ArrayList<String> validDuration = new ArrayList<>();
        for(String s : durations){
            double d1 = fromFractionToDouble(s);
            double d2 = fromFractionToDouble(min_duration);
            double d3 = fromFractionToDouble(max_duration);
            if(d1 >= d2 && d1 <= d3) {
                validDuration.add(s);
            }
        }
        Random r = new Random();
        String d = validDuration.get(r.nextInt(validDuration.size()));
        return d.split("/");
    }

    public String generate_height(){
        return String.valueOf(new Random().nextInt(max_height-min_height) + min_height);
    }


    private double fromFractionToDouble(String s){
        s = s.trim();
        String[] digits = s.split("/");
        return Double.parseDouble(digits[0]) / Double.parseDouble(digits[1]);
    }
}
