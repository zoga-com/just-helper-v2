package com.prikolz.justhelper.codespace.values;

import net.minecraft.nbt.DoubleTag;
import net.minecraft.world.item.Items;

public class Location extends DevValue {

    public static final String type = "location";
    public static final DevValueRegistry<Location> registry = DevValueRegistry.create(
            Location.type,
            nbt -> {
                var x = nbt.getDouble("x").orElse(0.0);
                var y = nbt.getDouble("y").orElse(0.0);
                var z = nbt.getDouble("z").orElse(0.0);
                var yaw = nbt.getDouble("yaw").orElse(0.0);
                var pitch = nbt.getDouble("pitch").orElse(0.0);

                return new Location(x, y, z, yaw, pitch);
            },
            (value, nbt) -> {
                nbt.put("x", DoubleTag.valueOf(value.x));
                nbt.put("y", DoubleTag.valueOf(value.y));
                nbt.put("z", DoubleTag.valueOf(value.z));
                nbt.put("yaw", DoubleTag.valueOf(value.yaw));
                nbt.put("pitch", DoubleTag.valueOf(value.pitch));
            }
    );

    public Double x;
    public Double y;
    public Double z;
    public Double yaw;
    public Double pitch;

    public Location(Double x, Double y, Double z, Double yaw, Double pitch) {
        super(Location.type, Items.MAP, "Местоположение({x} {y} {z} | {yaw} {pitch})");
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public String miniBuilder() {
        return x + "/" + y + "/" + z + "(" + yaw + "/" + pitch + ")";
    }
}
