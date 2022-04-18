package manager;

import type.Epic;


import type.Subtask;


import type.Task;


import java.util.*;


public interface Manager {

    //    Получение списка всех задач.
    List<Task> getAllTask();

    //    Получение списка всех эпиков.
    List<Task> getAllEpic();

    //    Получение списка всех подзадач определённого эпика.
    List<Subtask> getAllSubtask(Epic epic);

    //    Получение задачи любого типа по идентификатору.
    Task getTaskById(int id);

    //    Добавление новой задачи, эпика и подзадачи. Сам объект должен передаваться в качестве параметра.
    void addTask(Task task);

    //    Обновление задачи любого типа по идентификатору. Новая версия объекта передаётся в виде параметра.
    void refreshTask(int id, Task task);

    //    Удаление ранее добавленных задач — всех и по идентификатору.
    void deleteTaskById(int id);

    void deleteAllTask();

    List<Task> getHistory();
}
