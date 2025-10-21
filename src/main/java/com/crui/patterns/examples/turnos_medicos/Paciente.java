package com.crui.patterns.examples.turnos_medicos;

public class Paciente {
    private String nombre;
    private String obraSocial;
    private String email;

    public Paciente(String nombre, String obraSocial, String email) {
        this.nombre = nombre;
        this.obraSocial = obraSocial;
        this.email = email;
    }

    public String getObraSocial() {
        return obraSocial;
    }

    public void avisarCambioDeFechayHora(Turno turno) {
        System.out.println(
            "Mail para " + email + ": El turno con " + turno.getDoctor() + " se ha cambiado a " + turno.getFechaYHora()
        );
    }

    @Override
    public String toString() {
        return nombre + " (" + obraSocial + ")";
    }
}

