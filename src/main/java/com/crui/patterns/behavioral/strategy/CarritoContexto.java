package com.crui.patterns.behavioral.strategy;


import java.util.ArrayList;
import java.util.List;

public class CarritoContexto {
    private List<Double> items;
    private DescuentoInterfaz estrategiaDescuento;

    public CarritoContexto() {
        this.items = new ArrayList<>();
    }

    public void agregarItem(double precio) {
        this.items.add(precio);
    }

    public void setEstrategiaDescuento(DescuentoInterfaz estrategiaDescuento) {
        this.estrategiaDescuento = estrategiaDescuento;
    }

    public double calcularTotal() {
        return items.stream().mapToDouble(Double::doubleValue).sum();
    }

    public double procesarCompra() {
        double total = calcularTotal();
        if (estrategiaDescuento != null) {
            double totalFinal = estrategiaDescuento.aplicarDescuento(total);
            System.out.println("Total original: $" + String.format("%.2f", total));
            System.out.println("Total con descuento: $" + String.format("%.2f", totalFinal));
            return totalFinal;
        } else {
            System.out.println("No se ha seleccionado una estrategia de descuento.");
            return total;
        }
    }
}

