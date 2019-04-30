package com.basic.instrumen.sample;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.*;

/**
 * @author Jarvis
 * @date 2018/9/13
 */
public class JsonDemo {

    /**
     * @param jsonResult
     * @return
     * @throws JSONException
     */
    private static Map<String, Object> jsonObjectToMap(JSONObject jsonResult) throws JSONException {

        Map<String, Object> result = new HashMap<>();

        if (jsonResult != null) {

            Iterator<String> keyIt = jsonResult.keys();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Object val = jsonResult.get(key);
                if (val != null) {
                    if (val instanceof JSONObject) {
                        Map<String, Object> valMap = jsonObjectToMap((JSONObject) val);
                        result.put(key, valMap);
                    } else if (val instanceof JSONArray) {
                        JSONArray ja = (JSONArray) val;
                        result.put(key, jsonArrayToList(ja));
                    } else {
                        result.put(key, val);
                    }
                } else {
                    result.put(key, null);
                }
            }
        }
        return result;
    }


    private static List<Object> jsonArrayToList(JSONArray jsonArray) {
        int capacity = jsonArray.length();
        List<Object> objectList = new ArrayList<>(capacity);
        if (jsonArray != null) {
            for (int i = 0; i < capacity; i++) {
                Object val = jsonArray.get(i);
                if (val != null) {
                    if (val instanceof JSONObject) {
                        Map<String, Object> map = jsonObjectToMap((JSONObject) val);
                        objectList.add(map);
                    } else if (val instanceof JSONArray) {
                        objectList.add(val);
                    } else {
                        objectList.add(val);
                    }
                }
            }
        }
        return objectList;
    }

    private static int getType(Class<?> type) {
        if (type != null && (String.class.isAssignableFrom(type) || Character.class.isAssignableFrom(type)
                || Character.TYPE.isAssignableFrom(type) || char.class.isAssignableFrom(type))) {
            return 0;
        }
        if (type != null && (Byte.TYPE.isAssignableFrom(type) || Short.TYPE.isAssignableFrom(type) || Integer.TYPE.isAssignableFrom(type) || Integer.class.isAssignableFrom(type) || Number.class.isAssignableFrom(type) || int.class.isAssignableFrom(type) || byte.class.isAssignableFrom(type) || short.class.isAssignableFrom(type))) {
            return 1;
        }
        if (type != null && (Long.TYPE.isAssignableFrom(type) || long.class.isAssignableFrom(type))) {
            return 2;
        }
        if (type != null && (Float.TYPE.isAssignableFrom(type) || float.class.isAssignableFrom(type))) {
            return 3;
        }
        if (type != null && (Double.TYPE.isAssignableFrom(type) || double.class.isAssignableFrom(type))) {
            return 4;
        }
        if (type != null && (Boolean.TYPE.isAssignableFrom(type) || Boolean.class.isAssignableFrom(type) || boolean.class.isAssignableFrom(type))) {
            return 5;
        }
        if (type != null && type.isArray()) {
            return 6;
        }
        if (type != null && Connection.class.isAssignableFrom(type)) {
            return 7;
        }
        if (type != null && JSONArray.class.isAssignableFrom(type)) {
            return 8;
        }
        if (type != null && List.class.isAssignableFrom(type)) {
            return 9;
        }
        if (type != null && Map.class.isAssignableFrom(type)) {
            return 10;
        }
        return 11;
    }

    private static Object jsonObjectToObject(JSONObject obj, Field field) throws JSONException {
        //field.getType:获取属性声明时类型对象（返回class对象）
        switch (getType(field.getType())) {
            case 0:
                return obj.opt(field.getName());
            case 1:
                return obj.optInt(field.getName());
            case 2:
                return obj.optLong(field.getName());
            case 3:
            case 4:
                return obj.optDouble(field.getName());
            case 5:
                return obj.optBoolean(field.getName());
            case 6:
            case 7:
                //JsonArray型
            case 8:
                return obj.optJSONArray(field.getName());
            case 9:
                return jsonArrayToList(obj.optJSONArray(field.getName()));
            case 10:
                return jsonObjectToMap(obj.optJSONObject(field.getName()));
            default:
                return null;
        }
    }

    /**
     * json 序列化
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     * @throws JSONException
     */
    public static String toJson(Object obj) throws IllegalAccessException, JSONException {
        JSONObject json = new JSONObject();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            switch (getType(field.getType())) {
                case 0:
                    json.put(field.getName(), (field.get(obj) == null ? "" : field.get(obj)));
                    break;
                case 1:
                    json.put(field.getName(), (int) (field.get(obj) == null ? 0 : field.get(obj)));
                    break;
                case 2:
                    json.put(field.getName(), (long) (field.get(obj) == null ? 0 : field.get(obj)));
                    break;
                case 3:
                    json.put(field.getName(), (float) (field.get(obj) == null ? 0 : field.get(obj)));
                    break;
                case 4:
                    json.put(field.getName(), (double) (field.get(obj) == null ? 0 : field.get(obj)));
                    break;
                case 5:
                    json.put(field.getName(), (boolean) (field.get(obj) == null ? false : field.get(obj)));
                    break;
                case 6:
                case 7:
                    //JsonArray型
                case 8:
                    json.put(field.getName(), (field.get(obj)));
                    break;
                case 9:
                    json.put(field.getName(), new JSONArray((List<?>) field.get(obj)));
                    break;
                case 10:
                    json.put(field.getName(), new JSONObject((HashMap<?, ?>) field.get(obj)));
                    break;
                default:
                    return null;
            }
        }
        return json.toString();
    }

    /**
     * json 解析器，反序列化
     *
     * @param jsonStr
     * @param type
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T parseJson(String jsonStr, Class<T> type) throws Exception {
        if (jsonStr == null || jsonStr.equals("")) {
            throw new NullPointerException("JsonString can't be null");
        }
        T data = type.getDeclaredConstructor().newInstance();
        Field[] fields = type.getDeclaredFields();
        JSONObject jsonObject = (JSONObject) new JSONTokener(jsonStr).nextValue();
        for (Field field : fields) {
            field.setAccessible(true);
            field.set(data, jsonObjectToObject(jsonObject, field));
        }
        return data;
    }

}

/**
 * 测试 json序列化
 */
class JsonTest {

    public static void main(String[] args) throws Exception {
        Person person = new Person();
        person.id = 10001;
        person.msg = "JAVABean转化为JSON~";
        person.error = true;

        String jsonStr = JsonDemo.toJson(person);
        System.out.println("json序列化");
        System.out.println(jsonStr);

        System.out.println();
        System.out.println("json反序列化");
        Person obj = JsonDemo.parseJson(jsonStr, Person.class);
        System.out.println("id " + obj.id + " msg: " + obj.msg + " error " + obj.error);
    }
}


/**
 * json对象
 */
class Person {
    int id;
    String msg;
    boolean error;
}