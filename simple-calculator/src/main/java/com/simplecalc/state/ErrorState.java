package com.simplecalc.state;

import com.simplecalc.core.Expression;

public final class ErrorState implements ParseState {
    private final Context ctx;

    public ErrorState(Context ctx) {
        this.ctx = ctx;
    }

    private ParseState recover() {
        ctx.reset(); // limpa a expressão atual
        return new EmptyState(ctx); // volta ao início
    }

    @Override
    public ParseState onNumber(int v) {
        return recover().onNumber(v);
    }

    @Override
    public ParseState onOperator(String sym) {
        System.out.println("Reinício.");
        return recover();
    }

    @Override
    public ParseState onEquals() {
        return recover();
    }

    // ganchos não usados hoje
    @Override
    public ParseState onList() {
        return recover();
    }

    @Override
    public ParseState onMem(Expression e) {
        return recover();
    }

    @Override
    public ParseState onCombine(Expression e) {
        return recover();
    }
}
