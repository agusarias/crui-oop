package com.crui.patterns.structural.decoratorIgnacio;

public class Main {
    public static void main (String[] args) {
        Bebida base = new CafeSimple();
        System.out.println(base.getDescripcion() + " $" + base.getCosto());

        Bebida conLeche = new Leche(base);
        System.out.println(conLeche.getDescripcion() + " $" + conLeche.getCosto());

        Bebida conLecheYAzucar = new Azucar(conLeche);
        System.out.println(conLecheYAzucar.getDescripcion() + " $" + conLecheYAzucar.getCosto());

        Bebida conLecheAzucarYCrema = new Crema(conLecheYAzucar);
        System.out.println(conLecheAzucarYCrema.getDescripcion() + " $" + conLecheAzucarYCrema.getCosto());

    }
} 

