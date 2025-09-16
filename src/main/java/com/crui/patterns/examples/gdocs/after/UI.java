package com.crui.patterns.examples.gdocs.after;

public class UI implements Listener {
  public void refreshPage() {
    System.out.println("Refreshing page...");
  }

  @Override
  public void onDocumentChanged(Document document, Change change) {
    // UI se adapta a la notificación de cambios en el documento, y no al revés
    this.refreshPage();
  }
}
