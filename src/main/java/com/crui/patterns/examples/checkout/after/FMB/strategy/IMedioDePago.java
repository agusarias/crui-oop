package com.crui.patterns.examples.checkout.after.FMB.strategy;

interface IMedioDePago {
  boolean pay(double amount);
}