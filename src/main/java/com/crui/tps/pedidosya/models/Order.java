package com.crui.tps.pedidosya.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Order {
  String id;

  Customer customer;
  Driver driver;
  List<OrderProduct> products;
  List<OrderUpdate> updates;
  OrderStatus status;

  public Order(String id, Customer customer, List<OrderProduct> products) {
    this.id = id;
    this.customer = customer;
    this.products = products;
    this.updates = new ArrayList<>();
    this.status = OrderStatus.PENDING;
    this.driver = null;
  }

  public void setDriver(Driver driver) {
    this.driver = driver;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public void addUpdate(OrderUpdate update) {
    this.updates.add(update);
  }

  public void addUpdate(String updateDescription) {
    this.updates.add(
        new OrderUpdate(UUID.randomUUID().toString(), updateDescription, new Date().toString()));
  }

  public Customer getCustomer() {
    return customer;
  }

  public Driver getDriver() {
    return driver;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public List<OrderUpdate> getUpdates() {
    return updates;
  }
}
