package com.crui.tps.pedidosya.models;

import java.util.List;

public class OpeningTimes {
  List<Day> days;
  String openTime;
  String closeTime;

  public OpeningTimes(List<Day> days, String openTime, String closeTime) {
    this.days = days;
    this.openTime = openTime;
    this.closeTime = closeTime;
  }
}
