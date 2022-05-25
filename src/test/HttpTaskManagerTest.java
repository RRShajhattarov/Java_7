package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import manager.HttpTaskManager;
import manager.InMemoryTasksManager;
import network.HttpTaskServer;
import network.LocalDateAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;

import static manager.HttpTaskManager.*;
import java.io.IOException;

import java.time.LocalDateTime;
import java.util.HashMap;


import static org.junit.jupiter.api.Assertions.*;

class HttpTaskManagerTest extends InMemoryTasksManager {
    static String httpPath = "http://localhost:";
    HttpTaskServer httpTaskServer;
    Gson gson;
    Task task;
    HashMap<Integer, Task> allTasks;
    String json = "{" +
            "  \"type\": \"TASK\",\n" +
            "  \"name\": \"name1\",\n" +
            "  \"description\": \"task1\",\n" +
            "  \"taskId\": 234,\n" +
            "  \"status\": \"NEW\",\n" +
            "  \"duration\": 1200,\n" +
            "  \"startTime\": \"25.05.22 12:20\"\n" +
            "}";

    @BeforeEach
    void newGson() throws IOException, InterruptedException {
        allTasks = new HashMap<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .serializeNulls();
        gson = gsonBuilder.create();

        new HttpTaskManager(httpPath);

        httpTaskServer = new HttpTaskServer();
        httpTaskServer.start();
        task = gson.fromJson(json, Task.class);
        allTasks.put(task.getId(),task);
        save("tasks", gson.toJson(tasks));

        load(httpPath);
    }

    @AfterEach
    void stopServers() {
        httpTaskServer.stop();
        kvClient.KVStop();
    }

    @Test
    void loadShouldRestoreTasks()  {
        assertFalse(allTasks.isEmpty());
    }

    @Test
    void shouldSaveTask() {
        assertTrue(allTasks.containsValue(task));
    }

}