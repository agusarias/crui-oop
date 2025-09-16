package com.crui.tps.pedidosya.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.crui.tps.pedidosya.models.Product;
import com.crui.tps.pedidosya.models.ProductAdditional;
import com.crui.tps.pedidosya.models.ProductPhoto;
import com.crui.tps.pedidosya.models.ProductVariant;
import com.crui.tps.pedidosya.models.Restaurant;
import com.crui.tps.pedidosya.utils.RestaurantBuilder;

public class RestaurantRepo {
  List<Restaurant> restaurants = new ArrayList<>();

  public List<Restaurant> searchRestaurants(String query) {
    return restaurants.stream()
        .filter(restaurant -> restaurant.getName().toLowerCase().contains(query.toLowerCase()))
        .collect(Collectors.toList());
  }

  public RestaurantRepo() {
    RestaurantBuilder restaurantBuilder = new RestaurantBuilder();
    Restaurant manolo =
        restaurantBuilder
            .withName("Manolo pizza")
            .withDescription("De italia a tu casa")
            .withAddress("Ratti 500, Ituzaingo, Buenos Aires, Argentina")
            .withPhone("1234567890")
            .withEmail("manolo@example.com")
            .withWebsite("https://manolo.com")
            .openingEveryDay("10:00", "20:00")
            .withProduct(
                new Product(
                    "1",
                    "Pizza",
                    "Pizza de muzzarella",
                    1000,
                    List.of(new ProductVariant("1", "Pizza de muzzarella", 1000)),
                    List.of(new ProductPhoto("1", "https://www.google.com", "Pizza de muzzarella")),
                    List.of(new ProductAdditional("1", "Pizza de muzzarella", 1000))))
            .build();

    Restaurant camorra =
        restaurantBuilder
            .withName("La Camorra")
            .withDescription("La mejor pizza de la ciudad")
            .withAddress("Ratti 600, Ituzaingo, Buenos Aires, Argentina")
            .withEmail("camorra@example.com")
            .withWebsite("https://camorra.com")
            .openingEveryDay("10:00", "20:00")
            .withProduct(
                new Product(
                    "1",
                    "Pizza",
                    "Pizza de muzzarella",
                    1000,
                    List.of(new ProductVariant("1", "Pizza de muzzarella", 1000)),
                    List.of(new ProductPhoto("1", "https://www.google.com", "Pizza de muzzarella")),
                    List.of(new ProductAdditional("1", "Pizza de muzzarella", 1000))))
            .build();

    Restaurant donAntonio =
        restaurantBuilder
            .withName("Don Antonio pizza")
            .withDescription("Calzones y mas")
            .withAddress("Ratti 900, Ituzaingo, Buenos Aires, Argentina")
            .withEmail("don_antonio@example.com")
            .withWebsite("https://don_antonio.com")
            .openingEveryDay("10:00", "20:00")
            .withProduct(
                new Product(
                    "1",
                    "Pizza",
                    "Pizza",
                    1000,
                    List.of(new ProductVariant("1", "Muzzarella", 1000)),
                    List.of(new ProductPhoto("1", "photo_url_1", "Pizza")),
                    List.of(new ProductAdditional("1", "Oregano", 1000))))
            .build();

    restaurants.add(manolo);
    restaurants.add(camorra);
    restaurants.add(donAntonio);
  }
}
