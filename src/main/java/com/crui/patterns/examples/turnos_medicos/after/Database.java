package com.crui.patterns.examples.turnos_medicos.after;

import java.util.List;

public class Database {
  private static Database instance;
  private List<Doctor> doctores;

  private Database() {
    this.doctores =
        List.of(
            CreadorDeDoctores.crearCardiologoGeneral("Dra. Girgenti Ana", "agirgenti@gmail.com"),
            CreadorDeDoctores.crearNeumonologo("Dr. Jorge Gutierrez", "jgutierrez@gmail.com"),
            CreadorDeDoctores.crearAlergista("Dra. Florencia Aranda", "faranda@gmail.com"),
            CreadorDeDoctores.crearClinicoGeneral("Dr. Esteban Quiroga", "equiroga@gmail.com"),
            CreadorDeDoctores.crearTraumatologo("Dr. Mario Gómez", "mgomez@gmail.com"));
  }

  public static Database getInstance() {
    if (instance == null) {
      instance = new Database();
    }
    return instance;
  }

  public List<Doctor> getDoctores() {
    return doctores;
  }

  public Doctor getDoctor(String descripcionEspecialidad) {
    for (Doctor doctor : doctores) {
      if (doctor.especialidad.contiene(descripcionEspecialidad)) {
        return doctor;
      }
    }
    return null;
  }
}
