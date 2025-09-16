package com.crui.patterns.examples.gdocs.after;

public class Change {
  private User user;
  private String content;
  private String description;

  public Change(User user, String content, String description) {
    this.user = user;
    this.content = content;
    this.description = description;
  }

  @Override
  public String toString() {
    return "Change [user="
        + user.getName()
        + ", content="
        + content
        + ", description="
        + description
        + "]";
  }
}
