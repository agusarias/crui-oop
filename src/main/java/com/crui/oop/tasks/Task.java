package com.crui.oop.tasks;

import java.time.LocalDateTime;

public class Task {
  private boolean isComplete;
  private String description;
  private LocalDateTime createdAt;

  private static final int LIMIT = 10;

  public Task(boolean isComplete, String description) {
    this.isComplete = isComplete;
    this.description = description;
    this.createdAt = LocalDateTime.now();
  }

  public String toString() {
    return "[" + (isComplete ? "X" : " ") + "] " + description;
  }

  public boolean isTooOld() {
    return createdAt.isBefore(LocalDateTime.now().minusDays(1));
  }

  public void setDescription(String description) {
    if (description.length() > LIMIT) {
      throw new IllegalArgumentException("Description is too long");
    }
    this.description = description;
  }
}
