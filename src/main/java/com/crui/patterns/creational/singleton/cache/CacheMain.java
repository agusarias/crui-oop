package com.crui.patterns.creational.singleton.cache;

public class CacheMain {
  public static void main(String[] args) {
    Cache cache = new Cache();

    cache.set("369022", "Juan Perez");
    cache.set("369023", "Maria Gomez");
    cache.set("369024", "Pedro Rodriguez");
    cache.set("369025", "Ana Martinez");
    cache.set("369026", "Luis Garcia");
    cache.set("369027", "Carlos Rodriguez");
    cache.set("369028", "Ana Martinez");

    System.out.println(cache);
  }
}
