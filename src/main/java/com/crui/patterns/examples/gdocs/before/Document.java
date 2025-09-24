package com.crui.patterns.examples.gdocs.before;

import java.util.ArrayList;
import java.util.List;

public class Document {
  private String title;
  private String content;
  private User user;
  private List<String> changeHistory;
  private UI ui;
  private Trello trello;

  public Document(User user, String title, String content, UI ui, Trello trello) {
    this.user = user;
    this.title = title;
    this.content = content;
    this.ui = ui;
    // TODO: Document tiene que conocer todas las integraciones, es un problema de acoplamiento
    this.trello = trello;
    this.changeHistory = new ArrayList<>();
  }

  public void setContent(String content) {
    this.content = content;
    this.notify("Cambiando contenido del documento a " + content);
  }

  public void notify(String change) {
    this.user.notify(change, "owner");
    this.changeHistory.add(change);
    this.ui.refreshPage();
    this.trello.notify("Cambiando contenido del documento a " + content);
    // TODO: Este método va a seguir creciendo para cada integración, está muy acoplado.
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void save() {
    System.out.println("Saving document...");
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public void print() {
    System.out.println("Printing document...");
    System.out.println(title);
    System.out.println(content);
  }
}
