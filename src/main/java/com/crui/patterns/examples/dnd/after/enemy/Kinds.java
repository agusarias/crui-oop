package com.crui.patterns.examples.dnd.after.enemy;

public enum Kinds {
  GOBLIN("goblin"),
  ORC("orc"),
  DRAGON("dragon");

  private String kind;

  Kinds(String kind) {
    this.kind = kind;
  }

  public String getKind() {
    return kind;
  }
}
