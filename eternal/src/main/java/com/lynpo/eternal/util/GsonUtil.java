package com.lynpo.eternal.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GsonUtil {

    private static Gson GSON = null;

    private static final Gson GSON_NO_NULLS = createGson(false);

    static {
        if (GSON == null) {
            GSON = new GsonBuilder().create();
        }
    }

    private GsonUtil() {
    }

    /**
     * 将对象转化成 Json
     */
    public static String objectToJson(Object ts) {
        String jsonStr = null;
        if (GSON != null) {
            jsonStr = GSON.toJson(ts);
        }
        return jsonStr;
    }

    /**
     * 将 Json 转化为对象
     */
    public static <T> T getObjectJSON(String jsonString, Class<T> cls) {
        T t = null;
        try {
            t = GSON.fromJson(jsonString, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 将 Json 转化为map
     */
    public static Map<String, String> getObjectJSON(String jsonString) {
        Map<String, String> t = new HashMap<>();
        try {
            t = GSON.fromJson(jsonString, new TypeToken<Map<String, String>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * Serializes an empty object into json.
     *
     * @return an empty object serialized into json.
     */
    public static String emptyJson() {
        return toJson(new JsonObject(), true);
    }

    /**
     * Serializes an object into json.
     *
     * @param object The object to serialize.
     * @return object serialized into json.
     */
    public static String toJson(final Object object) {
        return toJson(object, true);
    }

    /**
     * Serializes an object into json.
     *
     * @param object       The object to serialize.
     * @param includeNulls Determines if nulls will be included.
     * @return object serialized into json.
     */
    public static String toJson(final Object object, final boolean includeNulls) {
        return includeNulls ? GSON.toJson(object) : GSON_NO_NULLS.toJson(object);
    }

    /**
     * Serializes an object into json.
     *
     * @param src       The object to serialize.
     * @param typeOfSrc The specific genericized type of src.
     * @return object serialized into json.
     */
    public static String toJson(final Object src, final Type typeOfSrc) {
        return toJson(src, typeOfSrc, true);
    }

    /**
     * Serializes an object into json.
     *
     * @param src          The object to serialize.
     * @param typeOfSrc    The specific genericized type of src.
     * @param includeNulls Determines if nulls will be included.
     * @return object serialized into json.
     */
    public static String toJson(final Object src, final Type typeOfSrc, final boolean includeNulls) {
        return includeNulls ? GSON.toJson(src, typeOfSrc) : GSON_NO_NULLS.toJson(src, typeOfSrc);
    }

    /**
     * This method serializes the specified object into its equivalent representation as a tree of
     * {@link JsonElement}s
     *
     * @param src          The object to serialize.
     * @return object serialized into json.
     */
    public static JsonElement toJsonTree(final Object src) {
        return GSON.toJsonTree(src);
    }

    /**
     * Converts {@link String} to given type.
     *
     * @param json The json to convert.
     * @param type Type json will be converted to.
     * @return instance of type
     */
    public static <T> T fromJson(final String json, final Class<T> type) {
        try {
            return GSON.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    /**
     * Converts {@link String} to given type.
     *
     * @param json the json to convert.
     * @param type type type json will be converted to.
     * @return instance of type
     */
    public static <T> T fromJson(final String json, final Type type) {
        try {
            return GSON.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    /**
     * Converts {@link Reader} to given type.
     *
     * @param reader the reader to convert.
     * @param type   type type json will be converted to.
     * @return instance of type
     */
    public static <T> T fromJson(final Reader reader, final Class<T> type) {
        try {
            return GSON.fromJson(reader, type);
        } catch (JsonSyntaxException | JsonIOException e) {
            return null;
        }
    }

    /**
     * Converts {@link Reader} to given type.
     *
     * @param reader the reader to convert.
     * @param type   type type json will be converted to.
     * @return instance of type
     */
    public static <T> T fromJson(final Reader reader, final Type type) {
        try {
            return GSON.fromJson(reader, type);
        } catch (JsonIOException | JsonSyntaxException e) {
            return null;
        }
    }

    /**
     * Return the type of {@link List} with the {@code type}.
     *
     * @param type The type.
     * @return the type of {@link List} with the {@code type}
     */
    public static Type getListType(final Type type) {
        return TypeToken.getParameterized(List.class, type).getType();
    }

    /**
     * Return the type of {@link Set} with the {@code type}.
     *
     * @param type The type.
     * @return the type of {@link Set} with the {@code type}
     */
    public static Type getSetType(final Type type) {
        return TypeToken.getParameterized(Set.class, type).getType();
    }

    /**
     * Return the type of map with the {@code keyType} and {@code valueType}.
     *
     * @param keyType   The type of key.
     * @param valueType The type of value.
     * @return the type of map with the {@code keyType} and {@code valueType}
     */
    public static Type getMapType(final Type keyType, final Type valueType) {
        return TypeToken.getParameterized(Map.class, keyType, valueType).getType();
    }

    /**
     * Return the type of array with the {@code type}.
     *
     * @param type The type.
     * @return the type of map with the {@code type}
     */
    public static Type getArrayType(final Type type) {
        return TypeToken.getArray(type).getType();
    }

    /**
     * Return the type of {@code rawType} with the {@code typeArguments}.
     *
     * @param rawType       The raw type.
     * @param typeArguments The type of arguments.
     * @return the type of map with the {@code type}
     */
    public static Type getType(final Type rawType, final Type... typeArguments) {
        return TypeToken.getParameterized(rawType, typeArguments).getType();
    }

    /**
     * Create a pre-configured {@link Gson} instance.
     *
     * @param serializeNulls determines if nulls will be serialized.
     * @return {@link Gson} instance.
     */
    private static Gson createGson(final boolean serializeNulls) {
        final GsonBuilder builder = new GsonBuilder();
//        builder.registerTypeAdapter(String.class, new StringTypeAdapter());
        if (serializeNulls) builder.serializeNulls();
        return builder.create();
    }

    public static int getInt(final JsonObject jsonObject, final String key) {
        return getInt(jsonObject, key, 0);
    }

    public static int getInt(final JsonObject jsonObject, final String key, int defaultValue) {
        try {
            if (jsonObject != null && jsonObject.has(key) &&
                    !jsonObject.get(key).isJsonNull() &&
                    jsonObject.get(key).isJsonPrimitive()) {
                return jsonObject.get(key).getAsInt();
            }
        } catch (Exception e) {
            return defaultValue;
        }

        return defaultValue;
    }

    public static boolean getBoolean(final JsonObject jsonObject, final String key) {
        return getBoolean(jsonObject, key, false);
    }

    public static boolean getBoolean(final JsonObject jsonObject, final String key, boolean defaultValue) {
        try {
            if (jsonObject != null && jsonObject.has(key) &&
                    !jsonObject.get(key).isJsonNull() &&
                    jsonObject.get(key).isJsonPrimitive()) {
                return jsonObject.get(key).getAsBoolean();
            }
        } catch (Exception e) {
            return defaultValue;
        }

        return defaultValue;
    }

    public static String getString(final JsonObject jsonObject, final String key) {
        return getString(jsonObject, key, "");
    }

    public static String getString(final JsonObject jsonObject, final String key, String defaultValue) {
        try {
            if (jsonObject != null && jsonObject.has(key) &&
                    !jsonObject.get(key).isJsonNull() &&
                    jsonObject.get(key).isJsonPrimitive()) {
                return jsonObject.get(key).getAsString();
            }
        } catch (Exception e) {
            return defaultValue;
        }

        return defaultValue;
    }

    public static JsonObject getAsJsonObject(final JsonElement jsonElement) {
        try {
            if (jsonElement != null) {
                return jsonElement.getAsJsonObject();
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public static JsonObject getJsonObject(final JsonObject jsonObject, final String key) {
        return getJsonObject(jsonObject, key, new JsonObject());
    }

    public static JsonObject getJsonObject(final JsonObject jsonObject, final String key, JsonObject defValue) {
        try {
            if (jsonObject != null && jsonObject.has(key) &&
                    !jsonObject.get(key).isJsonNull() &&
                    jsonObject.get(key).isJsonObject()) {
                return jsonObject.get(key).getAsJsonObject();
            }
        } catch (Exception e) {
            return defValue;
        }

        return defValue;
    }

    public static JsonArray getJsonArray(final JsonObject jsonObject,
                                         final String key,
                                         final JsonArray defaultValue) {
        try {
            if (jsonObject != null && jsonObject.has(key) &&
                    !jsonObject.get(key).isJsonNull() &&
                    jsonObject.get(key).isJsonArray()) {
                return jsonObject.getAsJsonArray(key);
            }
        } catch (Exception e) {
            return defaultValue;
        }
        return defaultValue;
    }

    public static <T> ArrayList<T> getJsonArray(final JsonObject jsonObject, final String key, Type type) {
        JsonArray jsonArray = getJsonArray(jsonObject, key, new JsonArray(0));
        if (jsonArray != null && jsonArray.size() > 0) {
            try {
                return GSON.fromJson(jsonArray, getListType(type));
            } catch (JsonSyntaxException e) {
                return new ArrayList<>(0);
            }
        } else {
            return new ArrayList<>(0);
        }
    }

    public static <T> ArrayList<T> toList(final String json, Type type) {
        if (json != null) {
            try {
                return GSON.fromJson(json, getListType(type));
            } catch (JsonSyntaxException e) {
                return new ArrayList<>(0);
            }
        } else {
            return new ArrayList<>(0);
        }
    }

    public static int getInt(final String jsonString, final String key) {
        JsonObject jsonObject = fromJson(jsonString, JsonObject.class);
        return getInt(jsonObject, key, 0);
    }

    public static int getInt(final String jsonString, final String key, int defaultValue) {
        try {
            JsonObject jsonObject = fromJson(jsonString, JsonObject.class);
            if (jsonObject != null && jsonObject.has(key) &&
                    !jsonObject.get(key).isJsonNull() &&
                    jsonObject.get(key).isJsonPrimitive()) {
                return jsonObject.get(key).getAsInt();
            }
        } catch (Exception e) {
            return defaultValue;
        }

        return defaultValue;
    }

    public static boolean getBoolean(final String jsonString, final String key) {
        JsonObject jsonObject = fromJson(jsonString, JsonObject.class);
        return getBoolean(jsonObject, key, false);
    }

    public static boolean getBoolean(final String jsonString, final String key, boolean defaultValue) {
        try {
            JsonObject jsonObject = fromJson(jsonString, JsonObject.class);
            if (jsonObject != null && jsonObject.has(key) &&
                    !jsonObject.get(key).isJsonNull() &&
                    jsonObject.get(key).isJsonPrimitive()) {
                return jsonObject.get(key).getAsBoolean();
            }
        } catch (Exception e) {
            return defaultValue;
        }

        return defaultValue;
    }

    public static String getString(final String jsonString, final String key) {
        JsonObject jsonObject = fromJson(jsonString, JsonObject.class);
        return getString(jsonObject, key, "");
    }

    public static String getString(final String jsonString, final String key, String defaultValue) {
        try {
            JsonObject jsonObject = fromJson(jsonString, JsonObject.class);
            if (jsonObject != null && jsonObject.has(key) &&
                    !jsonObject.get(key).isJsonNull() &&
                    jsonObject.get(key).isJsonPrimitive()) {
                return jsonObject.get(key).getAsString();
            }
        } catch (Exception e) {
            return defaultValue;
        }

        return defaultValue;
    }

    public static JsonObject getJsonObject(final String jsonString, final String key) {
        JsonObject jsonObject = fromJson(jsonString, JsonObject.class);
        return getJsonObject(jsonObject, key, new JsonObject());
    }

    private static JsonObject getJsonObject(final String jsonString, final String key, JsonObject defValue) {
        try {
            JsonObject jsonObject = fromJson(jsonString, JsonObject.class);
            if (jsonObject != null && jsonObject.has(key) &&
                    !jsonObject.get(key).isJsonNull() &&
                    jsonObject.get(key).isJsonObject()) {
                return jsonObject.get(key).getAsJsonObject();
            }
        } catch (Exception e) {
            return defValue;
        }

        return defValue;
    }

    public static <T> ArrayList<T> getJsonArray(final String jsonString, final String key, Type type) {
        JsonObject jsonObject = fromJson(jsonString, JsonObject.class);
        JsonArray jsonArray = getJsonArray(jsonObject, key, new JsonArray(0));
        if (jsonArray != null && jsonArray.size() > 0) {
            try {
                return GSON.fromJson(jsonArray, getListType(type));
            } catch (JsonSyntaxException e) {
                return new ArrayList<>(0);
            }
        } else {
            return new ArrayList<>(0);
        }
    }
}
