package network;

import com.sun.net.httpserver.HttpServer;
import manager.HttpTaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8091;
    private static String httpPath = "http://localhost:";
    HttpServer httpServer;

    public static String getHttpPath() {
        return httpPath;
    }


    public void start() throws IOException {
        httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);
        // эндпоинт для всех задач, подзадач
        httpServer.createContext("/tasks", new TaskHandler());
        // эндпоинт для всех задач
        httpServer.createContext("/tasks/task", new TaskHandler());
        // эндпоинт для всех эпиков
        httpServer.createContext("/tasks/epic", new TaskHandler());
        // эндпоинт для всех подзадач
        httpServer.createContext("/tasks/subtask", new TaskHandler());
        // эндпоинт для истории задач
        httpServer.createContext("/tasks/history", new TaskHandler());
        // эндпоинт для удаления всех задач
        httpServer.createContext("/tasks/task/delete", new TaskHandler());
        // эндпоинт для удаления по id
        httpServer.createContext("/tasks/task/?d=", new TaskHandler());
        // эндпоинт для создания/изменения задачи
        httpServer.createContext("/tasks/task", new TaskHandler());
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(0);
    }
}

