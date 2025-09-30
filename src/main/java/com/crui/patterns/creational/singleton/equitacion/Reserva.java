package com.crui.patterns.creational.singleton.equitacion;

// representa una reserva hecha por un cliente para una pista específica
//conecta un cliente con una pista

public class Reserva {//crea la clase reserva
    private String nombreCliente; // una reserva tiene una pista y un cliente
    private Pista pista;

    //constructor que recibe el nombre del cliente y la pista
    public Reserva(String nombreCliente, Pista pista) {
        this.nombreCliente = nombreCliente;
        this.pista = pista;
    }

    // métodos públicos para obtener el nombre del cliente y la pista
    public String getNombreCliente() {
        return nombreCliente;
    }

    public Pista getPista() {
        return pista;
    }
}
