package com.csx.test.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Singleton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Singleton
public class JsonUtils {

    /**
     * -----get the List of values for a given key from JsonObject  ---------
     */
    public static List<Object> parseObject(JSONObject json, String key) {
        List<Object> jsonValues = new ArrayList<>();
        getKey(json, key, jsonValues);
        return jsonValues;
    }

    /**
     * -----Create a JsonObject and write to File from given Map  ---------
     * FileName should contain path including file extention
     */
    public String CreateJsonFromMap(Map<Object,Object> data,String fileName) throws IOException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonfile = new File(fileName);
        String jsonArrayAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        objectMapper.writeValue(jsonfile, data);
        return jsonArrayAsString;
    }

    /**
     * -----Read the Json Data from a file  ---------
     * FileName should contain path including file extention
     */
    public Map<String, String> readJsonFromFile(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonfile = new File(fileName);
        Map<String, String> dataRead = objectMapper.readValue(jsonfile, new TypeReference<Map<String, String>>() {
        });
        return dataRead;
    }

    /**
     * -----Read the Json Data from a JsonString  ---------
     *
     */
    public Map<String, Object> readJsonFromFromString(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> dataRead = objectMapper.readValue(jsonString,new TypeReference<Map<String, Object>>() {
        });
        return dataRead;
    }


    public static void getKey(JSONObject json, String key, List<Object> values) throws JSONException {
        boolean exists = json.has(key);
        Iterator<?> keys;
        String nextKeys;
        if (!exists) {
            keys = json.keys();
            while (keys.hasNext()) {
                nextKeys = (String) keys.next();
                try {
                    if (json.get(nextKeys) instanceof JSONObject) {
                        if (exists == false) {
                            getKey(json.getJSONObject(nextKeys), key, values);
                        }
                    } else if (json.get(nextKeys) instanceof JSONArray) {
                        JSONArray jsonarray = json.getJSONArray(nextKeys);
                        for (int i = 0; i < jsonarray.length(); i++) {
                            String jsonarrayString = jsonarray.get(i).toString();
                            JSONObject innerJSOn = new JSONObject(jsonarrayString);
                            if (exists == false) {
                                getKey(innerJSOn, key, values);
                            }
                        }
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        } else {
            values.add(json.get(key));
        }
    }
}
