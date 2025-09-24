package com.crui.oop.tasks.repositories;

import java.util.List;
import com.crui.oop.tasks.Task;

class TaskRepositoryMysql implements TaskRepository {
  Database db;

  public List<Task> getAll() {
    String sql = "SELECT * FROM tasks_in_mysql";
    List<Task> tasks = this.db.<Task>call(sql);
    return tasks;
  }
}
