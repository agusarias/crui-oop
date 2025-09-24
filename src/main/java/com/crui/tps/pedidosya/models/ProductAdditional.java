package com.crui.tps.pedidosya.models;

public class ProductAdditional {
  String id;
  String name;
  double price;

  public ProductAdditional(String id, String name, double price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public String toString() {
    return name + " - " + price;
  }
}
