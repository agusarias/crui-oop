package com.crui.patterns.behavioral.strategy.pagos;

import java.time.YearMonth;

public class ProcesadorDePagos {

    public boolean ejecutarPago(double monto, int opcion) {
        EstrategiaPago estrategia;

        switch (opcion) {
            case 1: // Efectivo
                estrategia = new PagoEfectivo("Caja 1 - Sucursal Ituzaingó");
                break;

            case 2: // Tarjeta
                estrategia = new PagoTarjetaDeCredito(
                        "4539 1488 0343 6467",
                        "Jorge Marinoni",
                        "123",
                        YearMonth.now().plusMonths(6)
                );
                break;

            case 3: // Mercado Pago
                estrategia = new PagoMercadoPago("jorge@sharktech.com.ar", "tok_ABC123");
                break;

            default:
                System.out.println("Opción inválida. Elija 1, 2 o 3.");
                return false;
        }

        return estrategia.pagar(monto);
    }
}