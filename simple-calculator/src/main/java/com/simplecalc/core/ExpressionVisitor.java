package com.simplecalc.core;

public interface ExpressionVisitor {
    void visit(NumberExpr n);

    void visit(BinaryExpr b);
}
