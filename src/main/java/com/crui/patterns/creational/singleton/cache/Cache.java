package com.crui.patterns.creational.singleton.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Cache {
  private static Cache instancia;

  private Map<String, String> datos;

  private Cache() {
    this.datos = new HashMap<String, String>();
  }

  public static Cache getInstancia() {
    if (Cache.instancia == null) {
      Cache.instancia = new Cache(); // Lazy load
    }
    return Cache.instancia;
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
