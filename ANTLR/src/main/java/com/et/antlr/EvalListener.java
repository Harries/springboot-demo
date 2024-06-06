package com.et.antlr;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.Map;

public class EvalListener extends LabeledExprBaseListener {
    // Store variables (for assignment)
    private final Map<String, Integer> memory = new HashMap<>();
    // Store expression results
    private final ParseTreeProperty<Integer> values = new ParseTreeProperty<>();
    private int result=0;
    @Override
    public void exitPrintExpr(LabeledExprParser.PrintExprContext ctx) {
        int value = values.get(ctx.expr());
        //System.out.println(value);
        result=value;
    }
    public int getResult() {
       return result;
    }
    @Override
    public void exitAssign(LabeledExprParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        int value = values.get(ctx.expr());
        memory.put(id, value);
    }

    @Override
    public void exitMulDiv(LabeledExprParser.MulDivContext ctx) {
        int left = values.get(ctx.expr(0));
        int right = values.get(ctx.expr(1));
        if (ctx.op.getType() == LabeledExprParser.MUL) {
            values.put(ctx, left * right);
        } else {
            values.put(ctx, left / right);
        }
    }

    @Override
    public void exitAddSub(LabeledExprParser.AddSubContext ctx) {
        int left = values.get(ctx.expr(0));
        int right = values.get(ctx.expr(1));
        if (ctx.op.getType() == LabeledExprParser.ADD) {
            values.put(ctx, left + right);
        } else {
            values.put(ctx, left - right);
        }

    }

    @Override
    public void exitInt(LabeledExprParser.IntContext ctx) {
        int value = Integer.parseInt(ctx.INT().getText());
        values.put(ctx, value);
    }

    @Override
    public void exitId(LabeledExprParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if (memory.containsKey(id)) {
            values.put(ctx, memory.get(id));
        } else {
            values.put(ctx, 0); // default value if the variable is not found
        }
    }

    @Override
    public void exitParens(LabeledExprParser.ParensContext ctx) {
        values.put(ctx, values.get(ctx.expr()));
    }
}
