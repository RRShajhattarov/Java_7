package type;


public class Subtask extends Task {
    private Epic epic;

    public Subtask(String name, String description) {
        super(name, description);
    }

    public Subtask(String name, String description, int taskId, Status status) {
        super(name, description, taskId, status);
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

