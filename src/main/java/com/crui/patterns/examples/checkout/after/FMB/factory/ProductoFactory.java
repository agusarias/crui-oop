package com.crui.patterns.examples.checkout.after.FMB.factory;

import java.util.List;
import java.util.Random;

import com.crui.patterns.examples.checkout.after.FMB.Producto;

public class ProductoFactory {
    private static List<String> nombresProducto = List.of(
                                                            "Teclado RGB",
                                                            "Gorra Hackaton",
                                                            "MousePad 'Virgen a los 40'",
                                                            "Tira LED RGB",
                                                            "Monitor 24 pulgadas",
                                                            "Monitor 27 pulgadas",
                                                            "Monitor 32 pulgadas",
                                                            "Monitor 43 pulgadas"
                                                        );

    Public static Producto create() {
        String nombre = nombresProducto.get(new Random().nextInt(nombresProducto.size()));
        double precio = new Random().nextDouble() * 990 + 10;   // range 10-1000
        return new Producto(nombre, precio);
    }
}