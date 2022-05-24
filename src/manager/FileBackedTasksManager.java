package manager;

import tasks.*;
import util.ManagerSaveException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileBackedTasksManager extends InMemoryTasksManager {
    public static List<Task> allTasks = new ArrayList<>();
    private static final String FILE = "resources/file.csv";

    public static FileBackedTasksManager loadFromFile(String file) {  // Метод возвращает объект FileBackedTasksManager с информацией и историей из файла
        FileBackedTasksManager fbtm = new FileBackedTasksManager();
        String[] fileArrayHist = new String[0];
        try {
            String fileString = Files.readString(Path.of(file));
            String[] fileArray = fileString.split("\n");
            fileArrayHist = fileArray;
            for (int i = 1; i < fileArray.length - 2; i++) {
                Task task = fbtm.fromString(fileArray[i]);
                fbtm.setTask(task);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException num) {
            throw new NumberFormatException("Пустой файл!");
        }
        try {
            List<Integer> integerList = InMemoryHistoryManager.fromString(fileArrayHist[fileArrayHist.length - 1]);
            for (Integer integer : integerList) {
                fbtm.getTaskById(integer);
            }
        } catch (NumberFormatException numHist) {
            throw new NumberFormatException("Пустая история!");
        }

        return fbtm;
    }
    
    public void save() {  // Метод сохраняет информацию о добавленных задачах в файл
        allTasks.addAll(memoryTasks);
    }

    public String toString(Task task) {  // Метод преобразовывает в одну строку информацию о задаче
        String id = String.valueOf(task.getId());
        String name = task.getName();
        String status = String.valueOf(task.getStatus());
        String description = task.getDescription();
        String duration = String.valueOf(task.getDuration());
        String startTime = String.valueOf(task.getStartTime());
        if (task instanceof Subtask) {
            Epic epic = ((Subtask) task).getEpic();
            if (epic == null) {
                return id + "," + Type.SUBTASK + "," + name + "," + status + ",Description " + description + "," + duration + "," + startTime + "\n";
            } else {
                return id + "," + Type.SUBTASK + "," + name + "," + status + ",Description " + description + "," + duration + "," + startTime + "," + epic.getId() + "\n";
            }
        }
        if (task instanceof Epic) {
            return id + "," + Type.EPIC + ","  + name + "," + status + ",Description " + description + "," + duration + "," + startTime + "\n";
        } else return id + "," + Type.TASK + ","  + name + "," + status + ",Description " + description + "," + duration + "," + startTime + "\n";
    }

    public Task fromString(String str) { // Метод возвращает задачу и ее параметры из файла
        String[] row = str.split(",");
        int id = Integer.parseInt(row[0]);
        String type = row[1];
        String name = row[2];
        String status = row[3];
        String description = row[4];
        long duration = Long.parseLong(row[5]);
        LocalDateTime startTime = LocalDateTime.parse(row[6]);
        Subtask subtask = null;


        if (Objects.equals(type, String.valueOf(Type.TASK))) {
            return new Task(name, description, id, Status.valueOf(status), duration, startTime);

        } else if (Objects.equals(type, String.valueOf(Type.EPIC))) {
            return new Epic(name, description, id, Status.valueOf(status));

        } else if (Objects.equals(type, String.valueOf(Type.SUBTASK))) {
            subtask = new Subtask(name, description, id, Status.valueOf(status), duration, startTime);
            if (row.length == 7) {
                int idEpic = Integer.parseInt(row[5]);
                subtask.setEpic((Epic) getTaskById(idEpic));
            }
            return subtask;
        } else return null;
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    public void saveHistory(List<Task> history,String file) { // Метод сохраняет историю в файл
        try {
            Files.writeString(Path.of(file),"\n" + InMemoryHistoryManager.toString(history),StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи в файл!");
        }
    }

    public static void main(String[] args) {
        Subtask subtask1 = new Subtask("subtask1","subtask1 example");
        Subtask subtask2 = new Subtask("subtask2", "subtask2 example");
        Subtask subtask3 = new Subtask("subtask3", "subtask3 example");
        Epic epic1 = new Epic("epic1", "epic1 example");
        Epic epic2 = new Epic("epic2", "epic2 example");
        Task task1 = new Task("task1","task1 example");
        subtask1.setEpic(epic1);
        subtask2.setEpic(epic2);
        //subtask3.setEpic(epic1);
        FileBackedTasksManager ff = new FileBackedTasksManager();
        ff.addTask(task1);
        ff.addTask(epic2);
        ff.addTask(epic1);
        ff.addTask(subtask3);
        ff.addTask(subtask2);
        ff.addTask(subtask1);
        ff.getTaskById(task1.getId());
        ff.getTaskById(epic2.getId());
        ff.saveHistory(ff.getHistory(),FILE);

        // FileBackedTasksManager fb = FileBackedTasksManager.loadFromFile(FILE);
       // System.out.println(fb.getHistory());
    }
}
