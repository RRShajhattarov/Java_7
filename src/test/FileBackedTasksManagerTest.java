package test;

import manager.FileBackedTasksManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends InMemoryTasksManagerTest {


    FileBackedTasksManager taskManager;
    List<Task> history = new ArrayList<Task>();


    @BeforeEach
    void beforeEach() {
        task1 = new Task("task1", "Description task1 example");
        task2 = new Task("task2", "Description task2 example");
        epic1 = new Epic("epic1", "Description epic1 example");
        subtask1 = new Subtask("subtask1", "Description subtask1 example");
        subtask2 = new Subtask("subtask2", "Description subtask2 example");
        taskManager = new FileBackedTasksManager();
        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);
        subtask1.setEpic(epic1);
        subtask2.setEpic(epic1);
    }


    @Test
    void loadFromFileTestSimple() {
        taskManager = FileBackedTasksManager.loadFromFile("resources/file.csv");
        assertEquals(taskManager.getTaskById(16).getName(),task1.getName());
    }

    @Test
    void loadFromFileTestEmpty() {
        NumberFormatException ex = assertThrows(NumberFormatException.class,
                () -> FileBackedTasksManager.loadFromFile("resources/fileEmpty.csv"));
        assertEquals("Пустой файл!", ex.getMessage());

    }

    @Test
    void loadFromFileTestWithoutHistory() {
        NumberFormatException ex = assertThrows(NumberFormatException.class,
                () -> FileBackedTasksManager.loadFromFile("resources/fileEmpty.csv"));
        assertEquals("Пустая история!", ex.getMessage());
    }

    @Test
    void testToStringTest() {
        String task1String = taskManager.toString(task1);
        assertFalse(task1String.isEmpty());
    }

    @Test
    void fromStringTest() {
        String task1String = taskManager.toString(task1);
        Task taskNew = taskManager.fromString(task1String);
        assertEquals(taskNew.getName(),task1.getName());
    }

    @Test
    void saveHistoryTest() {
        FileBackedTasksManager fb = FileBackedTasksManager.loadFromFile("resources/fileSaveHistory.csv");
        assertFalse(fb.getHistory().isEmpty());
    }
}