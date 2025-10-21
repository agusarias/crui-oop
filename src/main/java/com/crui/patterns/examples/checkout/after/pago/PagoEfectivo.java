package com.crui.patterns.examples.checkout.after.pago;


public class PagoEfectivo implements MedioDePago {
    @Override
    public boolean pay(double amount) {
        System.out.println("[Cash] Recibidos $" + amount + " en efectivo.");
        return true;
    }
}

