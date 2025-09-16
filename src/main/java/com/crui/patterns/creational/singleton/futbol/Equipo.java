package com.crui.patterns.creational.singleton.futbol;

import java.util.List;
import java.util.stream.Collectors;

public class Equipo {
  private List<Jugador> jugadores;

  public Equipo(List<Jugador> jugadores) {
    this.jugadores = jugadores;
  }

  @Override
  public String toString() {
    return jugadores.stream().map(Jugador::toString).collect(Collectors.joining(", "));
  }
}
