package com.crui.patterns.examples.checkout.after;

import com.crui.patterns.examples.checkout.after.pago.MedioDePago;
import com.crui.patterns.examples.checkout.after.pago.MedioDePagoFactory;       
import com.crui.patterns.examples.checkout.after.orden.AnalyticsListener;
import com.crui.patterns.examples.checkout.after.dominio.Carrito;
import com.crui.patterns.examples.checkout.after.orden.EmailListener;
import com.crui.patterns.examples.checkout.after.orden.Orden;
import com.crui.patterns.examples.checkout.after.dominio.Producto;

public class Main {
    public static void main(String[] args) {
        // Productos base
        Producto libro = new Producto("Clean Code", 25.0);
        Producto mouse = new Producto("Mouse USB", 12.5);

        // Armo el carrito
        Carrito carrito = new Carrito();
        carrito.add(libro);
        carrito.add(mouse);

        Orden orden = new Orden(carrito);
        orden.incluirEnvoltorio(true);
        orden.incluirEnvioExpress(true);

        orden.addListener(new EmailListener());
        orden.addListener(new AnalyticsListener());

        // Uso de la FACTORY
        String paymentType = "card"; // "cash", "card", "mercado-pago"
        MedioDePago medioDePago = MedioDePagoFactory.crear(paymentType);
        orden.setMedioDePago(medioDePago);

        // Muestra totales y paga
        orden.printSummary();
        orden.pagar();
    }
}

