package com.crui.patterns.examples.gdocs.before;

public class GoogleDocsExample {

  public static void main(String[] args) {
    UI ui = new UI();
    Trello trello = new Trello();
    User user = new User("John Doe", "john.doe@example.com");

    Document document = new Document(user, "Test", "This is a test document", ui, trello);

    // TODO: cómo hago para notificar a un segundo usuario?
    // User otherUser = new User("Jane Doe", "jane.doe@example.com");

    document.setContent("This is a test document updated 1");
    document.setContent("This is a test document updated 2");
  }

  public void createDocument() {
    System.out.println("Creating a document...");
  }
}
