package server;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import manager.InMemoryHistoryManager;
import network.LocalDateAdapter;

import static manager.InMemoryTasksManager.*;

import tasks.*;




/**
 * Постман: https://www.getpostman.com/collections/a83b61d9e1c81c10575c
 */
public class KVServer {
    public static final int PORT = 8078;
    private final String API_KEY;
    private final HttpServer server;
    private final Map<String, String> data = new HashMap<>();

    public KVServer() throws IOException {
        API_KEY = generateApiToken();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/register", this::register);
        server.createContext("/save", this::save);
        server.createContext("/load", this::load);
    }

    private void load(HttpExchange h) throws IOException {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter());

            Gson gson = gsonBuilder.create();

            try {
                System.out.println("\n/load");
                if (!hasAuth(h)) {
                    System.out.println("Запрос неавторизован, нужен параметр в query API_KEY со значением апи-ключа");
                    h.sendResponseHeaders(403, 0);
                    return;
                }
                switch (h.getRequestMethod()) {
                    case "GET":
                        // ловлю ошибку на неверный формат полей json
                        try {
                            tasks = gson.fromJson(data.get("tasks"), new TypeToken<HashMap<Integer,Task>>() {
                            }.getType());
                            // восстанавливаю историю через отдельный массив
                            List<Task> forHistoryRecover = gson.fromJson(data.get("historyKey"), new TypeToken<InMemoryHistoryManager>() {
                            }.getType());
                            memoryHistory.clear(new InMemoryHistoryManager());
                            for (Task task : forHistoryRecover) {
                                memoryHistory.add(task);
                            }

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        h.sendResponseHeaders(200, 0);
                        System.out.println("Задачи восстановлены.");
                        break;
                    default:
                        System.out.println("/load ждёт GET-запрос, а получил: " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                        break;
                }
            } finally {
                h.close();
            }

    }

    private void save(HttpExchange h) throws IOException {
        try {
            System.out.println("\n/save");
            if (!hasAuth(h)) {
                System.out.println("Запрос неавторизован, нужен параметр в query API_TOKEN со значением апи-ключа");
                h.sendResponseHeaders(403, 0);
                return;
            }
            if ("POST".equals(h.getRequestMethod())) {
                String key = h.getRequestURI().getPath().substring("/save/".length());
                if (key.isEmpty()) {
                    System.out.println("Key для сохранения пустой. key указывается в пути: /save/{key}");
                    h.sendResponseHeaders(400, 0);
                    return;
                }
                String value = readText(h);
                if (value.isEmpty()) {
                    System.out.println("Value для сохранения пустой. value указывается в теле запроса");
                    h.sendResponseHeaders(400, 0);
                    return;
                }
                data.put(key, value);
                System.out.println("Значение для ключа " + key + " успешно обновлено!");
                h.sendResponseHeaders(200, 0);
            } else {
                System.out.println("/save ждёт POST-запрос, а получил: " + h.getRequestMethod());
                h.sendResponseHeaders(405, 0);
            }
        } finally {
            h.close();
        }
    }

    private void register(HttpExchange h) throws IOException {
        try {
            System.out.println("\n/register");
            if ("GET".equals(h.getRequestMethod())) {
                sendText(h, API_KEY);
            } else {
                System.out.println("/register ждёт GET-запрос, а получил " + h.getRequestMethod());
                h.sendResponseHeaders(405, 0);
            }
        } finally {
            h.close();
        }
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        System.out.println("API_KEY: " + API_KEY);
        server.start();
    }

    private String generateApiToken() {
        return "" + System.currentTimeMillis();
    }

    protected boolean hasAuth(HttpExchange h) {
        String rawQuery = h.getRequestURI().toString();
        return rawQuery != null && (rawQuery.contains("API_KEY=" + API_KEY) || rawQuery.contains("API_KEY=DEBUG"));
    }

    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }

    public void stop() {
        server.stop(0);
    }
}