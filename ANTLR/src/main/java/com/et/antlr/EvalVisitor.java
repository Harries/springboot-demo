package com.et.antlr;

import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends LabeledExprBaseVisitor<Integer> {
    // Store variables (for assignment)
    Map<String, Integer> memory = new HashMap<>();

    /** stat : expr NEWLINE */
    @Override
    public Integer visitPrintExpr(LabeledExprParser.PrintExprContext ctx) {
        Integer value = visit(ctx.expr()); // evaluate the expr child
       // System.out.println(value);         // print the result
        return value;                          // return dummy value
    }

    /** stat : ID '=' expr NEWLINE */
    @Override
    public Integer visitAssign(LabeledExprParser.AssignContext ctx) {
        String id = ctx.ID().getText(); // id is left-hand side of '='
        int value = visit(ctx.expr());  // compute value of expression on right
        memory.put(id, value);          // store it in our memory
        return value;
    }

    /** expr : expr op=('*'|'/') expr */
    @Override
    public Integer visitMulDiv(LabeledExprParser.MulDivContext ctx) {
        int left = visit(ctx.expr(0));  // get value of left subexpression
        int right = visit(ctx.expr(1)); // get value of right subexpression
        if (ctx.op.getType() == LabeledExprParser.MUL) return left * right;
        return left / right; // must be DIV
    }

    /** expr : expr op=('+'|'-') expr */
    @Override
    public Integer visitAddSub(LabeledExprParser.AddSubContext ctx) {
        int left = visit(ctx.expr(0));  // get value of left subexpression
        int right = visit(ctx.expr(1)); // get value of right subexpression
        if (ctx.op.getType() == LabeledExprParser.ADD) return left + right;
        return left - right; // must be SUB
    }

    /** expr : INT */
    @Override
    public Integer visitInt(LabeledExprParser.IntContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }

    /** expr : ID */
    @Override
    public Integer visitId(LabeledExprParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if (memory.containsKey(id)) return memory.get(id);
        return 0; // default value if the variable is not found
    }

    /** expr : '(' expr ')' */
    @Override
    public Integer visitParens(LabeledExprParser.ParensContext ctx) {
        return visit(ctx.expr()); // return child expr's value
    }

    /** stat : NEWLINE */
    @Override
    public Integer visitBlank(LabeledExprParser.BlankContext ctx) {
        return 0; // return dummy value
    }
}
