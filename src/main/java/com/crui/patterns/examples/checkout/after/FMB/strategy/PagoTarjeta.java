package com.crui.patterns.examples.checkout.after.FMB.strategy;

public class PagoTarjeta implements IMedioDePago {
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