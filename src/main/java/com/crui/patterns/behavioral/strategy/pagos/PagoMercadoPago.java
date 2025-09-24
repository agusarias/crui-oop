package com.crui.patterns.behavioral.strategy.pagos;

public class PagoMercadoPago implements EstrategiaPago {

    private final String emailCuenta;
    private final String tokenSeguridad;

    public PagoMercadoPago(String emailCuenta, String tokenSeguridad) {
        this.emailCuenta = emailCuenta;
        this.tokenSeguridad = tokenSeguridad;
    }

    @Override
    public boolean pagar(double monto) {
        if (monto <= 0) {
            System.out.println("[MercadoPago] Monto inválido.");
            return false;
        }
        // Validación del token:
        if (tokenSeguridad == null || tokenSeguridad.length() < 6) {
            System.out.println("[MercadoPago] Token inválido.");
            return false;
        }
        System.out.printf("[MercadoPago] Pagando $ %.2f con la cuenta %s...%n", monto, emailCuenta);
        // Integración real con API:
        System.out.println("[MercadoPago] Pago aprobado");
        return true;
    }
}

