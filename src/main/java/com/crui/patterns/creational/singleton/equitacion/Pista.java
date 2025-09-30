package com.crui.patterns.creational.singleton.equitacion;

// se crea la clase Pista, representa una pista de equitación
public class Pista {
    private String nombre; //atributo privado nombre solo accesible dentro de la clase pista

    //constructor que recibe el nombre de la pista
    public Pista(String nombre) {
        this.nombre = nombre;
    }

    // método público para obtener el nombre de la pista
    public String getNombre() {
        return nombre;
    }
}
