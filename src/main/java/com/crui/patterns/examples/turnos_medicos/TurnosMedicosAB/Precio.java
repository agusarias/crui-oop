package com.crui.patterns.examples.turnos_medicos.TurnosMedicosAB;

public class Precio implements IPago{
    float monto;
    float precioBase;

    public Precio(float monto){
        this.monto = monto;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    @Override
    public float getDescuento(Boolean especialidad) {
        return 0.0f;
    }
}
