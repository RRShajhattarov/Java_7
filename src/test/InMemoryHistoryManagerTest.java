package test;

import manager.InMemoryHistoryManager;
import manager.InMemoryTasksManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Status;
import tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest extends InMemoryTasksManager {
    InMemoryHistoryManager historyManager;
    Task task1;
    Task task2;
    Task task3;

    @BeforeEach
    void beforeEach() {
        task1 = new Task("task1", "task1 example", 1, Status.NEW);
        task2 = new Task("task2", "task2 example", 2, Status.NEW);
        task3 = new Task("task3", "task3 example", 3, Status.NEW);
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void testAdd() {
        historyManager.add(task1);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История пустая.");
        assertEquals(1, history.size(), "История пустая.");
    }

    @Test
    void testAddDuplicate() {
        historyManager.add(task1);
        historyManager.add(task1);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История пустая.");
        assertEquals(1, history.size(), "История пустая.");
    }

    @Test
    void testRemoveFirst() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        final List<Task> historyBefore = historyManager.getHistory();
        assertNotNull(historyBefore, "История не пустая.");
        assertEquals(3, historyBefore.size(), "История не пустая.");
        historyManager.remove(task1.getId());
        final List<Task> historyAfter = historyManager.getHistory();
        assertEquals(2, historyAfter.size(), "Длина не сходится.");
        assertEquals(historyAfter.get(0), historyBefore.get(1), "Задача не сходится.");
    }

    @Test
    void testRemoveSecond() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        final List<Task> historyBefore = historyManager.getHistory();
        assertNotNull(historyBefore, "История не пустая.");
        assertEquals(3, historyBefore.size(), "История не пустая.");
        historyManager.remove(task2.getId());
        final List<Task> historyAfter = historyManager.getHistory();
        assertEquals(2, historyAfter.size(), "Длина не сходится.");
        assertEquals(historyAfter.get(1), historyBefore.get(2), "Задача не сходится.");
    }

    @Test
    void testRemoveLast() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        final List<Task> historyBefore = historyManager.getHistory();
        assertNotNull(historyBefore, "История пустая.");
        assertEquals(3, historyBefore.size(), "История пустая.");
        historyManager.remove(task3.getId());
        final List<Task> historyAfter = historyManager.getHistory();
        assertEquals(2, historyAfter.size(), "Длина не сходится.");
        assertEquals(historyAfter.get(1), historyBefore.get(2), "Задача не сходится.");
    }

    @Test
    void testGetHistoryFor2Task() {
        historyManager.add(task1);
        historyManager.add(task2);
        final List<Task> historyList = historyManager.getHistory();
        assertNotNull(historyList, "История пустая.");
        assertEquals(2, historyList.size(), "Длина не сходится.");
    }

    @Test
    void testGetHistoryForDuplicateTask() {
        historyManager.add(task1);
        historyManager.add(task1);
        final List<Task> historyList = historyManager.getHistory();
        assertNotNull(historyList, "История пустая.");
        assertEquals(1, historyList.size(), "Длина не сходится.");
    }

    @Test
    void testGetHistoryForEmptyList() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> historyManager.getHistory());
        assertEquals("Пустой список!", ex.getMessage());
    }

}