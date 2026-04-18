package ru.zoga_com.jmcd.ui.widgets;

import com.prikolz.justhelper.JustHelperClient;
import com.prikolz.justhelper.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class TransparentButton extends AbstractWidget {
    private final Component text;
    private final Runnable clickCallback;
    private final Color mainColor;
    private final Color hoverColor;
    private final Color mainTextColor;
    private final Color hoverTextColor;

    public TransparentButton(Component title, int x, int y, int width, int height, int transparentLevel, Runnable callback) {
        this(title, x, y, width, height, new Color(0, 0, 0, transparentLevel), new Color(33, 188, 255, transparentLevel), new Color(255, 255, 255), new Color(255, 255, 85), TextUtils.minimessage(""), callback);
    }

    public TransparentButton(Component title, int x, int y, int width, int height, int transparentLevel, Component tooltip, Runnable callback) {
        this(title, x, y, width, height, new Color(0, 0, 0, transparentLevel), new Color(33, 188, 255, transparentLevel), new Color(255, 255, 255), new Color(255, 255, 85), tooltip, callback);
    }

    public TransparentButton(Component title, int x, int y, int width, int height, Color defaultColor, Color hoverColor, Color defaultTextColor, Color hoverTextColor, Component tooltip, Runnable callback) {
        super(x, y, width, height, Component.empty());

        this.text = title;
        this.clickCallback = callback;
        this.mainColor = defaultColor;
        this.hoverColor = hoverColor;
        this.mainTextColor = defaultTextColor;
        this.hoverTextColor = hoverTextColor;

        this.setTooltip(Tooltip.create(tooltip));
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        guiGraphics.fill(getX(), getY(), getX() + this.width, getY() + this.height, this.getAccentColor().getRGB());
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, text, getX() + (this.width / 2), getY() + (this.height / 2) - (Minecraft.getInstance().font.lineHeight / 2), this.getTextColor().getRGB());
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

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {}

    private Color getAccentColor() {
        return this.isHovered() ? this.hoverColor : this.mainColor;
    }

    private Color getTextColor() {
        return this.isHovered() ? this.hoverTextColor : this.mainTextColor;
    }
}
