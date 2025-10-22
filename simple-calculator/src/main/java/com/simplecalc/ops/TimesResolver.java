package com.simplecalc.ops;

public final class TimesResolver extends BaseOpResolver {
    @Override
    protected Operator tryResolve(String token) {
        if (!"*".equals(token))
            return null;
        return new Operator() {
            public String symbol() {
                return "*";
            }

            public int apply(int a, int b) {
                return a * b;
            }
        };
    }
}
