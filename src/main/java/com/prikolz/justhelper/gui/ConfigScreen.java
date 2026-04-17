package com.prikolz.justhelper.gui;

import com.google.gson.JsonObject;
import com.prikolz.justhelper.Config;
import com.prikolz.justhelper.JustHelperClient;
import com.prikolz.justhelper.commands.JustHelperCommand;
import com.prikolz.justhelper.mixin.MultiLineEditBoxMixin;
import com.prikolz.justhelper.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.ScreenDirection;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.zoga_com.jmcd.widgets.TransparentButton;

import java.util.Collection;

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
                    JustHelperCommand.feedback("<sprite:gui:icon/checkmark><#9AFF1F> Конфиг обновлен");
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
                                JustHelperCommand.feedback("<sprite:gui:icon/checkmark><#9AFF1F> Конфиг обновлен");
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

    static class JSONHolder extends AbstractWidget {
        private final MultiLineEditBox box;
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
}
