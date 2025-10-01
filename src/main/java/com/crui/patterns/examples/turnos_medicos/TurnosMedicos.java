package com.crui.patterns.examples.turnos_medicos;

import java.util.List;

/**
 * Turnos Medicos
 *
 * <p>Contestar a continuación las siguientes preguntas:
 *
 * <p>- Qué patrón de diseño podés identificar en el código dado?
 *
 * <p>- Qué patrones de diseño se podrían agregar para mejorar el código?
 *
 * <p>Implementar uno o más de estos patrones adicionales para mejorar el código.
 */
public class TurnosMedicos {

  public static void main(String[] args) {
    System.out.println();
    System.out.println("Turnos Medicos");
    System.out.println("=============");
    System.out.println();
    DataBase database = DataBase.getInstance();

    Paciente paciente = new Paciente("Ignacio Segovia", "OSDE", "isegovia@gmail.com");
    String especialidad = "Cardiología";
    Doctor doctor = database.getDoctor(especialidad);

    if (doctor == null) {
      System.out.println("No se encontró el doctor para la especialidad: " + especialidad);
      return;
    }

    // Precio base en base a la especialidad
    double precioBase;
    if (doctor.getEspecialidad().contiene("Cardiología")) {
      precioBase = 8000d;
    } else if (doctor.getEspecialidad().contiene("Neumonología")) {
      precioBase = 7000d;
    } else if (doctor.getEspecialidad().contiene("Kinesiología")) {
      precioBase = 7000d;
    } else {
      precioBase = 5000d;
    }

    // Descuento en base a la obra social y la especialidad
    double descuento;
    switch (paciente.getObraSocial()) {
      case "OSDE":
        descuento =
            doctor.getEspecialidad().contiene("Cardiología")
                ? 1d // 100% de descuento en cardiología
                : 0.2d; // 20% de descuento
        break;
      case "IOMA":
        descuento =
            doctor.getEspecialidad().contiene("Kinesiología")
                ? 1d // 100% de descuento en kinesiología
                : 0.15d; // 15% de descuento
        break;
      case "PAMI":
        descuento = 1.0d; // 100% de descuento
        break;
      default:
        descuento = 0.0d; // 0% de descuento
        break;
    }

    // Aplico el descuento
    double precio = precioBase - precioBase * descuento;
    // Nuevo turno
    Turno turno = new Turno(paciente, doctor, "2025-01-01 10:00", precio);
    System.out.println(turno);

    // Cambio de turno
    turno.setFechaYHora("2025-01-01 11:00");
    System.out.println();
  }
}
