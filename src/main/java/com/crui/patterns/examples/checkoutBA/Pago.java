package com.crui.patterns.examples.checkoutBA;


  // ===================== STRATEGY (Pago) =====================
public class Pago {
    private String amount;
    private MedioDePago medioDePago;//Strategy

    public Pago(String amount, MedioDePago medioDePago){
        this.amount = amount;
        this.medioDePago = medioDePago;
    }

    public void setMedioDePago(MedioDePago medioDePago){
        this.medioDePago = medioDePago;
    }

    public MedioDePago getMedioDePago(){
        return this.medioDePago;
    }

    public void pagar(){
        medioDePago.pay(Double.parseDouble(amount));
    }


}
