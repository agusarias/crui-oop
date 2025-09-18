package com.crui.patterns.creational.singleton.equitacion;

public class Main {
    public static void main(String[] args) {
        // con getinstancia llama al metodo que me devuelve la única instancia posible
        GestorCalendarioPistas gestor = GestorCalendarioPistas.getInstancia();

        // se crean 2 pistas
        Pista pistaA = new Pista("Pista A");
        Pista pistaB = new Pista("Pista B");

        // Se agregan las pistas a usar o disponibles
        gestor.agregarPista(pistaA);
        gestor.agregarPista(pistaB);

        // Se crean las reservas 2 ejemplos con clientes y pistas
        Reserva reserva1 = new Reserva("Ana", pistaA);
        Reserva reserva2 = new Reserva("Luis", pistaB);

        // Se agregan las reservas al gestor o administrador
        gestor.agregarReserva(reserva1);
        gestor.agregarReserva(reserva2);

        // Mostrar reservas (recorre y muestra las reservas, los clientes y las pistas reservadas)
        gestor.mostrarReservas();
    }
}
