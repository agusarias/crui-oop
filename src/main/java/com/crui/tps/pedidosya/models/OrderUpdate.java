package com.crui.tps.pedidosya.models;

public class OrderUpdate {
  String id;
  String description;
  String timestamp;

  public OrderUpdate(String id, String description, String timestamp) {
    this.id = id;
    this.description = description;
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return timestamp + " -  " + description;
  }
}
