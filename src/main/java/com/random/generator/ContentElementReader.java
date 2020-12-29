package com.random.generator;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class ContentElementReader {
    private ArrayList<String> contents;

    ContentElementReader(String fileName) throws IOException, URISyntaxException {
        contents = new ArrayList<>();
        readContentIntoFile(fileName);
    }

    private void readContentIntoFile(String fileName) throws IOException, URISyntaxException {
        //URL resource = getClass().getClassLoader().getResource(fileName);
        //assert resource != null;
        //File file = new File(resource.toURI());
        //FileInputStream fstream = new FileInputStream(file);

        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String strLine;

        while ((strLine = br.readLine()) != null){
            strLine = strLine.replaceAll(" ", "_");
            contents.add(strLine.trim());
        }
        br.close();
    }

    public ArrayList<String> getContentsOfElement(){
        return new ArrayList<>(contents);
    }
}
