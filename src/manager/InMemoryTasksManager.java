package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;


import java.util.*;

public class InMemoryTasksManager implements Manager {
    public static HashMap<Integer, Task> tasks = new HashMap<>();
    public static List<Task> memoryTasks = new ArrayList<>();
    public static InMemoryHistoryManager memoryHistory = new InMemoryHistoryManager();
    public static TreeSet<Task> sortedTasks = new TreeSet<>();
    //    Получение списка всех задач.
    @Override
    public List<Task> getAllTask() {
        ArrayList<Task> taskList = new ArrayList<>();
        for (Task value : tasks.values()) {
            if (!(value instanceof Epic)) {
                taskList.add(value);
                sortedTasks.add(value);
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
    public List<Task> getAllSubtaskWithoutEpic() {
        ArrayList<Task> subtaskList = new ArrayList<>();
        for (Task value : tasks.values()) {
            if (value instanceof Subtask) {
                subtaskList.add(value);
            }
        }
        return subtaskList;
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
    //    Добавил проверку на пересечения
    @Override
    public void addTask(Task task) {
        for (Task sortedTask : sortedTasks) {
            if(sortedTask.getStartTime().isBefore(task.getStartTime())
                    && sortedTask.getEndDate().isAfter(task.getStartTime())
             || sortedTask.getStartTime().isBefore(task.getEndDate())
                    && sortedTask.getEndDate().isAfter(task.getEndDate())) {
                System.out.println("Обнаружены пересечения, задача не была добавлена");
                return;
            }
        }
        tasks.put(task.getId(), task);
        memoryTasks.add(task);
    }

    //    Обновление задачи любого типа по идентификатору. Новая версия объекта передаётся в виде параметра.
    //    Добавил проверку на пересечения
    @Override
    public void refreshTask(int id, Task task) {
        for (Task sortedTask : sortedTasks) {
            if(sortedTask.getStartTime().isBefore(task.getStartTime())
                    && sortedTask.getEndDate().isAfter(task.getStartTime())
                    || sortedTask.getStartTime().isBefore(task.getEndDate())
                    && sortedTask.getEndDate().isAfter(task.getEndDate())) {
                System.out.println("Обнаружены пересечения, задача не была добавлена");
                return;
            }
        }
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
    public String deleteTaskById(int id) {
        try {
            if (memoryHistory.getHistory().contains(tasks.get(id))) {
                memoryHistory.remove(id);
            }

            tasks.remove(id);
            return "Все задачи удалены";
        } catch (NullPointerException nullPointerException) {
            throw new NullPointerException("Несуществующий id");
        }
    }

    @Override
    public String deleteAllTask() {
        tasks.clear();
        memoryTasks.clear();
        return "Все задачи удалены";
    }

    @Override
    public List<Task> getHistory(){
        return memoryHistory.getHistory();
    }

    public void setTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public TreeSet<Task> getPrioritizedTasks() {
        return sortedTasks;
    }



}
