package com.crui.tps.pedidosya.models;

import java.util.List;
import java.util.stream.Collectors;

public class OrderProduct {
  Product product;
  int quantity;
  double price;
  ProductVariant variant;
  List<ProductAdditional> additionals;

  public OrderProduct(
      Product product, int quantity, ProductVariant variant, List<ProductAdditional> additionals) {
    this.product = product;
    this.quantity = quantity;
    this.variant = variant;
    this.additionals = additionals;
  }

  public String toString() {
    return product.getName()
        + " - "
        + product.getPrice()
        + " \n "
        + "  Variants: "
        + variant.toString()
        + " \n "
        + "  Additionals: "
        + additionals.stream().map(ProductAdditional::toString).collect(Collectors.joining(", "));
  }
}
