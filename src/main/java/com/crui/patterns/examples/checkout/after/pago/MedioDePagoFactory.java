package com.crui.patterns.examples.checkout.after.pago;


public class MedioDePagoFactory {

    public static MedioDePago crear(String tipo) {
        if ("card".equalsIgnoreCase(tipo)) {
            // Datos hardcodeados para simplificar, igual que en el original
            return new PagoTarjeta("Juan Perez", "4111111111111111");
        } else if ("cash".equalsIgnoreCase(tipo)) {
            return new PagoEfectivo();
        } else {
            throw new IllegalArgumentException("Tipo de pago no soportado: " + tipo);
        }
    }
}

