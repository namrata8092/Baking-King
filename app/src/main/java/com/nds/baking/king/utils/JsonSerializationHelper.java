package com.nds.baking.king.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.nds.baking.king.models.RecipeResponseModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    public static RecipeResponseModel deserializeObject(String json) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JsonReader reader = new JsonReader(new StringReader(json));
            reader.setLenient(true);
            return gson.fromJson(json, RecipeResponseModel.class);
        } catch (JsonParseException jsonParseException) {
            return null;
        }
    }

    public static String readFakeResponseFromAssets(Context context, int resourceId) throws IOException {
        InputStream stream = context.getResources().openRawResource(resourceId);
        InputStreamReader streamReader = new InputStreamReader(stream, "UTF-8");
        String jsonData = streamReader.toString();
        streamReader.close();
        stream.close();
        return jsonData;
    }

    public static String readFakeResponseFromRaw(Context context, int resourceId) {
        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                try {
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return result.toString();
                } finally {
                    reader.close();
                }
            } finally {
                inputStream.close();
            }
        } catch (IOException e) {
            return null;
        }
    }

}
