package com.crui.patterns.examples.checkout.before;



public class MercadoPagoAPI {
    public boolean runPayment(int amountInCents) {
        System.out.println("[MercadoPagoAPI] Procesando " + amountInCents + " centavos...");
        return amountInCents <= 15000;
    }
} 