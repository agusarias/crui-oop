package com.crui.patterns.examples.checkout.after.FMB;

import java.util.*;

import com.crui.patterns.examples.checkout.after.FMB.*; // Wildcard. Fix once we refactor and have all
// actual dependencies.

/**
 * Contestar a continuación las siguientes preguntas: - Qué patrón de diseño podés identificar en el
 * código dado? - Qué patrón de diseño podrías agregar para mejorar el código?
 *
 * <p>Implementar UN patrón adicional para mejorar el código.
 */
public class Checkout {

  public static void main(String[] args) {
    // Productos base
    Producto libro = new Producto("Clean Code", 25.0);
    Producto mouse = new Producto("Mouse USB", 12.5);

    // Armo el carrito
    Carrito carrito = new Carrito();
    carrito.add(libro);
    carrito.add(mouse);

    Orden orden = new Orden(carrito);
    orden.incluirEnvoltorio(true);
    orden.incluirEnvioExpress(true);

    orden.addListener(new EmailListener());
    orden.addListener(new AnalyticsListener());

    String paymentType = "card"; // puede ser "cash", "card", "mercado-pago"
    MedioDePago medioDePago;
    if ("card".equalsIgnoreCase(paymentType)) {
      medioDePago = new PagoTarjeta("Juan Perez", "4111111111111111");
    } else {
      medioDePago = new PagoEfectivo();
    }
    orden.setMedioDePago(medioDePago);

    // Muestra totales y paga
    orden.printSummary();
    orden.pagar();
  }
}
