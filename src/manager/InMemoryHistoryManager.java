package manager;

import tasks.Task;

import util.CustomList;
import util.Node;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryHistoryManager implements HistoryManager {

    public Map<Integer, Node<Task>> historyNode = new HashMap<>();
    private final CustomList<Node<Task>> customList = new CustomList<>();


    @Override
    public void add(Task task) {
        if(historyNode.containsKey(task.getId())) {

            customList.removeNode(historyNode.get(task.getId()));
            historyNode.remove(task.getId());
        }
        Node<Task> nodeTask = customList.linkLast(task);
        historyNode.put(task.getId(), nodeTask);

    }

    @Override
    public void remove(int id) {
        customList.removeNode(historyNode.get(id));
        historyNode.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        try {
            return customList.getTasks(customList);
        }
        catch (NullPointerException nullPointerException) {
            throw new NullPointerException("Пустой список!");
        }
    }
    public void clear(InMemoryHistoryManager inMemoryHistory) {
        historyNode.clear();
    }

    @Override
    public CustomList<Node<Task>> getCustomList() {
        try {
            return customList;
        }
        catch (NullPointerException nullPointerException) {
            throw new NullPointerException("Пустой лист!");
        }
    }

    public static List<Integer> fromString(String value) { // Метод возвращает лист с id задач истории из файла
        String[] row = value.split(",");
        List<Integer> idHistory = new ArrayList<>();
        for (String val : row) {
            idHistory.add(Integer.parseInt(val.trim()));
        }
        return idHistory;
    }

    public static String toString(List<Task> manager) { // Метод записывает историю просмотров в файл
        return manager.stream()
                .map(Task::getId)
                .map(Object::toString)
                .collect(Collectors.toList()).toString().replaceAll("\\[","").replaceAll("]","");
    }
}

