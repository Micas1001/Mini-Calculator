package com.simplecalc.ops;

public abstract class BaseOpResolver implements OperatorResolver {
    private OperatorResolver nxt;

    @Override
    public OperatorResolver next(OperatorResolver n) {
        this.nxt = n;
        return n;
    }

    @Override
    public Operator resolve(String token) {
        Operator op = tryResolve(token);
        if (op != null)
            return op;
        return (nxt != null) ? nxt.resolve(token) : null;
    }

    protected abstract Operator tryResolve(String token);
}
