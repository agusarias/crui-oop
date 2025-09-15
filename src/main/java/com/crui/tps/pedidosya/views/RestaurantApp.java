package com.crui.tps.pedidosya.views;

import com.crui.tps.pedidosya.models.Order;
import com.crui.tps.pedidosya.models.OrderStatus;
import com.crui.tps.pedidosya.models.Restaurant;

public class RestaurantApp {
  Restaurant restaurant;

  public RestaurantApp(Restaurant restaurant) {
    this.restaurant = restaurant;
  }

  public void showRestaurant() {
    System.out.println(restaurant.getName());
    System.out.println(restaurant.getDescription());
  }

  public void acceptOrder(Order order) {
    System.out.println("Accepting order: " + order.getCustomer().getName());
    order.setStatus(OrderStatus.ACCEPTED_BY_RESTAURANT);
    order.addUpdate("Order accepted by restaurant");
  }

  public void startPreparation(Order order) {
    System.out.println("Starting preparation: " + order.getCustomer().getName());
    order.setStatus(OrderStatus.IN_PREPARATION);
    order.addUpdate("Order started preparation");
  }

  public void finishPreparation(Order order) {
    System.out.println("Finishing preparation: " + order.getCustomer().getName());
    order.setStatus(OrderStatus.READY_FOR_DELIVERY);
    order.addUpdate("Order finished preparation");
  }
}
