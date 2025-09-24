package com.crui.patterns.creational.singleton.futbol;

public class Jugador {
  private String nombre;
  private String numero;

  public Jugador(String nombre, String numero) {
    this.nombre = nombre;
    this.numero = numero;
  }

  public void patear(Pelota pelota) {
    System.out.println(nombre + "(" + numero + ") patea la pelota");
    pelota.cambioDireccion(10);
  }

  public void recuperar(Pelota pelota) {
    System.out.println(nombre + "(" + numero + ") recupera la pelota");
    pelota.cambioDireccion(0);
  }

  @Override
  public String toString() {
    return nombre + "(" + numero + ")";
  }
}
