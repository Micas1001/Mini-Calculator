package com.simplecalc.state;

import com.simplecalc.build.ExpressionBuilder;
import com.simplecalc.history.History;

public final class Context {
    public final ExpressionBuilder builder;
    public final History history; // já deixamos o histórico para a próxima etapa

    public Context(ExpressionBuilder builder, History history) {
        this.builder = builder;
        this.history = history;
    }

    public void reset() {
        builder.reset();
    }
}
