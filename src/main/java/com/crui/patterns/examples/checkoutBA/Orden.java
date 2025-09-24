package com.crui.patterns.examples.checkoutBA;

public class Orden {
    private final Carrito carrito;
    private final List<OrdenEventListener> listeners = new ArrayList<>();
    private MedioDePago paymentGateway;

    // “Extras” modelados con flags (candidato a DECORATOR)
    private boolean envoltorioRegalo; // +$5
    private boolean envioExpress; // +$10

    public static Orden(Carrito carrito){
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
