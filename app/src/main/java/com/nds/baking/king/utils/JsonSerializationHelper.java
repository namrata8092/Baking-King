package com.nds.baking.king.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;

/**
 * Created by Namrata Shah on 5/9/2017.
 */

public final class JsonSerializationHelper {

    private JsonSerializationHelper() {
    }

    public static <T> T deserializeObject(Class<T> objectClass, String json) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JsonReader reader = new JsonReader(new StringReader(json));
            reader.setLenient(true);
            return gson.fromJson(json, objectClass);
        } catch (JsonParseException jsonParseException) {
            return null;
        }
    }
}
