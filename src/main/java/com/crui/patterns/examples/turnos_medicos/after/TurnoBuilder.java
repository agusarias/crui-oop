package com.crui.patterns.examples.turnos_medicos.after;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * BUILDER para construir Turnos con validaciones y valores por defecto.
 *
 * Reglas aplicadas:
 * - paciente y doctor: obligatorios.
 * - fechaYHora: si no se setea o no cumple el formato "YYYY-MM-DD HH:MM", queda "A confirmar".
 * - precio: si no se setea, se calcula con la lógica original:
 *   precioBase por especialidad y descuento por obra social.
 */

public class TurnoBuilder {

  private static final Pattern FECHA_HORA_REGEX =
      Pattern.compile("^\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}$");

  private static final String FECHA_POR_DEFECTO = "A confirmar";

  private Paciente paciente;
  private Doctor doctor;
  private String fechaYHora; // opcional
  private Double precio;     // opcional

  private TurnoBuilder() {}

  public static TurnoBuilder nuevo() {
    return new TurnoBuilder();
  }

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

  public TurnoBuilder precio(double precio) {
    this.precio = precio;
    return this;
  }

  public Turno construir() {
    validarObligatorios();

    String fechaFinal = normalizarFecha(this.fechaYHora);

    double precioFinal =
        (this.precio != null)
            ? this.precio
            : calcularPrecioPorDefecto(
                Objects.requireNonNull(paciente).obraSocial,
                Objects.requireNonNull(doctor).especialidad);

    return new Turno(paciente, doctor, fechaFinal, precioFinal);
  }

  private void validarObligatorios() {
    if (paciente == null) {
      throw new IllegalArgumentException("El paciente es obligatorio para crear un Turno.");
    }
    if (doctor == null) {
      throw new IllegalArgumentException("El doctor es obligatorio para crear un Turno.");
    }
  }

  private String normalizarFecha(String fecha) {
    if (fecha == null || !FECHA_HORA_REGEX.matcher(fecha).matches()) {
      return FECHA_POR_DEFECTO;
    }
    return fecha;
  }

  // --- Lógica original de precios ---
  private double calcularPrecioPorDefecto(String obraSocial, Especialidad especialidad) {
    float precioBase = calcularPrecioBase(especialidad);
    float descuento = calcularDescuento(obraSocial, especialidad);
    return precioBase - precioBase * descuento;
  }

  private float calcularPrecioBase(Especialidad esp) {
    if (esp != null && esp.contiene("Cardiología")) {
      return 8000f;
    } else if (esp != null && esp.contiene("Neumonología")) {
      return 7000f;
    } else if (esp != null && esp.contiene("Kinesiología")) {
      return 7000f;
    } else {
      return 5000f;
    }
  }

  private float calcularDescuento(String obraSocial, Especialidad esp) {
    if (obraSocial == null) return 0f;

    switch (obraSocial) {
      case "OSDE":
        return (esp != null && esp.contiene("Cardiología")) ? 1.0f : 0.2f;
      case "IOMA":
        return (esp != null && esp.contiene("Kinesiología")) ? 1.0f : 0.15f;
      case "PAMI":
        return 1.0f;
      default:
        return 0.0f;
    }
  }
}
