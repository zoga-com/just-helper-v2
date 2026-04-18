package com.prikolz.justhelper.gui;

import com.prikolz.justhelper.Config;
import com.prikolz.justhelper.commands.JustHelperCommand;
import com.prikolz.justhelper.gui.widgets.JSONHolder;
import com.prikolz.justhelper.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.zoga_com.jmcd.Messages;
import ru.zoga_com.jmcd.ui.widgets.TransparentButton;

public class ConfigScreen extends Screen {
    private JSONHolder editBox;

    protected ConfigScreen() {
        super(Component.literal("JustHelper configuration"));
    }

    public static ConfigScreen create() {
        return new ConfigScreen();
    }

    @Override
    protected void init() {
        var json = editBox == null ? Config.getJSON() : editBox.box.getValue();
        var minecraft = Minecraft.getInstance();
        var title = new StringWidget(TextUtils.minimessage("<#FFCC00>Config (JSON)"), minecraft.font);
        title.setPosition(width / 2 - title.getWidth() / 2, 5);

        var saveButton = new TransparentButton(
                Component.translatable("gui.done"), width / 2 + 7, height - 25,
                100, 20, 125,
                () -> {
                    Config.saveConfig(this.editBox.box.getValue());
                    Config.get().read();
                    Minecraft.getInstance().setScreen(null);
                    JustHelperCommand.feedback(Messages.CONFIG_UPDATED);
                }
        );

        var editBox = net.minecraft.client.gui.components.MultiLineEditBox.builder()
                .setX(20)
                .setY(20)
                .build(minecraft.font, this.width - 40, this.height - 60, Component.literal("JSON"));
        editBox.setValue(json);
        this.editBox = new JSONHolder(editBox, saveButton);

        var closeButton = new TransparentButton(
                Component.translatable("gui.cancel"), width / 2 - 107, height - 25,
                100, 20, 125,
                () -> minecraft.setScreen(null)
        );

        var resetButton = new TransparentButton(
                Component.translatable("controls.reset"), width - 70, height - 35,
                50, 20, 125,
                () -> {
                    Minecraft.getInstance().setScreen(new ConfirmScreen(
                            "Сбросить конфиг?",
                            "Конфиг JustHelper будет сброшен до значений по умолчанию.",
                            () -> {
                                Config.printLogs(Config.get().reset());
                                Config.get().read();
                                JustHelperCommand.feedback(Messages.CONFIG_UPDATED);
                                Minecraft.getInstance().setScreen(null);
                            },
                            () -> Minecraft.getInstance().setScreen(this)
                    ));
                }
        );

        var folderButton = new TransparentButton(
                TextUtils.minimessage("<white><font:jmcd:icons>0"),
                20,
                height - 35,
                20, 20,
                125,
                Component.literal("Открыть папку"),
                Config::openConfigFolder
        );

        this.editBox.checkValid();

        addRenderableWidget(this.editBox);
        addRenderableWidget(closeButton);
        addRenderableWidget(saveButton);
        addRenderableWidget(title);
        addRenderableWidget(resetButton);
        addRenderableWidget(folderButton);
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(null);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
