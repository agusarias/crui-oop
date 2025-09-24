package com.crui.patterns.creational.singleton.futbol;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Partido {

  private List<Jugador> equipoA;
  private List<Jugador> equipoB;
  private Random random = new Random();

  Partido() {
    equipoA =
        new ArrayList<Jugador>(
            List.of(
                new Jugador("Juan", "4"), new Jugador("Maria", "5"), new Jugador("Ariel", "10")));
    equipoB =
        new ArrayList<Jugador>(
            List.of(
                new Jugador("Juan", "8"), new Jugador("Lola", "9"), new Jugador("Pablo", "10")));
  }

  public List<Jugador> jugar() {
    Pelota pelota = new Pelota();
    int cantidadJugadores = Math.min(equipoA.size(), equipoB.size());
    List<Jugador> equipoActual = this.equipoA;
    List<Jugador> equipoOponente = this.equipoB;
    List<Jugador> aux;
    int i = 0;
    while (i < 1000) {
      i++;
      boolean recupera = random.nextInt(100) < 50;
      if (recupera) {
        equipoOponente.get(i).recuperar(pelota);
        aux = equipoActual;
        equipoActual = equipoOponente;
        equipoOponente = aux;
        i = 0;
      } else {
        equipoActual.get(i).patear(pelota);
        if (i >= cantidadJugadores - 1) {
          return equipoActual;
        }
      }
    }
    return equipoActual;
  }
}
