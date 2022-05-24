package test;

import manager.InMemoryTasksManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.assertFalse;

public abstract class TaskManagerTest<T extends InMemoryTasksManager> { // абстрактный класс с проверкой методов интерфейса
    Task task1;
    Task task2;
    Epic epic1;
    Subtask subtask1;
    Subtask subtask2;
    Task task3;
    Epic epic2;
    Epic epic3;
    Subtask subtask3;
    Task task4;

    abstract void addNewTask();

    abstract void testGetAllTaskSimple();

    abstract void testGetAllTaskEmptyList();

    abstract void testGetAllEpicSimple() ;

    abstract void testGetAllEpicEmptyList();

    abstract void testGetAllSubtaskSimple();

    abstract void testGetAllSubtaskEmptyList();

    abstract void testGetTaskByIdSimple();

    abstract void testGetTaskByIncorrectId();

    abstract void testRefreshTask();

    abstract void testRefreshTaskIncorrectId();

    abstract void testDeleteTaskByIdSimple();

    abstract void testDeleteTaskByIncorrectId();

    abstract void testDeleteAllTask();


}
