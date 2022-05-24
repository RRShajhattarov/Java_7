package tasks;

import util.IdGenerator;

import java.time.*;
import java.util.Objects;

public class Task implements Comparable {
    protected Type type;
    protected String name;
    protected String description;
    protected int taskId;
    protected Status status;
    protected long duration;
    protected LocalDateTime startTime;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        taskId = IdGenerator.generateId();
        this.status = Status.NEW;
        type = Type.TASK;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Task(String name, String description, int taskId, Status status, long duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.taskId = taskId;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
        type = Type.TASK;
    }

    public Task(String name, String description, int taskId, Status status) {
        this.name = name;
        this.description = description;
        this.taskId = taskId;
        this.status = status;
        type = Type.TASK;
    }

    public LocalDateTime getEndDate() {
        return startTime.plusMinutes(duration);
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
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

    public void setId(int taskId) {
        this.taskId = taskId;
    }

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

    @Override
    public int compareTo(Object o) {
        Task task = (Task) o;
        if(this.getStartTime() == null || task.getStartTime() == null) {
            return 1;
        }
        if (this.getStartTime().isAfter(task.getStartTime())) return 1;
        else if (this.getStartTime().isBefore(task.getStartTime())) {
            return -1;
        } else {
            return 0;
        }
    }
}

