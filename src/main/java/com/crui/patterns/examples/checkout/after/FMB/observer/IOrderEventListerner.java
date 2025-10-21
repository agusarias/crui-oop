package com.crui.patterns.examples.checkout.after.FMB.observer;

import com.crui.patterns.examples.checkout.after.FMB.Orden;

interface IOrdenEventListener {
  void onPaid(Orden order);
}