package com.crui.patterns.behavioral.strategy.pagos;

import java.time.YearMonth;

public class PagoTarjetaDeCredito implements EstrategiaPago {

    private final String numero;
    private final String nombreTitular;
    private final String cvv;
    private final YearMonth vencimiento;

    public PagoTarjetaDeCredito(String numero, String nombreTitular, String cvv, YearMonth vencimiento) {
        this.numero = numero.replaceAll("\\s+", "");
        this.nombreTitular = nombreTitular;
        this.cvv = cvv;
        this.vencimiento = vencimiento;
    }

    @Override
    public boolean pagar(double monto) {
        if (monto <= 0) {
            System.out.println("[Tarjeta] Monto inválido.");
            return false;
        }
        if (!validarDatos()) {
            System.out.println("[Tarjeta] Datos de tarjeta inválidos.");
            return false;
        }
        System.out.printf("[Tarjeta] Pagando $ %.2f con la tarjeta **** **** **** %s a nombre de %s...%n",
                monto, ultimos4(), nombreTitular);
        // Envío a gateway de pago:
        System.out.println("[Tarjeta] Pago aprobado");
        return true;
    }

    private boolean validarDatos() {
        // Validación longitud y fecha:
        if (numero.length() < 13 || numero.length() > 19) return false;
        if (cvv.length() < 3 || cvv.length() > 4) return false;
        if (vencimiento.isBefore(YearMonth.now())) return false;
        // Luhn simplificado:
        return luhn(numero);
    }

    private String ultimos4() {
        int len = numero.length();
        return numero.substring(Math.max(0, len - 4));
    }

    private boolean luhn(String digits) {
        int sum = 0;
        boolean alternate = false;
        for (int i = digits.length() - 1; i >= 0; i--) {
            char c = digits.charAt(i);
            if (!Character.isDigit(c)) return false;
            int n = c - '0';
            if (alternate) {
                n *= 2;
                if (n > 9) n -= 9;
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
}
