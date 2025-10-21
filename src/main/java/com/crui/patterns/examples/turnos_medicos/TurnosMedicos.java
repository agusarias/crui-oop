package com.crui.patterns.examples.turnos_medicos;

import java.util.List;

/**
 * Turnos Medicos
 *
 * <p>Contestar a continuación las siguientes preguntas:
 *
 * <p>- Qué patrón de diseño podés identificar en el código dado?
 * Patrones identificados:
 * SINGLETON porque en la clase database utiliza getInstance() para que haya una unica instancia de la base de datos 
 * en toda la aplicación, permitiendo evitar crear varias listas de doctores.
 * Tambien está presente el patrón FACTORY, que puede identificarse en la clase CreadorDeDoctores usando metodos 
 * para crear los doctores segun especialidad, por lo tanto está centralizando la logica de creación en 
 * un solo lugar.
 * Por último también se utiliza el patron OBSERVER en la clase de Turno, la cual tiene el método 
 * avisarCambioDeFechaYHora, turno está funcionando como sujeto y Medico y Paciente son los escuchadores.
 * 
 * <p>- Qué patrones de diseño se podrían agregar para mejorar el código?
 *Se puede incorporar STRATEGY y DECORATOR. El patrón que se agrega en este caso es Strategy, para resolver la 
 falta de cumplimiento con el Principio de Responsabilidad única y hacer el código más extensible.
 Decorator se podría utilizar para el caso de que se quieran agregar extras respecto del turno(Por ej:
 consulta virtual)
 NOTA IMPORTANTE: Lo que hago es crear dos interfaces que describen comportamientos distintos necesarios para que las 
 estrategias concretas lo implementen (Dejo señalada la versión original del código y debajo la versión nueva utilizando el patrón 
 para dejar visible el problema que se resuelve, el calculo de precioBase y de Descuento).

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

    // ===========================================================================
    // VERSION ORIGINAL (sin patrón)
    //Está mezclando dos lógicas distintas y utiliza mucho if y switch haciendo el 
    //código menos extensible. Podría mejorarse y limpiar el main.
    // ===========================================================================
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

    // En version original se aplica el descuento (por el uso del patron queda sin uso)
    float precio = precioBase - precioBase * descuento;

    // ====================================================================================
    // VERSION UTILIZANDO EL PATRON  STRATEGY
    //Se separa la lógica de precio y de descuento.
    //Se puede agregar nuevas reglas sin tocar este código, solo creando nuevas estrategias.
    // ===================================================================================
    PrecioStrategy precioStrategy = new PrecioPorEspecialidadStrategy();
    DescuentoStrategy descuentoStrategy = new DescuentoPorObraSocialStrategy();

    float precioBaseStrategy = precioStrategy.calcularPrecioBase(doctor);
    float descuentoStrategyValor = descuentoStrategy.calcularDescuento(paciente, doctor);
    float precioFinalStrategy = precioBaseStrategy - precioBaseStrategy * descuentoStrategyValor;
    // ================================

    // Nuevo turno
    Turno turno = new Turno(paciente, doctor, "2025-01-01 10:00", precioFinalStrategy);
    System.out.println(turno);

    // Cambio de turno
    turno.setFechaYHora("2025-01-01 11:00");
    System.out.println();
  }

//========================================================
  // DOS INTERFACES DE ESTRATEGIA PARA DOS COMPORTAMIENTOS
//=======================================================
  interface PrecioStrategy {
    float calcularPrecioBase(Doctor doctor);
  }

  interface DescuentoStrategy {
    float calcularDescuento(Paciente paciente, Doctor doctor);
  }
//======================================================================
  // ESTABLEZCO DOS IMPLEMENTACIONES CONCRETAS QUE UTILIZAN LA INTERFAZ
//======================================================================
  //IMPLEMENTACION 1: encapsulo la lógica de precios por especialidad
  static class PrecioPorEspecialidadStrategy implements PrecioStrategy {
    @Override
    public float calcularPrecioBase(Doctor doctor) {
      if (doctor.especialidad.contiene("Cardiología")) return 8000;
      if (doctor.especialidad.contiene("Neumonología")) return 7000;
      if (doctor.especialidad.contiene("Kinesiología")) return 7000;
      return 5000;
    }
  }
  //IMPLEMENTACION 2: centralizo la lógica de descuentos para que se más fácil
  //modificar en el futuro.
  static class DescuentoPorObraSocialStrategy implements DescuentoStrategy {
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
