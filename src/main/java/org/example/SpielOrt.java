package org.example;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class SpielOrt {
    @SerializedName("Id")
    private int id;

    @SerializedName("Team1")
    private String team1;

    @SerializedName("Team2")
    private String team2;

    @SerializedName("Datum")
    private LocalDate datum;

    @SerializedName("Spielort")
    private String spielOrt;

    @SerializedName("Kapazität")
    private int kapazitat;

    public SpielOrt() {}

    public SpielOrt(int id, String team1, String team2, LocalDate datum, String spielOrt, int kapazitat) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.datum = datum;
        this.spielOrt = spielOrt;
        this.kapazitat = kapazitat;
    }

    // Getters și Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getSpielOrt() {
        return spielOrt;
    }

    public void setSpielOrt(String spielOrt) {
        this.spielOrt = spielOrt;
    }

    public int getKapazitat() {
        return kapazitat;
    }

    public void setKapazitat(int kapazitat) {
        this.kapazitat = kapazitat;
    }

    @Override
    public String toString() {
        return "Spielort{" +
                "id=" + id +
                ", team1='" + team1 + '\'' +
                ", team2='" + team2 + '\'' +
                ", datum=" + datum +
                ", spielOrt='" + spielOrt + '\'' +
                ", kapazitat=" + kapazitat +
                '}';
    }
}
