package com.crui.patterns.examples.checkout.after.order;

public class AnalyticsListener implements OrdenEventListener {
  @Override
  public void onPaid(Orden order) {
    System.out.println("[Analytics] Registrando venta. Ítems: " + order.getItemCount());
  }
}
