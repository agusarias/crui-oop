package com.crui.patterns.behavioral.strategy;

public class Main {
    public static void main(String[] args) {
        CarritoContexto carrito = new CarritoContexto();
        carrito.agregarItem(50.0);
        carrito.agregarItem(75.0);
        carrito.agregarItem(125.0);

        System.out.println("--- Compra con Descuento por Temporada ---");
        carrito.setEstrategiaDescuento(new DescuentoPorTemporada());
        carrito.procesarCompra();

        System.out.println("\n--- Compra con Descuento por Cliente Frecuente ---");
        carrito.setEstrategiaDescuento(new DescuentoClienteFrecuente());
        carrito.procesarCompra();
    }
}
