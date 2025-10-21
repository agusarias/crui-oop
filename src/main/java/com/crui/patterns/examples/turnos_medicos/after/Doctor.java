package com.crui.patterns.examples.turnos_medicos.after;

public class Doctor {
  String nombre;
  Especialidad especialidad;
  String email;

  public Doctor(String nombre, Especialidad especialidad, String email) {
    this.nombre = nombre;
    this.especialidad = especialidad;
    this.email = email;
  }

  public void avisarCambioDeFechayHora(Turno turno) {
    System.out.println(
        "Mail para "
            + email
            + ": El turno para "
            + turno.getPaciente()
            + " se ha cambiado a "
            + turno.getFechaYHora());
  }

  @Override
  public String toString() {
    return nombre + " (" + especialidad + ")";
  }
}
