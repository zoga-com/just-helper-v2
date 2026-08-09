package com.prikolz.justhelper.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.prikolz.justhelper.CommandBuffer;
import com.prikolz.justhelper.codespace.CodeSpace;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;

public class BackCommand extends JustHelperCommand {

    public BackCommand() {
        super("back");
        this.description = "<gray>- Вернет вас на место, откуда вы были телепортированны.";
    }

    @Override
    public LiteralArgumentBuilder<ClientSuggestionProvider> create(LiteralArgumentBuilder<ClientSuggestionProvider> main) {
        return main.executes(context -> {
            var prevPos = CodeSpace.anchor;
            if (prevPos == null) return JustHelperCommand.feedback("<yellow>JustHelper >> Перемещений не было, вернуться некуда");
            CommandBuffer.add("tp " + prevPos.x + " " + prevPos.y + " " + prevPos.z);
            CodeSpace.teleportAnchor();
            return 1;
        });
    }
}
