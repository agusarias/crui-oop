    

package com.crui.patterns.examples.turnos_medicos;

public class Especialidad {
    private String descripcion;

    public Especialidad(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean contiene(String descripcion) {
        return this.descripcion.contains(descripcion);
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
