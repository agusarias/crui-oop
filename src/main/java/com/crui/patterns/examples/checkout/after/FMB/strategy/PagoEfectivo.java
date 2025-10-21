package com.crui.patterns.examples.checkout.after.FMB.strategy;

static class PagoEfectivo implements IMedioDePago {
  @Override
  public boolean pay(double amount) {
    System.out.println("[Cash] Recibidos $" + amount + " en efectivo.");
    return true;
  }
}