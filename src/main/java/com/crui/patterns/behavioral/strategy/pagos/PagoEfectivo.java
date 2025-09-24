package com.crui.patterns.behavioral.strategy.pagos;

public class PagoEfectivo implements EstrategiaPago {

    private final String puntoDeCobro;

    public PagoEfectivo(String puntoDeCobro) {
        this.puntoDeCobro = puntoDeCobro;
    }

    @Override
    public boolean pagar(double monto) {
        if (monto <= 0) {
            System.out.println("[Efectivo] Monto inválido.");
            return false;
        }
        System.out.printf("[Efectivo] Generando comprobante por $ %.2f para pagar en: %s...%n", monto, puntoDeCobro);
        System.out.println("[Efectivo] Comprobante emitido. Pendiente de acreditación.");
        return true;
    }
}
