package com.simplecalc.ops;

public interface Operator {
    String symbol();

    int apply(int a, int b);
}
