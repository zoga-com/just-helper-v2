package com.prikolz.justhelper.codespace.values;

import net.minecraft.world.item.Items;

public class UnknownValue extends DevValue {

    public UnknownValue() {
        super("null", Items.BARRIER, "Неизвестное значение");
    }

    @Override
    public String miniBuilder() {
        return "";
    }
}
