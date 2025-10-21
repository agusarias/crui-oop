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
 * 
 * 
  * Se encuentra el patrón observer en las clases de paciente y doctor, las cuales se suscriben
  a un turno, y al cambiar la fecha y hora del mismo, son notificados.

  El patrón factory se encuentra al crear una instancia de un médico, ya sea cardiologo, 
  neumonologo etc.

  El patron singleton aparece cuando se accede a la instancia de la base de datos al principio del archivo.

  Se puede agregar el patron decorator para manejar de mejor manera los costos, también puede
  usarse el patron strategy.

  El patron que decidi agregar es el de decorator para los precios de cada turno, en vez
  de definirlo en el main, hay una interfaz y una clase creada para resolver los precios
  especificos. Ademas decidi agregarle factory para calcular los costos de una mejor manera
 */
public class TurnosMedicos {

public static void main(String[] args) {

    System.out.println("--------------------");
    
    System.out.println("Turnos Medicos OESTE");

    System.out.println("--------------------");
    
    Database database = Database.getInstance();
    Paciente paciente = new Paciente("Ignacio Segovia", "OSDE", "isegovia@gmail.com");
    String especialidad = "Cardiología";
    Doctor doctor = database.getDoctor(especialidad);

    Doctor doctor2 = database.getDoctor("Kinesiología");

    Paciente paciente2 = new Paciente("Pepe Argento", "SIN_OBRA_SOCIAL", "pepe@gmail.com");

    // Aplico decorator

    double precioBase2 = CalculadoraDeCostos.getPrecioBase(doctor2.especialidad.descripcion);
    

    // Creo el componente base sin descuentos
    double precioBase = CalculadoraDeCostos.getPrecioBase(doctor.especialidad.descripcion);
    CostoTurno costo = new TurnoBase(precioBase); 

    // Aplico el decorator y uso las instancias junto con el costo para calcular el descuento
    costo = CalculadoraDeCostos.aplicarDescuento(costo, paciente, doctor);

    CostoTurno costo2 = new TurnoBase(precioBase2);

    costo2 = CalculadoraDeCostos.aplicarDescuento(costo2, paciente2, doctor2);
    
    // Nuevo turno
    // Paso el objeto 'costo' decorado directamente al turno
    Turno turno = new Turno(paciente, doctor, "2025-01-01 10:00", costo);

    Turno turno2 = new Turno(paciente2, doctor2,"2025-01-01 10:00", costo2);
    System.out.println(turno);
    System.out.println(turno2);

    // Cambio de turno
    turno.setFechaYHora("2025-01-01 11:00");

    turno2.setFechaYHora("2026-08-02 09:00");
    System.out.println();
}

  // Interfaz para manejar los costos
  public interface CostoTurno {
  
    double getCosto();
    
  }



  static class TurnoBase implements CostoTurno {
    private double precioBase; 

    public TurnoBase(double precioBase) {
        this.precioBase = precioBase;
    }

    @Override
    public double getCosto() {
        return precioBase;
    }
}

  static abstract class DescuentoDecorator implements CostoTurno {
      protected CostoTurno costoTurnoDecorado;
      protected Doctor doctor; 

      public DescuentoDecorator(CostoTurno costoTurnoDecorado, Doctor doctor) {
          this.costoTurnoDecorado = costoTurnoDecorado;
          this.doctor = doctor;
      }


  }

  static class DescuentoOSDE extends DescuentoDecorator {

    public DescuentoOSDE(CostoTurno costoTurnoDecorado, Doctor doctor) {
        super(costoTurnoDecorado, doctor);
    }

      @Override
      public double getCosto() {
          double costo = costoTurnoDecorado.getCosto();

          // Aplico la lógica específica de OSDE
          if (doctor.especialidad.contiene("Cardiología")) {
              // 100% de descuento en Cardiología
              return 0.0;
          } else {
              // 20% de descuento en el resto
            return costo * 0.80;
          }
      }
  }

  static class DescuentoPAMI extends DescuentoDecorator {

    public DescuentoPAMI(CostoTurno costoTurnoDecorado, Doctor doctor) {
        super(costoTurnoDecorado, doctor);
    }

    @Override
    public double getCosto() {
        // 100% de descuento siempre porque PAMI es gratis
        return 0.0;
    }
}

static class DescuentoIOMA extends DescuentoDecorator {

    public DescuentoIOMA(CostoTurno costoTurnoDecorado, Doctor doctor) {
        super(costoTurnoDecorado, doctor);
    }

    @Override
    public double getCosto() {
        double costo = costoTurnoDecorado.getCosto();

        // Aplico la lógica específica de IOMA
        if (doctor.especialidad.contiene("Kinesiología")) {
            // 100% de descuento en Kinesiología
            return 0.0;
        } else {
            // 15% de descuento en el resto
            return costo * 0.85; 
        }
    }
}

static class CalculadoraDeCostos {
    
    // Método auxiliar para obtener el precio base según la especialidad
    public static double getPrecioBase(String especialidad) {
        if (especialidad.contains("Cardiología")) {
            return 8000;
        } else if (especialidad.contains("Neumonología") || especialidad.contains("Kinesiología")) {
            return 7000;
        } else {
            return 5000;
        }
    }
    
    // Uso el patron factory para separar la logica y ordenarla
public static CostoTurno aplicarDescuento(CostoTurno costoBase, Paciente paciente, Doctor doctor) {
    CostoTurno costoDecorado;
    
    switch (paciente.obraSocial) {
        case "OSDE":
            costoDecorado = new DescuentoOSDE(costoBase, doctor);
            break;
        case "IOMA":
            costoDecorado = new DescuentoIOMA(costoBase, doctor);
            break;
        case "PAMI":
            costoDecorado = new DescuentoPAMI(costoBase, doctor);
            break;
        default:
            costoDecorado = costoBase; // no hay descuento
            break;
    }
    return costoDecorado;
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
    // Almaceno el costo en costoTurno
    private CostoTurno costoTurno; 

    // Constructor modificado
    public Turno(Paciente paciente, Doctor doctor, String fechaYHora, CostoTurno costoTurno) {
        this.paciente = paciente;
        this.doctor = doctor;
        this.fechaYHora = fechaYHora;
        this.costoTurno = costoTurno; 
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
        // llamo a costo turno para obtener el costo
        return "Turno para " + paciente + " con " + doctor + " el " + fechaYHora + " - $" + costoTurno.getCosto(); 
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
