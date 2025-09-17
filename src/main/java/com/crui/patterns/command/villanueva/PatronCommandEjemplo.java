package com.crui.patterns.command.villanueva;

import java.util.ArrayList;
import java.util.List;

// Ejemplo de Patrón Command
// Contexto: una aplicación de gestión de tareas

// Problema: deseamos implementar un sistema de gestión de tareas
// que permita agregar, eliminar y marcar tareas como completadas.

class Tarea {
    private String nombre;
    private boolean completada;

    public Tarea(String nombre) {
        this.nombre = nombre;
        this.completada = false;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void completar() {
        this.completada = true;
    }
}

// Problema: la lógica de gestión de tareas puede volverse compleja
class ComplexTareaManager {
    private List<Tarea> tareas = new ArrayList<>();

    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
        System.out.println("Tarea agregada: " + tarea.getNombre());
    }

    public void eliminarTarea(Tarea tarea) {
        tareas.remove(tarea);
        System.out.println("Tarea eliminada: " + tarea.getNombre());
    }

    public void completarTarea(Tarea tarea) {
        tarea.completar();
        System.out.println("Tarea completada: " + tarea.getNombre());
    }
    // tener otros métodos complejos...
    // como listar tareas, buscar tareas, clasificar tareas, etc.
    // esta clase puede crecer y volverse difícil de mantener.
    // y tiene demasiadas responsabilidades.
}


// Solución: utilizamos el patrón Command para encapsular las operaciones
// en objetos separados, permitiendo una mayor flexibilidad y mantenibilidad.

// Comando
// La interfaz Command define un método común para todos los comandos
interface Comando {
    void ejecutar();
}

// Podemos tener diferentes comandos para diferentes operaciones
// Cada comando implementa la interfaz Command
class AgregarTareaComando implements Comando {
    private List<Tarea> listaTareas;
    private Tarea tarea;

    public AgregarTareaComando(List<Tarea> listaTareas, Tarea tarea) {
        // este comando sabe cómo agregar una tarea a la lista
        // puedo recibir la lista de tareas y la tarea a agregar
        // y luego ejecutar la operación cuando se llame a ejecutar()
        this.listaTareas = listaTareas;
        this.tarea = tarea;
    }

    @Override
    public void ejecutar() {
        listaTareas.add(tarea);
        System.out.println("Tarea agregada: " + tarea.getNombre());
    }
}

class EliminarTareaComando implements Comando {
    private List<Tarea> listaTareas;
    private Tarea tarea;

    public EliminarTareaComando(List<Tarea> listaTareas, Tarea tarea) {
        this.listaTareas = listaTareas;
        this.tarea = tarea;
    }

    @Override
    public void ejecutar() {
        listaTareas.remove(tarea);
        System.out.println("Tarea eliminada: " + tarea.getNombre());
    }
}

class CompletarTareaComando implements Comando {
    private Tarea tarea;

    public CompletarTareaComando(Tarea tarea) {
        this.tarea = tarea;
    }

    @Override
    public void ejecutar() {
        tarea.completar();
        System.out.println("Tarea completada: " + tarea.getNombre());
    }
}

class BuscarTareaComando implements Comando {
    private List<Tarea> listaTareas;
    private String nombreTarea;

    public BuscarTareaComando(List<Tarea> listaTareas, String nombreTarea) {
        this.listaTareas = listaTareas;
        this.nombreTarea = nombreTarea;
    }

    @Override
    public void ejecutar() {
        for (Tarea tarea : listaTareas) {
            if (tarea.getNombre().equalsIgnoreCase(nombreTarea)) {
                System.out.println("Tarea encontrada: " + tarea.getNombre() + " (Completada: " + tarea.isCompletada() + ")");
                return;
            }
        }
        System.out.println("Tarea no encontrada: " + nombreTarea);
    }
}

class ListarTareasComando implements Comando {
    private List<Tarea> listaTareas;

    public ListarTareasComando(List<Tarea> listaTareas) {
        this.listaTareas = listaTareas;
    }

    @Override
    public void ejecutar() {
        System.out.println("Lista de tareas:");
        for (Tarea tarea : listaTareas) {
            System.out.println("- " + tarea.getNombre() + " (Completada: " + tarea.isCompletada() + ")");
        }
    }
}

// Invocador
// El invocador puede ser una clase que maneje la ejecución de comandos
class TareaManager {
    private List<Comando> historialComandos = new ArrayList<>();

    public void executeComando(Comando comando) {
        comando.ejecutar();
        historialComandos.add(comando);
    }

    // Podríamos agregar métodos para deshacer comandos, rehacer comandos, etc.
}

public class PatronCommandEjemplo {
    public static void main(String[] args) {
        // Podria parecer un poco rebuscado para un ejemplo tan simple,
        // pero en aplicaciones reales con muchas operaciones y lógica compleja,
        // el patrón Command ayuda a mantener el código limpio y manejable.
        // cada clase quedo mas enfocada en una sola responsabilidad.
        List<Tarea> listaTareas = new ArrayList<>();
        TareaManager TareaManager = new TareaManager();

        Tarea tarea1 = new Tarea("Comprar leche");
        Tarea tarea2 = new Tarea("Estudiar patrones de diseño");

        Comando agregarTarea1 = new AgregarTareaComando(listaTareas, tarea1);
        Comando agregarTarea2 = new AgregarTareaComando(listaTareas, tarea2);
        Comando completarTarea1 = new CompletarTareaComando(tarea1);
        Comando eliminarTarea = new EliminarTareaComando(listaTareas, tarea2);
        Comando buscarTarea = new BuscarTareaComando(listaTareas, "Comprar leche");
        Comando listarTareas = new ListarTareasComando(listaTareas);

        TareaManager.executeComando(agregarTarea1);
        TareaManager.executeComando(agregarTarea2);
        TareaManager.executeComando(completarTarea1);
        TareaManager.executeComando(eliminarTarea);
        TareaManager.executeComando(buscarTarea);
        TareaManager.executeComando(listarTareas);
    }
}