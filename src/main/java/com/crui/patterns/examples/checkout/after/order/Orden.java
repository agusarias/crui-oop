package com.crui.patterns.examples.checkout.after.order;

import java.util.*;
import com.crui.patterns.examples.checkout.after.domain.Carrito;
import com.crui.patterns.examples.checkout.after.payment.MedioDePago;
import com.crui.patterns.examples.checkout.after.pricing.Pricing;

public class Orden {
  private final Carrito carrito;
  private final List<OrdenEventListener> listeners = new ArrayList<>();
  private final Pricing pricing;            // <- DECORATOR
  private MedioDePago paymentGateway;       // <- STRATEGY

  public Orden(Carrito carrito, Pricing pricing) {
    this.carrito = carrito;
    this.pricing = pricing;
  }

  public void addListener(OrdenEventListener l) { listeners.add(l); }

  public void setMedioDePago(MedioDePago p) { this.paymentGateway = p; }

  public int getItemCount() { return carrito.getItems().size(); }

  public double total() { return pricing.total(); }

  public void printSummary() {
    System.out.println("=== ORDER SUMMARY ===");
    System.out.println(pricing.details());
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
