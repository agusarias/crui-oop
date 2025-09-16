package com.crui.patterns.examples.dnd.after.util;

import java.util.List;

import com.crui.patterns.examples.dnd.after.enemy.Enemy;
import com.crui.patterns.examples.dnd.after.fight.Fight;
import com.crui.patterns.examples.dnd.after.hero.Hero;

public interface FightEventsListener {
  void fightStarts();

  void roundStarts(int round);

  void heroHits(Hero hero, Enemy target, int dealt);

  void heroDefeated(Hero hero);

  void enemyHits(Enemy enemy, Hero target, int dealt);

  void enemyDefeated(Enemy enemy);

  void enemiesDefeated(List<Enemy> enemies);

  void printStatus(Fight fight);

  void printResult(Fight fight);
}
