package com.crui.tps.pedidosya.models;

public enum OrderStatus {
  PENDING("PENDING"),
  ACCEPTED_BY_RESTAURANT("ACCEPTED_BY_RESTAURANT"),
  ACCEPTED_BY_DRIVER("ACCEPTED_BY_DRIVER"),
  IN_PREPARATION("IN_PREPARATION"),
  READY_FOR_DELIVERY("READY_FOR_DELIVERY"),
  ON_THE_WAY("ON_THE_WAY"),
  DELIVERED("DELIVERED"),
  CANCELLED("CANCELLED");

  private final String value;

  OrderStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
