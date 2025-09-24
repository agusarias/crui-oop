package com.crui.patterns.examples.gdocs.after;

// Implementamos el adapter TrelloAdapter para que se adapte a la notificación de cambios en el
// documento.
// Este adapter conoce cómo llamar a la clase Trello, y conoce la interfaz Listener, así que actua
// de intermediario entre el Document y la clase Trello.
public class TrelloAdapter implements Listener {

  private Trello trello;

  public TrelloAdapter(Trello trello) {
    this.trello = trello;
  }

  @Override
  public void onDocumentChanged(Document document, Change change) {
    this.trello.notify(change.toString());
  }
}
