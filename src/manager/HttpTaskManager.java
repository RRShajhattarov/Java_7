package manager;

import server.KVClient;
import util.ManagerSaveException;

import java.io.IOException;

public class HttpTaskManager extends FileBackedTasksManager{

    private static final int PORT = 8080;
    public static KVClient kvClient;

    public HttpTaskManager(String http) throws IOException, InterruptedException {
        kvClient = new KVClient(http);
        load(http);
    }
    public HttpTaskManager() {

    }

    public void save(String key, String json) throws IOException, InterruptedException {
        kvClient.post(key, json);
    }

    public static void load(String http) throws ManagerSaveException {
        try {
            kvClient.load(http);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
