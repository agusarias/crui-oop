package com.crui.patterns.examples.checkout.before;

import java.util.*;

/**
 * Contestar a continuación las siguientes preguntas:
 *
 * <p>- Qué patrón de diseño podés identificar en el código dado?
 *
 * <p>* Strategy: Los medios de pago se implementan usando una interfaz para generalizar su
 * funcionamiento. Esto permite que agregar medios de pago en el futuro sea mas sencillo, y no
 * implique modificar la lógica en todo el código.
 *
 * <p>* Observer: Cuando una orden está paga, se quiere notificar al cliente y guardar metricas.
 * Para eso se utiliza el observer, evitando poner lógica de notificación y métricas en Order, lo
 * que implicaria romper el Single Responsability Principle.
 *
 * <p>- Qué patrones de diseño podrías agregar para mejorar el código?
 *
 * <p>Adapter: Para incluir Mercado Pago como medio de pago, hay que adaptar la API a la interfaz
 * MedioDePago.
 *
 * <p>Factory: La creación de las instancias de MedioDePago requiere configuración y va a crecer con
 * el tiempo. Conviene centralizar esa lógica en una clase factory.
 *
 * <p>Implementar uno o más de esos patrones.
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

    IOrden orden = new Orden(carrito);

    if (true) {
      orden = new OrdenConEnvoltorioRegalo(orden);
    }

    if (orden.calcularTotal() > 10) {
      orden = new OrdenConEnvioExpress(orden);
    }

    orden.addListener(new EmailListener());
    orden.addListener(new AnalyticsListener());
    orden.addListener(new LoggingListener());

    String paymentType = "card"; // puede ser "cash", "card", "mercado-pago"

    MedioDePago medioDePago = MedioDePagoFactory.create(paymentType);
    orden.setMedioDePago(medioDePago);

    // Muestra totales y paga
    double total = orden.calcularTotal();
    orden.pagar(total);
  }

  static class MedioDePagoFactory {

    static MedioDePago create(String paymentType) {
      switch (paymentType) {
        case "card":
          return new PagoTarjeta("Juan Perez", "4111111111111111");
        case "mp":
          return new MercadoPago(MedioDePagoFactory.getMercadoPagoAPIKey());
        default:
          return new PagoEfectivo();
      }
    }

    private static String getMercadoPagoAPIKey() {
      return "user_and_pass";
    }
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
    void onPaid(Orden order); // Se llama cuando la orden fue pagada

    void onRejected(Orden order); // Se llama cuando la order fue rechazada
  }

  static class EmailListener implements OrdenEventListener {
    @Override
    public void onPaid(Orden order) {
      System.out.println(
          "[Email] Enviando comprobante a cliente. Total: $" + order.calcularTotal());
    }

    @Override
    public void onRejected(Orden order) {
      System.out.println("[Email] La orden fue rechazada por un problema en el pago.");
    }
  }

  static class AnalyticsListener implements OrdenEventListener {
    @Override
    public void onPaid(Orden order) {
      System.out.println(
          "[Analytics] Registrando venta. Ítems: " + order.getCarrito().getItems().size());
    }

    @Override
    public void onRejected(Orden order) {
      System.out.println("[Analytics] Registrando venta rechazada");
    }
  }

  static class LoggingListener implements OrdenEventListener {
    @Override
    public void onPaid(Orden order) {
      System.out.println("=== ORDER SUMMARY ===");
      System.out.println("Items: " + order.carrito);
      System.out.println("Subtotal: $" + order.carrito.subtotal());
      System.out.println("TOTAL: $" + order.calcularTotal());
    }

    @Override
    public void onRejected(Orden order) {
      System.out.println("=== ORDER REJECTED SUMMARY ===");
      System.out.println("Items: " + order.carrito);
      System.out.println("Subtotal: $" + order.carrito.subtotal());
      System.out.println("TOTAL: $" + order.calcularTotal());
    }
  }

  interface IOrden {
    public void addListener(OrdenEventListener l);

    public void setMedioDePago(MedioDePago m);

    public double calcularTotal();

    public void pagar(double monto);
  }

  static class OrdenConEnvoltorioRegalo implements IOrden {
    IOrden orden;

    OrdenConEnvoltorioRegalo(IOrden orden) {
      this.orden = orden;
    }

    public void addListener(OrdenEventListener l) {
      this.orden.addListener(l);
    }

    public void setMedioDePago(MedioDePago m) {
      this.orden.setMedioDePago(m);
    }

    public double calcularTotal() {
      double total = this.orden.calcularTotal();
      double totalActualizado = total + 5;
      return totalActualizado;
    }

    public void pagar(double monto) {
      this.orden.pagar(monto);
    }
  }

  static class OrdenConEnvioExpress implements IOrden {
    IOrden orden;

    OrdenConEnvioExpress(IOrden orden) {
      this.orden = orden;
    }

    public void addListener(OrdenEventListener l) {
      this.orden.addListener(l);
    }

    public void setMedioDePago(MedioDePago m) {
      this.orden.setMedioDePago(m);
    }

    public double calcularTotal() {
      double total = this.orden.calcularTotal();
      double totalActualizado = total + 10;
      return totalActualizado;
    }

    public void pagar(double monto) {
      this.orden.pagar(monto);
    }
  }

  static class Orden implements IOrden {
    private final Carrito carrito;
    private final List<OrdenEventListener> listeners = new ArrayList<>();
    private MedioDePago paymentGateway;

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

    public double calcularTotal() {
      double total = carrito.subtotal();
      return total;
    }

    public void pagar(double total) {
      if (paymentGateway == null) {
        System.out.println("No hay gateway de pago configurado.");
        return;
      }
      boolean ok = paymentGateway.pay(total);
      if (ok) {
        System.out.println("Pago exitoso por $" + total);
        notifyPaid();
      } else {
        System.out.println("Pago rechazado.");
        notifyRejected();
      }
    }

    private void notifyPaid() {
      for (OrdenEventListener listener : this.listeners) {
        listener.onPaid(this);
      }
    }

    private void notifyRejected() {
      for (OrdenEventListener listener : this.listeners) {
        listener.onRejected(this);
      }
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

  static class MercadoPago implements MedioDePago {
    private MercadoPagoAPI client;

    public MercadoPago(String user_and_pass) {
      this.client = new MercadoPagoAPI(user_and_pass);
    }

    public boolean pay(double amount) {
      int amountInCents = (int) Math.round(amount * 100);
      return client.runPayment(amountInCents);
    }
  }

  // ===================== API externa =====================

  /** Esta API de pagos es externa y no podemos modificarla. Falta integrarla */
  static class MercadoPagoAPI {
    String user_and_pass = null; // ~contraseña

    public MercadoPagoAPI(String user_and_pass) {
      this.user_and_pass = user_and_pass;
    }

    public boolean runPayment(int amountInCents) {
      System.out.println(
          "[MercadoPagoAPI] Procesando "
              + amountInCents
              + " centavos... (con api key: "
              + this.user_and_pass
              + ")");
      // Lógica ficticia: acepta todo hasta 15.000 centavos (150.00)
      return amountInCents <= 15000;
    }
  }
}
