package com.crui.patterns.structural.decoratorIgnacio;

public class Crema extends DecoradorBebida {
    public Crema(Bebida bebida) {
        super(bebida);
    }
    
    @Override
    public String getDescripcion() {
        return bebidaDecorada.getDescripcion() + " con Crema";
    }
    
    @Override
    public double getCosto() {
        return bebidaDecorada.getCosto() + 25.0;
    }
    
}
