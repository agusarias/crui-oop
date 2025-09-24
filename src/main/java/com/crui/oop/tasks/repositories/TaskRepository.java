package com.crui.oop.tasks.repositories;

import java.util.List;
import com.crui.oop.tasks.Task;

public interface TaskRepository {
  public List<Task> getAll();
}
