package com.crui.tps.pedidosya.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.crui.tps.pedidosya.models.Day;
import com.crui.tps.pedidosya.models.OpeningTimes;
import com.crui.tps.pedidosya.models.Product;
import com.crui.tps.pedidosya.models.Restaurant;

public class RestaurantBuilder {
  String name;
  String description;
  String address;
  String phone;
  String email;
  String website;
  OpeningTimes openingTimes;
  List<Product> products;

  public RestaurantBuilder() {
    reset();
  }

  public void reset() {
    this.name = null;
    this.description = "Default";
    this.address = "Default";
    this.phone = "123";
    this.email = "asdasd@asdas.com";
    this.website = "htt:asdasd";
    this.openingTimes = new OpeningTimes(List.of(Day.MONDAY), "10:00", "20:00");
    this.products = new ArrayList<Product>();
  }

  public RestaurantBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public RestaurantBuilder withDescription(String description) {
    this.description = description;
    return this;
  }

  public RestaurantBuilder withAddress(String address) {
    this.address = address;
    return this;
  }

  public RestaurantBuilder withPhone(String phone) {
    this.phone = phone;
    return this;
  }

  public RestaurantBuilder withEmail(String email) {
    this.email = email;
    return this;
  }

  public RestaurantBuilder withWebsite(String website) {
    this.website = website;
    return this;
  }

  public RestaurantBuilder withOpeningTimes(OpeningTimes openingTimes) {
    this.openingTimes = openingTimes;
    return this;
  }

  public RestaurantBuilder withProduct(Product product) {
    this.products.add(product);
    return this;
  }

  public RestaurantBuilder withProducts(List<Product> products) {
    this.products.addAll(products);
    return this;
  }

  public RestaurantBuilder withOpeningTimes(List<Day> days, String openTime, String closeTime) {
    this.openingTimes = new OpeningTimes(days, openTime, closeTime);
    return this;
  }

  public RestaurantBuilder openingEveryDay(String openTime, String closeTime) {
    this.openingTimes = new OpeningTimes(List.of(Day.values()), openTime, closeTime);
    return this;
  }

  public RestaurantBuilder openingWeekDays(String openTime, String closeTime) {
    this.openingTimes =
        new OpeningTimes(
            List.of(Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY),
            openTime,
            closeTime);
    return this;
  }

  public RestaurantBuilder openingWeekends(String openTime, String closeTime) {
    this.openingTimes =
        new OpeningTimes(List.of(Day.FRIDAY, Day.SATURDAY, Day.SUNDAY), openTime, closeTime);
    return this;
  }

  public Restaurant build() {
    return new Restaurant(
        UUID.randomUUID().toString(),
        this.name,
        this.description,
        this.address,
        this.phone,
        this.email,
        this.website,
        this.openingTimes,
        this.products);
  }
}
