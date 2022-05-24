package server;

import manager.HttpTaskManager;
import org.apiguardian.api.API;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

public class KVClient {
    private static final int PORT = 8078;
    KVServer kvServer = new KVServer();
    private final String API_KEY;
    private URI uri;
    private static HttpRequest httpRequest;

    public KVClient(String url) throws IOException, InterruptedException {
        kvServer.start();
        uri = URI.create(url + PORT + "/register");

        httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpResponse<String> response = client.send(httpRequest, handler);
        API_KEY = response.body();
    }

    public void post(String key, String json) throws IOException, InterruptedException {
        URI uriPost = URI.create(uri + "/save/" + key + "API_KEY=" + API_KEY);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(uriPost)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpResponse<String> response = client.send(httpRequest, handler);
    }

    public void load(String path) throws IOException, InterruptedException {
        URI uriLoad = URI.create(path + PORT + "/load/" + "API_KEY=" + API_KEY);

        httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(uriLoad)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpResponse<String> response = client.send(httpRequest, handler);

    }

    public void KVStop() {
        kvServer.stop();
    }

    public void start() {
        kvServer.start();
    }

}
