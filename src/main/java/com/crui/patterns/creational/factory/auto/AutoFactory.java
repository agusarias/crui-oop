package com.crui.patterns.creational.factory.auto;

import java.util.List;

public class AutoFactory {

  public static Auto createAutoSedan() {
    return AutoFactory.createAuto("sedan");
  }

  public static Auto createAutoSUV() {
    return AutoFactory.createAuto("suv");
  }

  public static Auto createAutoHatchback() {
    return AutoFactory.createAuto("hatchback");
  }

  public static Auto createAutoCoupe() {
    return AutoFactory.createAuto("coupe");
  }

  public static Auto createAuto(String type) {
    switch (type) {
      case "sedan":
        return new Auto("Toyota", "Corolla", "1.8L", List.of("aire acondicionado", "GPS", "asientos de tela"));
      case "suv":
        return new Auto(
            "Honda", "CR-V", "2.0L", List.of("tracción 4x4", "asientos de cuero", "techo corredizo"));
      case "hatchback":
        return new Auto("Volkswagen", "Golf", "1.4L Turbo", List.of("pantalla táctil", "cámara de estacionamiento"));
      case "coupe":
        return new Auto(
            "BMW",
            "320i",
            "2.0L Turbo",
            List.of("asientos deportivos", "suspensión deportiva", "llantas de aleación", "escape deportivo"));
      default:
        return new Auto("Chevrolet", "Spark", "1.2L", List.of("estéreo", "aire acondicionado"));
    }
  }
}