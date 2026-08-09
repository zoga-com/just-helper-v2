package com.prikolz.justhelper.commands.arguments.searching.module;

import com.prikolz.justhelper.codespace.CodeSpace;
import com.prikolz.justhelper.commands.arguments.searching.FoundSignInfo;

public class FloorFilter extends SearchModule.NumberFilter<Integer> {
    public FloorFilter() {
        super("floor", builder -> {
            builder.add("1");
            builder.add("15");
            for (var floor : CodeSpace.getFloorDescribes()) builder.add(floor.plain);
        });
    }

    @Override
    public SearchModule create() {
        return new FloorFilter();
    }

    @Override
    Integer toNumber(String value) {
        return CodeSpace.getFloor(value);
    }

    @Override
    Integer getCompareValue(FoundSignInfo entry) {
        return entry.sign().codePos.floor;
    }

}
