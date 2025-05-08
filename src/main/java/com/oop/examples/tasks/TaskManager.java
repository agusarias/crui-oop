package com.oop.examples.tasks;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.oop.examples.tasks.repositories.TaskRepository;

// To-Do list
// [] ir al super
// [x] bañar al perro

public class TaskManager {

  private List<Task> tasks;
  private TaskRepository taskRepository;

  public TaskManager(TaskRepository taskRepository) {
    this.tasks = new ArrayList<>();
    this.taskRepository = taskRepository;
  }

  public void addTask(boolean isComplete, String description) {
    Task task = new Task(isComplete, description);
    this.tasks.add(task);
  }

  public List<Task> getAllTasks() {
    List<Task> tasks = this.taskRepository.getAll();
    tasks.removeIf(Task::isTooOld);
    return tasks;
  }

  public List<Task> getAllTasks2() {
    // List<Task> tasks = TaskWithORM.findAll();
    // tasks.removeIf(Task::isTooOld);
    // return tasks;
    return this.getAllTasks();
  }

  public void changeDescription(Task task, String description) {
    task.setDescription(description);
  }

  public void exportsTasksToFile(String filePath) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      for (Task task : tasks) {
        writer.write(task.toString());
        writer.newLine();
      }
    } catch (IOException e) {
      System.err.println("Error writing to file: " + e.getMessage());
    }
  }
  public void imprimirTareas() {
    if (tasks.isEmpty()) {
        System.out.println("No hay tareas cargadas.");
    } else {
        System.out.println("Lista de tareas:");
        for (Task task : tasks) {
            System.out.println("- " + task.getDescription() + " [" + (task.isComplete() ? "completa" : "pendiente") + "]");
        }
    }
}
}