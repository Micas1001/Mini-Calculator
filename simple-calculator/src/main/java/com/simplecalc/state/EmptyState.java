package com.simplecalc.state;

public final class EmptyState implements ParseState {
    private final Context ctx;

    public EmptyState(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public ParseState onNumber(int v) {
        ctx.builder.pushNumber(v);
        return new AfterOperand(ctx);
    }

    @Override
    public ParseState onOperator(String sym) {
        System.out.println("Erro: operador inesperado");
        return new ErrorState(ctx);
    }

    @Override
    public ParseState onEquals() {
        System.out.println("Erro: expressão vazia");
        return new ErrorState(ctx);
    }

    @Override
    public ParseState onList() {
        ctx.history.list(); // <-- NOVO
        return this;
    }

    @Override
    public ParseState onMem(com.simplecalc.core.Expression e) {
        ctx.builder.pushOperand(e); // <-- NOVO
        return new AfterOperand(ctx);
    }

    @Override
    public ParseState onCombine(com.simplecalc.core.Expression e) {
        ctx.builder.pushOperand(e);
        return new AfterOperand(ctx);
    }

}
