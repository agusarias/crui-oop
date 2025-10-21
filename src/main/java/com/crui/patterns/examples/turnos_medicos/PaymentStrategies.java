package com.crui.patterns.examples.turnos_medicos;

interface PaymentStrategy {
    /**
     * Aplica recargos o bonificaciones y devuelve el monto final.
     */
    double aplicarPago(double monto);

    String nombre();
}

public class PaymentStrategies {

    public static PaymentStrategy resolve(String medioDePago) {
        if (medioDePago == null) return new Efectivo();
        switch (medioDePago.toUpperCase()) {
            case "TARJETA": return new TarjetaCredito();
            case "BILLETERA": return new BilleteraVirtual();
            default: return new Efectivo();
        }
    }

    private static class Efectivo implements PaymentStrategy {
        @Override
        public double aplicarPago(double monto) { return monto; }
        @Override
        public String nombre() { return "EFECTIVO"; }
    }

    private static class TarjetaCredito implements PaymentStrategy {
        @Override
        public double aplicarPago(double monto) { return monto * 1.05d; } // 5% recargo
        @Override
        public String nombre() { return "TARJETA"; }
    }

    private static class BilleteraVirtual implements PaymentStrategy {
        @Override
        public double aplicarPago(double monto) { return monto * 0.98d; } // 2% bonificación
        @Override
        public String nombre() { return "BILLETERA"; }
    }
}
