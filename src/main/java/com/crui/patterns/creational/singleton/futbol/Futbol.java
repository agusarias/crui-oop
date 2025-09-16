package com.crui.patterns.creational.singleton.futbol;

import java.util.List;
import java.util.stream.Collectors;

public class Futbol {
  public static void main(String[] args) {
    Partido partido = new Partido();
    List<Jugador> equipoGanador = partido.jugar();
    System.out.println(
        "El equipo ganador es: "
            + equipoGanador.stream().map(Jugador::toString).collect(Collectors.joining(", ")));
  }
}
