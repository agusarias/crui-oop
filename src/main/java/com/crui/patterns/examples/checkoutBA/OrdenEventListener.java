package com.crui.patterns.examples.checkoutBA;

public interface OrdenEventListener {
    void onPaid(Orden order);
}

class EmailListener implements OrdenEventListener {
    @Override
    public void onPaid(Orden order) {
        System.out.println("[Email] Enviando comprobante a cliente. Total: $" + order.total());
    }
}

class AnalyticsListener implements OrdenEventListener {
    @Override
    public void onPaid(Orden order) {
        System.out.println(
            "[Analytics] Registrando venta. Ítems: " + order.getCarrito().getItems().size());
    }
}