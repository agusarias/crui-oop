package com.crui.patterns.examples.checkoutBA;

public interface MedioDePago {
    boolean pay(double amount);
}

// Estrategia de pago en efectivo
class PagoEfectivoStrategy implements MedioDePago {
    @Override
    public boolean pay(double amount) {
        System.out.println("[Cash] Recibidos $" + amount + " en efectivo.");
        return true;
    }
}

// Estrategia de pago con tarjeta
class PagoTarjetaStrategy implements MedioDePago {
    private String holder;
    private int cardNumber;

    public PagoTarjetaStrategy(String holder, int cardNumber) {
        this.holder = holder;
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("[Card] Autorizando tarjeta de " + holder + " ****" + last4(cardNumber));
        return amount <= 100.0;
    }

    private String last4(int cardNumber) {
        String cardStr = String.valueOf(cardNumber);
        if (cardStr.length() < 4) return "????";
        return cardStr.substring(cardStr.length() - 4);
    }
}