package com.crui.patterns.structural.decoratorIgnacio;

public class CafeSimple implements Bebida {
    @Override
    public String getDescripcion(){
        return "Cafe Simple";
    }
    
    @Override
    public double getCosto(){
        return 100.0;
    }
}