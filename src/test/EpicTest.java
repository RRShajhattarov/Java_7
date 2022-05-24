package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    Epic epic;
    Subtask subtask1;
    Subtask subtask2;

    @BeforeEach
    void beforeEach() {
        epic = new Epic("epic", "epic example");
        subtask1 = new Subtask("subtask1", "subtask1 example");
        subtask2 = new Subtask("subtask2", "subtask2 example");
    }

    @Test
    public void statusEmptyListOfSubtasks() {
        Status status = epic.getStatus();
        assertEquals(Status.NEW,status);
    }

    @Test
    public void statusListOfSubtasksNew() {
        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);
        subtask1.setEpic(epic);
        subtask2.setEpic(epic);
        Status status = epic.getStatus();
        assertEquals(Status.NEW,status);
    }

    @Test
    public void statusListOfSubtasksDone() {

        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);
        subtask1.setEpic(epic);
        subtask2.setEpic(epic);
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        Status status = epic.getStatus();
        assertEquals(Status.DONE,status);
    }

    @Test
    public void statusListOfSubtasksInProgress() {
        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);
        subtask1.setEpic(epic);
        subtask2.setEpic(epic);
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setStatus(Status.IN_PROGRESS);
        Status status = epic.getStatus();
        assertEquals(Status.IN_PROGRESS,status);
    }



}