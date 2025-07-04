package com.pedidosya.views;

import java.util.Date;
import java.util.UUID;

import com.pedidosya.models.Driver;
import com.pedidosya.models.Order;
import com.pedidosya.models.OrderStatus;
import com.pedidosya.models.OrderUpdate;

public class DriverApp {

  Driver loggedDriver;
  Order currentOrder;

  public void login(Driver driver) {
    this.loggedDriver = driver;
  }

  public void offerOrderToDriver(Order order) {
    System.out.println("Offering order to driver: " + order.getCustomer().getName());
    this.currentOrder = order;
  }

  public void acceptOrder() {
    System.out.println(
        loggedDriver.getName() + " is accepting order: " + currentOrder.getCustomer().getName());
    currentOrder.setDriver(loggedDriver);
    currentOrder.setStatus(OrderStatus.ACCEPTED_BY_DRIVER);
    currentOrder.addUpdate("Order accepted by driver");
  }

  public void rejectOrder() {
    System.out.println("Rejecting order: " + currentOrder.getCustomer().getName());
    currentOrder.addUpdate("Order rejected by driver");
  }

  public void startDelivery() {
    System.out.println("Starting delivery: " + currentOrder.getCustomer().getName());
    currentOrder.setStatus(OrderStatus.ON_THE_WAY);
    currentOrder.addUpdate("Order started delivery");
  }

  public void finishDelivery() {
    System.out.println("Finishing delivery: " + currentOrder.getCustomer().getName());
    currentOrder.setStatus(OrderStatus.DELIVERED);
    currentOrder.addUpdate("Order finished delivery");
  }
}
