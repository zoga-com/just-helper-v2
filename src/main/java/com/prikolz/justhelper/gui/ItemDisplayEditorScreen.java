package com.prikolz.justhelper.gui;

import com.prikolz.justhelper.commands.ItemEditorCommand;
import com.prikolz.justhelper.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;

import java.util.ArrayList;
import java.util.List;

public class ItemDisplayEditorScreen extends Screen {
    private String nameField;
    private String loreField;
    private EditBox nameEditBox = null;
    private MultiLineEditBox loreEditBox = null;

    public ItemDisplayEditorScreen(ItemStack item) {
        super(Component.literal("Редактирование отображения предмета"));
        var lore = item.get(DataComponents.LORE);

        var lines = lore == null ? List.<Component>of() : lore.lines();
        StringBuilder minimessage = new StringBuilder();
        for (Component line : lines) {
            minimessage.append(TextUtils.toMiniMessage(line)).append('\n');
        }
        loreField = minimessage.toString();
        nameField = TextUtils.toMiniMessage(item.getOrDefault(DataComponents.CUSTOM_NAME, item.getItemName()));
    }

    @Override
    public void init() {
        if (loreEditBox != null) loreField = loreEditBox.getValue();
        if (nameEditBox != null) nameField = nameEditBox.getValue();

        var font = Minecraft.getInstance().font;

        var title = new StringWidget(this.title, font);
        title.setPosition(width / 2 - font.width(this.title) / 2, 10);

        nameEditBox = new EditBox(font, 60, 30, Component.literal("Название"));
        nameEditBox.setX(60);
        nameEditBox.setY(30);
        nameEditBox.setWidth(width - 120);
        nameEditBox.setHeight(20);
        nameEditBox.setMaxLength(Integer.MAX_VALUE);
        nameEditBox.setValue(nameField);
        var nameTitle = new StringWidget(Component.literal("Название"), font);
        nameTitle.setPosition(nameEditBox.getX(), nameEditBox.getY() - 10);

        loreEditBox = new MultiLineEditBox.Builder().setX(60).setY(nameEditBox.getY() + nameEditBox.getHeight() + 20)
                .build(font, width - 120, height - 120, Component.literal("Описание"));
        loreEditBox.setValue(loreField);
        var loreTitle = new StringWidget(Component.literal("Описание"), font);
        loreTitle.setPosition(loreEditBox.getX(), loreEditBox.getY() - 10);

        var ok = Button.builder(Component.literal("Применить"), button -> ItemEditorCommand.itemResolver(item -> {
            var list = new ArrayList<Component>();
            var content = loreEditBox.getValue();
            for (String line : content.split("\n")) list.add(TextUtils.minimessage(line));
            item.set(DataComponents.LORE, new ItemLore(list));
            item.set(DataComponents.CUSTOM_NAME, TextUtils.minimessage(nameEditBox.getValue()));
            Minecraft.getInstance().setScreen(null);
            return 1;
        })).pos(width / 2 + 5, loreEditBox.getHeight() + loreEditBox.getY() + 10).width(100).build();

        var cancel = Button.builder(Component.literal("Отмена"), button -> {
            Minecraft.getInstance().setScreen(null);
        }).pos(width / 2 - 105, loreEditBox.getHeight() + loreEditBox.getY() + 10).width(100).build();

        addRenderableWidget(title);
        addRenderableWidget(nameTitle);
        addRenderableWidget(nameEditBox);
        addRenderableWidget(loreTitle);
        addRenderableWidget(loreEditBox);
        addRenderableWidget(ok);
        addRenderableWidget(cancel);
    }
}
