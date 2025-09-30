package com.crui.patterns.examples.checkout.before;

public class PagoEfectivo implements MedioDePago {
    @Override
    public boolean pay(double amount) {
        System.out.println("[PagoEfectivo] Procesando pago en efectivo. Total: $" + amount);
        return true;
    }
}
