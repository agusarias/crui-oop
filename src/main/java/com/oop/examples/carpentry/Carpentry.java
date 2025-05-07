package com.oop.examples.carpentry;

import com.oop.examples.encapsulation.Person;
import com.oop.examples.tasks.Task;

public class Carpentry {
    private Person person;
    private Task task;

    // Constructor que combina Person y Task
    public Carpentry(Person person, Task task) {
        this.person = person;
        this.task = task;
    }

    // Método para obtener información de la tarea de carpintería
    public void getInfoTaskCarpentry() {
        System.out.println("Carpentry Task Details:");
        System.out.println("Assigned to: " + person.getName() + " (Email: " + person.getEmail() + ")");
        System.out.println("Task Description: " + task.getDescription());
    }
}
