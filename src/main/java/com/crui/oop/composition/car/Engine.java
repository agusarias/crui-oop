package com.crui.oop.composition.car;

public class Engine {
  private String type;
  private int power;
  private boolean isOn;

  public Engine(String type, int power) {
    this.type = type;
    this.power = power;
    this.isOn = false;
  }

  protected void start() {
    isOn = true;
  }

  protected void stop() {
    isOn = false;
  }
}
