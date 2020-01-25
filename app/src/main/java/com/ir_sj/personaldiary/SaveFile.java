package com.ir_sj.personaldiary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import xute.markdeditor.MarkDEditor;


public class SaveFile {
    String location;

    public void WriteToFile(String myData) {
        File file = new File(location);

        try {
            FileWriter writer;
            writer = new FileWriter(file.getAbsoluteFile(), true);

            // Writes text to a character-output stream
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(myData.toString());
            bufferWriter.close();

            //log("Company data saved at file location: " + crunchify_file_location + " Data: " + myData + "\n");
        } catch (IOException e) {
            //log("Hmm.. Got an error while saving Company data to file " + e.toString());
        }
    }

    public String ReadFromFile() {
        String str=null;
        File file = new File(location);
        if (!file.exists())
            System.out.println("File doesn't exist");

        /*InputStreamReader isReader;
        try {
            isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");

            JsonReader myReader = new JsonReader(isReader);
            MarkDEditor markDEditor = new Gson().fromJson(myReader, EditorActivity.class);
            markDEditor.loadDraft();

        } catch (Exception e) {

        }*/
        BufferedReader objReader = null;
        try {
            String strCurrentLine;


            objReader = new BufferedReader(new FileReader(location));

            while ((strCurrentLine = objReader.readLine()) != null) {


                str= str + strCurrentLine;
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {
                if (objReader != null)
                    objReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return str;
    }



    }

