package com.crui.patterns.examples.turnos_medicos;

import java.util.List;

/**
 * Turnos Medicos
 *
 * <p>Contestar a continuación las siguientes preguntas:
 *
 * <p>- Qué patrón de diseño podés identificar en el código dado?
 * 
 * Singleton → Database (garantiza que hay una única instancia de la base de datos, tiene el construcor privado y el método getInstance() que devuelve la única instancia)
 * factory → CreadorDeDoctores (Encapsula la creación de doctores según la especialidad y esto evita que se repita el código)
 * Observer → TurnosMedicos (Cuando se cambia la fecha y hora en turnos se notifica a los observadores, en caso de agregar otros observadores como por ejemplo whatsapp se agraga observadores)
 * 
 *
 * <p>- Qué patrones de diseño se podrían agregar para mejorar el código?
 * 
 * strategy → para encapsular el cálculo del precio base según la especialidad y para encapsular el cálculo del descuento según la obra social
 * Observer → para que Turno no conozca a Paciente ni Doctor directamente (separar turno de paciente y dortor).
 * Builder → se pasan muchos parametros a Turno, se puede utilizar builder para simplificar la creación de turnos y que el constructor no sea tan largo.
 * Adapter  → Si el sistema tuviera que conectarse a una API externa de pagos o de historias clínicas.
 * 
 *
 * <p>Implementar uno o más de estos patrones adicionales para mejorar el código.
 * se Implementa strategy en precio base y descuento; 
 * 
 * 
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
    //**strategy** encapsula el precio base según la especialidad 

    // ------------------- STRATEGY: PRECIO BASE -------------------
    PrecioBaseStrategy precioBaseStrategy = new PrecioBasePorEspecialidad();
    float precioBase = precioBaseStrategy.calcularPrecioBase(doctor);

    // ------------------- STRATEGY: DESCUENTO -------------------
    DescuentoStrategy descuentoStrategy = new DescuentoPorObraSocial();
    float descuento = descuentoStrategy.calcularDescuento(paciente, doctor);


    // Aplico el descuento
    float precio = precioBase - precioBase * descuento;

    // Nuevo turno
    Turno turno = new Turno(paciente, doctor, "2025-01-01 10:00", precio);
    System.out.println(turno);

    // Cambio de turno
    turno.setFechaYHora("2025-01-01 11:00");
    System.out.println();
  }

 // Descuento en base a la obra social y la especialidad
  //**strategy** ya que si se agrega otra obra social hay que modificar main.
  
  // ------------------- STRATEGY: PRECIO BASE -------------------
  interface PrecioBaseStrategy {
    float calcularPrecioBase(Doctor doctor);
  }
  static class PrecioBasePorEspecialidad implements PrecioBaseStrategy {
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
  }

  // ------------------- STRATEGY: DESCUENTO -------------------
  interface DescuentoStrategy {
    float calcularDescuento(Paciente paciente, Doctor doctor);
  }

  static class DescuentoPorObraSocial implements DescuentoStrategy {
    @Override
    public float calcularDescuento(Paciente paciente, Doctor doctor) {
      switch (paciente.obraSocial) {
        case "OSDE":
          return doctor.especialidad.contiene("Cardiología") ? 1f : 0.2f;
        case "IOMA":
          return doctor.especialidad.contiene("Kinesiología") ? 1f : 0.15f;
        case "PAMI":
          return 1.0f;
        default:
          return 0.0f;
      }
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
   //**podria aplicarse Builder y observer **
   
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

  //**Observer**
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
  //**Singleton**
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

//**factory**
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
