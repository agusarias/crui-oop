package com.crui.patterns.examples.dnd.after.hero;

public enum Kinds {
  WARRIOR("warrior"),
  ELF("elf"),
  DWARF("dwarf");

  private String kind;

  Kinds(String kind) {
    this.kind = kind;
  }

  public String getKind() {
    return kind;
  }
}
