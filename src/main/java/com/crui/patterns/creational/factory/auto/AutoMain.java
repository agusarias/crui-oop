package com.crui.patterns.creational.factory.auto;

import java.util.List;

public class AutoMain {

  public static void main(String[] args) {

    // Ejemplo sin usar el patron factory

    Auto auto2 =
        new Auto("Toyota", "Corolla", "1.8L", List.of("aire acondicionado", "GPS", "asientos de tela"));
    System.out.println(auto2);

    // Usando el patron factory

    Auto auto = AutoFactory.createAuto("coupe");
    System.out.println(auto);
    System.out.println(AutoFactory.createAutoSedan());
    System.out.println(AutoFactory.createAutoSUV());
    System.out.println(AutoFactory.createAutoHatchback());
    System.out.println(AutoFactory.createAutoCoupe());
  }
}