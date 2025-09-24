package com.crui.patterns.examples.checkout.before;

import java.util.*;

/**
 * Contestar a continuación las siguientes preguntas: 
 * - Qué patrón de diseño podés identificar en el
 * código dado? 
 * Decorator( decoran los extras, envoltorioRegalo y envvioExpress, para incorporar sus costos al total), 
 * strategy ()
 * adapter ()
 * 
 * - Qué patrón de diseño podrías agregar para mejorar el código?
 * 
 *  adapter en la api para poder usarlo
 *
 * <p>Implementar UN patrón adicional para mejorar el código.
 * adapter en linea 
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

    // patron observer
    orden.addListener(new EmailListener());
    orden.addListener(new AnalyticsListener());

//implementar adapter y  strategy
String paymentType = "mercado-pago"; // puede ser "cash", "card", "mercado-pago"
MedioDePago medioDePago;

if ("card".equalsIgnoreCase(paymentType)) {
    medioDePago = new PagoTarjeta("Juan Perez", "4111111111111111");
} else if ("cash".equalsIgnoreCase(paymentType)) {
    medioDePago = new PagoEfectivo();
} else if ("mercado-pago".equalsIgnoreCase(paymentType)) {
    medioDePago = new PagoMercadoPagoAdapter(new MercadoPagoAPI());
} else {
    throw new IllegalArgumentException("Medio de pago no soportado");
}

orden.setMedioDePago(medioDePago);  

    // Muestra totales y pagos
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

    public void add(Producto producto) {
      items.add(producto);
    }

    public List<Producto> getItems() {
      return Collections.unmodifiableList(items);
    }

    public double subtotal() {
      double s = 0;
      for (Producto producto : items) s += producto.getPrecio();
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
      System.out.println("[Email] Enviando comprobante a cliente. Total: $" + order. calcularTotal());
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
      private boolean envoltorioRegalo = false;
      private boolean envioExpress = false;

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

    // “Extras” modelados con flags (candidato a DECORATOR) es por ser extras
    interface OrdenComponent {
    double calcularTotal();
    String getDescripcion();
}

    static class OrdenBase implements OrdenComponent {
      private final Carrito carrito;

      public OrdenBase(Carrito carrito) {
        this.carrito = carrito;
    }

    @Override
    public double calcularTotal() {
        return carrito.subtotal();
    }

    @Override
    public String getDescripcion() {
        return carrito.toString();
    }
}

abstract static class OrdenDecorator implements OrdenComponent {
    protected OrdenComponent orden;

    public OrdenDecorator(OrdenComponent orden) {
        this.orden = orden;
    }

    @Override
    public double calcularTotal() {
        return orden.calcularTotal();
    }

    @Override
    public String getDescripcion() {
        return orden.getDescripcion();
    }
}

static class EnvoltorioRegalo extends OrdenDecorator {
    public EnvoltorioRegalo(OrdenComponent orden) {
      super(orden);
    }

    @Override
    public double calcularTotal() {
      return super.calcularTotal() + 5.0;
    }

    @Override
    public String getDescripcion() {
      return super.getDescripcion() + " + Envoltorio de regalo";
    }
}

static class EnvioExpress extends OrdenDecorator {
    public EnvioExpress(OrdenComponent orden) {
        super(orden);
    }

    @Override
    public double calcularTotal() {
      return super.calcularTotal() + 10.0;
    }

    @Override
    public String getDescripcion() {
       return super.getDescripcion() + " + Envío Express";
    }
}
  public double calcularTotal() {
    OrdenComponent base = new OrdenBase(carrito);
      if (envoltorioRegalo) base = new EnvoltorioRegalo(base);
      if (envioExpress) base = new EnvioExpress(base);
      return base.calcularTotal();
      }

  public void printSummary() {
      OrdenComponent base = new OrdenBase(carrito);
      if (envoltorioRegalo) base = new EnvoltorioRegalo(base);
      if (envioExpress) base = new EnvioExpress(base);

      System.out.println("=== ORDER SUMMARY ===");
      System.out.println("Items: " + carrito);
      System.out.println("Detalle: " + base.getDescripcion());
      System.out.println("TOTAL: $" + base.calcularTotal());
    }


    public void pagar() {
      if (paymentGateway == null) {
        System.out.println("No hay gateway de pago configurado.");
        return;
      }
      boolean ok = paymentGateway.pay(calcularTotal());
      if (ok) {
        System.out.println("Pago exitoso por $" + calcularTotal());
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

  // ===================== API externa =====================

  /** Esta API de pagos es externa y no podemos modificarla. Falta integrarla. se puede aplicar Adapter*/
  // Simulación de la API externa MercadoPagoAPI
  static class MercadoPagoAPI {
      // Simula el pago, retorna true si el monto es menor o igual a 20000 centavos (200.00)
      public boolean runPayment(int amountInCents) {
          System.out.println("[MercadoPagoAPI] Procesando pago de $" + (amountInCents / 100.0));
          return amountInCents <= 20000;//acepta hasta 200 dolares
      }
  }
  
  // ADAPTER para integrar la API externa con nuestra interfaz de pagos
static class PagoMercadoPagoAdapter implements MedioDePago {
    private final MercadoPagoAPI mpApi;

    public PagoMercadoPagoAdapter(MercadoPagoAPI mpApi) {
        this.mpApi = mpApi;
    }

    @Override
    public boolean pay(double amount) {
        int amountInCents = (int) (amount * 100);
        return mpApi.runPayment(amountInCents);
    }
  }
}