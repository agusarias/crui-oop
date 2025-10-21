package com.crui.patterns.examples.turnos_medicos;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Turnos Medicos
 *
 * <p>Contestar a continuación las siguientes preguntas:
 *
 * <p>- Qué patrón de diseño podés identificar en el código dado?
 * 
 *   Patron Singleton en la clase Database, que asegura que solo haya una instancia de la base de datos  en toda la aplicacion, controlando el acceso a los datos de los doctores. 
 *   Patron Factory en la clase Creadordedoctores, que encapsula la creacion de doctores con especialidades especificas, evitando la duplicacion del codigo.
 *   Patron Observer en la clase Turno, que notifica a los interesados doctor y paciente sobre cambios en la fecha y hora del turno.
 * 
 * <p> - Qué patrones de diseño se podrían agregar para mejorar el código?
 *  Se podria agregar el Patron Strategy para manejar diferentes estrategias de calculo de precios y descuentos segun la especialidad y obra social. Eliminaría los largos switch y if-else, haciéndolo más mantenible y extensible.
 *  Se podria agregar el Patron Builder para Construccion de Turnos ya que este tiene muchos parametros, agregar el patron Builder haria mas legible la creacion de turnos complejos y permitiria validaciones entre la contrucción.
 *  Se podria agregar el Patron Decorator para agregar funcionalidades adicionales a los turnos, como recordatorios o notificaciones, sin modificar la clase Turno.

 * <p>Implementar uno o más de estos patrones adicionales para mejorar el código.
 * Se agrego el patron Strategy para el calculo de precios y descuentos.
 * Se agrego el patron Builder para la construccion de turnos.
 * 
 */
public class TurnosMedicos {

  // Patron Strategy - Interfaces
  public interface EstrategiaPrecio {
    float calcularPrecio(String especialidad);
  }

  public interface EstrategiaDescuento {
    float calcularDescuento(String obraSocial, String especialidad);
  }

  // PatronStrategy  - Implementaciones concretas
  public static class EstrategiaPrecioEstandar implements EstrategiaPrecio {
    private static final Map<String, Float> PRECIOS = new HashMap<>();
    
    static {
      PRECIOS.put("Cardiología", 8000f);
      PRECIOS.put("Neumonología", 7000f);
      PRECIOS.put("Kinesiología", 7000f);
    }
    
    @Override
    public float calcularPrecio(String especialidad) {
      for (Map.Entry<String, Float> entry : PRECIOS.entrySet()) {
        if (especialidad.contains(entry.getKey())) {
          return entry.getValue();
        }
      }
      return 5000f; // Precio por defecto
    }
  }

  public static class EstrategiaDescuentoObraSocial implements EstrategiaDescuento {
    @Override
    public float calcularDescuento(String obraSocial, String especialidad) {
      switch (obraSocial) {
        case "OSDE":
          return especialidad.contains("Cardiología") ? 1f : 0.2f;
        case "IOMA":
          return especialidad.contains("Kinesiología") ? 1f : 0.15f;
        case "PAMI":
          return 1.0f;
        default:
          return 0.0f;
      }
    }
  }

  // Patron Builder para construccion de turnos
  public static class TurnoBuilder {
    private Paciente paciente;
    private Doctor doctor;
    private String fechaYHora;
    private EstrategiaPrecio estrategiaPrecio = new EstrategiaPrecioEstandar();
    private EstrategiaDescuento estrategiaDescuento = new EstrategiaDescuentoObraSocial();

    public TurnoBuilder paciente(Paciente paciente) {
      this.paciente = paciente;
      return this;
    }

    public TurnoBuilder doctor(Doctor doctor) {
      this.doctor = doctor;
      return this;
    }

    public TurnoBuilder fechaYHora(String fechaYHora) {
      this.fechaYHora = fechaYHora;
      return this;
    }

    public TurnoBuilder estrategiaPrecio(EstrategiaPrecio estrategia) {
      this.estrategiaPrecio = estrategia;
      return this;
    }

    public TurnoBuilder estrategiaDescuento(EstrategiaDescuento estrategia) {
      this.estrategiaDescuento = estrategia;
      return this;
    }

