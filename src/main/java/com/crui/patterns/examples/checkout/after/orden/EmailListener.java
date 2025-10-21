package com.crui.patterns.examples.checkout.after.orden;


public class EmailListener implements OrdenEventListener {
    @Override
    public void onPaid(Orden order) {
        System.out.println("[Email] Enviando comprobante a cliente. Total: $" + order.total());
    }
}
