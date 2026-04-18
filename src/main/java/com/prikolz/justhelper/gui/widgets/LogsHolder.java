package com.prikolz.justhelper.gui.widgets;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.narration.NarrationElementOutput;

public class LogsHolder extends AbstractWidget {
    public final MultiLineEditBox box;

    public LogsHolder(MultiLineEditBox box) {
        super(box.getX(), box.getY(), box.getWidth(), box.getHeight(), box.getMessage());
        this.box = box;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        box.renderWidget(guiGraphics, i, j, f);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {}

    @Override
    public void mouseMoved(double d, double e) {
        box.mouseMoved(d, e);
    }

    @Override
    public boolean mouseScrolled(double d, double e, double f, double g) {
        return box.mouseScrolled(d, e, f, g);
    }

    @Override
    public boolean isMouseOver(double d, double e) {
        return box.isMouseOver(d, e);
    }
}
