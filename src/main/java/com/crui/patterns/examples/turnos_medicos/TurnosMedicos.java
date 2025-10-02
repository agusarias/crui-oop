package com.crui.patterns.examples.turnos_medicos;


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
 */
public class TurnosMedicos {

  public static void main(String[] args) {
    System.out.println();
    System.out.println("Turnos Medicos");
    System.out.println("=============");
    System.out.println();
    DataBase database = DataBase.getInstance();

    Paciente paciente = new Paciente("Ignacio Segovia", "OSDE", "isegovia@gmail.com");
    String especialidad = "Cardiología";
    Doctor doctor = database.getDoctor(especialidad);

    if (doctor == null) {
      System.out.println("No se encontró el doctor para la especialidad: " + especialidad);
      return;
    }

    // Strategy: Pricing (precio base por especialidad)
    PricingStrategy pricing = PricingStrategies.resolve(doctor);
    double precioBase = pricing.calcularPrecioBase(doctor);

    // Strategy: Discount (descuento por obra social y especialidad)
    DiscountStrategy discount = DiscountStrategies.resolve(paciente, doctor);
    double descuento = discount.calcularDescuento(paciente, doctor);

    // Aplico el descuento
    double precioConDescuento = precioBase - precioBase * descuento;

    // Strategy: Payment (medio de pago). Por defecto, EFECTIVO
    String medioDePago = "EFECTIVO"; // cambiar a "TARJETA" o "BILLETERA" si se desea
    PaymentStrategy payment = PaymentStrategies.resolve(medioDePago);
    double precioFinal = payment.aplicarPago(precioConDescuento);

    // Nuevo turno con precio final
    Turno turno = new Turno(paciente, doctor, "2025-01-01 10:00", precioFinal);
    System.out.println(turno);

    // Cambio de turno
    turno.setFechaYHora("2025-01-01 11:00");
    System.out.println();
  }
}
