package com.crui.tps.uber;

import com.crui.tps.uber.views.CustomerApp;
import com.crui.tps.uber.views.DriverApp;

public class UberFlow {
  public static void main(String[] args) {
    System.out.println("UberFlow");

    /**
     * Simular de manera similar al ejemplo de pedidosya, el caso de uso principal de uber.
     *
     * <p>El programa inicia cuando el usuario ingresa el destino y el origen, y termina cuando el
     * usuario califica el viaje.
     *
     * <p>El diagrama de flujo realizado en el trabajo anterior puede servir de base para el
     * desarrollo del programa.
     */
    CustomerApp customerApp = new CustomerApp();
    DriverApp driverApp = new DriverApp();
  }
}
