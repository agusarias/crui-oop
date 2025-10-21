package com.crui.patterns.examples.turnos_medicos;

interface PricingStrategy {
    double calcularPrecioBase(Doctor doctor);
}

public class PricingStrategies {

    public static PricingStrategy resolve(Doctor doctor) {
        if (doctor.getEspecialidad().contiene("Cardiología")) {
            return new CardiologiaPricing();
        } else if (doctor.getEspecialidad().contiene("Neumonología")) {
            return new NeumonologiaPricing();
        } else if (doctor.getEspecialidad().contiene("Kinesiología")) {
            return new KinesiologiaPricing();
        } else {
            return new DefaultPricing();
        }
    }

    private static class CardiologiaPricing implements PricingStrategy {
        @Override
        public double calcularPrecioBase(Doctor doctor) {
            return 8000d;
        }
    }

    private static class NeumonologiaPricing implements PricingStrategy {
        @Override
        public double calcularPrecioBase(Doctor doctor) {
            return 7000d;
        }
    }

    private static class KinesiologiaPricing implements PricingStrategy {
        @Override
        public double calcularPrecioBase(Doctor doctor) {
            return 7000d;
        }
    }

    private static class DefaultPricing implements PricingStrategy {
        @Override
        public double calcularPrecioBase(Doctor doctor) {
            return 5000d;
        }
    }
}
