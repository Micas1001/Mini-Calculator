package com.simplecalc.core;

public final class NumberExpr implements Expression {
    private final int value;

    public NumberExpr(int value) {
        this.value = value;
    }

    @Override
    public int eval() {
        return value;
    }

    @Override
    public String print() {
        return Integer.toString(value);
    }

    @Override
    public void accept(ExpressionVisitor v) {
        v.visit(this);
    } // <-- NOVO
}
