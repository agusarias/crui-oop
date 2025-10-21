package com.crui.patterns.examples.turnos_medicos.after;

public class Especialidad {
  String descripcion;

  public Especialidad(String descripcion) {
    this.descripcion = descripcion;
  }

  public boolean contiene(String descripcion) {
    return this.descripcion != null && this.descripcion.contains(descripcion);
  }

  @Override
  public String toString() {
    return descripcion;
  }
}
