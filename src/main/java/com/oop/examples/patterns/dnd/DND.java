package com.oop.examples.patterns.dnd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Programa inicial para refactorización con patrones de diseño.
 *
 * <p>Ejecutar usando el comando:
 *
 * <p>mvn exec:java -Dexec.mainClass="com.oop.examples.patterns.dnd.DND"
 */
public class DND {

  public static void main(String[] args) {
    // Configuración del héroe

    Weapon sword = new Weapon("Sword", 12);
    Armour leather = new Armour("Leather", 5);
    Hero hero = new Hero("Artemis", "elf", 20, 8, 180);
    hero.setWeapon(sword);
    hero.setArmour(leather);

    // Enemigos variados
    List<Enemy> enemies =
        Arrays.asList(
            new Enemy("Goblin", "goblin", 10, 4, 45),
            new Enemy("Orc", "orc", 16, 6, 60),
            new Enemy("Dragon", "dragon", 22, 10, 80));

    // Pelea
    Fight fight = new Fight(hero, enemies);
    fight.run();
  }

  // ----------------- Clases del dominio (versión inicial a refactorizar) -----------------

  static class Weapon {
    private String type;
    private int attack;

    public Weapon(String type, int attack) {
      this.type = type;
      this.attack = attack;
    }

    public String getType() {
      return type;
    }

    public int getAttack() {
      return attack;
    }
  }

  static class Armour {
    private String type;
    private int defense;

    public Armour(String type, int defense) {
      this.type = type;
      this.defense = defense;
    }

    public String getType() {
      return type;
    }

    public int getDefense() {
      return defense;
    }
  }

  static class Dice {
    private static Dice instance;
    private Random random;

    private Dice() {
      this.random = new Random();
    }

    static Dice getInstance() {
      if (instance == null) {
        instance = new Dice();
      }
      return instance;
    }

    public static boolean chance(int percent) {
      return getInstance().random.nextInt(100) < percent;
    }
  }

  abstract static class Character {
    private String name;
    private String kind; // elf, human, dwarf, warrior
    private int baseAttack;
    private int baseDefense;
    private int health;
    private Random random = new Random();

    public Character(String name, String kind, int baseAttack, int baseDefense, int health) {
      this.name = name;
      this.kind = kind;
      this.baseAttack = baseAttack;
      this.baseDefense = baseDefense;
      this.health = health;
    }

    public String getName() {
      return name;
    }

    public String getKind() {
      return kind;
    }

    public int getHealth() {
      return health;
    }

    public boolean isAlive() {
      return health > 0;
    }

    public int getAttack() {
      return baseAttack;
    }

    public int getDefense() {
      return baseDefense;
    }

    public void setHealth(int health) {
      this.health = health;
    }

    public int getAdditionalAttack(Character target) {
      return 0;
    }

    public int getAdditionalDefense(Character aggresor) {
      return 0;
    }

    public int takeDamage(Character aggresor, int attack) {
      int defense = getDefense() + getAdditionalDefense(aggresor);
      int mitigated = Math.max(1, Math.max(1, attack) - (defense / 2));
      this.setHealth(Math.max(0, this.getHealth() - mitigated));
      return mitigated;
    }

    public int hit(Character target) {
      int attack = getAttack() + getAdditionalAttack(target);
      if (Dice.chance(10)) { // 10% critical hit
        attack += 8;
      }
      return target.takeDamage(this, attack);
    }
  }

  static class Hero extends Character {
    private Weapon weapon;
    private Armour armour;

    public Hero(String name, String kind, int baseAttack, int baseDefense, int health) {
      super(name, kind, baseAttack, baseDefense, health);
    }

    public void setWeapon(Weapon w) {
      this.weapon = w;
    }

    public void setArmour(Armour a) {
      this.armour = a;
    }

    public int getAdditionalAttack(Character target) {
      int base = this.weapon != null ? this.weapon.getAttack() : 0;
      if ("warrior".equals(super.getKind())) {
        return base + 2;
      }
      if ("dragon".equals(target.getKind()) && !"elf".equals(target.getKind())) {
        base += 4;
      }
      return base;
    }

    public int getAdditionalDefense(Character aggresor) {
      int base = this.armour != null ? this.armour.getDefense() : 0;
      if ("warrior".equals(super.getKind())) {
        return base + 2;
      }
      return base;
    }
  }

  static class Enemy extends Character {
    public Enemy(String name, String kind, int baseAttack, int baseDefense, int health) {
      super(name, kind, baseAttack, baseDefense, health);
    }

    public int getAdditionalAttack(Character target) {
      int base = super.getAttack();
      if ("orc".equals(super.getKind())) {
        base += 2;
        if (Dice.chance(20)) {
          base += 5;
        }
      }
      if ("goblin".equals(super.getKind())) {
        base += 1;
        if (Dice.chance(15)) {
          base += 5;
        }
      }
      if ("dragon".equals(super.getKind())) {
        base += 4;
      }
      return base;
    }

    public int getAdditionalDefense(Character aggresor) {
      int base = super.getDefense();
      if ("dragon".equals(super.getKind())) {
        base += 4;
      }
      return base;
    }
  }

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
      printStatus();

      while (hero.isAlive() && anyEnemyAlive()) {
        System.out.println("\n--- Round " + (round++) + " ---");

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

        printStatus();
      }

      System.out.println("\n=== RESULT ===");
      if (hero.isAlive()) {
        System.out.println("Hero wins! 🎉");
      } else {
        System.out.println("Enemies prevail... 😭");
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
}
