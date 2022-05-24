package test;

import manager.InMemoryTasksManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTasksManagerTest extends TaskManagerTest<InMemoryTasksManager> { // Реализовал наследование аналогично классам менеджера

    InMemoryTasksManager taskManager;

    @BeforeEach
    void beforeEach() {
        task1 = new Task("task1", "task1 example");
        epic1 = new Epic("epic1", "epic1 example");
        subtask1 = new Subtask("subtask1", "subtask1 example");
        subtask2 = new Subtask("subtask2", "subtask2 example");
        taskManager = new InMemoryTasksManager();
        task2 = new Task("task2", "task2 example");
        task3 = new Task("task3", "task3 example");
        task4 = new Task("task4", "task4 example", 10, Status.NEW);
        epic2 = new Epic("epic2", "epic2 example");
        epic3 = new Epic("epic3", "epic3 example");
        subtask3 = new Subtask("subtask3", "subtask3 example");
        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);
        epic1.addSubtask(subtask3);
        subtask1.setEpic(epic1);
        subtask2.setEpic(epic1);
        subtask3.setEpic(epic1);
    }

    @Override
    @Test
    public void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", 12, Status.NEW);
        taskManager.addTask(task);

        final Task savedTask = taskManager.getTaskById(12);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTask();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Override
    @Test
    public void testGetAllTaskSimple() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        int task1Id = task1.getId();
        int task2Id = task2.getId();
        int task3Id = task3.getId();
        final Task savedTask1 = taskManager.getTaskById(task1Id);
        final Task savedTask2 = taskManager.getTaskById(task2Id);
        final Task savedTask3 = taskManager.getTaskById(task3Id);

        assertNotNull(savedTask1, "Задача не найдена.");
        assertNotNull(savedTask2, "Задача не найдена.");
        assertNotNull(savedTask3, "Задача не найдена.");
        assertEquals(task1, savedTask1, "Задачи не совпадают.");
        assertEquals(task2, savedTask2, "Задачи не совпадают.");
        assertEquals(task3, savedTask3, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTask();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(3, tasks.size(), "Неверное количество задач.");
        assertEquals(task1, tasks.get(0), "Задачи не совпадают.");
        assertEquals(task2, tasks.get(1), "Задачи не совпадают.");
        assertEquals(task3, tasks.get(2), "Задачи не совпадают.");
    }

    @Override
    @Test
    void testGetAllTaskEmptyList() {
        final List<Task> tasks = taskManager.getAllTask();
        assertEquals(0, tasks.size());
    }

    @Override
    @Test
    void testGetAllEpicSimple() {
        taskManager.addTask(epic1);
        taskManager.addTask(epic2);
        taskManager.addTask(epic3);

        int task1Id = epic1.getId();
        int task2Id = epic2.getId();
        int task3Id = epic3.getId();
        final Task savedTask1 = taskManager.getTaskById(task1Id);
        final Task savedTask2 = taskManager.getTaskById(task2Id);
        final Task savedTask3 = taskManager.getTaskById(task3Id);

        assertNotNull(savedTask1, "Задача не найдена.");
        assertNotNull(savedTask2, "Задача не найдена.");
        assertNotNull(savedTask3, "Задача не найдена.");
        assertEquals(epic1, savedTask1, "Задачи не совпадают.");
        assertEquals(epic2, savedTask2, "Задачи не совпадают.");
        assertEquals(epic3, savedTask3, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllEpic();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(3, tasks.size(), "Неверное количество задач.");
        assertEquals(epic1, tasks.get(0), "Задачи не совпадают.");
        assertEquals(epic2, tasks.get(1), "Задачи не совпадают.");
        assertEquals(epic3, tasks.get(2), "Задачи не совпадают.");
    }
    @Override
    @Test
    void testGetAllEpicEmptyList() {
        final List<Task> tasks = taskManager.getAllEpic();
        assertEquals(0, tasks.size());
    }
    @Override
    @Test
    void testGetAllSubtaskSimple() {
        taskManager.addTask(subtask1);
        taskManager.addTask(subtask2);
        taskManager.addTask(subtask3);
        taskManager.addTask(epic1);
        int task1Id = subtask1.getId();
        int task2Id = subtask2.getId();
        int task3Id = subtask3.getId();
        final Task savedTask1 = taskManager.getTaskById(task1Id);
        final Task savedTask2 = taskManager.getTaskById(task2Id);
        final Task savedTask3 = taskManager.getTaskById(task3Id);

        assertNotNull(savedTask1, "Задача не найдена.");
        assertNotNull(savedTask2, "Задача не найдена.");
        assertNotNull(savedTask3, "Задача не найдена.");
        assertEquals(subtask1, savedTask1, "Задачи не совпадают.");
        assertEquals(subtask2, savedTask2, "Задачи не совпадают.");
        assertEquals(subtask3, savedTask3, "Задачи не совпадают.");

        final List<Subtask> tasks = taskManager.getAllSubtask(epic1);

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(3, tasks.size(), "Неверное количество задач.");
        assertEquals(subtask1, tasks.get(0), "Задачи не совпадают.");
        assertEquals(subtask2, tasks.get(1), "Задачи не совпадают.");
        assertEquals(subtask3, tasks.get(2), "Задачи не совпадают.");
    }
    @Override
    @Test
    void testGetAllSubtaskEmptyList() {
        taskManager.addTask(epic2);
        final List<Subtask> tasks = taskManager.getAllSubtask(epic2);
        assertEquals(0, tasks.size());
    }
    @Override
    @Test
    void testGetTaskByIdSimple() {
        taskManager.addTask(task1);
        int task1Id = task1.getId();
        final Task savedTask1 = taskManager.getTaskById(task1Id);
        assertEquals(task1, savedTask1, "Задачи не совпадают.");
    }
    @Override
    @Test
    void testGetTaskByIncorrectId() {
        taskManager.addTask(task4);
        final Task savedTask1 = taskManager.getTaskById(15);
        assertNull(savedTask1, "Задача найдена.");
    }
    @Override
    @Test
    void testRefreshTask() {
        taskManager.addTask(task1);
        int task1Id = task1.getId();
        final Task savedTask1 = taskManager.getTaskById(task1Id);
        assertNotNull(savedTask1, "Задача не найдена.");
        assertEquals(task1, savedTask1, "Задачи не совпадают.");
        taskManager.refreshTask(task1Id, task2);
        assertEquals(task2.getName(), savedTask1.getName(), "Задачи не совпадают.");
        assertEquals(task2.getDescription(), savedTask1.getDescription(), "Задачи не совпадают.");
        assertEquals(task2.getStatus(), savedTask1.getStatus(), "Задачи не совпадают.");
    }
    @Override
    @Test
    void testRefreshTaskIncorrectId() {
        int task1Id = 1;
        final Task savedTask1 = taskManager.getTaskById(task1Id);
        assertNull(savedTask1, "Задача найдена.");
    }
    @Override
    @Test
    void testDeleteTaskByIdSimple() {
        taskManager.addTask(task1);
        int task1Id = task1.getId();
        final Task savedTask1 = taskManager.getTaskById(task1Id);
        assertNotNull(savedTask1, "Задача не найдена.");
        assertEquals(task1, savedTask1, "Задачи не совпадают.");

        taskManager.deleteTaskById(task1Id);
        final List<Task> tasks = taskManager.getAllTask();
        assertEquals(0, tasks.size());
    }
    @Override
    @Test
    void testDeleteTaskByIncorrectId() {
        taskManager.addTask(task4);

        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> taskManager.deleteTaskById(15));
        final List<Task> tasks = taskManager.getAllTask();
        assertEquals("Несуществующий id", ex.getMessage());
        assertEquals(1, tasks.size());
    }
    @Override
    @Test
    void testDeleteAllTask() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        final List<Task> tasksBefore = taskManager.getAllTask();
        assertEquals(3,tasksBefore.size());
        taskManager.deleteAllTask();
        final List<Task> tasksAfter = taskManager.getAllTask();
        assertEquals(0,tasksAfter.size());
    }
}