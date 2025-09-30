package com.oop.examples.patterns.dnd;

import com.oop.examples.patterns.dnd.factory.RandomDNDFactory; // Get random generators for Characters and Equipment
import com.oop.examples.patterns.dnd.utility.RNG; // Random number generator
import com.oop.examples.patterns.dnd.buff.BuffManager; // Buff system
import com.oop.examples.patterns.dnd.character.Hero;  // Base character class
import com.oop.examples.patterns.dnd.character.Enemy; // Base character class
import com.oop.examples.patterns.dnd.character.HeroType; // Hero types
import com.oop.examples.patterns.dnd.character.EnemyKind; // Enemy types
// import com.oop.examples.patterns.dnd.Fight; // Main game loop. Technically not needed since it's the same package

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Programa principal de setup e inicio de la partida.
 *
 * <p>Ejecutar usando el comando:
 *
 * <p>mvn exec:java -Dexec.mainClass="com.oop.examples.patterns.dnd.DND"
 */
public class DND {

  public static void main(String[] args) {
    // Configuración del héroe - Versión original
    /*
    Weapon sword = new Weapon("Sword", 12);
    Armour leather = new Armour("Leather", 5);
    Hero hero = new Hero("Artemis", HeroType.ELF, 20, 8, 180);  // name, type, attack, defense, health
    hero.setWeapon(sword);
    hero.setArmour(leather);
    */
    
    // Usar la fábrica para crear personajes
    RandomDNDFactory RandomDNDFactory = new RandomDNDFactory();
    
    // Crear héroe con nombre fijo pero stats aleatorios (sin arma ni armadura)
    Hero hero = (Hero) RandomDNDFactory.createRandomHero(false, false);
    hero = new Hero("Artemis", hero.getType(), hero.getAttack(), hero.getDefense(), hero.getHealth());
    hero.setWeapon(RandomDNDFactory.generateRandomWeapon());
    hero.setArmour(RandomDNDFactory.generateRandomArmour());

    // Enemigos variados - Versión original
    /*
    List<Enemy> enemies = Arrays.asList(
        new Enemy("Goblin", EnemyKind.GOBLIN, 10, 4, 45),
        new Enemy("Orc", EnemyKind.ORC, 16, 6, 60),
        new Enemy("Dragon", EnemyKind.DRAGON, 22, 10, 80));
    */
    
    // Crear enemigos con nombres fijos pero stats aleatorios
    List<Enemy> enemies = new ArrayList<>();
    
    Enemy goblin = (Enemy) RandomDNDFactory.createRandomEnemy();
    enemies.add(new Enemy("Goblin", EnemyKind.GOBLIN, goblin.getAttack(), goblin.getDefense(), goblin.getHealth()));
    
    Enemy orc = (Enemy) RandomDNDFactory.createRandomEnemy();
    enemies.add(new Enemy("Orc", EnemyKind.ORC, orc.getAttack(), orc.getDefense(), orc.getHealth()));
    
    Enemy dragon = (Enemy) RandomDNDFactory.createRandomEnemy();
    enemies.add(new Enemy("Dragon", EnemyKind.DRAGON, dragon.getAttack(), dragon.getDefense(), dragon.getHealth()));
    
    // Pelea
    Fight fight = new Fight(hero, enemies);
    fight.run();
  }
}
