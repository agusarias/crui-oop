package com.crui.patterns.examples.dnd.after.util;

import java.util.List;
import java.util.stream.Collectors;

import com.crui.patterns.examples.dnd.after.enemy.Enemy;
import com.crui.patterns.examples.dnd.after.fight.Fight;
import com.crui.patterns.examples.dnd.after.hero.Hero;

public class ConsoleView implements FightEventsListener {
  @Override
  public void fightStarts() {
    System.out.println("Fight starts!");
  }

  @Override
  public void roundStarts(int round) {
    System.out.println("Round " + round + " starts!");
  }

  @Override
  public void heroHits(Hero hero, Enemy target, int dealt) {
    System.out.println(
        hero.getIcon()
            + " "
            + hero.getName()
            + " hits "
            + target.getName()
            + " for "
            + dealt
            + " damage.");
  }

  @Override
  public void enemyHits(Enemy enemy, Hero target, int dealt) {
    System.out.println(
        enemy.getIcon()
            + " "
            + enemy.getName()
            + " hits "
            + target.getName()
            + " for "
            + dealt
            + " damage.");
  }

  @Override
  public void enemyDefeated(Enemy enemy) {
    System.out.println(enemy.getIcon() + " " + enemy.getName() + " is defeated!");
  }

  @Override
  public void enemiesDefeated(List<Enemy> enemies) {
    System.out.println("All enemies are defeated!");
  }

  @Override
  public void heroDefeated(Hero hero) {
    System.out.println(hero.getIcon() + " " + hero.getName() + " is defeated!");
  }

  @Override
  public void printStatus(Fight fight) {
    Hero hero = fight.getHero();
    List<Enemy> enemies = fight.getEnemies();
    System.out.printf(
        "%s %s: HP=%d, ATK=%d, DEF=%d%n",
        hero.getIcon(), hero.getName(), hero.getHealth(), hero.getAttack(), hero.getDefense());
    for (Enemy e : enemies) {
      System.out.printf(
          "%s %-12s (kind=%-7s): HP=%3d, ATK=%2d, DEF=%2d%n",
          e.getIcon(), e.getName(), e.getKind(), e.getHealth(), e.getAttack(), e.getDefense());
    }
  }

  @Override
  public void printResult(Fight fight) {
    System.out.println("\n=== RESULT ===");
    if (fight.getHero().isAlive()) {
      System.out.println("Hero %s wins! 🎉".formatted(fight.getHero().getName()));
    } else {
      System.out.println(
          "Enemies prevail... 😭 %s"
              .formatted(
                  fight.getEnemies().stream()
                      .map(Enemy::getName)
                      .collect(Collectors.joining(", "))));
    }
  }
}
