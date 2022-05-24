package manager;

import manager.InMemoryTasksManager;

import java.io.IOException;

public class Managers {
    public static Manager getDefault(String path) throws IOException, InterruptedException {
        return new HttpTaskManager(path);
    }

    public static HistoryManager getDefaultHistory(){return new InMemoryHistoryManager();}
}
