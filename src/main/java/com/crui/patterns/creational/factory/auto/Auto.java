package com.crui.patterns.creational.factory.auto;

import java.util.List;
import java.util.stream.Collectors;

public class Auto {
  private String marca;
  private String modelo;
  private String motor;
  private List<String> accesorios;

  public Auto(String marca, String modelo, String motor, List<String> accesorios) {
    this.marca = marca;
    this.modelo = modelo;
    this.motor = motor;
    this.accesorios = accesorios;
  }

  public String getMarca() {
    return marca;
  }

  public String getModelo() {
    return modelo;
  }

  public String getMotor() {
    return motor;
  }

  public List<String> getAccesorios() {
    return accesorios;
  }

  @Override
  public String toString() {
    return "Auto{"
        + "marca='"
        + marca
        + '\''
        + ", modelo='"
        + modelo
        + '\''
        + ", motor='"
        + motor
        + '\''
        + ", accesorios='"
        + accesorios.stream().map(String::toString).collect(Collectors.joining(", "))
        + '\''
        + '}';
  }
}