package manager;

import type.Epic;
import type.Subtask;
import type.Task;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class InMemoryTasksManager implements Manager {
    HashMap<Integer, Task> tasks = new HashMap<>();
    InMemoryHistoryManager memoryHistory = new InMemoryHistoryManager();

    //    Получение списка всех задач.
    @Override
    public List<Task> getAllTask() {
        ArrayList<Task> taskList = new ArrayList<>();
        for (Task value : tasks.values()) {
            if (!(value instanceof Epic) && !(value instanceof Subtask)) {
                taskList.add(value);
            }
        }
        return taskList;
    }

    //    Получение списка всех эпиков.
    @Override
    public List<Task> getAllEpic() {
        ArrayList<Task> taskList = new ArrayList<>();
        for (Task value : tasks.values()) {
            if (value instanceof Epic) {
                taskList.add(value);
            }
        }
        return taskList;
    }

    //    Получение списка всех подзадач определённого эпика.
    @Override
    public List<Subtask> getAllSubtask(Epic epic) {
        if (tasks.isEmpty()) {
            return Collections.emptyList();
        }
        Epic task = (Epic) tasks.get(epic.getId());
        return task.getSubtasks();
    }

    //    Получение задачи любого типа по идентификатору.
    @Override
    public Task getTaskById(int id) {
        Task anyTask = tasks.get(id);
        if (anyTask != null) {
            memoryHistory.add(tasks.get(id));
        }
        return anyTask;
    }

    //    Добавление новой задачи, эпика и подзадачи. Сам объект должен передаваться в качестве параметра.
    @Override
    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    //    Обновление задачи любого типа по идентификатору. Новая версия объекта передаётся в виде параметра.
    @Override
    public void refreshTask(int id, Task task) {
        Task existingTask = tasks.get(id);
        if (existingTask != null) {
            existingTask.setDescription(task.getDescription());
            existingTask.setName(task.getName());
            if (!(task instanceof Epic)) {
                existingTask.setStatus(task.getStatus());
            }
        }
    }

    //    Удаление ранее добавленных задач — всех и по идентификатору.
    @Override
    public void deleteTaskById(int id) {
        if(memoryHistory.getHistory().contains(tasks.get(id))) {
            memoryHistory.remove(id);
        }
        tasks.remove(id);
    }

    @Override
    public void deleteAllTask() {
        tasks.clear();
    }

    @Override
    public List<Task> getHistory(){
        return memoryHistory.getHistory();
    }

    public void setTask(Task task) {
        tasks.put(task.getId(), task);
    }

}
