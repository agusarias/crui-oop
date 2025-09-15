package com.crui.tps.pedidosya.models;

public class Customer {
  String id;
  String name;
  String email;
  String phone;
  String address;

  public Customer(String id, String name, String email, String phone, String address) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.address = address;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
