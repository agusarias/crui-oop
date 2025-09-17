package com.oop.examples.patterns.dnd;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Fight {
    private Hero hero;
    private List<Enemy> enemies;
    private int round = 1;
    private List<FightLogObserver> observers = new CopyOnWriteArrayList<>();
    private Terrain terrain;

    public Fight(Hero hero, List<Enemy> enemies) {
        this(hero, enemies, null);
    }

    public Fight(Hero hero, List<Enemy> enemies, Terrain terrain) {
        this.hero = hero;
        this.enemies = new ArrayList<>(enemies);
        this.terrain = terrain;
    }

    public void addObserver(FightLogObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(FightLogObserver observer) {
        observers.remove(observer);
    }

    private void notifyLog(String message) {
        for (FightLogObserver observer : observers) {
            observer.onLog(message);
        }
    }

    public void run() {
        if (terrain != null) {
            notifyLog("\n=== Terreno: " + terrain.getName() + " ===");
        }
        printlnHeader("FIGHT STARTS!");
        printStatus();

        while (hero.isAlive() && anyEnemyAlive()) {
            notifyLog("\n--- Round " + (round++) + " ---");

            // Héroe ataca primero al primer enemigo vivo
            Enemy target = firstAliveEnemy();
            if (target != null) {
                int atk = hero.getAttack();
                if (terrain != null) {
                    atk = terrain.modifyAttack(hero.getType(), atk);
                }
                int dealt = target.takeDamage(atk);
                notifyLog(String.format(
                    "🗡️ %s hits %s for %d dmg. (%s HP=%d)",
                    hero.getName(), target.getName(), dealt, target.getName(), target.getHealth()));
                if (!target.isAlive()) {
                    notifyLog(String.format("%s is defeated!", target.getName()));
                }
            }

            // Enemigos atacan si viven
            for (Enemy e : enemies) {
                if (e.isAlive() && hero.isAlive()) {
                    int atk = e.getAttack();
                    if (terrain != null) {
                        atk = terrain.modifyAttack(e.getKind(), atk);
                    }
                    int dealt = hero.takeDamage(atk);
                    String emoji = e.getKind().equals("dragon") ? "🐉" : "🧌";
                    notifyLog(String.format(
                        "%s %s hits %s for %d dmg. (%s HP=%d)",
                        emoji, e.getName(), hero.getName(), dealt, hero.getName(), hero.getHealth()));
                }
            }

            printStatus();
        }

        notifyLog("\n=== RESULT ===");
        if (hero.isAlive()) {
            notifyLog("Hero wins! 🎉");
        } else {
            notifyLog("Enemies prevail... 😭");
        }
    }

    private boolean anyEnemyAlive() {
        for (Enemy e : enemies) if (e.isAlive()) return true;
        return false;
    }

    private Enemy firstAliveEnemy() {
        for (Enemy e : enemies) if (e.isAlive()) return e;
        return null;
    }

    private void printStatus() {
        int heroAtk = hero.getAttack();
        int heroDef = hero.getDefense();
        if (terrain != null) {
            heroAtk = terrain.modifyAttack(hero.getType(), heroAtk);
            heroDef = terrain.modifyDefense(hero.getType(), heroDef);
        }
        notifyLog(String.format(
            "Hero %s: HP=%d, ATK=%d, DEF=%d",
            hero.getName(), hero.getHealth(), heroAtk, heroDef));
        for (Enemy e : enemies) {
            int eAtk = e.getAttack();
            int eDef = e.getDefense();
            if (terrain != null) {
                eAtk = terrain.modifyAttack(e.getKind(), eAtk);
                eDef = terrain.modifyDefense(e.getKind(), eDef);
            }
            notifyLog(String.format(
                "Enemy %-12s (kind=%-7s): HP=%3d, ATK=%2d, DEF=%2d",
                e.getName(), e.getKind(), e.getHealth(), eAtk, eDef));
        }
    }

    private void printlnHeader(String msg) {
        notifyLog("\n==============================");
        notifyLog(msg);
        notifyLog("==============================");
    }
}
