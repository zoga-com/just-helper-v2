package com.prikolz.justhelper.gui;

import com.prikolz.justhelper.commands.JustHelperCommand;
import com.prikolz.justhelper.dev.values.Text;
import com.prikolz.justhelper.gui.widgets.FrameWidget;
import com.prikolz.justhelper.util.JustMCUtils;
import com.prikolz.justhelper.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
import net.minecraft.world.item.ItemStack;
import ru.zoga_com.jmcd.Messages;
import ru.zoga_com.jmcd.ui.widgets.TransparentButton;

public class TextEditorScreen extends Screen {
    private final Text.ParsingType currentFormat;
    private String currentContent;
    private boolean preview;

    protected TextEditorScreen(Text text) {
        super(Component.literal("JustHelper Text Editor"));
        this.currentFormat = text.parsingType;
        this.currentContent = text.text;
        this.preview = false;
    }

    public static TextEditorScreen create(Text text) {
        return new TextEditorScreen(text);
    }

    @Override
    protected void init() {
        var minecraft = Minecraft.getInstance();
        var title = new StringWidget(TextUtils.minimessage("<#FFCC00>Text Editor"), minecraft.font);
        title.setPosition(width / 2 - title.getWidth() / 2, 5);

        var formatting = new StringWidget(currentFormat.getNameComponent(), minecraft.font);
        formatting.setPosition(20, height - 45);

        MultiLineTextWidget previewBox = new MultiLineTextWidget(TextUtils.serialize(this.currentContent, this.currentFormat), minecraft.font);
        previewBox.setWidth(this.width / 2 - 26);
        previewBox.setMaxWidth(this.width / 2 - 26);
        previewBox.setHeight(this.height - 96);
        previewBox.setPosition(this.width / 2 + 14, 54);
        FrameWidget previewFrame = new FrameWidget(this.width / 2 - 30, this.height - 100).setPos(this.width / 2 + 10, 50);

        var editBox =
                MultiLineEditBox.builder()
                .setX(20)
                .setY(50)
                .build(minecraft.font, this.preview ? this.width / 2 - 30 : this.width - 40,  this.height - 100, Component.literal("Текст"));
        editBox.setValue(this.currentContent);
        editBox.setCharacterLimit(24999);
        editBox.setValueListener((newValue) -> {
            this.currentContent = newValue;
            previewBox.setMessage(TextUtils.serialize(newValue, this.currentFormat));
        });

        TransparentButton previewButton = new TransparentButton(
                TextUtils.minimessage(Messages.PREVIEW_BUTTON_TRUE),
                TextUtils.minimessage(Messages.PREVIEW_BUTTON_FALSE),
                () -> this.preview,
                this.width / 2 - 50, 25, 100, 20, 125, () -> {
                    this.preview = !this.preview;
                    if(this.preview) {
                        editBox.setWidth(this.width / 2 - 30);
                        addRenderableWidget(previewFrame);
                        addRenderableWidget(previewBox);
                        minecraft.screen.resize(width, height);
                    } else {
                        editBox.setWidth(this.width - 40);
                        removeWidget(previewFrame);
                        removeWidget(previewBox);
                        minecraft.screen.resize(width, height);
                    }
                }
        );

        var saveButton = new TransparentButton(
                Component.translatable("gui.done"), width / 2 + 7, height - 25,
                100, 20, 125, () -> {
                    assert minecraft.player != null;

                    ItemStack newTextItem = JustMCUtils.setTextValue(minecraft.player.getMainHandItem(), this.currentContent);
                    minecraft.player.getInventory().setItem(minecraft.player.getInventory().getSelectedSlot(), newTextItem);
                    minecraft.getConnection().send(new ServerboundSetCreativeModeSlotPacket(36 + minecraft.player.getInventory().getSelectedSlot(), newTextItem));
                    JustHelperCommand.feedback(Messages.TEXT_UPDATED);
                    minecraft.setScreen(null);
                }
        );

        var closeButton = new TransparentButton(
                Component.translatable("gui.cancel"), width / 2 - 107, height - 25,
                100, 20, 125, () -> minecraft.setScreen(null)
        );

        addRenderableWidget(title);
        addRenderableWidget(editBox);
        if(preview) {
            addRenderableWidget(previewFrame);
            addRenderableWidget(previewBox);
        }
        addRenderableWidget(formatting);
        addRenderableWidget(previewButton);
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
