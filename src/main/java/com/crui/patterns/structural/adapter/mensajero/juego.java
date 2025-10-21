package com.crui.patterns.structural.adapter.mensajero;

// defino una interfaz
interface Enemy {

    void attack();

    void defend();

}

// creo una clase normal
class Dragon implements Enemy {

    public void attack() {
        System.out.println("elimine with fire");
    }

    public void defend() {
        System.out.println("fly away");
    }
}

// clase externa que no tiene compatibilidad
class Mago {

    public void deleteEnemy() {
        System.out.println("magic paralyzes heart, death subit");
    }

    public void emergency() {
        System.out.println("magic vanish");
    }
}

// clase que adapta a Enemy
class Emperator implements Enemy {

    private Mago mago;

    public Emperator(Mago mago) {
        this.mago = mago;
    }

    @Override
    public void attack() {
        mago.deleteEnemy();
    }

    @Override
    public void defend() {
        mago.emergency();
    }
}

// salida con la interfaz y salida adaptandor
class AdapterLucha {
    public static void main(String[] args) {

        Enemy dragon = new Dragon();
        dragon.attack();
        dragon.defend();

        Mago mago = new Mago();
        Enemy emperator = new Emperator(mago);
        emperator.attack();
        emperator.defend();

    }
}