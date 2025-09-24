package com.crui.tps.pedidosya.models;

public class ProductPhoto {
  String id;
  String url;
  String description;

  public ProductPhoto(String id, String url, String description) {
    this.id = id;
    this.url = url;
    this.description = description;
  }

  public String toString() {
    return url + " - " + description;
  }
}
