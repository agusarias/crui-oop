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
    Database database = Database.getInstance();

    Paciente paciente = new Paciente("Ignacio Segovia", "OSDE", "isegovia@gmail.com");
    String especialidad = "Cardiología";
    Doctor doctor = database.getDoctor(especialidad);

    if (doctor == null) {
      System.out.println("No se encontró el doctor para la especialidad: " + especialidad);
      return;
    }

    // Precio base en base a la especialidad
    float precioBase;
    if (doctor.especialidad.contiene("Cardiología")) {
      precioBase = 8000;
    } else if (doctor.especialidad.contiene("Neumonología")) {
      precioBase = 7000;
    } else if (doctor.especialidad.contiene("Kinesiología")) {
      precioBase = 7000;
    } else {
      precioBase = 5000;
    }

    // Descuento en base a la obra social y la especialidad
    float descuento;
    switch (paciente.obraSocial) {
      case "OSDE":
        descuento =
            doctor.especialidad.contiene("Cardiología")
                ? 1f // 100% de descuento en cardiología
                : 0.2f; // 20% de descuento
        break;
      case "IOMA":
        descuento =
            doctor.especialidad.contiene("Kinesiología")
                ? 1f // 100% de descuento en kinesiología
                : 0.15f; // 15% de descuento
        break;
      case "PAMI":
        descuento = 1.0f; // 100% de descuento
        break;
      default:
        descuento = 0.0f; // 0% de descuento
        break;
    }

    // Aplico el descuento
    float precio = precioBase - precioBase * descuento;

    // Nuevo turno
    Turno turno = new Turno(paciente, doctor, "2025-01-01 10:00", precio);
    System.out.println(turno);

    // Cambio de turno
    turno.setFechaYHora("2025-01-01 11:00");
    System.out.println();
  }

  public static class Paciente {
    String nombre;
    String obraSocial;
    String email;

    Paciente(String nombre, String obraSocial, String email) {
      this.nombre = nombre;
      this.obraSocial = obraSocial;
      this.email = email;
    }

    public void avisarCambioDeFechayHora(Turno turno) {
      System.out.println(
          "Mail para "
              + email
              + ": El turno con "
              + turno.doctor
              + " se ha cambiado a "
              + turno.fechaYHora);
    }

    @Override
    public String toString() {
      return nombre + " (" + obraSocial + ")";
    }
  }

  public static class Especialidad {
    String descripcion;

    Especialidad(String descripcion) {
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

  public static class Doctor {
    String nombre;
    Especialidad especialidad;
    String email;

    Doctor(String nombre, Especialidad especialidad, String email) {
      this.nombre = nombre;
      this.especialidad = especialidad;
      this.email = email;
    }

    public void avisarCambioDeFechayHora(Turno turno) {
      System.out.println(
          "Mail para "
              + email
              + ": El turno para "
              + turno.paciente
              + " se ha cambiado a "
              + turno.fechaYHora);
    }

    @Override
    public String toString() {
      return nombre + " (" + especialidad + ")";
    }
  }

  public static class Turno {
    private Paciente paciente;
    private Doctor doctor;
    private String fechaYHora;
    private double precio;

    public Turno(Paciente paciente, Doctor doctor, String fechaYHora, double precio) {
      this.paciente = paciente;
      this.doctor = doctor;
      this.fechaYHora = fechaYHora;
      this.precio = precio;
    }

    public void setFechaYHora(String fechaYHora) {
      this.fechaYHora = fechaYHora;
      this.avisarCambioDeFechayHora(this);
    }

    public void avisarCambioDeFechayHora(Turno turno) {
      this.doctor.avisarCambioDeFechayHora(turno);
      this.paciente.avisarCambioDeFechayHora(turno);
    }

    @Override
    public String toString() {
      return "Turno para " + paciente + " con " + doctor + " el " + fechaYHora + " - $" + precio;
    }
  }

  public static class Database {
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

  public static class CreadorDeDoctores {

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

  /* BUILDER */

}

/* <r>  RESPUESTAS:
 * <r>  - En el codigo dado puedo identificar un patron de diseño FACTORY en la clase "CreadorDeDoctores".
 * 
 * <r>  - Para mejorar el codigo se podrian añadir los siguientes patrones de diseño:
 *        BUILDER: Declarando una clase "Persona" para que funcione como base de las clases "Paciente" y "Doctor", 
 *        de paso serviria para a futuro poder ampliar los perfiles de los medicos y pacientes con datos como DNI, fecha de nacimiento, etc etc...
 * 
 *        OBSERVER: Mediante una interfaz Hospital crear un metodo que notifique al medico y al paciente de la fecha inicial del turno, y en caso de que la
 *        fecha de dicho turno sea modificado, notificar del cambio de fecha del turno al medico y al paciente.
 * 
 * <r>  - Se "intento" implementar el BUILDER.
 */