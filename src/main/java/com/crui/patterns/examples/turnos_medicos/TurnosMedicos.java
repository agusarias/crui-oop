package com.crui.patterns.examples.turnos_medicos;

import java.util.List;

/**
 * Turnos Medicos
 *
 * <p>Contestar a continuación las siguientes preguntas:
 *
 * <p>- Qué patrón de diseño podés identificar en el código dado?
 * Se identifican los siguientes patrones de diseño:
 *  - Singleton: en la clase Database que cuenta con un constructor privado y a través del método 
 *  estático se puede acceder a la creación de la instancia, que valida si no existe la instancia,
 *  la crea, y si existe la retorna.
 *  
 *  - Simple Factory: en la clase CreadorDeDoctores contamos con múltiples métodos estáticos que a 
 *  la clase Doctor le retorna un objeto específico perteneciente a dicha clase.
 * 
 *  - Observer: en la clase Turno, se implementa un método que permite disparar eventos en las clases
 *  Doctor y Paciente
 *
 * 
 *
 * <p>- Qué patrones de diseño se podrían agregar para mejorar el código?
 *  -Se agregó el patrón de diseño Strategy para encapsular la lógica de precio base y de descuentos según 
 * obra social, y se crearon las interfaces y clases con el patrón simple factory para la 
 *  implementación de los mismos en el main.
 *  
 *  -Se reemplazó el patrón Simple Factory utilizado en CreadorDeDoctores a Factory Method para mejorar
 *  la extensibilidad y robustes del código.
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

    // Precio base en base a la especialidad
    // float precioBase;
    // if (doctor.especialidad.contiene("Cardiología")) {
    //   precioBase = 8000;
    // } else if (doctor.especialidad.contiene("Neumonología")) {
    //   precioBase = 7000;
    // } else if (doctor.especialidad.contiene("Kinesiología")) {
    //   precioBase = 7000;
    // } else {
    //   precioBase = 5000;
    // }

    // Descuento en base a la obra social y la especialidad
    // float descuento;
    // switch (paciente.obraSocial) {
    //   case "OSDE":
    //     descuento =
    //         doctor.especialidad.contiene("Cardiología")
    //             ? 1f // 100% de descuento en cardiología
    //             : 0.2f; // 20% de descuento
    //     break;
    //   case "IOMA":
    //     descuento =
    //         doctor.especialidad.contiene("Kinesiología")
    //             ? 1f // 100% de descuento en kinesiología
    //             : 0.15f; // 15% de descuento
    //     break;
    //   case "PAMI":
    //     descuento = 1.0f; // 100% de descuento
    //     break;
    //   default:
    //     descuento = 0.0f; // 0% de descuento
    //     break;
    // }

    IPrecioBase obtenerPrecio = PrecioBase.getPrecioStrategy(doctor);
    float precioBase = obtenerPrecio.getPrecioBase(doctor, paciente);

    IDescuento aplicarDescuento = AplicarDescuento.getDescuento(paciente.obraSocial);

    float descuento = aplicarDescuento.calcularDescuento(doctor, paciente);

    // Aplico el descuento
    float precio = precioBase - precioBase * descuento;

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
                new CardiologoGeneral().crearDoctor("Dra. Girgenti Ana", "agirgenti@gmail.com"),
                new NeumonologoGeneral().crearDoctor("Dr. Jorge Gutierrez", "jgutierrez@gmail.com"),
                new Alergista().crearDoctor("Dra. Florencia Aranda", "faranda@gmail.com"),
                new ClinicoGeneral().crearDoctor("Dr. Esteban Quiroga", "equiroga@gmail.com"),
                new Traumatologo().crearDoctor("Dr. Mario Gómez", "mgomez@gmail.com")
            );
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

  // public static class Database {
  //   private static Database instance;
  //   private List<Doctor> doctores;

  //   private Database() {
  //     this.doctores =
  //         List.of(
  //             CreadorDeDoctores.crearCardiologoGeneral("Dra. Girgenti Ana", "agirgenti@gmail.com"),
  //             CreadorDeDoctores.crearNeumonologo("Dr. Jorge Gutierrez", "jgutierrez@gmail.com"),
  //             CreadorDeDoctores.crearAlergista("Dra. Florencia Aranda", "faranda@gmail.com"),
  //             CreadorDeDoctores.crearClinicoGeneral("Dr. Esteban Quiroga", "equiroga@gmail.com"),
  //             CreadorDeDoctores.crearTraumatologo("Dr. Mario Gómez", "mgomez@gmail.com"));
  //   }

    
  //   public static Database getInstance() {
  //     if (instance == null) {
  //       instance = new Database();
  //     }
  //     return instance;
  //   }

  //   public List<Doctor> getDoctores() {
  //     return doctores;
  //   }

  //   public Doctor getDoctor(String descripcionEspecialidad) {
  //     for (Doctor doctor : doctores) {
  //       if (doctor.especialidad.contiene(descripcionEspecialidad)) {
  //         return doctor;
  //       }
  //     }
  //     return null;
  //   }
  // }

  // public static class CreadorDeDoctores {

  //   public static Doctor crearCardiologoGeneral(String nombre, String email) {
  //     return new Doctor(nombre, new Especialidad("Cardiología > General"), email);
  //   }

  //   public static Doctor crearNeumonologo(String nombre, String email) {
  //     return new Doctor(nombre, new Especialidad("Neumonología > General"), email);
  //   }

  //   public static Doctor crearAlergista(String nombre, String email) {
  //     return new Doctor(nombre, new Especialidad("Neumonología > Alergias"), email);
  //   }

  //   public static Doctor crearKinesiologo(String nombre, String email) {
  //     return new Doctor(nombre, new Especialidad("Kinesiología > General"), email);
  //   }

  //   public static Doctor crearTraumatologo(String nombre, String email) {
  //     return new Doctor(nombre, new Especialidad("Kinesiología > Traumatología"), email);
  //   }

  //   public static Doctor crearClinicoGeneral(String nombre, String email) {
  //     return new Doctor(nombre, new Especialidad("Clínica > General"), email);
  //   }
  // }

  // ---- Aplicando Strategy ----
  public interface IDescuento {
    float calcularDescuento(Doctor doctor, Paciente paciente);
  }

    public interface IPrecioBase {
    float getPrecioBase (Doctor doctor, Paciente paciente);
  }

  public static class DescuentoOsde implements IDescuento {
    @Override
    public float calcularDescuento(Doctor doctor, Paciente paciente) {
        // Lógica de OSDE: 100% en Cardiología, 20% en el resto
        return doctor.especialidad.contiene("Cardiología")
                ? 1.0f 
                : 0.2f;
    }
  }

  public static class DescuentoIoma implements IDescuento {
    @Override
    public float calcularDescuento(Doctor doctor, Paciente paciente) {
        // Lógica de IOMA: 100% en Kinesiología, 15% en el resto
        return doctor.especialidad.contiene("Kinesiología")
                ? 1.0f
                : 0.15f;
    }
  }

  public static class DescuentoPami implements IDescuento {
    @Override
    public float calcularDescuento(Doctor doctor, Paciente paciente) {
        // Lógica de PAMI: 100% en todo
        return 1.0f;
    }
  }

  public static class SinDescuento implements IDescuento {
    @Override
    public float calcularDescuento(Doctor doctor, Paciente paciente) {
        // Lógica por defecto: 0% de descuento
        return 0.0f;
    }
  }

public static class PrecioCardiologia implements IPrecioBase {
    @Override
    public float getPrecioBase(Doctor doctor, Paciente paciente) {
        return 8000;
    }
}

public static class PrecioNeumonologia implements IPrecioBase {
    @Override
    public float getPrecioBase(Doctor doctor, Paciente paciente) {
        return 7000;
    }
}

public static class PrecioKinesiologia implements IPrecioBase {
    @Override
    public float getPrecioBase(Doctor doctor, Paciente paciente) {
        return 7000;
    }
}

public static class PrecioBaseDefault implements IPrecioBase {
    @Override
    public float getPrecioBase(Doctor doctor, Paciente paciente) {
        return 5000; // El precio base por defecto
    }
}

  // ---- Aplicando Simple Factory ----

  public static class AplicarDescuento {
    public static IDescuento getDescuento(String obraSocial) {
        switch (obraSocial) {
            case "OSDE":
                return new DescuentoOsde();
            case "IOMA":
                return new DescuentoIoma();
            case "PAMI":
                return new DescuentoPami();
            default:
                return new SinDescuento(); 
        }
    }
  }  

  public static class PrecioBase {
    public static IPrecioBase getPrecioStrategy(Doctor doctor) {
        if (doctor.especialidad.contiene("Cardiología")) {
            return new PrecioCardiologia();
        } else if (doctor.especialidad.contiene("Neumonología")) {
            return new PrecioNeumonologia();
        } else if (doctor.especialidad.contiene("Kinesiología")) {
            return new PrecioKinesiologia();
        } else {
            return new PrecioBaseDefault();
        }
    }
  }

  // ---- Aplicando Factory Method ----

public interface IDoctor {
    Doctor crearDoctor(String nombre, String email);
}

public static class CardiologoGeneral implements IDoctor {
    @Override
    public Doctor crearDoctor(String nombre, String email) {
        return new Doctor(nombre, new Especialidad("Cardiología > General"), email);
    }
}

public static class NeumonologoGeneral implements IDoctor {
    @Override
    public Doctor crearDoctor(String nombre, String email) {
        return new Doctor(nombre, new Especialidad("Neumonología > General"), email);
    }
}

public static class Alergista implements IDoctor {
    @Override
    public Doctor crearDoctor(String nombre, String email) {
        return new Doctor(nombre, new Especialidad("Neumonología > Alergias"), email);
    }
}

public static class ClinicoGeneral implements IDoctor {
    @Override
    public Doctor crearDoctor(String nombre, String email) {
        return new Doctor(nombre, new Especialidad("Clínica > General"), email);
    }
}


public static class Traumatologo implements IDoctor {
    @Override
    public Doctor crearDoctor(String nombre, String email) {
        return new Doctor(nombre, new Especialidad("Kinesiología > Traumatología"), email);
    }
}
}
