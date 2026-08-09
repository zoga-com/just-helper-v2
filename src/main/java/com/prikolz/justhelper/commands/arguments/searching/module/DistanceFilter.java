package com.prikolz.justhelper.commands.arguments.searching.module;

import com.prikolz.justhelper.commands.arguments.searching.FoundSignInfo;

public class DistanceFilter extends SearchModule.NumberFilter<Double> {
    public DistanceFilter(String determinant) {
        super(determinant, builder -> {
            builder.add("5");
            builder.add("50.5");
        });
    }

    @Override
    Double toNumber(String value) {
        var result = Double.parseDouble(value);
        if (result < 0 || result > 10000)
            throw new IllegalStateException("Число не входит в границы от 0 до 10000");
        return result;
    }

    @Override
    Double getCompareValue(FoundSignInfo entry) {
        return Math.sqrt(player().blockPosition().distSqr(entry.sign().pos));
    }

    @Override
    public SearchModule create() {
        return new DistanceFilter(this.determinant);
    }
}
