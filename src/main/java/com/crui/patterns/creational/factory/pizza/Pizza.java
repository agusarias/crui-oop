package com.crui.patterns.creational.factory.pizza;

import java.util.List;
import java.util.stream.Collectors;

public class Pizza {
  private String nombre;
  private String masa;
  private String salsa;
  private List<String> adicionales;

  public Pizza(String nombre, String masa, String salsa, List<String> adicionales) {
    this.nombre = nombre;
    this.masa = masa;
    this.salsa = salsa;
    this.adicionales = adicionales;
  }

  public String getNombre() {
    return nombre;
  }

  public String getMasa() {
    return masa;
  }

  public String getSalsa() {
    return salsa;
  }

  public List<String> getAdicionales() {
    return adicionales;
  }

  @Override
  public String toString() {
    return "Pizza{"
        + "nombre='"
        + nombre
        + '\''
        + ", masa='"
        + masa
        + '\''
        + ", salsa='"
        + salsa
        + '\''
        + ", adicionales='"
        + adicionales.stream().map(String::toString).collect(Collectors.joining(", "))
        + '\''
        + '}';
  }
}
