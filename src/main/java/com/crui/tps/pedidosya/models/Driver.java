package com.crui.tps.pedidosya.models;

public class Driver {
  String id;
  String name;
  String email;
  String phone;
  String address;
  boolean isAvailable;

  public Driver(
      String id, String name, String email, String phone, String address, boolean isAvailable) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.address = address;
    this.isAvailable = isAvailable;
  }

  public String getName() {
    return this.name;
  }

  public void setAvailable(boolean isAvailable) {
    this.isAvailable = isAvailable;
  }

  public boolean isAvailable() {
    return this.isAvailable;
  }
}
