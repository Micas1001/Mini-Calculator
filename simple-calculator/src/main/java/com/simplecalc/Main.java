package com.simplecalc;

import com.simplecalc.build.ExpressionBuilder;
import com.simplecalc.core.NumberFactory;
import com.simplecalc.core.Expression;
import com.simplecalc.core.ReplaceOperatorVisitor;
import com.simplecalc.history.History;
import com.simplecalc.ops.*;
import com.simplecalc.state.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Chain: + -> - -> * -> /
        OperatorResolver resolver = new PlusResolver();
        resolver.next(new MinusResolver()).next(new TimesResolver()).next(new DivResolver());

        NumberFactory nf = new NumberFactory();
        History history = new History();
        ExpressionBuilder builder = new ExpressionBuilder(nf, resolver);

        Context ctx = new Context(builder, history);
        ParseState state = new EmptyState(ctx);

        System.out.println("Simple Calculator — tokens separados por espaço.");
        System.out.println("Comandos: '=' avalia | 'L' lista | 'M{i}' usa expr do histórico");
        System.out.println("          'R i oldOp newOp' substitui operadores numa expr do histórico");
        System.out.println("          'C i j op' combina expr[i] (op) expr[j] como um operando");
        System.out.println(
                "Exemplo completo do enunciado: 1 + 2 * 4 = | L | 1 + 2 = | L | M1 + 3 = | L | R 0 * + | L | C 1 2 * = | L");
        System.out.println("Escreve 'exit' para sair.\n");

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.print("Input> ");
                String line = sc.nextLine().trim();
                if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("quit"))
                    break;
                if (line.isEmpty())
                    continue;

                String[] toks = line.split("\\s+");
                for (int i = 0; i < toks.length; i++) {
                    String t = toks[i];

                    // '=' avalia a expressão corrente
                    if ("=".equals(t)) {
                        state = state.onEquals();
                        continue;
                    }

                    // 'L' lista o histórico
                    if ("L".equalsIgnoreCase(t)) {
                        state = state.onList();
                        continue;
                    }

                    // 'M{i}' puxa uma expressão do histórico como operando
                    if (t.matches("M\\d+")) {
                        int idx = Integer.parseInt(t.substring(1));
                        try {
                            Expression e = history.get(idx);
                            state = state.onMem(e);
                        } catch (Exception ex) {
                            System.out.println("Erro: índice M inválido (" + idx + ")");
                            state = new ErrorState(ctx);
                        }
                        continue;
                    }

                    // 'R i oldOp newOp' (consome +3 tokens)
                    if ("R".equalsIgnoreCase(t)) {
                        if (i + 3 >= toks.length) {
                            System.out.println("Erro: R requer 3 argumentos (i oldOp newOp)");
                            state = new ErrorState(ctx);
                            break;
                        }
                        int idx;
                        try {
                            idx = Integer.parseInt(toks[++i]);
                        } catch (NumberFormatException nfe) {
                            System.out.println("Erro: índice inválido em R");
                            state = new ErrorState(ctx);
                            continue;
                        }
                        String oldOp = toks[++i];
                        String newOp = toks[++i];

                        Expression target;
                        try {
                            target = history.get(idx);
                        } catch (Exception ex) {
                            System.out.println("Erro: índice inexistente em R");
                            state = new ErrorState(ctx);
                            continue;
                        }

                        Operator newOperator = resolver.resolve(newOp);
                        if (newOperator == null) {
                            System.out.println("Erro: operador novo desconhecido em R: " + newOp);
                            state = new ErrorState(ctx);
                            continue;
                        }

                        target.accept(new ReplaceOperatorVisitor(oldOp, newOperator));
                        System.out.println("OK: substituição em " + idx + " (" + oldOp + " -> " + newOp + ")");
                        continue;
                    }

                    // 'C i j op' (consome +3 tokens) -> produz um OPERANDO combinado
                    if ("C".equalsIgnoreCase(t)) {
                        if (i + 3 >= toks.length) {
                            System.out.println("Erro: C requer 3 argumentos (i j op)");
                            state = new ErrorState(ctx);
                            break;
                        }
                        int i1, i2;
                        try {
                            i1 = Integer.parseInt(toks[++i]);
                            i2 = Integer.parseInt(toks[++i]);
                        } catch (NumberFormatException nfe) {
                            System.out.println("Erro: índices inválidos em C");
                            state = new ErrorState(ctx);
                            continue;
                        }
                        String opSym = toks[++i];

                        try {
                            Expression combined = history.combine(i1, i2, opSym, resolver);
                            state = state.onCombine(combined);
                        } catch (IllegalArgumentException iae) {
                            System.out.println("Erro: " + iae.getMessage());
                            state = new ErrorState(ctx);
                        }
                        continue;
                    }

                    // Operador conhecido?
                    Operator op = resolver.resolve(t);
                    if (op != null) {
                        state = state.onOperator(t);
                        continue;
                    }

                    // Número?
                    try {
                        int n = Integer.parseInt(t);
                        state = state.onNumber(n);
                    } catch (NumberFormatException nfe) {
                        System.out.println("Erro: token desconhecido '" + t + "'");
                        state = new ErrorState(ctx);
                    }
                }
            }
        }

        System.out.println("Bye!");
    }
}
