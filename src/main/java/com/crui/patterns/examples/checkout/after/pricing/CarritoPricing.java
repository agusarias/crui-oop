package com.crui.patterns.examples.checkout.after.pricing;

import com.crui.patterns.examples.checkout.after.domain.Carrito;

public class CarritoPricing implements Pricing {
  private final Carrito carrito;

  public CarritoPricing(Carrito carrito) {
    this.carrito = carrito;
  }

  @Override
  public double total() {
    return carrito.subtotal();
  }

  @Override
  public String details() {
    return "Items: " + carrito.toString() + "\nSubtotal: $" + carrito.subtotal();
  }
}
