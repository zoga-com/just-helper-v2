package com.prikolz.justhelper;

import com.prikolz.justhelper.commands.BackCommand;
import com.prikolz.justhelper.commands.JustHelperCommand;
import com.prikolz.justhelper.commands.arguments.SignsSearchingArgumentType;
import com.prikolz.justhelper.dev.*;
import com.prikolz.justhelper.dev.values.DevValue;
import com.prikolz.justhelper.dev.values.Variable;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;

import java.util.HashMap;
import java.util.Set;

public abstract class CodeSpace {
    public static final String WORLD_PREFIX = "world_";
    public static final String WORLD_SUFFIX = "_creativeplus_editor";

    public static final HashMap<Variable.Scope, VariablesHistory> history = new HashMap<>();
    public static final HashMap<BlockPos, SignInfo> signs = new HashMap<>();
    public static FloorDescribes describes = null;

    private static CodeSpaceRender render = null;

    private static String worldUUID;

    public static boolean isActive() {
        var name = getWorldName();
        if (name == null) return false;
        return name.endsWith(WORLD_SUFFIX) && name.startsWith(WORLD_PREFIX);
    }

    private static String getWorldName() {
        var level = Minecraft.getInstance().level;
        if (level == null) return null;
        return level.dimension().identifier().getPath();
    }

    public static void initialize() {
        CommandBuffer.clear();
        if (!isActive()) {
            render = null;
            worldUUID = null;
            history.forEach((k, v) -> v.save());
            history.clear();
            signs.clear();
            return;
        }
        var worldName = getWorldName();
        if (worldName == null) return;
        render = new CodeSpaceRender();
        worldUUID = worldName.substring(WORLD_PREFIX.length(), worldName.length() - WORLD_SUFFIX.length());
        JustHelperClient.LOGGER.info("Joined to develop world {}", worldUUID);
        history.forEach((k, v) -> v.save());
        history.clear();
        signs.clear();
        for (Variable.Scope scope : Variable.Scope.values()) {
            history.put( scope, new VariablesHistory(worldUUID, scope) );
        }
        describes = new FloorDescribes(worldUUID);
        describes.spawn();
    }

    public static CodeSpaceRender render() { return render; }

    public static void handleItemStack(ItemStack item) {
        if (!isActive()) return;
        var value = DevValue.fromItem(item);
        if (value == null) return;

        value.handleItemStack(item);
    }

    public static void addToHistory(Variable.Scope type, String name) {
        if (history.isEmpty() || name == null) return;
        history.get(type).history.add(name);
    }

    public static Set<String> getVariablesHistory(Variable.Scope type) {
        if (!history.containsKey(type)) return Set.of();
        return history.get(type).history;
    }

    public static void addSign(BlockEntity blockEntity) {
        if (!isActive()) return;
        if (!(blockEntity instanceof SignBlockEntity sign)) return;
        signs.put(sign.getBlockPos(), new SignInfo(sign));
    }

    public static void teleportAnchor() {
        if (!isActive()) return;
        var player = Minecraft.getInstance().player;
        var level = Minecraft.getInstance().level;
        if (player == null || level == null) return;
        var pos = new BlockCodePos(4, player.getBlockY(), player.getBlockZ());
        BackCommand.prevPos = player.position();
        if (!Config.get().teleportAnchor.value) return;
        var signInfo = SignInfo.getSign(pos);
        String hover;
        String display;
        if (signInfo != null) {
            var found = SignsSearchingArgumentType.FoundSignInfo.create(signInfo);
            hover = found.createHoverInfo(":3");
            if (found.lines().length > 1)
                display = "<white>" + signInfo.getMiniBlockSprite() + "<aqua> " + found.lines()[1];
            else
                display = found.lines()[0];
        } else {
            hover = "<gray>Нажмите для телепортации";
            var floor = describes.describes.get(pos.floor);
            if (floor == null) floor = pos.floor + " э";
            display = "<yellow>" + floor + "<white>/<yellow>" + pos.line + " л<white>/<yellow>" + pos.pos + " п";
        }
        JustHelperCommand.feedback(
                "\n<click:run_command:'/tp {1} {2} {3}'><hover:show_text:'{0}'><aqua>⚓ <white>Вернутся на <aqua>>> {4} <aqua><<\n",
                hover,
                player.getX(),
                player.getY(),
                player.getZ(),
                display
        );
    }
}
