package com.crui.patterns.examples.dnd.after.enemy;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.crui.patterns.examples.dnd.after.util.Dice;

public class EnemyFactory {

  private static final String[] NAMES = {
    "Mountain Troll",
    "Willy the Orc",
    "Baby Dragon",
    "Goblin King",
    "Giant Spider",
    "Skeleton",
    "Zombie",
    "Vampire",
    "Werewolf",
    "Witch",
    "Wizard",
    "Demon",
    "Devil",
    "Ghost",
    "Ghoul",
    "Goblin",
    "Orc",
    "Dragon"
  };

  public static Enemy createRandomEnemy() {
    int baseAttack = Dice.range(10, 20);
    int baseDefense = Dice.range(10, 20);
    int health = Dice.range(100, 200);
    Kinds kind = Kinds.values()[Dice.range(0, Kinds.values().length - 1)];
    String name = NAMES[Dice.range(0, NAMES.length - 1)];
    Enemy enemy;
    switch (kind) {
      case GOBLIN:
        enemy = new Goblin(name, kind.getKind(), baseAttack, baseDefense, health);
        break;
      case ORC:
        enemy = new Orc(name, kind.getKind(), baseAttack, baseDefense, health);
        break;
      case DRAGON:
        enemy = new Dragon(name, kind.getKind(), baseAttack, baseDefense, health);
        break;
      default:
        enemy = new Enemy(name, kind.getKind(), baseAttack, baseDefense, health);
        break;
    }
    return enemy;
  }

  public static List<Enemy> createRandomEnemies(int count) {
    return IntStream.range(0, count)
        .mapToObj(i -> createRandomEnemy())
        .collect(Collectors.toList());
  }
}
