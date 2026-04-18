package com.prikolz.justhelper.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.prikolz.justhelper.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;

public class TestMiniMessageCommand extends JustHelperCommand {
    public TestMiniMessageCommand() {
        super("tmm");
        this.description = "[minimessage] <gray>- Вывод указанного minimessage";
    }

    @Override
    public LiteralArgumentBuilder<ClientSuggestionProvider> create(LiteralArgumentBuilder<ClientSuggestionProvider> main) {
        return main.then(
                JustHelperCommands.argument("arg", StringArgumentType.greedyString()).executes(context -> {
                    var client = Minecraft.getInstance();
                    if (client.getConnection() == null || client.player == null) return 0;
                    client.player.displayClientMessage(TextUtils.minimessage(context.getArgument("arg", String.class)), false);
                    return 1;
                })
        );
    }
}
