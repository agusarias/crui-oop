package com.crui.patterns.examples.turnos_medicos;

import java.util.List;

/**
 * Turnos Medicos
 *
 * <p>Contestar a continuación las siguientes preguntas:
 *
 * <p>- Qué patrón de diseño podés identificar en el código dado?
 * Uno de los patrones que se pueden identificar en el código es el patrón Singleton, implementado en la clase Database.
 * Este patrón asegura que solo exista una instancia de la clase Database en toda la aplicación, proporcionando un punto de acceso global a esa instancia.
 *
 * Otro patrón que se puede identificar es el patrón Factory, implementado en la clase CreadorDeDoctores.
 * Usa métodos estáticos para crear diferentes tipos de objetos Doctor, para no exponer la lógica de instanciación al cliente.
 * 
 * Otro patrón que se puede identificar es el patrón Observer, implementado en las clases Turno, Doctor y Paciente.
 * Este patrón permite que los objetos se suscriban a eventos y sean notificados cuando esos eventos ocurren.
 * 
 * Otro patrón que se puede identificar es el patrón Strategy, implementado en la lógica de cálculo de precios y descuentos.
 *
 * <p>- Qué patrones de diseño se podrían agregar para mejorar el código?
 * Se podrían agregar los siguientes patrones de diseño para mejorar el código:
 * 1. Patrón Strategy: Para manejar diferentes estrategias de cálculo de precios y descuentos.
 * 2. Patrón Observer: Para manejar las notificaciones de cambios en los turnos de manera más flexible.
 * <p>Implementar uno o más de estos patrones adicionales para mejorar el código.

 * Patrón strategy:
 *  - Se podría crear una interfaz EstrategiaDePrecio con un método calcularPrecio(float precioBase).
 *  - Luego, se podrían implementar diferentes estrategias de precios que utilicen esta interfaz.
 *  - Por ejemplo, se podría tener una estrategia de precio para pacientes con OSDE, otra para IOMA, etc.
 *  - De esta manera, se podría cambiar la estrategia de precios en tiempo de ejecución sin modificar el código del cliente.
 * 
 *
 */
public class TurnosMedicos {

  public static void main(String[] args) {
    
    imprimirTurnos();
    ejecutarEjemplo();
  
  }
  private static void ejecutarEjemplo() {
    Database database = Database.getInstance();
    Paciente paciente = new Paciente("Ignacio Segovia", "OSDE", "isegovia@gmail.com");
    String especialidad = "Cardiología";
    Doctor doctor = database.getDoctor(especialidad);

    if (doctor == null) {
      System.out.println("No se encontró el doctor para la especialidad: " + especialidad);
      return;
    }

    // Strategy para calcular precio
    CalculadoraPrecio calculadora = new CalculadoraPrecioEstandar();
    float precio = calculadora.calcularPrecio(doctor.especialidad.descripcion, paciente.obraSocial);

     // Nuevo turno
    Turno turno = new Turno(paciente, doctor, "2025-01-01 10:00", precio);
    System.out.println(turno);

    // Cambio de turno
    turno.setFechaYHora("2025-01-01 11:00");
    System.out.println();}

  public static void imprimirTurnos(){
    System.out.println();
    System.out.println("Turnos Medicos");
    System.out.println("=============");
    System.out.println();

  }

  // -----------------------------Patrón Strategy------------------------------------------
  public interface CalculadoraPrecio {
    float calcularPrecio(String especialidad, String obraSocial);
  }
  public static class CalculadoraPrecioEstandar implements CalculadoraPrecio {
    @Override
    public float calcularPrecio(String especialidad, String obraSocial) {
      float precioBase = obtenerPrecioBase(especialidad);
      float descuento = obtenerdescuento(especialidad, obraSocial);
      return precioBase - (precioBase * descuento);
    }

    // Precio base en base a la especialidad
    private float obtenerPrecioBase(String especialidad) {
      if (especialidad.contains("Cardiología")) {
        return 8000;
      } else if (especialidad.contains("Neumonología")) {
        return 7000;
      } else if (especialidad.contains("Kinesiología")) {
        return 7000;
      } else {
        return 5000;
      }
      }

      // Descuento en base a la obra social y la especialidad
    private float obtenerdescuento(String especialidad, String obraSocial) {
      switch (obraSocial) {
        case "OSDE":
          return especialidad.contains("Cardiología")
                  ? 1f // 100% de descuento en cardiología
                  : 0.2f; // 20% de descuento
        case "IOMA":
          return especialidad.contains("Kinesiología")
              ? 1f // 100% de descuento en kinesiología
              : 0.15f; // 15% de descuento
        case "PAMI":
          return 1.0f; // 100% de descuento
        default:
          return 0.0f; // 0% de descuento
      }
      
    }
  }
  // -----------------------------Fin Patrón Strategy------------------------------------------

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
}
