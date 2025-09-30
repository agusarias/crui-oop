package com.crui.patterns.behavioral.command.videojuego_godina.before;

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

    // --- Manejador de entrada ---
    static class InputHandler {
        private Player player;
    
        public InputHandler(Player player) {
            this.player = player;
        }

        public void handleInput(String key) {
            switch (key) {
                case "A":
                    player.moveLeft();
                    break;
                case "D":
                    player.moveRight();
                    break;
                case "W":
                    player.jump();
                    break;
                case "SPACE":
                    player.attack();
                    break;
                default:
                    System.out.println("Tecla no asignada");
                    break;
            }
        }
    }

    // --- Método principal ---
    public static void main(String[] args){
        Player player = new Player();
        InputHandler inputHandler = new InputHandler(player);

        // Simulamos la entrada del usuario
        inputHandler.handleInput("A"); // Mover a la izquierda
        inputHandler.handleInput("D"); // Mover a la derecha
        inputHandler.handleInput("W"); // Saltar
        inputHandler.handleInput("SPACE"); // Atacar
        inputHandler.handleInput("X"); // Tecla no asignada
    }
}






