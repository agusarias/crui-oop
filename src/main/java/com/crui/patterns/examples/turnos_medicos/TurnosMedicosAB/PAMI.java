package com.crui.patterns.examples.turnos_medicos.TurnosMedicosAB;

public class PAMI extends PagoDecorator{

    public PAMI(IPago pago){
        super(pago);
    }

    @Override
    public float getDescuento(Boolean especialidad) {
        return 1.0f;
    }

}
