package com.prikolz.justhelper.commands.arguments.searching.module;

import com.prikolz.justhelper.commands.arguments.searching.FoundSignInfo;
import com.prikolz.justhelper.commands.arguments.searching.RemainingSuggestions;
import com.prikolz.justhelper.commands.arguments.searching.SearchingSettingsReader;

import java.util.Comparator;
import java.util.List;

public class NearbySort extends SearchModule {
    public static final String ID = "nearby";

    public NearbySort() {
        super(ID);
    }

    @Override
    public SearchModule create() { return new NearbySort(); }

    @Override
    public void read(SearchingSettingsReader reader, RemainingSuggestions suggestions) {}

    @Override
    public void applyOn(List<FoundSignInfo> list) {
        var pos = player().blockPosition();
        sort( list, Comparator.comparingDouble(info -> info.sign().pos.distSqr(pos)) );
    }
}
