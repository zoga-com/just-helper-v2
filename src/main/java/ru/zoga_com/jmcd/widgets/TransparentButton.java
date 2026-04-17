package ru.zoga_com.jmcd.widgets;

import com.prikolz.justhelper.JustHelperClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.input.MouseButtonEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class TransparentButton extends TransparentBox {
    private final int transparency;
    private final String text;
    private final Runnable clickCallback;

    public TransparentButton(String title, int x, int y, int width, int height, int transparentLevel, Runnable callback) {
        super(x, y, width, height, transparentLevel);

        this.transparency = transparentLevel;
        this.text = title;
        this.clickCallback = callback;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        guiGraphics.fill(getX(), getY(), getX() + this.width, getY() + this.height, new Color(0, 0, 0, transparency).getRGB());
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, text, getX() + (this.width / 2), getY() + (this.height / 2) - (Minecraft.getInstance().font.lineHeight / 2), 0xFFFFFFFF);
    }

    @Override
    public void onClick(@NotNull MouseButtonEvent mouseButtonEvent, boolean bl) {
        if(mouseButtonEvent.button() != 0) return;

        try {
            clickCallback.run();
        } catch (Exception e) {
            JustHelperClient.LOGGER.error("TransparentButton onClick callback error: {}", e.getMessage());
        }
    }
}
