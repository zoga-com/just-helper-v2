package com.prikolz.justhelper.commands.arguments.searching.module;

import com.prikolz.justhelper.commands.arguments.searching.FoundSignInfo;
import com.prikolz.justhelper.commands.arguments.searching.RemainingSuggestions;
import com.prikolz.justhelper.commands.arguments.searching.SearchingSettingsReader;

import java.util.List;

public class TextFilter extends SearchModule {
    public final int line;
    public String text = "";

    public TextFilter(int line) {
        super("text" + (line + 1), BodyType.REQUIRED, '=', '<', '>');
        this.line = line;
    }

    @Override
    public SearchModule create() {
        return new TextFilter(line);
    }

    @Override
    public void read(SearchingSettingsReader reader, RemainingSuggestions suggestions) {
        text = reader.readString(suggestions).toLowerCase();
    }

    @Override
    public void applyOn(List<FoundSignInfo> list) {
        switch (operand) {
            case '=' -> filter(list, it -> it.lines()[line].toLowerCase().equals(text));
            case '<' -> filter(list, it -> it.lines()[line].toLowerCase().contains(text));
            case '>' -> filter(list, it -> text.contains(it.lines()[line].toLowerCase()));
        }
    }
}
