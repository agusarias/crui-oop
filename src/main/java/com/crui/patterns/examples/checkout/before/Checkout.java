package com.crui.patterns.examples.checkout.before;

import java.util.*;

/**
 * Contestar a continuación las siguientes preguntas: - Qué patrón de diseño podés identificar en el
 * código dado?
 * 1- Strategy (MedioDePago)
 * 2- Observer (listeners de Orden) 
 * Qué patrón de diseño podrías agregar para mejorar el código?
 * 1- Adapter (PagoMercadoPago)
 * 2- Decorator (Extras de Orden) 
 *
 * <p>Implementar UN patrón adicional para mejorar el código.
 * NO TERMINE
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

    // Configuro medio de pago
    String paymentType = "card"; // puede ser "cash", "card", "mercado-pago"
    MedioDePago medioDePago;
    switch (paymentType) {
      case "cash":
        medioDePago = new PagoEfectivo();
        break;
      case "card":
        medioDePago = new PagoTarjeta("Juan Perez", "1234-5678-9012-3456");
        break;
      case "mercado-pago":
        medioDePago = new PagoMercadoPago();
        break;
      default:
        throw new RuntimeException("Medio de pago inválido: " + paymentType);
    }
    orden.setMedioDePago(medioDePago);

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

    private PricedComponent pricedComponent; // para DECORATOR

    // “Extras” modelados con flags (candidato a DECORATOR)
    private boolean envoltorioRegalo; // +$5
    private boolean envioExpress; // +$10

    public Orden(Carrito carrito) {
      this.carrito = carrito;
      this.pricedComponent = new CarritoPrice(carrito); // DECORATOR base
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

    /** 
    *public double total() {
    *  double total = carrito.subtotal();
    *  // Costo extra hardcodeado (refactor -> DECORATOR sobre Product o sobre un "PricedComponent")
    *  if (envoltorioRegalo) total += 5.0;
    *  if (envioExpress) total += 10.0;
    *  return total;
    *}
    **/

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

  // ===================== STRATEGY (Pago) =====================

  interface MedioDePago {
    boolean pay(double amount);
  }

  static class PagoEfectivo implements MedioDePago {
    @Override
    public boolean pay(double amount) {
      System.out.println("[Cash] Recibidos $" + amount + " en efectivo.");
      return true;
    }
  }

  static class PagoTarjeta implements MedioDePago {
    private final String holder;
    private final String cardNumber;

    public PagoTarjeta(String holder, String cardNumber) {
      this.holder = holder;
      this.cardNumber = cardNumber;
    }

    @Override
    public boolean pay(double amount) {
      // Simulación trivial
      System.out.println("[Card] Autorizando tarjeta de " + holder + " ****" + last4());
      return amount <= 100.0; // regla falsa: si pasa de 100, falla
    }

    private String last4() {
      if (cardNumber == null || cardNumber.length() < 4) return "????";
      return cardNumber.substring(cardNumber.length() - 4);
    }
  }

// ===================== DECORATOR (Extras) =====================

  interface PricedComponent {
    double Price(); 
  }

  public class CarritoPrice implements PricedComponent {
    private final Carrito carrito;

    public CarritoPrice(Carrito carrito) {
      this.carrito = carrito;
    }

    @Override
    public double Price() {
      return carrito.subtotal();
    }
  } 

  public class EnvoltorioRegalo implements PricedComponent {
    private final PricedComponent component;

    public EnvoltorioRegalo(PricedComponent component) {
      this.component = component;
    }

    @Override
    public double Price() {
      return component.Price() + 5.0;
    }
  }

  public class EnvioExpress implements PricedComponent {
    private final PricedComponent component;

    public EnvioExpress(PricedComponent component) {
      this.component = component;
    }

    @Override
    public double Price() {
      return component.Price() + 10.0;
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

  // Adapter de MercadoPagoAPI a MedioDePago

  static class PagoMercadoPago implements MedioDePago {
    private final MercadoPagoAPI api = new MercadoPagoAPI();

    @Override
    public boolean pay(double amount) {
      int amountInCents = (int) (amount * 100);
      return api.runPayment(amountInCents);
    }
  }

}
