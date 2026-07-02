package com.prikolz.justhelper.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.prikolz.justhelper.CommandBuffer;
import com.prikolz.justhelper.util.Pair;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;

public class AliasCommand extends JustHelperCommand {

    public String serverCommand;
    public Pair<String, ArgumentType<?>>[] arguments;
    public Command<ClientSuggestionProvider> context;

    @SafeVarargs
    public AliasCommand(String id, String serverCommand, Pair<String, ArgumentType<?>> ... arguments) {
        super(id);
        this.serverCommand = serverCommand;
        this.arguments = arguments;
        this.description = "";
        for (var arg : arguments) {
            this.description = this.description + "[" + arg.first + "] ";
        }
        this.description = this.description + "<gray>- Короткая версия команды '" + serverCommand + "'";
        context = (context) -> {
            var command = serverCommand;
            for (var arg : arguments) {
                try {
                    command = command.replace("$" + arg.first, context.getArgument(arg.first, Object.class).toString());
                } catch (Exception e) {
                    command = command.replace("$" + arg.first, "");
                }
            }
            while (command.endsWith(" ")) command = command.substring(0, command.length() - 1);
            CommandBuffer.add(command);
            return 1;
        };
    }

    @Override
    public LiteralArgumentBuilder<ClientSuggestionProvider> create(LiteralArgumentBuilder<ClientSuggestionProvider> main) {
        var line = new LineCommand(main);
        line.run(context);
        for (var arg : arguments) {
            line.arg(arg.first, arg.second);
            line.run(context);
        }
        return line.build();
    }
}
