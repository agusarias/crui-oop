package com.crui.patterns.examples.checkout.after;

import com.crui.patterns.examples.checkout.after.domain.*;
import com.crui.patterns.examples.checkout.after.order.*;
import com.crui.patterns.examples.checkout.after.payment.*;
import com.crui.patterns.examples.checkout.after.pricing.*;

public class App {
  public static void main(String[] args) {
    // Productos base
    Producto libro = new Producto("Clean Code", 25.0);
    Producto mouse = new Producto("Mouse USB", 12.5);

    // Carrito
    Carrito carrito = new Carrito();
    carrito.add(libro);
    carrito.add(mouse);

    // PRICING con DECORATOR: subtotal + extras enchufables
    Pricing pricing = new CarritoPricing(carrito);     // componente base
    pricing = new EnvoltorioRegalo(pricing, 5.0);      // +$5
    pricing = new EnvioExpress(pricing, 10.0);         // +$10

    // Orden (Observer se mantiene)
    Orden orden = new Orden(carrito, pricing);
    orden.addListener(new EmailListener());
    orden.addListener(new AnalyticsListener());

    // STRATEGY de pago
    String paymentType = "card"; // "cash" | "card"
    MedioDePago medioDePago = "card".equalsIgnoreCase(paymentType)
        ? new PagoTarjeta("Juan Perez", "4111111111111111")
        : new PagoEfectivo();
    orden.setMedioDePago(medioDePago);

    // Resumen y pago
    orden.printSummary();
    orden.pagar();
  }
}
