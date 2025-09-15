package com.crui.tps.pedidosya.models;

import java.util.List;

public class Restaurant {
  String id;
  String name;
  String description;
  String address;
  String phone;
  String email;
  String website;
  OpeningTimes openingTimes;
  List<Product> products;

  public Restaurant(
      String id,
      String name,
      String description,
      String address,
      String phone,
      String email,
      String website,
      OpeningTimes openingTimes,
      List<Product> products) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.address = address;
    this.phone = phone;
    this.email = email;
    this.website = website;
    this.openingTimes = openingTimes;
    this.products = products;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public List<Product> getProducts() {
    return products;
  }

  public OpeningTimes getOpeningTimes() {
    return openingTimes;
  }
}
