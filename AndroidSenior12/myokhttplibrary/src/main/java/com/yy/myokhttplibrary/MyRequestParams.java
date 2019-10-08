package com.yy.myokhttplibrary;

import java.util.concurrent.ConcurrentHashMap;

public class MyRequestParams {

    ConcurrentHashMap<String,String> params = new ConcurrentHashMap<>();

    public void put(String key, String value){
        if (key != null && value != null){
            params.put(key, value);
        }
    }

}
