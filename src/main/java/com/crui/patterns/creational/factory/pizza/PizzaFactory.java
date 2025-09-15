package com.crui.patterns.creational.factory.pizza;

import java.util.List;

public class PizzaFactory {

  public static Pizza createPizzaCuatroQuesos() {
    return PizzaFactory.createPizza("cuatro quesos");
  }

  public static Pizza createPizzaFugazzeta() {
    return PizzaFactory.createPizza("fugazzeta");
  }

  public static Pizza createPizzaNapolitana() {
    return PizzaFactory.createPizza("napolitana");
  }

  public static Pizza createPizzaMuzzarella() {
    return PizzaFactory.createPizza("muzzarella");
  }

  public static Pizza createPizza(String type) {
    switch (type) {
      case "muzzarella":
        return new Pizza("muzzarella", "normal", "tomate", List.of("oregano", "muzzarella doble"));
      case "napolitana":
        return new Pizza(
            "napolitana", "normal", "tomate", List.of("tomate", "aceituna", "muzzarella"));
      case "fugazzeta":
        return new Pizza("fugazzeta", "cebolla", "sin salsa", List.of("cebolla", "queso"));
      case "cuatro quesos":
        return new Pizza(
            "cuatro quesos",
            "normal",
            "sin salsa",
            List.of("parmesano", "fontina", "muzzarella", "gorgonzola"));
      default:
        return new Pizza("default", "normal", "tomate", List.of("oregano", "muzzarella"));
    }
  }
}
