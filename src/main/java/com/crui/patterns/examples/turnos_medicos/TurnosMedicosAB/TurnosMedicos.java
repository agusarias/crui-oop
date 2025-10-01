package com.crui.patterns.examples.turnos_medicos.TurnosMedicosAB;

import java.util.List;

/**
 * Turnos Medicos
 *
 * <p>Contestar a continuación las siguientes preguntas:
 *
 * <p>- Qué patrón de diseño podés identificar en el código dado?
 *  - Factory
 *  - Singleton
 *
 * <p>- Qué patrones de diseño se podrían agregar para mejorar el código?
 *  - Observer (Observer para Paciente y Doctor en especifico para avisar el cambio de fecha y hora, 
 *                      además nos permite hacer una personalizacion en caso de requerir, y posibles Notificaciones en el futuro) 
 *  - Strategy (Strategy para agregar logica adicional en base a la especialidad)
 *  - Decorator (Decorator para modificar el precio en base a la obra social)
 *  - Posible Builder (Posible builder para Turno en caso de que se llegue a necesitar de mas datos/atributos para la instancia de Turno llegando a complejizar Turno)
 * 
 * <p>Implementar uno o más de estos patrones adicionales para mejorar el código.
 */
public class TurnosMedicos {

  public static void main(String[] args) {
    System.out.println();
    System.out.println("Turnos Medicos");
    System.out.println("=============");
    System.out.println();
    Database database = Database.getInstance(); //Singleton

    Paciente paciente = new Paciente("Ignacio Segovia", "OSDE", "isegovia@gmail.com");
    String especialidad = "Kinesiología";

    //Acomodar Logica de buscar un doctor
    Doctor doctor = database.getDoctor(especialidad);
    if (doctor == null) {
      System.out.println("No se encontró el doctor para la especialidad: " + especialidad);
      return;
    }

    // Precio base en base a la especialidad
    // Patron Strategy para las distintas especialidades
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
    // Decorator para Precio 

    float descuento;
    float subtotal = precioBase;
    IPago pago = new Precio(subtotal);
    
    switch (paciente.obraSocial) {
      case "OSDE":
            IPago pagoWithOSDE = new OSDE(pago);
            descuento = pagoWithOSDE.getDescuento(doctor.especialidad.contiene("Cardiología")); 
        break;
      case "IOMA":
            IPago pagoWithIOMA = new IOMA(pago);
            descuento = pagoWithIOMA.getDescuento(doctor.especialidad.contiene("Kinesiología"));
        break;
      case "PAMI":
            descuento = 1.0f; // 100% de descuento
        break;
      default:
            descuento = 0.0f; // 0% de descuento
        break;
    }

    // Aplico el descuento
    float precio = (precioBase - (precioBase * descuento));
    System.out.println("Precio base: $" + String.format("%.2f", precioBase));
    System.out.println("Descuento: $" + String.format("%.2f", (precioBase - precio))); 
    System.out.println("Precio final: $" + String.format("%.2f", precio)); 

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
    //Observer para Paciente y Doctor
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
    //Singleton de Database
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

  //Factory de doctores(YA ESTA HECHO) pero se deberia de estar separado del main
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