    public Turno build() {
      // Validaciones durante la construcción
      if (paciente == null) {
        throw new IllegalStateException("El paciente es obligatorio para crear un turno");
      }
      if (doctor == null) {
        throw new IllegalStateException("El doctor es obligatorio para crear un turno");
      }
      if (fechaYHora == null || fechaYHora.trim().isEmpty()) {
        throw new IllegalStateException("La fecha y hora son obligatorias para crear un turno");
      }

      // Calculo automatico del precio usando las estrategias
      float precioBase = estrategiaPrecio.calcularPrecio(doctor.especialidad.descripcion);
      float descuento = estrategiaDescuento.calcularDescuento(paciente.obraSocial, doctor.especialidad.descripcion);
      float precio = precioBase - (precioBase * descuento);

      return new Turno(paciente, doctor, fechaYHora, precio);
    }
  }

  public static void main(String[] args) {
    System.out.println();
    System.out.println("Turnos Medicos");
    System.out.println("=============");
    System.out.println();
    Database database = Database.getInstance();

    // EJEMPLO 1: Paciente con OSDE y Cardiología (descuento 100%)
    Paciente paciente = new Paciente("Ignacio Segovia", "OSDE", "isegovia@gmail.com");
    String especialidad = "Cardiología";
    Doctor doctor = database.getDoctor(especialidad);

    if (doctor == null) {
      System.out.println("No se encontró el doctor para la especialidad: " + especialidad);
      return;
    }

    Turno turno = new TurnoBuilder()
        .paciente(paciente)
        .doctor(doctor)
        .fechaYHora("2025-01-01 10:00")
        .build();

    System.out.println(turno);
    turno.setFechaYHora("2025-01-01 11:00");
    System.out.println();

    // EJEMPLO 2: Paciente con IOMA y Kinesiología (descuento 100%)
    
    System.out.println("================");
    
    Paciente paciente2 = new Paciente("María González", "IOMA", "mgonzalez@hotmail.com");
    Doctor doctor2 = database.getDoctor("Kinesiología");
    
    if (doctor2 != null) {
      Turno turno2 = new TurnoBuilder()
          .paciente(paciente2)
          .doctor(doctor2)
          .fechaYHora("2025-01-02 14:30")
          .build();
      
      System.out.println(turno2);
      turno2.setFechaYHora("2025-01-02 15:00");
    }
    System.out.println();

    // EJEMPLO 3: Paciente sin obra social y Neumonología (sin descuento)
   
    System.out.println("===============");
    
    Paciente paciente3 = new Paciente("Carlos Pérez", "Sin obra social", "cperez@gmail.com");
    Doctor doctor3 = database.getDoctor("Neumonología");
    
    if (doctor3 != null) {
      Turno turno3 = new TurnoBuilder()
          .paciente(paciente3)
          .doctor(doctor3)
          .fechaYHora("2025-01-03 09:00")
          .build();
      
      System.out.println(turno3);
      turno3.setFechaYHora("2025-01-03 11:30");
    }
    System.out.println();
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

    public Paciente getPaciente() {
      return paciente;
    }

    public Doctor getDoctor() {
      return doctor;
    }

    public String getFechaYHora() {
      return fechaYHora;
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
              + turno.getDoctor()
              + " se ha cambiado a "
              + turno.getFechaYHora());
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
              + turno.getPaciente()
              + " se ha cambiado a "
              + turno.getFechaYHora());
    }

    @Override
    public String toString() {
      return nombre + " (" + especialidad + ")";
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
              CreadorDeDoctores.crearKinesiologo("Dr. Roberto Silva", "rsilva@gmail.com"),
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
      return new Doctor(nombre, new Especialidad("Cardiología"), email);
    }

    public static Doctor crearNeumonologo(String nombre, String email) {
      return new Doctor(nombre, new Especialidad("Neumonología"), email);
    }

    public static Doctor crearAlergista(String nombre, String email) {
      return new Doctor(nombre, new Especialidad("Alergias"), email);
    }

    public static Doctor crearKinesiologo(String nombre, String email) {
      return new Doctor(nombre, new Especialidad("Kinesiología"), email);
    }

    public static Doctor crearTraumatologo(String nombre, String email) {
      return new Doctor(nombre, new Especialidad("Traumatología"), email);
    }

    public static Doctor crearClinicoGeneral(String nombre, String email) {
      return new Doctor(nombre, new Especialidad("Medico Clinico"), email);
    }
  }
}
