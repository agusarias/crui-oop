package com.crui.patterns.creational.singleton.cache;

public class CacheMain {
  Cache cache;

  CacheMain() {
    this.cache = Cache.getInstancia();
  }

  public static void main(String[] args) {
    CacheMain instanciaDeMain = new CacheMain();
    String DNI1 = "369002534";
    String DNI2 = "123414123";
    System.out.println(instanciaDeMain.consultaNombre(DNI1));
    System.out.println(instanciaDeMain.consultaNombre(DNI2));
    System.out.println(instanciaDeMain.consultaNombre(DNI1));
    System.out.println(instanciaDeMain.consultaNombre(DNI1));

    CacheMain instanciaDeMain2 = new CacheMain();
    System.out.println(instanciaDeMain2.consultaNombre(DNI2));
    System.out.println(instanciaDeMain2.consultaNombre(DNI1));
    System.out.println(instanciaDeMain2.consultaNombre(DNI2));
  }

  public String consultaNombre(String dni) {
    // Me fijo si está en CACHE ✅
    String result = this.cache.get(dni);
    boolean estaEnCache = result != null;

    // Si NO está
    if (!estaEnCache) {
      // lo busco
      ServicioDNI servicio = new ServicioDNI();
      result = servicio.getNombreForDNI(dni);
      // lo cargo en cache
      this.cache.set(dni, result);
    }

    // Devuelvo lo que tengo en cache
    return result;
  }

  public String consultaNombreOld(String dni) {
    ServicioDNI servicio = new ServicioDNI();
    return servicio.getNombreForDNI(dni);
  }

  static class ServicioDNI {
    String getNombreForDNI(String dni) {
      try {
        Thread.sleep(1000);
        System.out.println("Yendo al servicio a buscar el dato....");
        return dni == "369002534" ? "Pepito" : "Josesito";
      } catch (Exception e) {
        return "Timeout";
      }
    }
  }
}
