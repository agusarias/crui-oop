package com.crui.patterns.behavioral.command.videojuego_godina.after;

import java.util.HashMap;
import java.util.Map;

// En este juego, el jugador puede realizar 4 acciones:
// 1. Moverse a la izquierda
// 2. Moverse a la derecha
// 3. Saltar
// 4. Atacar

// Clase que representa al jugador
public class Player {

    // -- Lógica del jugador --
    public void moveLeft() {
        System.out.println("El jugador se mueve a la izquierda");
    }

    public void moveRight() {
        System.out.println("El jugador se mueve a la derecha");
    }

    public void jump() {
        System.out.println("El jugador salta");
    }

    public void attack() {
        System.out.println("El jugador ataca");
    }

    // --- Interfaz para los comandos ---
    interface Command {
        void execute();
    }

    // --- Comandos concretos ---
    static class MoveLeftCommand implements Command {
        private Player player;

        public MoveLeftCommand(Player player) {
            this.player = player;
        }

        @Override
        public void execute() {
            player.moveLeft();
        }
    }

    static class MoveRightCommand implements Command {
        private Player player;

        public MoveRightCommand(Player player) {
            this.player = player;
        }

        @Override
        public void execute() {
            player.moveRight();
        }
    }

    static class JumpCommand implements Command {
        private Player player;

        public JumpCommand(Player player) {
            this.player = player;
        }

        @Override
        public void execute() {
            player.jump();
        }
    }

    static class AttackCommand implements Command {
        private Player player;

        public AttackCommand(Player player) {
            this.player = player;
        }

        @Override
        public void execute() {
            player.attack();
        }
    }

    // --- Manejador de entrada ---
    static class InputHandler {
        private Map<String, Command> commandMap = new HashMap<>();
    
        public void setCommand(String key, Command command) {
            commandMap.put(key, command);
        }

        public void handleInput(String key) {
            Command command = commandMap.get(key);
            if (command != null) {
                command.execute();
            } else {
                System.out.println("Tecla no asignada");
            }
        }
    }

    public static void main(){
        Player player = new Player();
        InputHandler inputHandler = new InputHandler();

        // Asignamos comandos a las teclas
        inputHandler.setCommand("A", new MoveLeftCommand(player));
        inputHandler.setCommand("D", new MoveRightCommand(player));
        inputHandler.setCommand("W", new JumpCommand(player));
        inputHandler.setCommand("SPACE", new AttackCommand(player));

        // Simulamos la entrada del usuario
        inputHandler.handleInput("A"); // Mover a la izquierda
        inputHandler.handleInput("D"); // Mover a la derecha
        inputHandler.handleInput("W"); // Saltar
        inputHandler.handleInput("SPACE"); // Atacar
        inputHandler.handleInput("X"); // Tecla no asignada
    }
}






