package com.crui.patterns.examples.turnos_medicos;

import java.util.List;

/**
 * Turnos Medicos
 *
 * <p>Contestar a continuación las siguientes preguntas:
 *
 * <p>- Qué patrón de diseño podés identificar en el código dado?
 * El patron de diseño que identifico en el codigo es el Singleton, ya que la clase Database asegura que solo exista una instancia de ella misma y proporciona un punto de acceso global a esa instancia a través del método getInstance(). que se puede implementar cuando en este codigo para mejorar el acceso a la base de datos.
 * otro patrón que se puede identificar es el Factory Method en la clase CreadorDeDoctores, que encapsula la creación de objetos Doctor con diferentes especialidades como por ejemplo crearCardiologoGeneral, crearNeumonologo, etc.
 * 
 * <p>- Qué patrones de diseño se podrían agregar para mejorar el código?
 * A este código se le podrían agregar patrones de diseño como:
 * 1. Patrón Strategy: Para manejar diferentes estrategias de cálculo de precios y descuentos basados en la obra social y especialidad del doctor.
 * 2. Patrón Observer: Para notificar a los pacientes y doctores sobre cambios en los turnos.
 * 3. Patrón Decorator: Para agregar funcionalidades a los turnos, como los recordatorios del turno o las confirmaciones, sin modificar la clase Turno directamente.
 * 4. Patrón Composite: para manejar grupos de turnos medicos o citas, permitiendo tratar un grupo de turnos como si fuera un solo turno.
 * 
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

    //Patron Strategy
    PrecioStrategy precioStrategy = new DefaultPrecioStrategy();
    float precioBase = precioStrategy.calcularPrecioBase(doctor);
    float descuento = precioStrategy.calcularDescuento(paciente, doctor);
    float precio = precioBase - precioBase * descuento;

    // para un nuevo turno con el patron observer
    Turno turno = new Turno(paciente, doctor, "2025-01-01 10:00", precio);

    turno.addListener(new PacienteListener(paciente));
    turno.addListener(new DoctorListener(doctor));



    System.out.println(turno);
    // para un cambio de turno
    turno.setFechaYHora("2025-01-01 11:00");
    System.out.println();
  }
  interface PrecioStrategy {
    float calcularPrecioBase(Doctor doctor);
    float calcularDescuento(Paciente paciente, Doctor doctor);
  }
  static class DefaultPrecioStrategy implements PrecioStrategy {
    @Override
    public float calcularPrecioBase(Doctor doctor) {
      if (doctor.especialidad.contiene("Cardiología")) {
        return 8000;
      } else if (doctor.especialidad.contiene("Neumonología")) {
        return 7000;
      } else if (doctor.especialidad.contiene("Kinesiología")) {
        return 7000;
      } else {
        return 5000;
      }
    }
    @Override
    public float calcularDescuento(Paciente paciente, Doctor doctor) {
      float descuento;
    // Descuento en base a la obra social y la especialidad
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
      return descuento;
   }
  }

  interface TurnoListener {
    void onCambioDeFechaYHora(Turno turno);
  }

  static class PacienteListener implements TurnoListener {
    private final Paciente paciente;

    public PacienteListener(Paciente paciente) {
      this.paciente = paciente;
    }

    @Override
    public void onCambioDeFechaYHora(Turno turno) {
      paciente.avisarCambioDeFechayHora(turno);
      System.out.println("Paciente notificado: " + paciente.nombre + "el turno se ha cambiado a " + turno.fechaYHora + "con el doctor " + turno.doctor.nombre);
      
    }
  }
  static class DoctorListener implements TurnoListener {
    private final Doctor doctor;

    public DoctorListener(Doctor doctor) {
      this.doctor = doctor;
    }

    @Override
    public void onCambioDeFechaYHora(Turno turno) {
      doctor.avisarCambioDeFechayHora(turno);
      System.out.println("Doctor notificado: " + doctor.nombre + "el turno se ha cambiado a " + turno.fechaYHora + "con el paciente " + turno.paciente.nombre);
    }
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

    private List<TurnoListener> listeners = new java.util.ArrayList<>();

    public Turno(Paciente paciente, Doctor doctor, String fechaYHora, double precio) {
      this.paciente = paciente;
      this.doctor = doctor;
      this.fechaYHora = fechaYHora;
      this.precio = precio;
    }
    public void addListener(TurnoListener listener) {
      listeners.add(listener);
    }

    public void setFechaYHora(String fechaYHora) {
      this.fechaYHora = fechaYHora;
      this.avisarCambioDeFechayHora(this);
    }

    public void avisarCambioDeFechayHora(Turno turno) {
      for (TurnoListener listener : listeners) {
        listener.onCambioDeFechaYHora(turno);
      }
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
}
