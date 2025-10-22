package com.simplecalc.state;

public final class AfterOperand implements ParseState {
    private final Context ctx;

    public AfterOperand(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public ParseState onNumber(int v) {
        System.out.println("Erro: dois operandos seguidos");
        return new ErrorState(ctx);
    }

    @Override
    public ParseState onOperator(String sym) {
        try {
            ctx.builder.pushOperator(sym);
            return new AfterOperator(ctx);
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            return new ErrorState(ctx);
        }
    }

    @Override
    public ParseState onEquals() {
        try {
            var expr = ctx.builder.finalizeExpr();
            int val = expr.eval();
            System.out.println("Result> " + val);
            ctx.history.add(expr); // <-- NOVO: guarda no histórico
            return new EmptyState(ctx);
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            return new ErrorState(ctx);
        }
    }

    @Override
    public ParseState onList() {
        ctx.history.list(); // <-- NOVO
        return this;
    }

    @Override
    public ParseState onMem(com.simplecalc.core.Expression e) {
        System.out.println("Erro: dois operandos seguidos"); // <-- NOVO
        return new ErrorState(ctx);
    }

    @Override
    public ParseState onCombine(com.simplecalc.core.Expression e) {
        System.out.println("Erro: dois operandos seguidos");
        return new ErrorState(ctx);
    }

}
