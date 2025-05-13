package com.oop.examples;

import com.oop.examples.shared.Fotografiable;

public class Photographer {
  private String name;

  public Photographer(String name) {
    this.name = name;
  }

  public void takePicture(Fotografiable f) {
    System.out.println(name + " está tomando una foto...");
    f.takePicture();
  }
}