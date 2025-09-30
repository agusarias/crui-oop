package com.crui.patterns.examples.checkout.after.FMB.observer;

static class EmailListener implements IOrdenEventListener {
  @Override
  public void onPaid(Orden order) {
    System.out.println(
      "[Email] Enviando comprobante a cliente. Total: $" + order.total()
    );
  }
}