package utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Map;

public class JsonUtils {
    public static JSONObject readJsonFromFile(String filename) {
        FileReader reader;
        try {
            reader = new FileReader(filename);
            JSONParser jsonParser = new JSONParser();
            return (JSONObject) jsonParser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSpecificValuesFromJson(String json, String parameterToGetValueFrom) {
        Map<String, Object> jsonData = getJsonDataInFormat(json);

        return String.valueOf(jsonData.get(parameterToGetValueFrom));
    }

    private static Map<String, Object> getJsonDataInFormat(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {
        }
                .getType();
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        try {
            return gson.fromJson(reader, type);
        } catch (JsonSyntaxException e) {
            throw new AssertionError(e.getMessage() + "\n" + json, e);
        }
    }
}
