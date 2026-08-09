package com.prikolz.justhelper.codespace.values;

import net.minecraft.nbt.StringTag;
import net.minecraft.world.item.Items;

public class Parameter extends DevValue {

    public static final String type = "parameter";
    public static final DevValueRegistry<Parameter> registry = DevValueRegistry.create(
            Parameter.type,
            nbt -> {
                var name = nbt.getString("name").orElse("null");
                return new Parameter(name);
            },
            (value, nbt) -> {
                nbt.put("name", StringTag.valueOf(value.name));
            }
    );

    public String name;

    public Parameter(String name) {
        super(Parameter.type, Items.HEART_OF_THE_SEA, "Параметр функции({name})");
        this.name = name;
    }

    @Override
    public String miniBuilder() {
        return name;
    }
}
