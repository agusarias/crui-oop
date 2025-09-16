package com.crui.patterns.creational.factory.pizza;

import java.util.List;

public class PizzaMain {

  public static void main(String[] args) {

    // Sin usar el patron factory
    // Tengo que conocer las opciones de cada parametro, tengo que saber si las combinaciones son
    // validas, realizar validaciones, etc.
    Pizza pizza2 =
        new Pizza("muzzarella", "normal", "tomate", List.of("oregano", "muzzarella doble"));
    System.out.println(pizza2);

    // Usando el patron factory
    // Se simplifica el uso de la clase Pizza, no necesito conocer las opciones de cada parametro,
    // el código queda más claro y fácil de entender.
    Pizza pizza = PizzaFactory.createPizza("cuatro quesos");
    System.out.println(pizza);
    System.out.println(PizzaFactory.createPizzaFugazzeta());
    System.out.println(PizzaFactory.createPizzaNapolitana());
    System.out.println(PizzaFactory.createPizzaMuzzarella());
    System.out.println(PizzaFactory.createPizzaCuatroQuesos());
  }
}
