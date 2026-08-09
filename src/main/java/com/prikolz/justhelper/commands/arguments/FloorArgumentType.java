package com.prikolz.justhelper.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.prikolz.justhelper.codespace.CodeSpace;
import com.prikolz.justhelper.util.TextUtils;
import net.minecraft.network.chat.Component;

import java.util.concurrent.CompletableFuture;

public class FloorArgumentType implements ArgumentType<Integer> {

    public static final DynamicCommandExceptionType MUST_BE_IN_DEV = new DynamicCommandExceptionType((object) -> Component.literal("Недоступно вне мира разработки /dev."));
    public static final DynamicCommandExceptionType FLOOR_NOT_FOUND = new DynamicCommandExceptionType((object) -> TextUtils.minimessage("Этаж {0} не найден!", object));
    public static final StringArgumentType parser = StringArgumentType.greedyString();

    private static String input = "";

    @Override
    public Integer parse(StringReader reader) throws CommandSyntaxException {
        if (!CodeSpace.isActive()) throw MUST_BE_IN_DEV.create("");
        String name = parser.parse(reader);
        input = name;
        var floor = CodeSpace.getFloor(name);
        if (floor == -1) throw FLOOR_NOT_FOUND.create(name);
        return floor;
    }

    public static <S> int getFloor(CommandContext<S> context, String argument) {
        input = "";
        return context.getArgument(argument, Integer.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        if (CodeSpace.isActive()) {
            if (input.isEmpty()) {
                CodeSpace.getFloorDescribes().forEach(it -> builder.suggest(it.plain));
                return builder.buildFuture();
            }
            for (var describe : CodeSpace.getFloorDescribes())
                if (describe.plain.toLowerCase().contains(input.toLowerCase())) builder.suggest(describe.plain);
        }
        return builder.buildFuture();
    }
}
