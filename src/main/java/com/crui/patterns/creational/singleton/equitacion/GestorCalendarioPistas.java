package com.crui.patterns.creational.singleton.equitacion;

import java.util.ArrayList;

public class GestorCalendarioPistas {
    // Atributo estático privado, almacena la única instancia (como es privado, nadie
    // puede acceder a él directamente)
    private static GestorCalendarioPistas instancia;

    //  Lista de pistas y reservas (guarda todas las pistas y reservas)
    private ArrayList<Pista> pistas;
    private ArrayList<Reserva> reservas;

    // Constructor privado (evita crear objetos con "new" desde fuera)
    private GestorCalendarioPistas() {
        pistas = new ArrayList<>(); // inicializo las listas
        reservas = new ArrayList<>(); // inicializo las listas
    }

    //  Método público estático, me devuelve la única instancia
    public static GestorCalendarioPistas getInstancia() {
        if (instancia == null) { // si no existe, la creo
            instancia = new GestorCalendarioPistas(); // se crea solo esta vez, unica vez de new   
        }
        return instancia; //si existe devolvemos la que ya existe
    }

    // Métodos de gestión (agrega las pistas)
    public void agregarPista(Pista pista) {
        pistas.add(pista);
    }

    // Agrega las reservas
    public void agregarReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    // recorre y muestra las reservas, los clientes y las pistas reservadas
    public void mostrarReservas() {
        for (Reserva r : reservas) {
            System.out.println(r.getNombreCliente() + " ha reservado " + r.getPista().getNombre());
        }
    }
}
