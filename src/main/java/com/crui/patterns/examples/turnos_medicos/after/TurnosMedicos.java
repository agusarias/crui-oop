package com.crui.patterns.examples.turnos_medicos.after;

public class TurnosMedicos {

  public static void main(String[] args) {
    System.out.println();
    System.out.println("Turnos Medicos");
    System.out.println("=============");
    System.out.println();

    Database database = Database.getInstance();

    Paciente paciente = new Paciente("Ignacio Segovia", "OSDE", "isegovia@gmail.com");
    String especialidad = "Cardiología";
    Doctor doctor = database.getDoctor(especialidad);

    if (doctor == null) {
      System.out.println("No se encontró el doctor para la especialidad: " + especialidad);
      return;
    }

    Turno turno = TurnoBuilder.nuevo()
        .paciente(paciente)
        .doctor(doctor)
        .fechaYHora("2025-01-01 10:00") // opcional
        // .precio(12345) // opcional
        .construir();

    System.out.println(turno);

    turno.setFechaYHora("2025-01-01 11:00");
    System.out.println();
  }
}
