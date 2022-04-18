package manager;

import type.Task;
import util.CustomList;
import util.Node;

import java.util.List;

public interface HistoryManager {
    void add(Task task);

    void remove(int id);

    List<Task> getHistory();
    CustomList<Node<Task>> getCustomList();

}
