package com.simplecalc.core;

import com.simplecalc.ops.Operator;

public final class ReplaceOperatorVisitor implements ExpressionVisitor {
    private final String oldSymbol;
    private final Operator newOperator;

    public ReplaceOperatorVisitor(String oldSymbol, Operator newOperator) {
        this.oldSymbol = oldSymbol;
        this.newOperator = newOperator;
    }

    @Override
    public void visit(NumberExpr n) {
        // nada a fazer em folhas
    }

    @Override
    public void visit(BinaryExpr b) {
        if (b.operator().symbol().equals(oldSymbol)) {
            b.setOperator(newOperator);
        }
        b.left().accept(this);
        b.right().accept(this);
    }
}
