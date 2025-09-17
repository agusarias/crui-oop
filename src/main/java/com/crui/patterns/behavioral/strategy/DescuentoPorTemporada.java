package com.crui.patterns.behavioral.strategy;


public class DescuentoPorTemporada implements DescuentoInterfaz {
    private static final double PORCENTAJE_DESCUENTO = 0.20;

    @Override
    public double aplicarDescuento(double total) {
        double descuento = total * PORCENTAJE_DESCUENTO;
        System.out.println("Aplicando descuento de temporada del 20%. Descuento: $" + String.format("%.2f", descuento));
        return total - descuento;
    }
}

