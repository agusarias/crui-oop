package com.crui.patterns.behavioral.strategy.pagos;

import java.util.Scanner;

public class Pagos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese monto a pagar: $ ");
        double monto = Double.parseDouble(sc.nextLine());

        System.out.println("\nSeleccione método de pago:");
        System.out.println("1) Efectivo");
        System.out.println("2) Tarjeta");
        System.out.println("3) Mercado Pago");
        System.out.print("Opción (1-3): ");

        int opcion;
        try {
            opcion = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida. Debe ingresar un número (1-3).");
            sc.close();
            return;
        }

        ProcesadorDePagos procesador = new ProcesadorDePagos();
        boolean ok = procesador.ejecutarPago(monto, opcion);

        System.out.println("Resultado: " + (ok ? "PAGO EXITOSO" : "PAGO FALLIDO"));
        sc.close();
    }
}