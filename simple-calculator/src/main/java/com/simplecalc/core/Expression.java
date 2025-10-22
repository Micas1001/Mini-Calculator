package com.simplecalc.core;

public interface Expression {
    int eval();

    String print();

    void accept(ExpressionVisitor v); // <-- NOVO
}
