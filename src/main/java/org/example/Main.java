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

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the capacity: ");
        int capacity = scanner.nextInt();
        showGamesWithCapacity(spielOrte, capacity);

        System.out.println("-------------------------------------------------");
        System.out.println("Spiele in München nach 30.06.2024");
        showGamesInMunichAfterDate(spielOrte, LocalDate.of(2024, 6, 30));

        System.out.println("-------------------------------------------------");
        writeToFileNumberOfGamesPerCity();
        System.out.println("Spiele pro Stadt in spielanzahl.txt geschrieben");
    }

    /**
     * Read data from JSON file
     * @return List of SpielOrt objects
     */
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

    /**
     * Show games with capacity greater or equal to the given capacity
     * @param spielOrte List of SpielOrt objects
     * @param capacity Capacity to compare
     */
    public static void showGamesWithCapacity(List<SpielOrt> spielOrte, int capacity) {
        for (SpielOrt ort : spielOrte) {
            if (ort.getKapazitat() >= capacity) {
                System.out.println(ort.getTeam1() + " vs " + ort.getTeam2() + " in " + ort.getSpielOrt() + " am " + ort.getDatum() + " mit Kapazitat: " + ort.getKapazitat());
            }
        }
    }

    /**
     * Show games in Munich after the given date
     * @param spielOrte List of SpielOrt objects
     * @param date Date to compare
     */
    public static void showGamesInMunichAfterDate(List<SpielOrt> spielOrte, LocalDate date) {
        for (SpielOrt ort : spielOrte) {
            if (ort.getSpielOrt().equals("München") && ort.getDatum().isAfter(date)) {
                System.out.println(ort.getTeam1() + " vs " + ort.getTeam2() + " in " + ort.getSpielOrt() + " am " + ort.getDatum() + " mit Kapazitat: " + ort.getKapazitat());
            }
        }
    }

    /**
     * Count the number of games per city
     * @param spielOrte List of SpielOrt objects
     * @return Map with the number of games per city
     */
    private static Map<String, Integer> countNumberOfGamesPerCity(List<SpielOrt> spielOrte) {
        Map<String, Integer> spielOrtMap = new HashMap<>();
        for (SpielOrt spielOrt : spielOrte) {
            spielOrtMap.put(spielOrt.getSpielOrt(), spielOrtMap.getOrDefault(spielOrt.getSpielOrt(), 0) + 1);
        }

        return spielOrtMap;
    }

    /**
     * Sort the number of games per city
     * @param spielOrtMap Map with the number of games per city
     * @return List of Maps with the sorted number of games per city
     */
    private static List<Map<String, Integer>> sortNumberOfGamesPerCity(Map<String, Integer> spielOrtMap) {
        List<Map<String, Integer>> sortedList = new ArrayList<>();
        spielOrtMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
                .forEachOrdered(x -> sortedList.add(Collections.singletonMap(x.getKey(), x.getValue())));

        return sortedList;
    }

    /**
     * Write the number of games per city to a file
     */
    public static void writeToFileNumberOfGamesPerCity() {
        Map<String, Integer> spielOrtMap = countNumberOfGamesPerCity(getFromJsonFile());
        List<Map<String, Integer>> sortedList = sortNumberOfGamesPerCity(spielOrtMap);

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("spielanzahl.txt"));
            for (Map<String, Integer> map : sortedList) {
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    writer.write(entry.getKey() + "%" + entry.getValue() + "\n");
                }
            }
        }   catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}