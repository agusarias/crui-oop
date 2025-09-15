package com.crui.patterns.examples.gdocs.after;

public class GoogleDocsExample {

  public static void main(String[] args) {
    UI ui = new UI();
    Trello trello = new Trello();
    TrelloAdapter trelloAdapter = new TrelloAdapter(trello);
    User user = new User("John Doe", "john.doe@example.com");

    Document document = new Document(user, "Test", "This is a test document");

    User otherUser = new User("Jane Doe", "jane.doe@example.com");

    document.addListener(user);
    document.addListener(otherUser);
    document.addListener(ui);
    document.addListener(trelloAdapter);

    document.setContent("This is a test document updated 1");
    document.setContent("This is a test document updated 2");
  }

  public void createDocument() {
    System.out.println("Creating a document...");
  }
}
