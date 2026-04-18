package com.prikolz.justhelper.gui.widgets;

import com.google.gson.JsonObject;
import com.prikolz.justhelper.JustHelperClient;
import com.prikolz.justhelper.mixin.MultiLineEditBoxMixin;
import com.prikolz.justhelper.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.ScreenDirection;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.zoga_com.jmcd.ui.widgets.TransparentButton;

import java.util.Collection;

public class JSONHolder extends AbstractWidget {
    public final MultiLineEditBox box;
    private final TransparentButton saveButton;
    private ExceptionInfo error = new ExceptionInfo(0, false, "");
    private final Font font;

    public JSONHolder(MultiLineEditBox box, TransparentButton saveButton) {
        super(box.getX(), box.getY(), box.getWidth(), box.getHeight(), Component.literal("JSON"));
        this.box = box;
        this.saveButton = saveButton;
        font = Minecraft.getInstance().font;
        box.setScrollAmount(0.0);
    }

    public void checkValid() {
        error = new ExceptionInfo(0, false, "");
        saveButton.active = true;
        saveButton.setTooltip(null);
        try {
            var obj = JustHelperClient.GSON.fromJson(box.getValue(), JsonObject.class);
            JustHelperClient.GSON.toJson(obj);
        } catch (Throwable t) {
            var lineError = 0;
            var errorMessage = t.getMessage() == null ? "line 0" : t.getMessage();
            var i = errorMessage.indexOf("line ");
            if (i != -1) try {
                var sub = errorMessage.substring(i + 5);
                i = sub.indexOf(" ");
                if (i != -1) lineError = Integer.parseInt(sub.substring(0, i));
            } catch (Throwable ignore) {
            }
            error = new ExceptionInfo(lineError, true, t.getMessage());
            saveButton.active = false;
            saveButton.setTooltip(Tooltip.create(
                    TextUtils.minimessage("<red>Ошибка JSON\n" + TextUtils.splitByWord(t.getMessage(), 40)),
                    Component.literal(t.getMessage())
            ));
        }
        var abox = (MultiLineEditBoxMixin) box;
        abox.setTextColor(error.error ? 0xffFFAA55 : 0xffFFFFBB);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        box.renderWidget(guiGraphics, i, j, f);
        if (error.error) {
            var lineCount = ((MultiLineEditBoxMixin) box).getTextField().getLineCount();
            var pos = ((double) error.line / lineCount) * (box.maxScrollAmount() + box.getHeight() - 4);
            if (box.scrollAmount() > pos) return;
            pos = (pos * 0.99 - 2 - box.scrollAmount());
            if (pos > box.getHeight()) return;
            int markerX = box.getX() - 12;
            guiGraphics.drawString(font, TextUtils.minimessage("<sprite:gui:icon/unseen_notification>"), markerX, (int) (pos) + box.getY(), 0xffFFAA00);
        }
    }

    @Override
    public void setFocused(boolean bl) {
        box.setFocused(bl);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        box.updateWidgetNarration(narrationElementOutput);
    }

    @Override
    public void mouseMoved(double d, double e) {
        box.mouseMoved(d, e);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean bl) {
        return box.mouseClicked(event, bl);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent mouseButtonEvent) {
        return box.mouseReleased(mouseButtonEvent);
    }

    @Override
    public boolean keyPressed(KeyEvent keyEvent) {
        boolean result = box.keyPressed(keyEvent);
        checkValid();
        return result;
    }

    @Override
    public boolean charTyped(CharacterEvent event) {
        boolean result = box.charTyped(event);
        checkValid();
        return result;
    }

    @Override
    public boolean mouseScrolled(double d, double e, double f, double g) {
        return box.mouseScrolled(d, e, f, g * 5);
    }

    @Override
    public boolean isMouseOver(double d, double e) {
        return box.isMouseOver(d, e);
    }

    @Override
    public @Nullable ComponentPath getCurrentFocusPath() {
        return box.getCurrentFocusPath();
    }

    @Override
    public @NotNull ScreenRectangle getBorderForArrowNavigation(ScreenDirection screenDirection) {
        return box.getBorderForArrowNavigation(screenDirection);
    }

    @Override
    public void setPosition(int i, int j) {
        box.setPosition(i, j);
    }

    @Override
    public @NotNull Collection<? extends NarratableEntry> getNarratables() {
        return box.getNarratables();
    }

    public record ExceptionInfo(int line, boolean error, String message) {}
}
