package com.crui.patterns.structural.composite;

import java.util.List;

public class Folders {

  public static void main(String[] args) {}

  class Folder {
    private String name;
    private List<Folder> folders;
    private List<File> files;

    public Folder(String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }
  }

  class File {
    private String name;
  }
}
