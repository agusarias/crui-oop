package com.crui.patterns.examples.gdocs.after;

import java.util.ArrayList;
import java.util.List;

public class Document {
  private String title;
  private String content;
  private User user;
  private List<Listener> listeners;

  public Document(User user, String title, String content) {
    this.user = user;
    this.title = title;
    this.content = content;
    // Document no conoce a los listeners, solo sabe que hay que notificarlos
    this.listeners = new ArrayList<>();
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
    this.notify(new Change(user, content, "Cambiando contenido del documento"));
  }

  public void setTitle(String title) {
    this.title = title;
    this.notify(new Change(user, title, "Cambiando título del documento"));
  }

  public void notify(Change change) {
    // No importa cuántas integraciones tengamos, se las notifica a todas igual
    this.listeners.forEach(listener -> listener.onDocumentChanged(this, change));
  }

  public void addListener(Listener listener) {
    this.listeners.add(listener);
  }

  public void save() {
    System.out.println("Saving document...");
  }

  public void print() {
    System.out.println("Printing document...");
    System.out.println(title);
    System.out.println(content);
  }
}
