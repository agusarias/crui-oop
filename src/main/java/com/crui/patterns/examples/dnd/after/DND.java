package com.crui.patterns.examples.dnd.after;

import java.util.List;

import com.crui.patterns.examples.dnd.after.enemy.Enemy;
import com.crui.patterns.examples.dnd.after.enemy.EnemyFactory;
import com.crui.patterns.examples.dnd.after.fight.Fight;
import com.crui.patterns.examples.dnd.after.fight.FightFactory;
import com.crui.patterns.examples.dnd.after.hero.Hero;
import com.crui.patterns.examples.dnd.after.hero.HeroFactory;
import com.crui.patterns.examples.dnd.after.util.ConsoleView;
import com.crui.patterns.examples.dnd.after.util.FightListeners;
import com.crui.patterns.examples.dnd.after.util.MetricsCollector;

/**
 * Programa inicial para refactorización con patrones de diseño.
 *
 * <p>Ejecutar usando el comando:
 *
 * <p>mvn exec:java -Dexec.mainClass="com.oop.examples.patterns.dnd.DND"
 */
public class DND {

  public static void main(String[] args) {

    ConsoleView consoleView = new ConsoleView();
    MetricsCollector metricsCollector = new MetricsCollector();
    FightListeners listeners = new FightListeners(List.of(consoleView, metricsCollector));
    System.out.println("Starting 100 fights...");
    for (int i = 0; i < 100; i++) {
      Fight fight = FightFactory.createFight(listeners);
      fight.run();
      System.out.println("--------------------------------");
    }
    metricsCollector.printMetrics();
  }
}
