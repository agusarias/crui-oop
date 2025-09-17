package com.crui.patterns.structural.decoratorIgnacio;

public class Azucar extends DecoradorBebida {
    public Azucar(Bebida bebida) {
        super(bebida);
    }
    
    @Override
    public String getDescripcion() {
        return bebidaDecorada.getDescripcion() + " con Azucar";
    }
    
    @Override
    public double getCosto() {
        return bebidaDecorada.getCosto() + 10.0;
    }
    
}
