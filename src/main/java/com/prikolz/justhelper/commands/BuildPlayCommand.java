package com.prikolz.justhelper.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.prikolz.justhelper.CommandBuffer;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;

public class BuildPlayCommand extends JustHelperCommand {
    public BuildPlayCommand() {
        super("bp");
    }

    @Override
    public LiteralArgumentBuilder<ClientSuggestionProvider> create(LiteralArgumentBuilder<ClientSuggestionProvider> main) {
        return main.executes(context -> {
            CommandBuffer.add("build");
            CommandBuffer.add("play");
            return 1;
        });
    }
}
