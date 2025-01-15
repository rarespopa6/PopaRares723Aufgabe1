package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        List<SpielOrt> spielOrte = getFromJsonFile();

        for (SpielOrt ort : spielOrte) {
            System.out.println(ort);
        }


    }

    public static List<SpielOrt> getFromJsonFile() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateJson()) // adaptor LocalDate JSON
                .create();

        List<SpielOrt> data = new ArrayList<>();

        try (FileReader reader = new FileReader("spielorte.json")) {
            SpielOrt[] myData = gson.fromJson(reader, SpielOrt[].class);

            return Arrays.stream(myData).toList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }


}