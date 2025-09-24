package com.crui.patterns.examples.checkoutBA;

import java.util.*;

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
    String amount = "37.5";
    Pago pago;
    if ("card".equalsIgnoreCase(paymentType)) {
      pago = new Pago(amount, new PagoTarjetaStrategy("Juan Perez", 411111111));
    } else {
      pago = new Pago(amount, new PagoEfectivoStrategy()); 
    }
    
    orden.setMedioDePago(pago.getMedioDePago());

    // Muestra totales y paga
    orden.printSummary();
    orden.pagar();
  }

  // ===================== Dominio =====================

  static class Producto {
    private final String nombre;
    private final double precio;

    public Producto(String nombre, double precio) {
      this.nombre = nombre;
      this.precio = precio;
    }

    public String getNombre() {
      return nombre;
    }

    public double getPrecio() {
      return precio;
    }

    @Override
    public String toString() {
      return nombre + " ($" + precio + ")";
    }
  }

  //Posible Singleton???
  static class Carrito {
    private final List<Producto> items = new ArrayList<>();

    public void add(Producto p) {
      items.add(p);
    }

    public List<Producto> getItems() {
      return Collections.unmodifiableList(items);
    }

    public double subtotal() {
      double s = 0;
      for (Producto p : items) s += p.getPrecio();
      return s;
    }

    @Override
    public String toString() {
      return items.toString();
    }
  }

  // ===================== ORDER + OBSERVER =====================

  interface OrdenEventListener {
    void onPaid(Orden order);
  }

  static class EmailListener implements OrdenEventListener {
    @Override
    public void onPaid(Orden order) {
      System.out.println("[Email] Enviando comprobante a cliente. Total: $" + order.total());
    }
  }

  static class AnalyticsListener implements OrdenEventListener {
    @Override
    public void onPaid(Orden order) {
      System.out.println(
          "[Analytics] Registrando venta. Ítems: " + order.getCarrito().getItems().size());
    }
  }

  static class Orden {
    private final Carrito carrito;
    private final List<OrdenEventListener> listeners = new ArrayList<>();
    private MedioDePago paymentGateway;

    // “Extras” modelados con flags (candidato a DECORATOR)
    private boolean envoltorioRegalo; // +$5
    private boolean envioExpress; // +$10

    public Orden(Carrito carrito) {
      this.carrito = carrito;
    }

    public Carrito getCarrito() {
      return carrito;
    }

    public void addListener(OrdenEventListener l) {
      listeners.add(l);
    }

    public void setMedioDePago(MedioDePago p) {
      this.paymentGateway = p;
    }

    public void incluirEnvoltorio(boolean v) {
      this.envoltorioRegalo = v;
    }

    public void incluirEnvioExpress(boolean v) {
      this.envioExpress = v;
    }

    public double total() {
      double total = carrito.subtotal();
      // Costo extra hardcodeado (refactor -> DECORATOR sobre Product o sobre un "PricedComponent")
      if (envoltorioRegalo) total += 5.0;
      if (envioExpress) total += 10.0;
      return total;
    }

    public void printSummary() {
      System.out.println("=== ORDER SUMMARY ===");
      System.out.println("Items: " + carrito);
      System.out.println("Subtotal: $" + carrito.subtotal());
      System.out.println("Envoltorio regalo: " + (envoltorioRegalo ? "Si (+$5)" : "No"));
      System.out.println("Envio express: " + (envioExpress ? "Si (+$10)" : "No"));
      System.out.println("TOTAL: $" + total());
    }

    public void pagar() {
      if (paymentGateway == null) {
        System.out.println("No hay gateway de pago configurado.");
        return;
      }
      boolean ok = paymentGateway.pay(total());
      if (ok) {
        System.out.println("Pago exitoso por $" + total());
        notifyPaid();
      } else {
        System.out.println("Pago rechazado.");
      }
    }

    private void notifyPaid() {
      for (OrdenEventListener l : listeners) l.onPaid(this);
    }
  }

  // ===================== API externa =====================

  /** Esta API de pagos es externa y no podemos modificarla. Falta integrarla */
  static class MercadoPagoAPI {
    public boolean runPayment(int amountInCents) {
      System.out.println("[MercadoPagoAPI] Procesando " + amountInCents + " centavos...");
      // Lógica ficticia: acepta todo hasta 15.000 centavos (150.00)
      return amountInCents <= 15000;
    }
  }
}
