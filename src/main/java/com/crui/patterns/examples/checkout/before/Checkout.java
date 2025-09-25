package com.crui.patterns.examples.checkout.before;

 import java.util.*;

public class Checkout {
    public static void main(String[] args) {
        Producto libro = new Producto("Clean Code", 25.0);
        Producto mouse = new Producto("Mouse USB", 12.5);

        Carrito carrito = new Carrito();
        carrito.add(libro);
        carrito.add(mouse);

        Orden orden = new Orden(carrito);
        orden.incluirEnvoltorio(true);
        orden.incluirEnvioExpress(true);

        orden.addListener(new EmailListener());
        orden.addListener(new AnalyticsListener());

        String paymentType = "card";
        MedioDePago medioDePago;
        if ("card".equalsIgnoreCase(paymentType)) {
            medioDePago = new PagoTarjeta("Juan Perez", "4111111111111111");
        } else {
            medioDePago = new PagoEfectivo();
        }
        orden.setMedioDePago(medioDePago);

        orden.printSummary();
        orden.pagar();
    }
}