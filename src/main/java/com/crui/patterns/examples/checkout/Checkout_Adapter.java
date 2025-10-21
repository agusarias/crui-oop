package com.crui.patterns.examples.checkout;

import java.util.*;

/**
 * Checkout refactorizado aplicando:
 * - Strategy: diferentes formas de pago.
 * - Observer: notificaciones al pagar.
 * - Decorator: extras de la orden (envoltorio y envío express).
 * - Adapter: integración de API externa de pagos.
 */
public class Checkout_Adapter {

    public static void main(String[] args) {
        // Productos base
        Producto libro = new Producto("Clean Code", 25.0);
        Producto mouse = new Producto("Mouse USB", 12.5);

        // Armo el carrito
        Carrito carrito = new Carrito();
        carrito.add(libro);
        carrito.add(mouse);

        // Creo la orden
        Orden orden = new Orden(carrito);
        orden.incluirEnvoltorio(true);
        orden.incluirEnvioExpress(true);

        // Observer
        orden.addListener(new EmailListener());
        orden.addListener(new AnalyticsListener());

        // Strategy + Adapter
        String paymentType = "mercado-pago"; // puede ser "cash", "card", "mercado-pago"
        MedioDePago medioDePago;
        if ("card".equalsIgnoreCase(paymentType)) {
            medioDePago = new PagoTarjeta("Juan Perez", "4111111111111111");
        } else if ("cash".equalsIgnoreCase(paymentType)) {
            medioDePago = new PagoEfectivo();
        } else if ("mercado-pago".equalsIgnoreCase(paymentType)) {
            medioDePago = new PagoMercadoPagoAdapter(new MercadoPagoAPI());
        } else {
            throw new IllegalArgumentException("Medio de pago no soportado");
        }

        orden.setMedioDePago(medioDePago);

        // Mostrar resumen y pagar
        orden.printSummary();
        orden.pagar();
    }

    // ===================== Dominio =====================
    static class Producto {
        private final String nombre;
        private final double precio;

        public Producto(String nombre, double precio) {
            this.nombre = nombre;
            this.precio = precio;
        }

        public String getNombre() {
            return nombre;
        }

        public double getPrecio() {
            return precio;
        }

        @Override
        public String toString() {
            return nombre + " ($" + precio + ")";
        }
    }

    static class Carrito {
        private final List<Producto> items = new ArrayList<>();

        public void add(Producto p) {
            items.add(p);
        }

        public List<Producto> getItems() {
            return Collections.unmodifiableList(items);
        }

        public double subtotal() {
            double total = 0;
            for (Producto p : items) total += p.getPrecio();
            return total;
        }

        @Override
        public String toString() {
            return items.toString();
        }
    }

    // ===================== Observer =====================
    interface OrdenEventListener {
        void onPaid(Orden order);
    }

    static class EmailListener implements OrdenEventListener {
        @Override
        public void onPaid(Orden order) {
            System.out.println("[Email] Enviando comprobante a cliente. Total: $" + order.calcularTotal());
        }
    }

    static class AnalyticsListener implements OrdenEventListener {
        @Override
        public void onPaid(Orden order) {
            System.out.println("[Analytics] Registrando venta. Ítems: " + order.getCarrito().getItems().size());
        }
    }

    // ===================== Orden + Decorator =====================
    static class Orden {
        private final Carrito carrito;
        private final List<OrdenEventListener> listeners = new ArrayList<>();
        private MedioDePago paymentGateway;

        private boolean envoltorioRegalo;
        private boolean envioExpress;

        public Orden(Carrito carrito) {
            this.carrito = carrito;
        }

        public Carrito getCarrito() {
            return carrito;
        }

        public void addListener(OrdenEventListener l) {
            listeners.add(l);
        }

        public void setMedioDePago(MedioDePago p) {
            this.paymentGateway = p;
        }

        public void incluirEnvoltorio(boolean v) {
            this.envoltorioRegalo = v;
        }

        public void incluirEnvioExpress(boolean v) {
            this.envioExpress = v;
        }

        // Patrón Decorator para extras
        interface OrdenComponent {
            double calcularTotal();
            String getDescripcion();
        }

