package com.simplecalc.history;

import com.simplecalc.core.Expression;
import java.util.ArrayList;
import java.util.List;

public final class History {
    private final List<Expression> list = new ArrayList<>();

    public void add(Expression e) {
        list.add(e);
    }

    public Expression get(int i) {
        return list.get(i);
    }

    public int size() {
        return list.size();
    }

    public void list() {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ": " + list.get(i).print());
        }
    }

    public Expression combine(int i, int j, String sym, com.simplecalc.ops.OperatorResolver resolver) {
        if (i < 0 || i >= list.size() || j < 0 || j >= list.size())
            throw new IllegalArgumentException("índices inválidos em C");

        var op = resolver.resolve(sym);
        if (op == null)
            throw new IllegalArgumentException("operador desconhecido em C: " + sym);

        var left = get(i);
        var right = get(j);
        return new com.simplecalc.core.BinaryExpr(left, right, op);
    }

}
