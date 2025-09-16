package com.crui.patterns.examples.gdocs.after;

// Quizás no podemos modificar esta clase, por ejemplo si es una librería externa, por eso
// implementamos el adapter TrelloAdapter.
public class Trello {
  public void notify(String change) {
    System.out.println("Trello notified about change: " + change);
  }
}
