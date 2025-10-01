package com.crui.patterns.examples.turnos_medicos.after;

public class Turno {
  private Paciente paciente;
  private Doctor doctor;
  private String fechaYHora;
  private double precio;

  Turno(Paciente paciente, Doctor doctor, String fechaYHora, double precio) {
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

  public double getPrecio() {
    return precio;
  }

  public void setFechaYHora(String fechaYHora) {
    this.fechaYHora = fechaYHora;
    this.avisarCambioDeFechayHora(this);
  }

  void avisarCambioDeFechayHora(Turno turno) {
    this.doctor.avisarCambioDeFechayHora(turno);
    this.paciente.avisarCambioDeFechayHora(turno);
  }

  @Override
  public String toString() {
    return "Turno para " + paciente + " con " + doctor + " el " + fechaYHora + " - $" + precio;
  }
}