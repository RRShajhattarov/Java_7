package type;

import util.IdGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> subtasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(String name, String description, int taskId, Status status) {
        super(name, description, taskId, status);
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }


    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }


    public void checkSubtaskStatus() {
        HashSet<Status> statuses = new HashSet<>();
        for (Subtask subtask : subtasks) {
            statuses.add(subtask.getStatus());
        }
        ArrayList<Status> list = new ArrayList<>(statuses);
        if (statuses.size() == 1) {
            this.setStatus(list.get(0));

        } else if (statuses.size() > 1) {
            this.setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + taskId +
                ", status='" + status + '\'' +
                '}';
    }


}
