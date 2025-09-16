package com.crui.tps.pedidosya.models;

public class ProductVariant {
  String id;
  String name;
  double price;

  public ProductVariant(String id, String name, double price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public String toString() {
    return name + " - " + price;
  }
}
