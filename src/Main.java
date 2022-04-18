import manager.FileBackedTasksManager;
import manager.InMemoryHistoryManager;
import manager.InMemoryTasksManager;
import type.Epic;
import type.Subtask;
import type.Task;

import java.io.File;
import java.util.stream.Collectors;


public class Main {

    public static void main(String[] args) {

        Subtask subtask1 = new Subtask("subtask1","subtask1 example");
        Subtask subtask2 = new Subtask("subtask2", "subtask2 example");
        Subtask subtask3 = new Subtask("subtask3", "subtask3 example");
        Epic epic1 = new Epic("epic1", "epic1 example");
        Epic epic2 = new Epic("epic2", "epic2 example");
        Task task1 = new Task("task1","task1 example");

        FileBackedTasksManager ff = new FileBackedTasksManager();
        ff.addTask(task1);
        ff.getTaskById(task1.getId());
        ff.saveHistory(ff.getHistory());
        File file = new File("file.csv");
        FileBackedTasksManager fb = FileBackedTasksManager.loadFromFile(file);
        System.out.println(fb.getHistory());


        // epic1.addSubtask(subtask1);
        //  epic1.addSubtask(subtask2);
        // epic1.addSubtask(subtask3);

        //customList.addTask(epic1);
        // customList.addTask(epic2);
        // customList.addTask(task1);
        //customList.getTaskById(epic1.getId());
        //customList.getTaskById(task1.getId());
        //customList.getTaskById(epic2.getId());
        // customList.getTaskById(task1.getId());
        //System.out.println(customList);
        //System.out.println(customList.getHistory());
        //customList.deleteTaskById(task1.getId());
        //System.out.println(customList.getHistory());






//        Manager manager = Managers.getDefault();
//
//        Epic epic = new Epic("epic", "epic example");
//        Subtask subtask = new Subtask("subtask", "subtask example");
//        Subtask subtask1 = new Subtask("subtask1", "subtask1 example");
//        epic.addSubtask(subtask);
//        subtask.setEpic(epic);
//        subtask1.setEpic(epic);
//        epic.addSubtask(subtask1);
//        Subtask update = new Subtask("update", "update example");
//        update.setStatus(Status.DONE);
//        Subtask update1 = new Subtask("update1", "update1 example");
//        update1.setStatus(Status.DONE);
//        manager.addTask(task);
//        manager.addTask(epic);
//        manager.addTask(subtask);
//        manager.addTask(subtask1);
//        System.out.println(epic);
//        manager.refreshTask(subtask.getId(), update);
//        manager.refreshTask(subtask1.getId(), update1);
//
//        System.out.println(epic);
//        System.out.println(subtask);
//        System.out.println(subtask1);
//        manager.refreshTask(epic.getId(), new Epic("adadad", "adadadadada"));
//        System.out.println(epic);
//        manager.getTaskById(12);
//        manager.getTaskById(13);
//        System.out.println(manager.history());

    }

} 