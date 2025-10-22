package com.simplecalc.ops;

public final class DivResolver extends BaseOpResolver {
    @Override
    protected Operator tryResolve(String token) {
        if (!"/".equals(token))
            return null;
        return new Operator() {
            public String symbol() {
                return "/";
            }

            public int apply(int a, int b) {
                if (b == 0)
                    throw new ArithmeticException("divisão por zero");
                return a / b;
            }
        };
    }
}
