package tasks;


import java.time.LocalDateTime;

public class Subtask extends Task {
    private Epic epic;

    public Subtask(String name, String description) {
        super(name, description);
        type = Type.SUBTASK;
    }

    public Subtask(String name, String description, int taskId, Status status, long duration, LocalDateTime startTime) {
        super(name, description, taskId, status, duration, startTime);
        type = Type.SUBTASK;
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }
    @Override
    public void setStatus(Status status) {
        this.status = status;
        if (epic != null) {
            epic.checkSubtaskStatus();
        }
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + taskId +
                ", status='" + status + '\'' +
                '}';
    }
}

