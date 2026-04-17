package ru.zoga_com.jmcd.widgets;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class TransparentBox extends AbstractWidget {
    private final int transparency;

    public TransparentBox(int x, int y, int width, int height, int transparentLevel) {
        super(x, y, width, height, Component.empty());

        assert transparentLevel >= 0 && transparentLevel <= 255;

        this.transparency = transparentLevel;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        guiGraphics.fill(getX(), getY(), getX() + this.width, getY() + this.height, new Color(0, 0, 0, transparency).getRGB());
    }

    // unused, but necessary method
    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {}
}
