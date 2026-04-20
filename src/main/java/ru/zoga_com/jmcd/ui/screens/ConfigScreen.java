package ru.zoga_com.jmcd.ui.screens;

import com.prikolz.justhelper.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.zoga_com.jmcd.ui.widgets.ConfigWidget;
import ru.zoga_com.jmcd.ui.widgets.TransparentButton;

public class ConfigScreen extends Screen {
    public ConfigScreen() {
        super(Component.literal("JMCD Config"));
    }

    @Override
    protected void init() {
        var minecraft = Minecraft.getInstance();
        var title = new StringWidget(TextUtils.minimessage("<#FFCC00>Config"), minecraft.font);
        title.setPosition(width / 2 - title.getWidth() / 2, 5);

        var config = new ConfigWidget(
                (int) (this.width / 3.5), 20, (int) (this.width - (this.width / 3.5) - 40), this.height - 60
        );

        var saveButton = new TransparentButton(
                Component.translatable("gui.done"), width / 2 + 7, height - 25,
                100, 20, 125, () -> {
                    assert minecraft.player != null;


                    minecraft.setScreen(null);
                }
        );

        var closeButton = new TransparentButton(
                Component.translatable("gui.cancel"), width / 2 - 107, height - 25,
                100, 20, 125, () -> minecraft.setScreen(null)
        );

        addRenderableWidget(title);
        addRenderableWidget(config);
        addRenderableWidget(closeButton);
        addRenderableWidget(saveButton);
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
