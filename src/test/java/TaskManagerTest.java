import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.crui.oop.tasks.TaskManager;
import com.crui.oop.tasks.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskManagerTest {

  private static final String TEST_FILE_PATH = "test_tasks.txt";

  @AfterEach
  void cleanUp() {
    // Eliminar el archivo de prueba después de cada test
    File file = new File(TEST_FILE_PATH);
    if (file.exists()) {
      file.delete();
    }
  }

  @Test
  void testExportTasksToFile() throws IOException {
    // Arrange
    List<Task> tasks = new ArrayList<>();
    tasks.add(new Task(false, "Task 1"));
    tasks.add(new Task(true, "Task 2"));
    TaskManager taskManager = new TaskManager(null); // Pasamos null porque no usamos el repositorio
    taskManager.addTask(false, "Task 1");
    taskManager.addTask(true, "Task 2");

    // Act
    taskManager.exportsTasksToFile(TEST_FILE_PATH);

    // Assert
    File file = new File(TEST_FILE_PATH);
    assertTrue(file.exists(), "El archivo no fue creado");

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      assertEquals("[ ] Task 1", reader.readLine());
      assertEquals("[X] Task 2", reader.readLine());
    }
  }
}
