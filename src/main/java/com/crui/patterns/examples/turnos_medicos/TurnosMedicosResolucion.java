package com.crui.patterns.examples.turnos_medicos;

import java.util.ArrayList;
import java.util.List;

/**
 * Turnos Medicos
 *
 * <p>Contestar a continuación las siguientes preguntas:
 *
 * <p>- Qué patrón de diseño podés identificar en el código dado?
 *      - La creación de la base de datos está utilizando el patrón Singleton. Con esto nos aseguramos que solo exista una instancia de la base de datos en toda la aplicación. De no existir el patrón Singleton, podríamos tener múltiples instancias de la base de datos, lo que podría llevar a inconsistencias y problemas de sincronización.
 *      - En CreadorDeDoctores se está utilizando el patrón Factory Method. Esto nos permite encapsular la lógica de creación de los objetos Doctor y Especialidad, facilitando la creación de diferentes tipos de doctores sin necesidad de conocer los detalles de su construcción.
 *
 * <p>- Qué patrones de diseño se podrían agregar para mejorar el código?
 *     - Las clases Paciente, Doctor y Turno podrían beneficiarse del patrón Observer para manejar las notificaciones de cambios en los turnos. Esto resuelve el problema de que tanto el paciente como el doctor deben ser notificados a la vez cuando se cambia la fecha y hora del turno.
 *     - la creación del turno podría beneficiarse del patrón Builder para manejar la construcción de objetos Turno con múltiples parámetros, especialmente si en el futuro se agregan más atributos al turno o si se añaden atributos opcionales.
 *     - La lógica de descuentos podría beneficiarse del patrón Strategy para manejar diferentes estrategias de descuento basadas en la obra social y la especialidad.
 *     - Si existieran otros calculos además del descuento, como impuestos o cargos adicionales, se podría implementar el patrón Decorator para agregar estos cálculos de manera flexible sin modificar la lógica existente.
 * 
 * <p>Implementar uno o más de estos patrones adicionales para mejorar el código.
 *  - Se implementa el patrón Observer para notificar a los observadores (paciente y doctor) cuando se cambia la fecha y hora del turno.
 *  - Se implementa el patrón Strategy para manejar diferentes estrategias de descuento basadas en la obra social y la especialidad.
 */
public class TurnosMedicosResolucion {

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
    CalculadoraPrecio calculadoraPrecio = new CalculadoraPrecio();
    // Establezco la estrategia de descuento según la obra social
    switch (paciente.getObraSocial()) {
      case "OSDE":
        calculadoraPrecio.establecerEstrategia(new DescuentoOSDE());
        break;
      case "IOMA":
        calculadoraPrecio.establecerEstrategia(new DescuentoIOMA());
        break;
      case "PAMI":
        calculadoraPrecio.establecerEstrategia(new DescuentoPAMI());
        break;
      default:
        // No aplica descuento
        break;
    }

    // Aplico el descuento
    float precio = precioBase - precioBase * calculadoraPrecio.calcularDescuento(paciente, doctor);

    // Nuevo turno
    Turno turno = new Turno(paciente, doctor, "2025-01-01 10:00", precio);
    System.out.println(turno);

