package com.prikolz.justhelper.config;

import com.google.gson.JsonPrimitive;
import com.prikolz.justhelper.Config;
import com.prikolz.justhelper.dev.values.Text;
import com.prikolz.justhelper.dev.values.Variable;
import net.kyori.adventure.text.format.NamedTextColor;

public class ValueDecorationParameters extends ConfigObject {

    public Config.Parameter<Boolean, JsonPrimitive> enabled = boolParameter("enabled", true);

    public ObjectParameter<VariableDecorationsParameter> variable = objectParameter(
            "variable",
            VariableDecorationsParameter::new
    );

    public ObjectParameter<TextDecorationsParameter> text = objectParameter(
            "text",
            TextDecorationsParameter::new
    );

    public ObjectParameter<NumberDecorationsParameter> number = objectParameter(
            "number",
            NumberDecorationsParameter::new
    );

    public static class VariableDecorationsParameter extends ConfigObject {
        public int getColor(Variable.Scope scope) {
            switch (scope) {
                case GAME -> {
                    return globalColor.value;
                }
                case SAVE -> {
                    return saveColor.value;
                }
                case LOCAL -> {
                    return localColor.value;
                }
                case LINE -> {
                    return lineColor.value;
                }
            }
            return lineColor.value;
        }

        public Config.Parameter<Integer, JsonPrimitive> characterLimit = intParameter("character_limit", 1, 0, 10);

        public Config.Parameter<Boolean, JsonPrimitive> useNames = boolParameter("use_variable_name", false);

        public Config.Parameter<Integer, JsonPrimitive> globalColor = colorParameter("global_color", 0xABC4D6);

        public Config.Parameter<Integer, JsonPrimitive> saveColor = colorParameter("save_color", NamedTextColor.YELLOW.value());

        public Config.Parameter<Integer, JsonPrimitive> localColor = colorParameter("local_color", NamedTextColor.GREEN.value());

        public Config.Parameter<Integer, JsonPrimitive> lineColor = colorParameter("line_color", NamedTextColor.AQUA.value());
    }

    public static class NumberDecorationsParameter extends ConfigObject {
        public Config.Parameter<Integer, JsonPrimitive> characterLimit = intParameter("character_limit", 2, 0, 10);
        public Config.Parameter<Integer, JsonPrimitive> color = colorParameter("color", NamedTextColor.YELLOW.value());
    }

    public static class TextDecorationsParameter extends ConfigObject {
        public int getColor(Text.ParsingType type) {
            switch (type) {
                case PLAIN -> {
                    return plainColor.value;
                }
                case LEGACY -> {
                    return legacyColor.value;
                }
                case MINI_MESSAGE -> {
                    return miniColor.value;
                }
                case JSON -> {
                    return jsonColor.value;
                }
            }
            return plainColor.value;
        }

        public Config.Parameter<Integer, JsonPrimitive> characterLimit = intParameter("character_limit", 2, 0, 10);

        public Config.Parameter<Integer, JsonPrimitive> plainColor = colorParameter("plain_color", NamedTextColor.WHITE.value());

        public Config.Parameter<Integer, JsonPrimitive> legacyColor = colorParameter("legacy_color", NamedTextColor.YELLOW.value());

        public Config.Parameter<Integer, JsonPrimitive> miniColor = colorParameter("minimessage_color", NamedTextColor.GREEN.value());

        public Config.Parameter<Integer, JsonPrimitive> jsonColor = colorParameter("json_color", 0xFFB657);
    }
}
