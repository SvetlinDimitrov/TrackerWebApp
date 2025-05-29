package org.example.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GsonWrapper {

  Gson gson =
      new GsonBuilder()
          .registerTypeAdapter(
              LocalDate.class,
              (JsonDeserializer<LocalDate>)
                  (json, typeOfT, context) ->
                      LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE))
          .registerTypeAdapter(
              LocalDate.class,
              (JsonSerializer<LocalDate>)
                  (src, typeOfSrc, context) ->
                      new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE)))
          .create();

  public String toJson(Object src) {
    return gson.toJson(src);
  }

  public <T> T fromJson(String json, Class<T> classOfT) {
    return gson.fromJson(json, classOfT);
  }
}
