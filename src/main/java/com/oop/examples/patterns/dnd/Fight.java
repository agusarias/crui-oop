package com.oop.examples.patterns.dnd;

import static com.oop.examples.patterns.dnd.character.Hero;
import static com.oop.examples.patterns.dnd.character.Enemy;

import java.util.ArrayList;
import java.util.List;

static class Fight {
    private Hero hero;
    private List<Enemy> enemies;
    private int round = 1;

    public Fight(Hero hero, List<Enemy> enemies) {
        this.hero = hero;
        this.enemies = new ArrayList<>(enemies);
    }

    public void run() {
        printlnHeader("FIGHT STARTS!");

        // Displays starter stats of fight.
        printStatus();

        while (!isFightOver()) {
            // The main logic is separated into a segment of segments to make logic cleaner and easier to follow.
            mainLoop()
        }

        // Show results after mainLoop ends.
        showResults()
    }

    private boolean isFightOver() {
        return (!hero.isAlive() || !anyEnemyAlive());
    }

    private void mainLoop() {
        System.out.println("\n--- Round " + (round++) + " ---");

        // Hero ataca primero
        heroTurn();

        // Enemigos atacan si viven
        enemyTurn();

        // Actualización del estado del combate
        printStatus();
    }

    private void heroTurn() {
        // Héroe ataca primero al primer enemigo vivo
        Enemy target = firstAliveEnemy();
        if (target != null) {
            int dealt = hero.hit(target);
            System.out.printf(
                "🗡️ %s hits %s for %d dmg. (%s HP=%d)%n",
                hero.getName(), target.getName(), dealt, target.getName(), target.getHealth());
            if (!target.isAlive()) {
                System.out.printf("%s is defeated!%n", target.getName());
            }
        }
    }

    private void enemyTurn() {
        // Enemigos atacan si viven
        for (Enemy e : enemies) {
            if (e.isAlive() && hero.isAlive()) {
            int dealt = e.hit(hero);
            String emoji = e.getKind().equals("dragon") ? "🐉" : "🧌";
            System.out.printf(
                "%s %s hits %s for %d dmg. (%s HP=%d)%n",
                emoji, e.getName(), hero.getName(), dealt, hero.getName(), hero.getHealth());
            }
        }
    }

    private void showResults() {
        System.out.println("\n=== RESULT ===");
        if (hero.isAlive()) {
            System.out.println("Hero wins! 🎉");
        } else {
            System.out.println("Enemies prevail... 😭");
        }
    }

    private boolean anyEnemyAlive() {
        return (firstAliveEnemy() != null);
    }

    private Enemy firstAliveEnemy() {
        for (Enemy e : enemies) if (e.isAlive()) return e;
        return null;
    }

    private void printStatus() {
        System.out.printf(
            "Hero %s: HP=%d, ATK=%d, DEF=%d%n",
            hero.getName(), hero.getHealth(), hero.getAttack(), hero.getDefense());
        for (Enemy e : enemies) {
            System.out.printf(
                "Enemy %-12s (kind=%-7s): HP=%3d, ATK=%2d, DEF=%2d%n",
                e.getName(), e.getKind(), e.getHealth(), e.getAttack(), e.getDefense());
        }
    }

    private void printlnHeader(String msg) {
        System.out.println("\n==============================");
        System.out.println(msg);
        System.out.println("==============================");
    }
}