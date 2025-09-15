package com.crui.patterns.examples.dnd.before;

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

  static class Hero {
    private String name;
    private String type; // elf, human, dwarf, warrior
    private int baseAttack;
    private int baseDefense;
    private int health;
    private Weapon weapon;
    private Armour armour;
    private Random random = new Random();

    public Hero(String name, String type, int baseAttack, int baseDefense, int health) {
      this.name = name;
      this.type = type;
      this.baseAttack = baseAttack;
      this.baseDefense = baseDefense;
      this.health = health;
    }

    public void setWeapon(Weapon w) {
      this.weapon = w;
    }

    public void setArmour(Armour a) {
      this.armour = a;
    }

    public String getName() {
      return name;
    }

    public String getType() {
      return type;
    }

    public int getHealth() {
      return health;
    }

    public boolean isAlive() {
      return health > 0;
    }

    public int getAttack() {
      int weaponAtk = (weapon != null) ? weapon.getAttack() : 0;
      if ("warrior".equals(type)) {
        weaponAtk += 2;
      }
      return baseAttack + weaponAtk;
    }

    public int getDefense() {
      int armourDef = (armour != null) ? armour.getDefense() : 0;
      return baseDefense + armourDef;
    }

    public int takeDamage(int damage) {
      int mitigated = Math.max(1, damage - (getDefense() / 2));
      health = Math.max(0, health - mitigated);
      return mitigated;
    }

    public int hit(Enemy target) {
      int atk = getAttack();
      if (random.nextInt(100) < 10) { // 10% critical hit
        atk += 8;
      }
      // Dragon es resistente a los no elfos
      if ("dragon".equals(target.getKind()) && !"elf".equals(type)) {
        atk -= 4;
      }
      atk = Math.max(1, atk);
      int dealt = target.takeDamage(atk);
      return dealt;
    }
  }

  static class Enemy {
    private String name;
    private String kind; // goblin, orc, dragon...
    private int baseAttack;
    private int baseDefense;
    private int health;
    private Weapon weapon;
    private Armour armour;
    private Random rng = new Random();

    public Enemy(String name, String kind, int baseAttack, int baseDefense, int health) {
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

    public void setWeapon(Weapon w) {
      this.weapon = w;
    }

    public void setArmour(Armour a) {
      this.armour = a;
    }

    public int getAttack() {
      int weaponAtk = (weapon != null) ? weapon.getAttack() : 0;
      int atk = baseAttack + weaponAtk;

      // Modificadores por tipo
      if ("orc".equals(kind)) {
        atk += 2;
      } else if ("goblin".equals(kind)) {
        // goblin más débil pero esquiva a veces
        if (rng.nextInt(100) < 15) {
          atk += 5;
        }
      } else if ("dragon".equals(kind)) {
        atk += 4;
      }
      return atk;
    }

    public int getDefense() {
      int armourDef = (armour != null) ? armour.getDefense() : 0;
      int def = baseDefense + armourDef;

      // Defensa especial hardcodeada (candidato a Strategy o State)
      if ("dragon".equals(kind)) {
        def += 3;
      }
      return def;
    }

    public int takeDamage(int damage) {
      int mitigated = Math.max(1, damage - (getDefense() / 2));
      health = Math.max(0, health - mitigated);
      return mitigated;
    }

    public int hit(Hero target) {
      int atk = getAttack();
      // Los orcos pueden tener un ataque extra el 20% de las veces
      if ("orc".equals(kind) && rng.nextInt(100) < 20) {
        atk += 6;
      }
      atk = Math.max(1, atk);
      int dealt = target.takeDamage(atk);
      return dealt;
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
