package com.crui.patterns.examples.checkout.after.FMB.strategy;

import com.crui.patterns.examples.checkout.after.FMB.api.MercadoPagoAPI;

static class PagoMercadoPago extends PagoTarjeta {

    /**
     * Una tarjeta normal tiene un límite de 100, hardcodeado en PagoTarjeta.
     * La API de MercadoPago nos permite superar este límite.
     * Por simplificación, ignoramos el límite de la API.
     */
    @Override
    public boolean pay(double amount) {
        system.out.println("[MercadoPago] Pagando $" + amount);
        // API expects value to be cents. We multiply by 100 and take only integer value to send.
        return MercadoPagoAPI.runPayment( (amount * 100).intValue() );
    }
}