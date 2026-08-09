package com.prikolz.justhelper.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.prikolz.justhelper.CommandBuffer;
import com.prikolz.justhelper.util.Scheduler;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;

public class BuildPlayCommand extends JustHelperCommand {
    public BuildPlayCommand() {
        super("bp");
        this.description = "<gray>- Отправляет на сервер 2 команды: /build и /play, для перезапуска мира.";
    }

    @Override
    public LiteralArgumentBuilder<ClientSuggestionProvider> create(LiteralArgumentBuilder<ClientSuggestionProvider> main) {
        return main.executes(context -> {
            CommandBuffer.add("build");
            Scheduler.runLater(20, () -> CommandBuffer.add("play"));
            return 1;
        });
    }
}
