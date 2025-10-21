package com.crui.patterns.examples.checkout.after.domain;

import java.util.*;

public class Carrito {
  private final List<Producto> items = new ArrayList<>();

  public void add(Producto p) { items.add(p); }

  public List<Producto> getItems() {
    return Collections.unmodifiableList(items);
  }

  public double subtotal() {
    double s = 0;
    for (Producto p : items) s += p.getPrecio();
    return s;
  }

  @Override
  public String toString() { return items.toString(); }
}
