package com.crui.patterns.examples.checkout.after.order;

public interface OrdenEventListener {
  void onPaid(Orden order);
}
