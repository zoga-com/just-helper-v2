package com.prikolz.justhelper.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.concurrent.CompletableFuture;

public class QuotedStringArgumentType implements ArgumentType<String> {
    public String lastInput = "";

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        lastInput = reader.readQuotedString();
        return lastInput;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        builder.suggest("\"" + lastInput + "\"");
        return builder.buildFuture();
    }
}
