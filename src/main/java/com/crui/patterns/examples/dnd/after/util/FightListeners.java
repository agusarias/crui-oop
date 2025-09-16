package com.crui.patterns.examples.dnd.after.util;

import java.util.List;

import com.crui.patterns.examples.dnd.after.enemy.Enemy;
import com.crui.patterns.examples.dnd.after.fight.Fight;
import com.crui.patterns.examples.dnd.after.hero.Hero;

public class FightListeners implements FightEventsListener {
  private List<FightEventsListener> listeners;

  public FightListeners(List<FightEventsListener> listeners) {
    this.listeners = listeners;
  }

  @Override
  public void fightStarts() {
    listeners.forEach(listener -> listener.fightStarts());
  }

  @Override
  public void roundStarts(int round) {
    listeners.forEach(listener -> listener.roundStarts(round));
  }

  @Override
  public void heroHits(Hero hero, Enemy target, int dealt) {
    listeners.forEach(listener -> listener.heroHits(hero, target, dealt));
  }

  @Override
  public void heroDefeated(Hero hero) {
    listeners.forEach(listener -> listener.heroDefeated(hero));
  }

  @Override
  public void enemyHits(Enemy enemy, Hero target, int dealt) {
    listeners.forEach(listener -> listener.enemyHits(enemy, target, dealt));
  }

  @Override
  public void enemyDefeated(Enemy enemy) {
    listeners.forEach(listener -> listener.enemyDefeated(enemy));
  }

  @Override
  public void enemiesDefeated(List<Enemy> enemies) {
    listeners.forEach(listener -> listener.enemiesDefeated(enemies));
  }

  @Override
  public void printStatus(Fight fight) {
    listeners.forEach(listener -> listener.printStatus(fight));
  }

  @Override
  public void printResult(Fight fight) {
    listeners.forEach(listener -> listener.printResult(fight));
  }
}
