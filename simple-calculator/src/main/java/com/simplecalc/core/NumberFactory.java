package com.simplecalc.core;

import java.util.HashMap;
import java.util.Map;

public final class NumberFactory {
    private final Map<Integer, NumberExpr> cache = new HashMap<>();

    public Expression get(int value) {
        return cache.computeIfAbsent(value, NumberExpr::new);
    }
}
