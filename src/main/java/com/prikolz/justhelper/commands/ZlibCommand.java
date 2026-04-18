package com.prikolz.justhelper.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.prikolz.justhelper.JustHelperClient;
import com.prikolz.justhelper.util.JustMCUtils;
import com.prikolz.justhelper.util.TextUtils;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import ru.zoga_com.jmcd.Messages;

public class ZlibCommand extends JustHelperCommand{
    public ZlibCommand() {
        super("zlib");
        this.description = "[compress/decompress] <gray>- Сжимает/Распаковывает текст в zlib.";
    }

    @Override
    public LiteralArgumentBuilder<ClientSuggestionProvider> create(LiteralArgumentBuilder<ClientSuggestionProvider> main) {
        var compressBranch = new LineCommand("compress")
                .arg("string", StringArgumentType.greedyString())
                .run(context -> {
                    var string = StringArgumentType.getString(context, "string");
                    var compressed = JustMCUtils.zlibCompress(string);
                    return JustHelperCommand.feedback(Messages.ZLIB_COMPRESS, TextUtils.copyValue(compressed));
                });

        var decompressBranch = new LineCommand("decompress")
                .arg("zlib", StringArgumentType.greedyString())
                .run(context -> {
                    var string = StringArgumentType.getString(context, "zlib");
                    try {
                        var decompressed = JustMCUtils.zlibDecompress(string);
                        return JustHelperCommand.feedback(Messages.ZLIB_DECOMPRESS, TextUtils.copyValue(decompressed));
                    } catch (Throwable t) {
                        JustHelperClient.LOGGER.printStackTrace(t, JustHelperClient.JustHelperLogger.LogType.ERROR);
                        return JustHelperCommand.feedback(Messages.DECOMPRESS_ERROR, t.getMessage());
                    }
                });

        return main.then(compressBranch.build()).then(decompressBranch.build());
    }
}
