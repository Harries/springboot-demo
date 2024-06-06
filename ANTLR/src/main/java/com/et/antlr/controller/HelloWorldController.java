package com.et.antlr.controller;

import com.et.antlr.EvalListener;
import com.et.antlr.EvalVisitor;
import com.et.antlr.LabeledExprLexer;
import com.et.antlr.LabeledExprParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public Map<String, Object> showHelloWorld() {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "HelloWorld");
        return map;
    }

    @RequestMapping("/calByVistor")
    public Integer calByVistor(@RequestParam String expr) {
        try {
            // Debug log for incoming expression
            System.out.println("Received expression: " + expr);

            // Handle URL encoded newline characters
            expr = expr.replace("%20", " ").replace("%5Cn", "\n");
            System.out.println("Processed expression: " + expr);

            // Initialize ANTLR components
            ANTLRInputStream input = new ANTLRInputStream(expr);
            LabeledExprLexer lexer = new LabeledExprLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LabeledExprParser parser = new LabeledExprParser(tokens);
            ParseTree tree = parser.prog(); // parse

            // Debug log for parse tree
            System.out.println("Parse tree: " + tree.toStringTree(parser));

            // Initialize EvalVisitor
            EvalVisitor eval = new EvalVisitor();
            int outcome = eval.visit(tree);
            return outcome;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Or handle the error in a more appropriate way
        }
    }

    @GetMapping("/calBylistener")
    public String calBylistener(@RequestParam String expr) {
        try {
            // Handle URL encoded newline characters
            expr = expr.replace("%20", " ").replace("%5Cn", "\n");
            InputStream is = new ByteArrayInputStream(expr.getBytes());
            ANTLRInputStream input = new ANTLRInputStream(is);
            LabeledExprLexer lexer = new LabeledExprLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LabeledExprParser parser = new LabeledExprParser(tokens);
            ParseTree tree = parser.prog(); // parse

            ParseTreeWalker walker = new ParseTreeWalker();
            EvalListener listener = new EvalListener();
            walker.walk(listener, tree);

            // Assuming the listener has a method to get the result of the last expression
            int result = listener.getResult();
            return String.valueOf(result);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}