package com.crui.patterns.examples.turnos_medicos;

import java.util.List;

/**
 * Turnos Medicos
 *
 * <p>Contestar a continuación las siguientes preguntas:
 * 
 * <p>- Qué patrón de diseño podés identificar en el código dado?
 *    Singleton: se utiliza para garantizar que haya una sola instancia de Database en el sistema,
 *               esto asegura un punto global de conexión con Database que es un recurso compartido para todo el sistema.
 *               Va a evitar problemas de inconsistencia y duplicación.
 * 
 *    Factory: se utiliza en CreadorDeDoctores para crear instancias de Doctor con especialidades preconfigurados,
 *             implementarlo hace que el código no esté acoplado, evita errores y centraliza la creación de doctores.
 * 
 * <p>- Qué patrones de diseño se podrían agregar para mejorar el código?
 *    Strategy: para definir el precio total del turno según los descuentos por obra social y especialidad,
 *              también se puede utilizar para definir el precio base.
 *              
 *    Observer: para dar avisos sobre Turno a cualquier clase suscriptora cuando cambie su estado.
 * 
 *    Factory: a partir de la implementación de Strategy, se abre la posibilidad de usar este patrón
 *             para crear la estrategia apropiada según los criterios apropiados para cada Strategy,
 *             por un lado, la especialidad del doctor, y por el otro, la obra social del paciente.
 *
 * <p>Implementar uno o más de estos patrones adicionales para mejorar el código.
 *      Implemento ambos Strategy
 *      Aplico Factory para la creación de las instancias de Strategy
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

    PrecioEspecialidad precioBase = PrecioEspecialidadFactory.crear(doctor);

    Descuento descuento = DescuentoFactory.crear(paciente.obraSocial);

    // Nuevo turno
    Turno turno = new Turno(paciente, doctor, "2025-01-01 10:00", precioBase);
    System.out.println(turno);

    turno.setDescuento(descuento);

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
    private PrecioEspecialidad precioEspecialidad;
    private Descuento descuento;
    private float precioFinal;
  

    public Turno(Paciente paciente, Doctor doctor, String fechaYHora, PrecioEspecialidad precio) {
      this.paciente = paciente;
      this.doctor = doctor;
      this.fechaYHora = fechaYHora;
      this.precioEspecialidad = precio;
      this.precioFinal = precio.obtener();
    }

    public void setDescuento(Descuento descuento){
      this.descuento = descuento;
      aplicarDescuento();
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
      return "Turno para " + paciente + " con " + doctor + " el " + fechaYHora + " - $" + precioFinal;
    }

  public void aplicarDescuento(){
      if(descuento != null){
          float valor = descuento.calcularValor(this);
          precioFinal = this.precioEspecialidad.obtener() * (1 - valor);
      }
    } 
  }

  public static class PrecioEspecialidadFactory {
    public static PrecioEspecialidad crear(Doctor doctor){
      if (doctor.especialidad.contiene("Cardiología")) {
        return new PrecioCardiologia();
      } else if (doctor.especialidad.contiene("Neumonología")) {
        return new PrecioNeumonologia();
      } else if (doctor.especialidad.contiene("Kinesiología")) {
        return new PrecioKinesiologia();
      } else {
        return new PrecioDefault();
      }
    }
  }

  interface PrecioEspecialidad {
    public float obtener();
  }

  public static class PrecioCardiologia implements PrecioEspecialidad{
    @Override
    public float obtener(){
      return 8000;
    }
  }

  public static class PrecioNeumonologia implements PrecioEspecialidad{
    @Override
    public float obtener(){
      return 7000;
    }
  }

  public static class PrecioKinesiologia implements PrecioEspecialidad{
    @Override
    public float obtener(){
      return 7000;
    }
  }

  public static class PrecioDefault implements PrecioEspecialidad{
    @Override
    public float obtener(){
      return 5000;
    }
  }

  public static class DescuentoFactory{
    public static Descuento crear(String obraSocial){
      switch (obraSocial) {
      case "OSDE": 
        return new DescuentoOSDE();

      case "IOMA":
        return new DescuentoIOMA();

      case "PAMI":
        return new DescuentoPAMI(); 

      default:
        return new DescuentoDefault(); 
      }
    }
  }
  interface Descuento {
    public float calcularValor(Turno turno);
  }

  public static class DescuentoOSDE implements Descuento {

    @Override
    public float calcularValor(Turno turno){
          return turno.doctor.especialidad.contiene("Cardiología") ? 1f : 0.2f; // 20% de descuento
    }

  }

  public static class DescuentoIOMA implements Descuento {
    @Override
    public float calcularValor(Turno turno){
          return turno.doctor.especialidad.contiene("Kinesiología") ? 1f : 0.15f; 
    }
  }

  public static class DescuentoPAMI implements Descuento {
    @Override
    public float calcularValor(Turno turno){
          return 1f;
    }
  }

  public static class DescuentoDefault implements Descuento {
    @Override
    public float calcularValor(Turno turno){
          return 0f;
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
