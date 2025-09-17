package com.crui.patterns.behavioral.strategy;

public class DescuentoClienteFrecuente implements DescuentoInterfaz {
    private static final double MONTO_DESCUENTO = 50.0;
    private static final double MONTO_MINIMO = 100.0;

    @Override
    public double aplicarDescuento(double total) {
        if (total > MONTO_MINIMO) {
            System.out.println("Aplicando descuento por cliente frecuente de $" + String.format("%.2f", MONTO_DESCUENTO));
            return total - MONTO_DESCUENTO;
        } else {
            System.out.println("No se aplica el descuento por cliente frecuente. El total es menor a $" + String.format("%.2f", MONTO_MINIMO));
            return total;
        }
    }
}

