package com.crui.patterns.examples.dnd.after.fight;

import java.util.List;

import com.crui.patterns.examples.dnd.after.enemy.Enemy;
import com.crui.patterns.examples.dnd.after.enemy.EnemyFactory;
import com.crui.patterns.examples.dnd.after.hero.Hero;
import com.crui.patterns.examples.dnd.after.hero.HeroFactory;
import com.crui.patterns.examples.dnd.after.util.Dice;
import com.crui.patterns.examples.dnd.after.util.FightEventsListener;

public class FightFactory {
  public static Fight createFight(FightEventsListener listener) {
    Hero hero = HeroFactory.createRandomHero();
    List<Enemy> enemies = EnemyFactory.createRandomEnemies(Dice.range(1, 10));

    return new Fight(hero, enemies, listener);
  }
}
