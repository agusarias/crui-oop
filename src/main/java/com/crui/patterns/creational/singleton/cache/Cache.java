package com.crui.patterns.creational.singleton.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Cache {
  private Map<String, String> datos;

  public Cache() {
    this.datos = new HashMap<>();
  }

  public void set(String key, String value) {
    this.datos.put(key, value);
  }

  public String get(String key) {
    return this.datos.get(key);
  }

  @Override
  public String toString() {
    return "Cache [datos="
        + datos.entrySet().stream()
            .map(entry -> entry.getKey() + "=" + entry.getValue())
            .collect(Collectors.joining(", "))
        + "]";
  }
}
