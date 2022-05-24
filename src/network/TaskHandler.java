package network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.HttpTaskManager;
import tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TaskHandler extends HttpTaskManager implements HttpHandler {
    private String response = "Не найдено значений";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        GsonBuilder gsonB = new GsonBuilder();
        gsonB.setPrettyPrinting()
                .serializeNulls();
        Gson gson = gsonB.create();

        String method = httpExchange.getRequestMethod();
        String requestURI = httpExchange.getRequestURI().toString();


        switch (method) {
            case "GET": {
                getMethod(requestURI, gson);
            }
            break;
            case "POST": {
                postMethod(httpExchange, gson);
            }
            break;
            case "DELETE": {
                deleteMethod(requestURI, gson, httpExchange);
            }
            break;
            default: {

            }
            break;
        }

        httpExchange.sendResponseHeaders(200, 0);

        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void getMethod(String requestURI, Gson gson)  {
        switch (requestURI) {
            case "/tasks": {
                response = gson.toJson(getPrioritizedTasks());
                try {
                    save("historyKey", gson.toJson(memoryHistory.getHistory()));
                } catch (IOException | InterruptedException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            }
            break;
            case "/tasks/task": {
                response = gson.toJson(getAllTask());
                try {
                    save("historyKey", gson.toJson(memoryHistory.getHistory()));
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
            break;
            case "/tasks/epic": {
                response = gson.toJson(getAllEpic());
                try {
                    save("historyKey", gson.toJson(memoryHistory.getHistory()));
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
            break;
            case "/tasks/subtask": {
                response = gson.toJson(getAllSubtaskWithoutEpic());
                try {
                    save("historyKey", gson.toJson(memoryHistory.getHistory()));
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
            break;
            case "/tasks/history": {
                response = gson.toJson(getHistory());
                try {
                    save("historyKey", gson.toJson(memoryHistory.getHistory()));
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
            break;
            default: {
                response = "Ошибка запроса";
            }
            break;
        }
    }

    private void postMethod(HttpExchange httpExchange, Gson gson) throws IOException {
        InputStream inputStream = httpExchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), Charset.defaultCharset());
        // получаю тип задачи
        Task t = gson.fromJson(body, Task.class);

                // проверка на наличие задачи с таким id
                // если задача есть, что будет изменена, если задачи нет, то создана новая
                if (!(tasks.isEmpty())) {
                    for (Task task : tasks.values()) {
                        if (task.getId() == t.getId()) {
                            tasks.remove(task.getId());
                            tasks.put(task.getId(), gson.fromJson(body, Task.class));
                            response = "Задача изменена";
                            try {
                                save("taskKey", gson.toJson(tasks));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        } else {
                            tasks.put(t.getId(), gson.fromJson(body, Task.class));
                            response = "Задача создана";
                            try {
                                save("taskKey", gson.toJson(tasks));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                } else {
                    Task task = gson.fromJson(body, Task.class);
                    tasks.put(task.getId(), task);

                    // System.out.println(tasks);
                    response = "Задача создана.";
                    try {
                        save("taskKey", gson.toJson(tasks));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
    }

    private void deleteMethod(String requestURI, Gson gson, HttpExchange httpExchange) throws IOException {
        if ("/tasks/task/delete".equals(requestURI)) {// удаление всех задач
            response = deleteAllTask();
            try {
                save("taskKey", gson.toJson(tasks));
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        } else {// нахожу подстроку с id
            int index = -1;
            try {
                index = Integer.parseInt(requestURI.replace("/tasks/task/?d=", ""));
            } catch (Exception e) {
                response = "Указан неверный адрес";
                httpExchange.sendResponseHeaders(404, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                    return;
                }
            }
            response = deleteTaskById(index);
            try {
                save("taskKey", gson.toJson(tasks));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
