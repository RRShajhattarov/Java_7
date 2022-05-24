package tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> subtasks = new ArrayList<>();
    long duration;
    LocalDateTime startTime;
    LocalDateTime endTime;
    public Epic(String name, String description) {
        super(name, description);
        type = Type.EPIC;
    }

    public Epic(String name, String description, int taskId, Status status) {
        super(name, description, taskId, status);
        type = Type.EPIC;
    }

    @Override
    public LocalDateTime getEndDate() {


        LocalDateTime endTime1;
        LocalDateTime endTime2 = LocalDateTime.of(2000, 1, 1, 0, 0);
        for (Subtask subtask : subtasks) {
            endTime1 = subtask.getEndDate();
            if (endTime1.isAfter(endTime2)) {
                endTime2 = endTime1;
            }
        }
        endTime = endTime2;
        return endTime;
    }

    private void initDate() {
        LocalDateTime startTime1;
        LocalDateTime startTime2 = LocalDateTime.of(3000, 1, 1, 0, 0);
        long duration1 = 0;


        for (Subtask subtask : subtasks) {
            startTime1 = subtask.startTime;
            if (startTime1.isBefore(startTime2)) {
                startTime2 = startTime1;
            }
        }
        startTime = startTime2;

        for (Subtask subtask : subtasks) {
            duration1 = duration1 + subtask.duration;
        }
        duration = duration1;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }


    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        if (subtask.getStartTime() != null) {
            initDate();
        }
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
