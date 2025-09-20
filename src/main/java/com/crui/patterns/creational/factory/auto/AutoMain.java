package com.crui.patterns.creational.factory.auto;

import java.util.List;

public class AutoMain {

  public static void main(String[] args) {

    // Sin usar el patron factory
    // Tengo que conocer las opciones de cada parametro, tengo que saber si las combinaciones son
    // validas, realizar validaciones, etc.
    Auto auto2 =
        new Auto("Toyota", "Corolla", "1.8L", List.of("aire acondicionado", "GPS", "asientos de tela"));
    System.out.println(auto2);

    // Usando el patron factory
    // Se simplifica el uso de la clase Auto, no necesito conocer las opciones de cada parametro,
    // el código queda más claro y fácil de entender.
    Auto auto = AutoFactory.createAuto("coupe");
    System.out.println(auto);
    System.out.println(AutoFactory.createAutoSedan());
    System.out.println(AutoFactory.createAutoSUV());
    System.out.println(AutoFactory.createAutoHatchback());
    System.out.println(AutoFactory.createAutoCoupe());
  }
}