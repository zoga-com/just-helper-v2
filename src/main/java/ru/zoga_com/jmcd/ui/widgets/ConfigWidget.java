package ru.zoga_com.jmcd.ui.widgets;

import com.prikolz.justhelper.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import ru.zoga_com.jmcd.ui.widgets.config.ConfigSetting;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigWidget extends AbstractWidget {
    private final List<ConfigSetting<?>> content = new ArrayList<>();
    private final int lineHeight = 40;
    private double currentScrollOffset = 0.0;
    private final List<TransparentButton> buttons = new ArrayList<>();
    private final HashMap<String, Object> values = new HashMap<>();

    public ConfigWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Component.empty());

        for (int i = 0; i < 50; i++) {
            content.add(new ConfigSetting<>("test" + i, "lol" + i, Boolean.TRUE));
        }
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        int totalHeight = getContentsHeight();
        int visibleHeight = this.getHeight();
        int maxScroll = Math.max(0, totalHeight - visibleHeight + (lineHeight / 2));

        this.currentScrollOffset = Math.clamp(this.currentScrollOffset, 0, maxScroll);

        guiGraphics.fill(getX(), getY(), getX() + this.width, getY() + this.height, new Color(0, 0, 0, 125).getRGB());

        buttons.clear();
        guiGraphics.enableScissor(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight());
        var font = Minecraft.getInstance().font;
        for (int i = 0; i < content.size(); i++) {
            float itemY = this.getY() + i * lineHeight - (float) this.currentScrollOffset;
            if (itemY >= this.getY() - lineHeight && itemY <= this.getY() + visibleHeight) {
                guiGraphics.drawString(font, content.get(i).title, this.getX() + 10, (int) itemY + 8, new Color(255, 255, 85).getRGB(), false);
                guiGraphics.drawString(font, content.get(i).description, this.getX() + 10, (int) itemY + 24, 0xFF7B7B7B, false);
                int finalI = i;
                if (!values.containsKey(content.get(i).title)) {
                    values.put(content.get(i).title, content.get(i).value);
                }
                var btn = new TransparentButton(
                        TextUtils.minimessage(((boolean) values.get(content.get(finalI).title)) ? "<green>Вкл" : "<red>Выкл"),
                        getX() + this.width - 50,
                        (int) (itemY + 10),
                        40, 20,
                        125,
                        () -> values.replace(content.get(finalI).title, !((boolean) values.get(content.get(finalI).title)))
                );
                buttons.add(btn);
                btn.render(guiGraphics, mouseX, mouseY, delta);
            }
        }
        guiGraphics.disableScissor();

        renderScrollbar(guiGraphics, totalHeight, visibleHeight, maxScroll);
    }

    private void renderScrollbar(GuiGraphics guiGraphics, int totalHeight, int visibleHeight, int maxScroll) {
        assert maxScroll > 0;

        int scrollbarX = this.getX() + this.getWidth() - 6;
        int thumbHeight = Math.max(10, (int) ((float) visibleHeight / totalHeight * visibleHeight));
        float scrollRatio = (float) this.currentScrollOffset / maxScroll;
        int thumbY = this.getY() + (int) (scrollRatio * (visibleHeight - thumbHeight));

        guiGraphics.fill(scrollbarX, this.getY(), scrollbarX + 6, this.getY() + visibleHeight, 0xFF303030);
        guiGraphics.fill(scrollbarX, thumbY, scrollbarX + 6, thumbY + thumbHeight, 0xFFC0C0C0);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
        this.currentScrollOffset -= deltaY * 10.0;
        return true;
    }

    private int getContentsHeight() {
        return content.size() * lineHeight;
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput builder) {
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent mbe, boolean bl) {
        if (mbe.button() != 0) return true;

        for (TransparentButton button : buttons) {
            if (button.isHovered()) {
                this.playDownSound(Minecraft.getInstance().getSoundManager());
                button.runCallback();
                break;
            }
        }
        return true;
    }
}
