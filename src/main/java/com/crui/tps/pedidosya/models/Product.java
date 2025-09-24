package com.crui.tps.pedidosya.models;

import java.util.List;
import java.util.stream.Collectors;

public class Product {
  String id;
  String name;
  String description;
  double price;
  List<ProductVariant> variants;
  List<ProductPhoto> photos;
  List<ProductAdditional> additionals;

  public Product(
      String id,
      String name,
      String description,
      double price,
      List<ProductVariant> variants,
      List<ProductPhoto> photos,
      List<ProductAdditional> additionals) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.variants = variants != null ? variants : List.of();
    this.photos = photos != null ? photos : List.of();
    this.additionals = additionals != null ? additionals : List.of();
  }

  public String toString() {
    return name
        + " - "
        + price
        + " \n "
        + "  Variants: "
        + variants.stream().map(ProductVariant::toString).collect(Collectors.joining(", "))
        + " \n "
        + "  Additionals: "
        + additionals.stream().map(ProductAdditional::toString).collect(Collectors.joining(", "))
        + " \n "
        + "  Photos: "
        + photos.stream().map(ProductPhoto::toString).collect(Collectors.joining(", "));
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public List<ProductVariant> getVariants() {
    return variants;
  }

  public List<ProductPhoto> getPhotos() {
    return photos;
  }

  public List<ProductAdditional> getAdditionals() {
    return additionals;
  }
}
