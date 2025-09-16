package com.crui.patterns.examples.dnd.after.util;

import java.util.List;

import com.crui.patterns.examples.dnd.after.enemy.Enemy;
import com.crui.patterns.examples.dnd.after.fight.Fight;
import com.crui.patterns.examples.dnd.after.hero.Hero;

public class MetricsCollector implements FightEventsListener {

  private int totalFights;
  private int totalWins;
  private int totalLosses;
  private int totalEnemiesDefeated;
  private int totalRounds;
  private int totalHeroHits;
  private int totalEnemyHits;

  @Override
  public void fightStarts() {
    totalFights++;
  }

  @Override
  public void roundStarts(int round) {
    totalRounds++;
  }

  @Override
  public void heroHits(Hero hero, Enemy target, int dealt) {
    totalHeroHits++;
  }

  @Override
  public void enemyHits(Enemy enemy, Hero target, int dealt) {
    totalEnemyHits++;
  }

  @Override
  public void enemyDefeated(Enemy enemy) {
    totalEnemiesDefeated++;
  }

  @Override
  public void enemiesDefeated(List<Enemy> enemies) {
    totalWins++;
  }

  @Override
  public void heroDefeated(Hero hero) {
    totalLosses++;
  }

  @Override
  public void printStatus(Fight fight) {
    // do nothing
  }

  @Override
  public void printResult(Fight fight) {
    // do nothing
  }

  public void printMetrics() {
    System.out.println("Total fights: " + totalFights);
    System.out.println("Total wins: " + totalWins);
    System.out.println("Total losses: " + totalLosses);
    System.out.println("Total enemies defeated: " + totalEnemiesDefeated);
    System.out.println("Total rounds: " + totalRounds);
    System.out.println("Total hero hits: " + totalHeroHits);
    System.out.println("Total enemy hits: " + totalEnemyHits);
  }
}
