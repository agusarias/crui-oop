package com.crui.patterns.examples.turnos_medicos;

interface DiscountStrategy {
    /**
     * Devuelve un factor de descuento entre 0 y 1 (1 == 100%).
     */
    double calcularDescuento(Paciente paciente, Doctor doctor);
}

public class DiscountStrategies {

    public static DiscountStrategy resolve(Paciente paciente, Doctor doctor) {
        String obra = paciente.getObraSocial();
        if ("OSDE".equalsIgnoreCase(obra)) {
            return new OsdeDiscount();
        } else if ("IOMA".equalsIgnoreCase(obra)) {
            return new IomaDiscount();
        } else if ("PAMI".equalsIgnoreCase(obra)) {
            return new PamiDiscount();
        } else {
            return new NoDiscount();
        }
    }

    private static class OsdeDiscount implements DiscountStrategy {
        @Override
        public double calcularDescuento(Paciente paciente, Doctor doctor) {
            // 100% en Cardiología, 20% en el resto
            return doctor.getEspecialidad().contiene("Cardiología") ? 1.0d : 0.2d;
        }
    }

    private static class IomaDiscount implements DiscountStrategy {
        @Override
        public double calcularDescuento(Paciente paciente, Doctor doctor) {
            // 100% en Kinesiología, 15% en el resto
            return doctor.getEspecialidad().contiene("Kinesiología") ? 1.0d : 0.15d;
        }
    }

    private static class PamiDiscount implements DiscountStrategy {
        @Override
        public double calcularDescuento(Paciente paciente, Doctor doctor) {
            return 1.0d; // 100%
        }
    }

    private static class NoDiscount implements DiscountStrategy {
        @Override
        public double calcularDescuento(Paciente paciente, Doctor doctor) {
            return 0.0d;
        }
    }
}
