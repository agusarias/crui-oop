package com.crui.patterns.examples.checkout.after.FMB.observer;

static class AnalyticsListener implements IOrdenEventListener {
  @Override
  public void onPaid(Orden order) {
    System.out.println(
      "[Analytics] Registrando venta. Ítems: " + order.getCarrito().getItems().size()
    );
  }
}