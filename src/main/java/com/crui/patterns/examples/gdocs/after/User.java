package com.crui.patterns.examples.gdocs.after;

public class User implements Listener {
  private String name;
  private String email;

  public User(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public void onDocumentChanged(Document document, Change change) {
    // User se adapta a la notificación de cambios en el documento, y no al revés
    System.out.println("User " + name + " notified as " + "about change: " + change.toString());
  }
}
