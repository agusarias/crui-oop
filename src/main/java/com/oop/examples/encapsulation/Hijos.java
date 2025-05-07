package com.oop.examples.encapsulation;

import javax.print.attribute.standard.MediaSize.NA;

public class Hijos extends Person {
    private String tipePlay;
    private String club;

    public Hijos(String name, int age, String email, String tipePlay, String club) {
        super(name, age, email);
        this.tipePlay = tipePlay;
        this.club = club;
    }

    public String getTipePlay() {
        return tipePlay;
    }

    public String getClub() {
        return club;
    }

    public void realizoTarea(String name) {
        boolean okTarea;
        if (okTarea = true) {
            System.out.println("Podes hacer deporte " + name);
        } else {
            System.out.println("No podes hacer deporte " + name);
        }

    }

}
