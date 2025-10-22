package com.simplecalc.core;

import com.simplecalc.ops.Operator;

public final class BinaryExpr implements Expression {
    private Expression left, right;
    private Operator op;

    public BinaryExpr(Expression left, Expression right, Operator op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    public Expression left() {
        return left;
    } // <-- NOVO

    public Expression right() {
        return right;
    } // <-- NOVO

    public Operator operator() {
        return op;
    } // <-- NOVO

    public void setOperator(Operator op) {
        this.op = op;
    } // <-- NOVO

    @Override
    public int eval() {
        return op.apply(left.eval(), right.eval());
    }

    @Override
    public String print() {
        String lp = (left instanceof BinaryExpr) ? "(" + left.print() + ")" : left.print();
        String rp = (right instanceof BinaryExpr) ? "(" + right.print() + ")" : right.print();
        return lp + op.symbol() + rp;
    }

    @Override
    public void accept(ExpressionVisitor v) {
        v.visit(this);
    } // <-- NOVO
}
