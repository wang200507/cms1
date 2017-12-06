package com.song.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author wangzy
 * @date 2017-11-21
 */
public class JsonUtil {

    public JsonUtil() {
    }

    public static String object2Json(Object info) {
        return JSON.toJSONString(info, true);
    }

    public static <T> T json2Object(String data, Class<T> clz) {
        return JSON.parseObject(data, clz);
    }

    public static <T> List<T> json2List(String jsonArrayStr, Class<T> clazz) {
        return JSON.parseArray(jsonArrayStr, clazz);
    }

    public static <T> Map<String, Object> json2Map(String jsonStr) {
        return (Map)json2Object(jsonStr, Map.class);
    }

    public static <T> Map<String, T> json2Map(String jsonStr, Class<T> clazz) {
        Map<String, T> map = JSON.parseObject(jsonStr, new TypeReference<Map<String, T>>() {
        }, new Feature[0]);
        Iterator i$ = map.entrySet().iterator();

        while(i$.hasNext()) {
            Map.Entry<String, T> entry = (Map.Entry)i$.next();
            JSONObject obj = (JSONObject)entry.getValue();
            map.put(entry.getKey(), JSONObject.toJavaObject(obj, clazz));
        }

        return map;
    }


}
