package com.crui.patterns.examples.turnos_medicos.TurnosMedicosAB;

public class IOMA extends PagoDecorator{

    public IOMA(IPago pago) {
        super(pago);
    }

    @Override
    public float getDescuento(Boolean especialidad) {
        if(especialidad) return 1.0f;
        return 0.15f;
    }
}
