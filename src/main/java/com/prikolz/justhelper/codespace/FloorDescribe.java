package com.prikolz.justhelper.codespace;

import com.prikolz.justhelper.mixin.DisplayMixin;
import com.prikolz.justhelper.mixin.TextDisplayMixin;
import com.prikolz.justhelper.util.TextUtils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.joml.Vector3f;

public class FloorDescribe {
    public final int floor;
    public String minimessage;
    public String plain;
    public Component component;
    public Display.TextDisplay entity = null;

    public static FloorDescribe empty(int floor) { return new FloorDescribe(floor, floor + " этаж"); }

    public FloorDescribe(int number, String minimessage) {
        this.floor = number;
        setText(minimessage);
    }

    public void setText(String minimessage) {
        this.minimessage = minimessage == null ? "null" : minimessage;
        this.component = TextUtils.minimessage(this.minimessage);
        this.plain = this.component.getString();
    }

    public void removeEntity() {
        entity.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
    }

    public void spawn(ClientLevel level) {
        if (level == null) return;
        if (entity != null) removeEntity();
        entity = new Display.TextDisplay(EntityType.TEXT_DISPLAY, level);
        entity.getEntityData().set(TextDisplayMixin.DATA_TEXT_ID(), component, true);
        entity.getEntityData().set(TextDisplayMixin.DATA_BACKGROUND_COLOR_ID(), 0xFF5B5959, true);
        entity.getEntityData().set(DisplayMixin.DATA_SCALE_ID(), new Vector3f(10), true);
        BlockPos pos = new BlockPos(-1, 4 + (7 * (floor - 1)), 47);
        entity.setPos(pos.getX() + 0.5, pos.getY() - 1, pos.getZ() + 0.5);
        entity.setYRot(90);

        level.addEntity(entity);
    }
}
