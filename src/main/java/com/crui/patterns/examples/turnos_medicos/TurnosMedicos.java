package com.crui.patterns.examples.turnos_medicos;

import java.util.List;

/**
 * Turnos Medicos
 *
 * <p>
 * Contestar a continuación las siguientes preguntas:
 *
 * <p>
 * - Qué patrón de diseño podés identificar en el código dado?
 * al final en el codigo tenemos CreadorDeDoctores,
 * que es un Factory Method, que expone diferentes
 * formas de crear doctores con diferentes especialidades
 *
 * y tenemos un Singleton en Database porque necesitas
 * una instancia de base de datos compartida para toda la aplicacion
 *
 * <p>
 * - Qué patrones de diseño se podrían agregar para mejorar el código?
 * Podriamos usar Strategy para los diferentes calculo de los precio, me di
 * cuenta que para poder implementar las estrageias
 * estaria bueno poder tener creado el turno pero ir paso a paso, para eso
 * podemos aplicar Builder
 *
 * <p>
 * Implementar uno o más de estos patrones adicionales para mejorar el código.
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
    com.crui.patterns.examples.turnos_medicos.TurnosMedicos.Turno.TurnoBuilder builder = new Turno.TurnoBuilder()
        .setDoctor(doctor)
        .setPaciente(paciente);

    if (doctor == null) {
      System.out.println("No se encontró el doctor para la especialidad: " + especialidad);
      return;
    }

    if (doctor.especialidad.contiene("Cardiología")) {
      builder.setPrecio(8000);
    } else if (doctor.especialidad.contiene("Neumonología")) {
      builder.setPrecio(7000);
    } else if (doctor.especialidad.contiene("Kinesiología")) {
      builder.setPrecio(7000);
    } else {
      builder.setPrecio(5000);
    }

    // Descuento en base a la obra social y la especialidad
    float descuento;
    switch (paciente.obraSocial) {
      case "OSDE":
        descuento = doctor.especialidad.contiene("Cardiología")
            ? 1f // 100% de descuento en cardiología
            : 0.2f; // 20% de descuento
        break;
      case "IOMA":
        descuento = doctor.especialidad.contiene("Kinesiología")
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
    // float precio = precioBase - precioBase * descuento;

    // Nuevo turno
    // Turno turno = new Turno(paciente, doctor, "2025-01-01 10:00", precio);
    Turno turno = builder
        .setFechaYHora("2025-01-01 10:00")
        .setPrecio(builder.precio - builder.precio * descuento)
        .build();
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

    // private Turno(Paciente paciente, Doctor doctor, String fechaYHora, double
    // precio) {
    // this.paciente = paciente;
    // this.doctor = doctor;
    // this.fechaYHora = fechaYHora;
    // this.precio = precio;
    // }
    private Turno(TurnoBuilder builder) {
      this.paciente = builder.paciente;
      this.doctor = builder.doctor;
      this.fechaYHora = builder.fechaYHora;
      this.precio = builder.precio;
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

    public static class TurnoBuilder {
      private Paciente paciente;
      private Doctor doctor;
      private String fechaYHora;
      private double precio;

      public TurnoBuilder setPaciente(Paciente paciente) {
        this.paciente = paciente;
        return this;
      }

      public TurnoBuilder setDoctor(Doctor doctor) {
        this.doctor = doctor;
        return this;
      }

      public TurnoBuilder setFechaYHora(String fechaYHora) {
        this.fechaYHora = fechaYHora;
        return this;
      }

      public TurnoBuilder setPrecio(double precio) {
        this.precio = precio;
        return this;
      }

      public Turno build() {
        return new Turno(this);
      }
    }

    public void setPrecio(double precio) {
      this.precio = precio;
    }

  }

  public static class Database {
    private static Database instance;
    private List<Doctor> doctores;

    private Database() {
      this.doctores = List.of(
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
}
