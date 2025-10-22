package com.simplecalc.state;

import com.simplecalc.core.Expression;

public interface ParseState {
    // Hoje só vamos usar estes três; os outros ficam para a próxima etapa
    ParseState onNumber(int v);

    ParseState onOperator(String sym);

    ParseState onEquals();

    // “ganchos” para próxima etapa (não usados agora, mas já ficam aqui)
    default ParseState onList() {
        return this;
    }

    default ParseState onMem(Expression e) {
        return this;
    }

    default ParseState onCombine(Expression e) {
        return this;
    }
}
