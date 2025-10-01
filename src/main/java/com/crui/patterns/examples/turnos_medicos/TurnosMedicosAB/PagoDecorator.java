package com.crui.patterns.examples.turnos_medicos.TurnosMedicosAB;

public abstract class PagoDecorator implements IPago {

    IPago pago;

    public PagoDecorator(IPago pago){
        this.pago = pago;
    }

    public float getDescuento(Boolean especialidad) {
        return pago.getDescuento(especialidad);
    }
}
