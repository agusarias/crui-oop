package com.crui.patterns.examples.turnos_medicos.TurnosMedicosAB;

public class OSDE extends PagoDecorator{

    public OSDE(IPago pago) {
        super(pago);
    }

    @Override
    public float getDescuento(Boolean especialidad) {
        if(especialidad) return 1f;
        return 0.2f;
    }


}
