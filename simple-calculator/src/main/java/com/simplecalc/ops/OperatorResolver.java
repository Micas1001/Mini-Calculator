package com.simplecalc.ops;

public interface OperatorResolver {
    OperatorResolver next(OperatorResolver n);

    Operator resolve(String token);
}
