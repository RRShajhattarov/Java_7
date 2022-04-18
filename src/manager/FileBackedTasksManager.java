package manager;

import type.*;

import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileBackedTasksManager extends InMemoryTasksManager {

    public static FileBackedTasksManager loadFromFile(File file) {  // Метод возвращает объект FileBackedTasksManager с информацией и историей из файла
        FileBackedTasksManager fbtm = new FileBackedTasksManager();
        try {
            String fileString = Files.readString(Path.of(file.getName()));
            String[] fileArray = fileString.split("\n");
            for (int i = 1; i < fileArray.length - 2; i++) {
                Task task = fbtm.fromString(fileArray[i]);
                fbtm.setTask(task);
            }
            List<Integer> integerList = InMemoryHistoryManager.fromString(fileArray[fileArray.length - 1]);
            for (Integer integer : integerList) {
                fbtm.getTaskById(integer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fbtm;
    }
    
    public void save(Task task) {  // Метод сохраняет информацию о добавленных задачах в файл
        String strTask = toString(task);
        try {
            Files.writeString(Path.of("file.csv"),strTask, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл!!!");
        }
    }

    public String toString(Task task) {  // Метод преобразовывает в одну строку информацию о задаче
        String id = String.valueOf(task.getId());
        String name = task.getName();
        String status = String.valueOf(task.getStatus());
        String description = task.getDescription();
        if (task instanceof Subtask) {
            String epic = String.valueOf(((Subtask) task).getEpic().getId());
            return id + "," + "SUBTASK" + "," + name + "," + status + ",Description " + description + "," + epic + "\n";
        }
        if (task instanceof Epic) {
            return id + "," + "EPIC" + ","  + name + "," + status + ",Description " + description + "\n";
        } else return id + "," + "TASK" + ","  + name + "," + status + ",Description " + description + "\n";
    }

    public Task fromString(String str) { // Метод возвращает задачу и ее параметры из файла
        String[] row = str.split(",");
        int id = Integer.parseInt(row[0]);
        String type = row[1];
        String name = row[2];
        String status = row[3];
        String description = row[4];
        Subtask subtask = null;

        if (Objects.equals(type, String.valueOf(Type.TASK))) {
            return new Task(name, description, id, Status.valueOf(status));

        } else if (Objects.equals(type, String.valueOf(Type.EPIC))) {
            return new Epic(name, description, id, Status.valueOf(status));

        } else if (Objects.equals(type, String.valueOf(Type.SUBTASK))) {
            int idEpic = Integer.parseInt(row[5]);
            subtask = new Subtask(name, description, id, Status.valueOf(status));
            subtask.setEpic((Epic) getTaskById(idEpic));
            return subtask;
        } else return null;
    }


    @Override
    public List<Task> getAllTask() {

        return super.getAllTask();
    }

    @Override
    public List<Task> getAllEpic() {

        return super.getAllEpic();
    }

    @Override
    public List<Subtask> getAllSubtask(Epic epic) {

        return super.getAllSubtask(epic);
    }

    @Override
    public Task getTaskById(int id) {
        return super.getTaskById(id);
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save(task);
    }

    @Override
    public void refreshTask(int id, Task task) {
        super.refreshTask(id, task);

    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);

    }

    @Override
    public void deleteAllTask() {
        super.deleteAllTask();

    }

    @Override
    public List<Task> getHistory() {

        return super.getHistory();
    }

    public void saveHistory(List<Task> history) { // Метод сохраняет историю в файл
        try {
            Files.writeString(Path.of("file.csv"),"\n" + InMemoryHistoryManager.toString(history),StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл!!!");
        }

    }

    @Override
    public void setTask(Task task) {
        super.setTask(task);
    }
}
