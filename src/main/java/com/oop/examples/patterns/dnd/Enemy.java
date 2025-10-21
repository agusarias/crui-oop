package com.oop.examples.patterns.dnd;

public class Enemy extends Personaje {

  private String kind; // goblin, orc, dragon...

  public Enemy(String name, String kind, int baseAttack, int baseDefense, int health) {
    super(name, baseAttack, baseDefense, health, null, null);
    this.kind = kind;
  }

  public String getKind() {
    return kind;
  }


  @Override
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

  @Override
  public int getDefense() {
    int def = super.getDefense();
    // Defensa especial hardcodeada (candidato a Strategy o State)
    if ("dragon".equals(kind)) {
      def += 3;
    }
    return def;
  }

  @Override
  public int hit(Personaje target) {
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
