package manager;

import manager.InMemoryTasksManager;

public class Managers {
    public static Manager getDefault(){
        return new InMemoryTasksManager();
    }

    public static HistoryManager getDefaultHistory(){return new InMemoryHistoryManager();}
}
