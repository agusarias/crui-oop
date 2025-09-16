package com.crui.tps.pedidosya.views;

import com.crui.tps.pedidosya.models.Customer;
import com.crui.tps.pedidosya.models.Order;
import com.crui.tps.pedidosya.models.OrderProduct;
import com.crui.tps.pedidosya.models.OrderStatus;
import com.crui.tps.pedidosya.models.Product;
import com.crui.tps.pedidosya.models.Restaurant;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.crui.tps.pedidosya.controllers.RestaurantCatalogController;

public class CustomerApp {
  Customer loggedCustomer;
  Restaurant selectedRestaurant;
  Order order;

  // Referencia local del controlador porque estamos en un ejemplo que
  // se ejecuta dentro de una sola máquina. En casos reales, sería una URL.
  RestaurantCatalogController restaurantCatalogController;

  public CustomerApp(RestaurantCatalogController restaurantCatalogController) {
    this.restaurantCatalogController = restaurantCatalogController;
  }

  public void login(Customer customer) {
    this.loggedCustomer = customer;
  }

  public List<Restaurant> searchRestaurants(String query) {
    System.out.println("Searching for restaurants with query: " + query);
    List<Restaurant> restaurants = restaurantCatalogController.searchRestaurants(query);
    System.out.println(
        "Results: "
            + restaurants.stream().map(Restaurant::getName).collect(Collectors.joining(", ")));

    return restaurants;
  }

  public List<Product> selectRestaurant(Restaurant restaurant) {
    this.selectedRestaurant = restaurant;
    System.out.println("Selected restaurant: " + selectedRestaurant.getName());

    // print products
    System.out.println(
        "Products: "
            + selectedRestaurant.getProducts().stream()
                .map(Product::toString)
                .collect(Collectors.joining("\n")));
    return selectedRestaurant.getProducts();
  }

  public Order createOrderWithProducts(List<Product> products) {

    List<OrderProduct> orderProducts =
        products.stream()
            .map(
                product ->
                    new OrderProduct(
                        product,
                        1,
                        product.getVariants().get(0), // Seleccionar la primer variante,
                        List.of())) // Sin adicionales
            .collect(Collectors.toList());

    System.out.println(
        "Creating order with products: "
            + orderProducts.stream().map(OrderProduct::toString).collect(Collectors.joining("\n")));

    this.order = new Order(UUID.randomUUID().toString(), loggedCustomer, orderProducts);
    this.order.setStatus(OrderStatus.PENDING);
    this.order.addUpdate("Order created");
    return this.order;
  }
}
