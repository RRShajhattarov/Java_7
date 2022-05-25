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
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskManagerTest extends InMemoryTasksManager {
    static String httpPath = "http://localhost:";
    HttpTaskServer httpTaskServer;
    Gson gson;
    Task task;
    String json = "{\"label\": \"Задача 1\",\"description\": \"Описание задачи 1\",\"status\": " +
            "\"NEW\",\"type\": \"TASK\",\"taskId\": 1,\"duration\": \"24\",\"startTime\": \"01.12.21\"}";

    @BeforeEach
    void newGson() throws IOException, InterruptedException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .serializeNulls();
        gson = gsonBuilder.create();

        new HttpTaskManager(httpPath);

        httpTaskServer = new HttpTaskServer();
        httpTaskServer.start();

        task = gson.fromJson(json, Task.class);
        tasks.put(task.getId(),task);
        save("tasks", gson.toJson(tasks));

        tasks.clear();

        load(httpPath);
    }

    @AfterEach
    void stopServers() {
        httpTaskServer.stop();
        kvClient.KVStop();
    }

    @Test
    void loadShouldRestoreTasks()  {
        assertFalse(tasks.isEmpty());
    }

    @Test
    void shouldSaveTask() {
        assertTrue(tasks.containsValue(task));
    }

}