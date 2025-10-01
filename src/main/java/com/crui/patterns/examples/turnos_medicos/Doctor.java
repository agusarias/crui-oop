

package com.crui.patterns.examples.turnos_medicos;

public class Doctor {
    private String nombre;
    private Especialidad especialidad;
    private String email;

    public Doctor(String nombre, Especialidad especialidad, String email) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.email = email;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void avisarCambioDeFechayHora(Turno turno) {
        System.out.println(
            "Mail para " + email + ": El turno para " + turno.getPaciente() + " se ha cambiado a " + turno.getFechaYHora()
        );
    }

    @Override
    public String toString() {
        return nombre + " (" + especialidad + ")";
    }
}

