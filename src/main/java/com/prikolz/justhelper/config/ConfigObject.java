package com.prikolz.justhelper.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.prikolz.justhelper.Config;
import com.prikolz.justhelper.util.Resolver;
import com.prikolz.justhelper.util.TextUtils;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class ConfigObject {
    public final List<Config.Parameter<?, ?>> parameters = new ArrayList<>();

    public <T extends ConfigObject> ObjectParameter<T> objectParameter(String key, Resolver<T> def) {
        return new ObjectParameter<>(
                def.resolve(),
                key,
                parameters,
                (value, logger) -> {
                    var result = new JsonObject();
                    value.write(result, logger);
                    return result;
                },
                (child, logger) -> {
                    var result = def.resolve();
                    result.read(child, logger);
                    return result;
                }
        );
    }

    public IntParameter colorParameter(String name, int defaultValue) {
        return new IntParameter(
                defaultValue,
                name,
                parameters,
                (value, logger) -> {
                    var named = NamedTextColor.namedColor(value);
                    if (named == null) return new JsonPrimitive("#" + Integer.toHexString(value));
                    return new JsonPrimitive(named.toString());
                },
                (json, logger) -> {
                    var string = json.getAsString();
                    var enumColor = TextUtils.ENamedTextColor.of(string);
                    if (enumColor == null) return TextUtils.parseHexColor(string);
                    return enumColor.value;
                }
        );
    }

    public BooleanParameter boolParameter(String name, boolean defaultValue) {
        return new BooleanParameter(
                defaultValue,
                name,
                parameters,
                (value, logger) -> new JsonPrimitive(value),
                (json, logger) -> json.getAsBoolean()
        );
    }

    public LongParameter longParameter(String name, long defaultValue, long min, long max) {
        return new LongParameter(
                defaultValue,
                name,
                parameters,
                (value, logger) -> new JsonPrimitive(value),
                (json, logger) -> Math.min(max, Math.max(min, json.getAsLong()))
        );
    }

    public IntParameter intParameter(String name, int defaultValue, int min, int max) {
        return new IntParameter(
                defaultValue,
                name,
                parameters,
                (value, logger) -> new JsonPrimitive(value),
                (json, logger) -> Math.min(max, Math.max(min, json.getAsInt()))
        );
    }

    public StringParameter stringParameter(String name, String def) {
        return new StringParameter(def, name, parameters, (value, logger) -> new JsonPrimitive(value), (child, logger) -> child.getAsString());
    }

    public JsonParameter jsonParameter(String name, JsonObject def) {
        return new JsonParameter(def, name, parameters, (value, logger) -> value, (json, logger) -> json);
    }

    public final <T, D extends JsonElement> Config.Parameter<T, D> parameter(
            String key,
            T def,
            Config.Parameter.JsonResolver<T, D> jsonResolver,
            Config.Parameter.ParameterResolver<T, D> parameterResolver
    ) {
        return new Config.Parameter<>(def, key, parameters, jsonResolver, parameterResolver);
    }

    public void write(JsonObject json, Config.ConfigLogger logger) {
        for (var parameter : parameters) parameter.write(json, logger);
    }

    public void read(JsonObject json, Config.ConfigLogger logger) {
        for (var parameter : parameters) parameter.read(json, logger);
    }

    public static class StringParameter extends Config.Parameter<String, JsonPrimitive> {
        public StringParameter(String defaultValue, String jsonKey, List<Config.Parameter<?, ?>> parameters, JsonResolver<String, JsonPrimitive> jsonResolver, ParameterResolver<String, JsonPrimitive> resolver) {
            super(defaultValue, jsonKey, parameters, jsonResolver, resolver);
        }
    }

    public static class BooleanParameter extends Config.Parameter<Boolean, JsonPrimitive> {
        public BooleanParameter(Boolean defaultValue, String jsonKey, List<Config.Parameter<?, ?>> parameters, JsonResolver<Boolean, JsonPrimitive> jsonResolver, ParameterResolver<Boolean, JsonPrimitive> resolver) {
            super(defaultValue, jsonKey, parameters, jsonResolver, resolver);
        }
    }

    public static class IntParameter extends Config.Parameter<Integer, JsonPrimitive> {
        public IntParameter(Integer defaultValue, String jsonKey, List<Config.Parameter<?, ?>> parameters, JsonResolver<Integer, JsonPrimitive> jsonResolver, ParameterResolver<Integer, JsonPrimitive> resolver) {
            super(defaultValue, jsonKey, parameters, jsonResolver, resolver);
        }
    }

    public static class LongParameter extends Config.Parameter<Long, JsonPrimitive> {
        public LongParameter(Long defaultValue, String jsonKey, List<Config.Parameter<?, ?>> parameters, JsonResolver<Long, JsonPrimitive> jsonResolver, ParameterResolver<Long, JsonPrimitive> resolver) {
            super(defaultValue, jsonKey, parameters, jsonResolver, resolver);
        }
    }

    public static class ObjectParameter<T extends ConfigObject> extends Config.Parameter<T, JsonObject> {
        public ObjectParameter(T defaultValue, String jsonKey, List<Config.Parameter<?, ?>> parameters, JsonResolver<T, JsonObject> jsonResolver, ParameterResolver<T, JsonObject> resolver) {
            super(defaultValue, jsonKey, parameters, jsonResolver, resolver);
        }
    }

    public static class JsonParameter extends Config.Parameter<JsonObject, JsonObject> {
        public JsonParameter(JsonObject defaultValue, String jsonKey, List<Config.Parameter<?, ?>> parameters, JsonResolver<JsonObject, JsonObject> jsonResolver, ParameterResolver<JsonObject, JsonObject> resolver) {
            super(defaultValue, jsonKey, parameters, jsonResolver, resolver);
        }
    }
}
