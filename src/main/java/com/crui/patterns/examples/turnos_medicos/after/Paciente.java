package com.crui.patterns.examples.turnos_medicos.after;

public class Paciente {
  String nombre;
  String obraSocial;
  String email;

  public Paciente(String nombre, String obraSocial, String email) {
    this.nombre = nombre;
    this.obraSocial = obraSocial;
    this.email = email;
  }

  public void avisarCambioDeFechayHora(Turno turno) {
    System.out.println(
        "Mail para "
            + email
            + ": El turno con "
            + turno.getDoctor()
            + " se ha cambiado a "
            + turno.getFechaYHora());
  }

  @Override
  public String toString() {
    return nombre + " (" + obraSocial + ")";
  }
}
