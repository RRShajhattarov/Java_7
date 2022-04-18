package type;

import util.IdGenerator;

import java.util.Objects;

public class Task {

    protected String name;
    protected String description;
    protected int taskId;
    protected Status status;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        taskId = IdGenerator.generateId();
        this.status = Status.NEW;
    }
    public Task(String name, String description, int taskId, Status status) {
        this.name = name;
        this.description = description;
        this.taskId = taskId;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return taskId;
    }

    public void setId(int taskId) {this.taskId = taskId;}

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }



    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + taskId +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId == task.taskId && Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, taskId, status);
    }
}

