package com.crui.patterns.examples.turnos_medicos.after;

public class CreadorDeDoctores {

  public static Doctor crearCardiologoGeneral(String nombre, String email) {
    return new Doctor(nombre, new Especialidad("Cardiología > General"), email);
  }

  public static Doctor crearNeumonologo(String nombre, String email) {
    return new Doctor(nombre, new Especialidad("Neumonología > General"), email);
  }

  public static Doctor crearAlergista(String nombre, String email) {
    return new Doctor(nombre, new Especialidad("Neumonología > Alergias"), email);
  }

  public static Doctor crearKinesiologo(String nombre, String email) {
    return new Doctor(nombre, new Especialidad("Kinesiología > General"), email);
  }

  public static Doctor crearTraumatologo(String nombre, String email) {
    return new Doctor(nombre, new Especialidad("Kinesiología > Traumatología"), email);
  }

  public static Doctor crearClinicoGeneral(String nombre, String email) {
    return new Doctor(nombre, new Especialidad("Clínica > General"), email);
  }
}
