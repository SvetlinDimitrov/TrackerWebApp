// package org.example.config;
//
// import com.google.gson.*;
// import java.lang.reflect.Type;
// import java.time.YearMonth;
// import java.time.format.DateTimeFormatter;
//
// public class YearMonthAdapter implements JsonSerializer<YearMonth>, JsonDeserializer<YearMonth> {
//
//    @Override
//    public JsonElement serialize(YearMonth src, Type typeOfSrc, JsonSerializationContext context)
// {
//        return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM")));
//    }
//
//    @Override
//    public YearMonth deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext
// context) throws JsonParseException {
//        return YearMonth.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM"));
//    }
// }
