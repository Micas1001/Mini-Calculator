package com.simplecalc.build;

import com.simplecalc.core.Expression;
import com.simplecalc.core.NumberFactory;
import com.simplecalc.core.BinaryExpr;
import com.simplecalc.ops.Operator;
import com.simplecalc.ops.OperatorResolver;

public final class ExpressionBuilder {
    private final NumberFactory nf;
    private final OperatorResolver resolver;

    private Expression current;
    private Operator pendingOp;

    public ExpressionBuilder(NumberFactory nf, OperatorResolver resolver) {
        this.nf = nf;
        this.resolver = resolver;
    }

    // Atalho para números
    public void pushNumber(int v) {
        pushOperand(nf.get(v));
    }

    // Usa um Expression já formado (ex.: no futuro C i j op)
    public void pushOperand(Expression operand) {
        if (current == null) {
            current = operand;
        } else if (pendingOp != null) {
            current = new BinaryExpr(current, operand, pendingOp);
            pendingOp = null;
        } else {
            throw new IllegalStateException("Operando inesperado (dois operandos seguidos?)");
        }
    }

    public void pushOperator(String sym) {
        Operator op = resolver.resolve(sym);
        if (op == null)
            throw new IllegalArgumentException("Operador desconhecido: " + sym);
        if (current == null)
            throw new IllegalStateException("Operador sem operando");
        if (pendingOp != null)
            throw new IllegalStateException("Dois operadores seguidos");
        pendingOp = op;
    }

    public Expression finalizeExpr() {
        if (current == null)
            throw new IllegalStateException("Expressão vazia");
        if (pendingOp != null)
            throw new IllegalStateException("Expressão incompleta");
        Expression out = current;
        current = null;
        return out;
    }

    public void reset() {
        current = null;
        pendingOp = null;
    }
}
