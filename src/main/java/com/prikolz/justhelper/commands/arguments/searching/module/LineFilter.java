package com.prikolz.justhelper.commands.arguments.searching.module;

import com.prikolz.justhelper.commands.arguments.searching.FoundSignInfo;

public class LineFilter extends SearchModule.NumberFilter<Integer> {
    public LineFilter() {
        super("line", builder -> {
            builder.add("1");
            builder.add("10");
        });
    }

    @Override
    Integer toNumber(String value) {
        return Integer.parseInt(value);
    }

    @Override
    Integer getCompareValue(FoundSignInfo entry) {
        return entry.sign().codePos.line;
    }

    @Override
    public SearchModule create() {
        return new LineFilter();
    }
}