        static class OrdenBase implements OrdenComponent {
            private final Carrito carrito;

            public OrdenBase(Carrito carrito) {
                this.carrito = carrito;
            }

            @Override
            public double calcularTotal() {
                return carrito.subtotal();
            }

            @Override
            public String getDescripcion() {
                return carrito.toString();
            }
        }

        abstract static class OrdenDecorator implements OrdenComponent {
            protected OrdenComponent orden;

            public OrdenDecorator(OrdenComponent orden) {
                this.orden = orden;
            }

            @Override
            public double calcularTotal() {
                return orden.calcularTotal();
            }

            @Override
            public String getDescripcion() {
                return orden.getDescripcion();
            }
        }

        static class EnvoltorioRegalo extends OrdenDecorator {
            public EnvoltorioRegalo(OrdenComponent orden) {
                super(orden);
            }

            @Override
            public double calcularTotal() {
                return super.calcularTotal() + 5.0;
            }

            @Override
            public String getDescripcion() {
                return super.getDescripcion() + " + Envoltorio de regalo";
            }
        }

        static class EnvioExpress extends OrdenDecorator {
            public EnvioExpress(OrdenComponent orden) {
                super(orden);
            }

            @Override
            public double calcularTotal() {
                return super.calcularTotal() + 10.0;
            }

            @Override
            public String getDescripcion() {
                return super.getDescripcion() + " + Envío Express";
            }
        }

        public double calcularTotal() {
            OrdenComponent base = new OrdenBase(carrito);
            if (envoltorioRegalo) base = new EnvoltorioRegalo(base);
            if (envioExpress) base = new EnvioExpress(base);
            return base.calcularTotal();
        }

        public void printSummary() {
            OrdenComponent base = new OrdenBase(carrito);
            if (envoltorioRegalo) base = new EnvoltorioRegalo(base);
            if (envioExpress) base = new EnvioExpress(base);

            System.out.println("=== ORDER SUMMARY ===");
            System.out.println("Items: " + carrito);
            System.out.println("Detalle: " + base.getDescripcion());
            System.out.println("TOTAL: $" + base.calcularTotal());
        }

        public void pagar() {
            if (paymentGateway == null) {
                System.out.println("No hay gateway de pago configurado.");
                return;
            }
            boolean ok = paymentGateway.pay(calcularTotal());
            if (ok) {
                System.out.println("Pago exitoso por $" + calcularTotal());
                notifyPaid();
            } else {
                System.out.println("Pago rechazado.");
            }
        }

        private void notifyPaid() {
            for (OrdenEventListener l : listeners) l.onPaid(this);
        }
    }

    // ===================== Strategy =====================
    interface MedioDePago {
        boolean pay(double amount);
    }

    static class PagoEfectivo implements MedioDePago {
        @Override
        public boolean pay(double amount) {
            System.out.println("[Cash] Recibidos $" + amount + " en efectivo.");
            return true;
        }
    }

    static class PagoTarjeta implements MedioDePago {
        private final String holder;
        private final String cardNumber;

        public PagoTarjeta(String holder, String cardNumber) {
            this.holder = holder;
            this.cardNumber = cardNumber;
        }

        @Override
        public boolean pay(double amount) {
            System.out.println("[Card] Autorizando tarjeta de " + holder + " ****" + last4());
            return amount <= 100.0; // simulación
        }

        private String last4() {
            if (cardNumber == null || cardNumber.length() < 4) return "????";
            return cardNumber.substring(cardNumber.length() - 4);
        }
    }

    // ===================== Adapter =====================
    static class MercadoPagoAPI {
        public boolean runPayment(int amountInCents) {
            System.out.println("[MercadoPagoAPI] Procesando $" + (amountInCents / 100.0));
            return amountInCents <= 20000;
        }
    }

    static class PagoMercadoPagoAdapter implements MedioDePago {
        private final MercadoPagoAPI mpApi;

        public PagoMercadoPagoAdapter(MercadoPagoAPI mpApi) {
            this.mpApi = mpApi;
        }

        @Override
        public boolean pay(double amount) {
            int amountInCents = (int) (amount * 100);
            return mpApi.runPayment(amountInCents);
        }
    }
}

