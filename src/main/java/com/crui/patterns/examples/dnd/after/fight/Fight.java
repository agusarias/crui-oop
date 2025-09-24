package com.crui.patterns.examples.dnd.after.fight;

import java.util.List;

import com.crui.patterns.examples.dnd.after.enemy.Enemy;
import com.crui.patterns.examples.dnd.after.hero.Hero;
import com.crui.patterns.examples.dnd.after.util.FightEventsListener;

import java.util.ArrayList;

public class Fight {
  private Hero hero;
  private List<Enemy> enemies;
  private int round = 1;
  private FightEventsListener listener;
  private static final int MAX_ROUNDS = 100;

  public Fight(Hero hero, List<Enemy> enemies, FightEventsListener listener) {
    this.hero = hero;
    this.enemies = new ArrayList<>(enemies);
    this.listener = listener;
  }

  public Hero getHero() {
    return hero;
  }

  public List<Enemy> getEnemies() {
    return enemies;
  }

  public int getRound() {
    return round;
  }

  public void run() {
    listener.fightStarts();
    listener.printStatus(this);

    boolean heroAlive = true;
    boolean enemiesAlive = true;
    while (round <= MAX_ROUNDS) {
      listener.roundStarts(round++);

      // Héroe ataca primero al primer enemigo vivo
      Enemy target = firstAliveEnemy();
      if (target != null) {
        int dealt = hero.hit(target);
        listener.heroHits(hero, target, dealt);
        if (!target.isAlive()) {
          listener.enemyDefeated(target);
        }
      }

      // Enemigos atacan si viven
      for (Enemy enemy : enemies) {
        if (enemy.isAlive() && hero.isAlive()) {
          int dealt = enemy.hit(hero);
          listener.enemyHits(enemy, hero, dealt);
        }
      }
      listener.printStatus(this);
      heroAlive = hero.isAlive();
      enemiesAlive = anyEnemyAlive();
      if (!heroAlive) {
        listener.heroDefeated(hero);
        break;
      }
      if (!enemiesAlive) {
        listener.enemiesDefeated(enemies);
        break;
      }
    }
    listener.printResult(this);
  }

  private boolean anyEnemyAlive() {
    for (Enemy e : enemies) if (e.isAlive()) return true;
    return false;
  }

  private Enemy firstAliveEnemy() {
    for (Enemy e : enemies) if (e.isAlive()) return e;
    return null;
  }
}
