package com.dem.es.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {
    public static String obj2Json(Object obj){
        Gson gson = new GsonBuilder().create();
        return gson.toJson(obj);
    }
}
