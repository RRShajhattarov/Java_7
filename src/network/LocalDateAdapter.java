package network;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends TypeAdapter<LocalDateTime> {

    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        if (localDateTime == null) {
            jsonWriter.value("null");
            return;
        }
        jsonWriter.value(localDateTime.format(formatter));
    }

    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        String s = jsonReader.nextString();
        return LocalDateTime.parse(s, formatter);
    }
}
