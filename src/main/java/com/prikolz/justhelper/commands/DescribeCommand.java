package com.prikolz.justhelper.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.prikolz.justhelper.codespace.CodeSpace;
import com.prikolz.justhelper.commands.arguments.FloorArgumentType;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;

public class DescribeCommand extends JustHelperCommand {
    public DescribeCommand() {
        super("describe");
        this.description = "[Этаж] [Текст] <gray>- Добавляет подпись(название) этажу. Название будет отображается в мире через визуализатор текста, а так-же в командах /find и /floor.";
    }

    @Override
    public LiteralArgumentBuilder<ClientSuggestionProvider> create(LiteralArgumentBuilder<ClientSuggestionProvider> main) {

        var setNode = new LineCommand("set")
                .arg("floor", IntegerArgumentType.integer(1, 60))
                .arg("text", StringArgumentType.greedyString())
                .run(context -> {
                    int floor = IntegerArgumentType.getInteger(context, "floor");
                    String text = StringArgumentType.getString(context, "text");
                    return describe(floor, text);
                })
                .build();

        var removeNode = new LineCommand("remove")
                .arg("floor", new FloorArgumentType())
                .run(context -> {
                    int floor = FloorArgumentType.getFloor(context, "floor");
                    return removeDescribe(floor);
                })
                .build();

        return main.then(setNode).then(removeNode).executes(context -> showAll());
    }

    public static int showFloor(int floor) {
        if (!CodeSpace.isActive()) return JustHelperCommand.feedback("<yellow>Доступно только в мире кода!");
        var text = CodeSpace.getFloorDescribe(floor, true);
        if (text == null) return JustHelperCommand.feedback("<dark_gray>Описание этажа {0} не установлено", floor);
        JustHelperCommand.feedback(
                "<yellow>{0}{2}<dark_gray> |> <click:suggest_command:'{3}'><hover:show_text:'{4}'><white>{1}",
                floor,
                text,
                floor < 10 ? " " : "",
                "/describe " + floor + " " + text,
                text.minimessage.replaceAll("<", "\\\\<")
        );
        return 1;
    }

    public static int showAll() {
        if (!CodeSpace.isActive()) return JustHelperCommand.feedback("<yellow>Доступно только в мире кода!");
        JustHelperCommand.feedback("<yellow>v <white>Описания этажей:");
        CodeSpace.getFloorDescribes().forEach(it -> showFloor(it.floor));
        JustHelperCommand.feedback("<yellow>^");
        return 1;
    }

    public static int describe(int floor, String describe) {
        if (!CodeSpace.isActive()) return JustHelperCommand.feedback("<yellow>Доступно только в мире кода!");
        CodeSpace.setFloorDescribe(floor, describe);
        JustHelperCommand.feedback("<green>Установлено описание <white>{0} <green>этажа:<white> {1}", floor, describe);
        return 1;
    }

    public static int removeDescribe(int floor) {
        if (!CodeSpace.isActive()) return JustHelperCommand.feedback("<yellow>Доступно только в мире кода!");
        var result = CodeSpace.deleteFloorDescribe(floor);
        if (result) return JustHelperCommand.feedback("<yellow>Описание <white>{0}<yellow> этажа удалено", floor);
        return JustHelperCommand.feedback("<yellow>Описание <white>{0}<yellow> этажа не установлено", floor);
    }
}
