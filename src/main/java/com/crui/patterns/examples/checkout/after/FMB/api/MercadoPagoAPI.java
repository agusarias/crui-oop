package com.crui.patterns.examples.checkout.after.FMB.api;

/** Esta API de pagos es externa y no podemos modificarla. Falta integrarla */
static class MercadoPagoAPI {
  public boolean runPayment(int amountInCents) {
    System.out.println("[MercadoPagoAPI] Procesando " + amountInCents + " centavos...");
    // Lógica ficticia: acepta todo hasta 15.000 centavos (150.00)
    return amountInCents <= 150_00; // Acá solo cambié cómo se ve, el guión bajo no significa nada.
  }
}