package com.prikolz.justhelper.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.prikolz.justhelper.DevelopmentWorld;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import ru.zoga_com.jmcd.Messages;

public class DescribeCommand extends JustHelperCommand {
    public DescribeCommand() {
        super("describe");
        this.description = "[Этаж] [Текст] <gray>- Добавляет подпись(название) этажу. Название будет отображается в мире через визуализатор текста, а так-же в командах /find и /floor.";
    }

    @Override
    public LiteralArgumentBuilder<ClientSuggestionProvider> create(LiteralArgumentBuilder<ClientSuggestionProvider> main) {
        return main.then(
                JustHelperCommands.argument("floor", IntegerArgumentType.integer(1)).then(
                        JustHelperCommands.argument("describe", StringArgumentType.greedyString()).executes(
                                context -> {
                                    int floor = IntegerArgumentType.getInteger(context, "floor");
                                    var describe = StringArgumentType.getString(context, "describe");
                                    return execute(floor, describe);
                                }
                        )
                ).executes(context -> showFloor(IntegerArgumentType.getInteger(context, "floor")))
        ).executes(context -> showAll());
    }

    public static int showFloor(int floor) {
        if (!DevelopmentWorld.isActive()) return JustHelperCommand.feedback(Messages.ONLY_ON_DEV);
        String text = DevelopmentWorld.describes.describes.get(floor);
        if (text == null) return JustHelperCommand.feedback(Messages.SHOW_FLOOR_DESCRIPTION_NOT_FOUND, floor);
        JustHelperCommand.feedback(
                Messages.SHOW_FLOOR,
                floor,
                text,
                floor < 10 ? " " : "",
                "/describe " + floor + " " + text,
                text.replaceAll("<", "\\\\<")
        );
        return 1;
    }

    public static int showAll() {
        if (!DevelopmentWorld.isActive()) return JustHelperCommand.feedback(Messages.ONLY_ON_DEV);

        JustHelperCommand.feedback(Messages.SHOW_FLOOR_ALL_BEFORE);
        DevelopmentWorld.describes.describes.keySet().forEach(DescribeCommand::showFloor);
        JustHelperCommand.feedback(Messages.SHOW_FLOOR_ALL_AFTER);
        return 1;
    }

    public static int execute(int floor, String describe) {
        if (!DevelopmentWorld.isActive()) return JustHelperCommand.feedback(Messages.ONLY_ON_DEV);

        DevelopmentWorld.describes.describe(floor, describe);
        JustHelperCommand.feedback(Messages.DESCRIBE, floor, describe);
        return 1;
    }
}