    // Cambio de turno
    turno.setFechaYHora("2025-01-01 11:00");
    System.out.println();
  }

  // Interfaz para la estrategia de descuento
  interface EstrategiaDescuento {
    float calcularDescuento(String obraSocial, Especialidad especialidad);
  }

  // Clases concretas para diferentes estrategias de descuento
  static class DescuentoOSDE implements EstrategiaDescuento {
    @Override
    public float calcularDescuento(String obraSocial, Especialidad especialidad) {
      if (obraSocial.equals("OSDE")) {
        if (especialidad.contiene("Cardiología")) {
          return 1f; // 100% de descuento en cardiología
        } else {
          return 0.2f; // 20% de descuento
        }
      }
      return 0f; // No aplica
    }
  }

  static class DescuentoIOMA implements EstrategiaDescuento {
    @Override
    public float calcularDescuento(String obraSocial, Especialidad especialidad) {
      if (obraSocial.equals("IOMA")) {
        if (especialidad.contiene("Kinesiología")) {
          return 1f; // 100% de descuento en kinesiología
        } else {
          return 0.15f; // 15% de descuento
        }
      }
      return 0f; // No aplica
    }
  }

  static class DescuentoPAMI implements EstrategiaDescuento {
    @Override
    public float calcularDescuento(String obraSocial, Especialidad especialidad) {
      if (obraSocial.equals("PAMI")) {
        return 1.0f; // 100% de descuento
      }
      return 0f; // No aplica
    }
  }

  // Clase para calcular el precio final usando las estrategias
  static class CalculadoraPrecio {
    private EstrategiaDescuento estrategiaDescuento;

    // Se establece la estrategia elegida
    public void establecerEstrategia(EstrategiaDescuento estrategia) {
      this.estrategiaDescuento = estrategia;
    }

    // Delega el cálculo del descuento a la estrategia
    public float calcularDescuento(Paciente paciente, Doctor doctor) {
      if (estrategiaDescuento == null) {
        throw new IllegalStateException("Estrategia de descuento no establecida");
      }
      return estrategiaDescuento.calcularDescuento(paciente.getObraSocial(), doctor.getEspecialidad());
    }
  }

  public static class Paciente implements IObserver {
    private String nombre;
    private String obraSocial;
    private String email;

    public Paciente(String nombre, String obraSocial, String email) {
      this.nombre = nombre;
      this.obraSocial = obraSocial;
      this.email = email;
    }

    @Override
    public void actualizar(Turno turno) {
      System.out.println("Mail para " + email + ": El turno se ha cambiado a " + turno.getFechaYHora());
    }

    // Getters para acceso desde CalculadoraPrecio
    public String getObraSocial() {
      return obraSocial;
    }

    public String getNombre() {
      return nombre;
    }

    public String getEmail() {
      return email;
    }

    @Override
    public String toString() {
      return nombre + " (" + obraSocial + ")";
    }
  }

  public static class Doctor implements IObserver {
    private String nombre;
    private Especialidad especialidad;
    private String email;

    public Doctor(String nombre, Especialidad especialidad, String email) {
      this.nombre = nombre;
      this.especialidad = especialidad;
      this.email = email;
    }

    public Especialidad getEspecialidad() {
      return especialidad;
    }
    
    @Override
    public void actualizar(Turno turno) {
      System.out.println("Mail para " + email + ": El turno se ha cambiado a " + turno.getFechaYHora());
    }

    @Override
    public String toString() {
      return nombre + " (" + especialidad + ")";
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

  // Interfaz del Observer
  interface IObserver {
    void actualizar(Turno turno);
  }

  // Interfaz del Subject
  interface ISubject {
    void registrarObserver(IObserver observer);
    void removerObserver(IObserver observer);
    void notificarObservers();
  }

  public static class Turno implements ISubject {
    private Paciente paciente;
    private Doctor doctor;
    private String fechaYHora;
    private double precio;
    private List<IObserver> observers;

    public Turno(Paciente paciente, Doctor doctor, String fechaYHora, double precio) {
      this.paciente = paciente;
      this.doctor = doctor;
      this.fechaYHora = fechaYHora;
      this.precio = precio;
      this.observers = new ArrayList<>();

      // Cada vez que se crea un turno, se registran los observers
      registrarObserver(paciente);
      registrarObserver(doctor);
    }

    public void setFechaYHora(String fechaYHora) {
      this.fechaYHora = fechaYHora;
      notificarObservers();
    }

    public String getFechaYHora() {
      return fechaYHora;
    }

    @Override
    public void registrarObserver(IObserver observer) {
      observers.add(observer);
    }

    @Override
    public void removerObserver(IObserver observer) {
      observers.remove(observer);
    }

    @Override
    public void notificarObservers() {
      for (IObserver observer : observers) {
        observer.actualizar(this);
      }
    }

    @Override
    public String toString() {
      return "Turno para " + paciente + " con " + doctor + " el "
          + fechaYHora
          + " - $"
          + precio;
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