package com.et.antlr;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName CalbyLisenter
 * @Description todo
 * @date 2024年06月06日 16:40
 */

public class CalbyLisener {
    public static void main(String[] args) throws IOException {
      /*  String inputFile = null;
        if ( args.length>0 ) inputFile = args[0];
        InputStream is = System.in;
        if ( inputFile!=null ) is = new FileInputStream(inputFile);*/
        ANTLRInputStream input = new ANTLRInputStream("1+2*3\n");
        LabeledExprLexer lexer = new LabeledExprLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LabeledExprParser parser = new LabeledExprParser(tokens);
        ParseTree tree = parser.prog(); // parse

        ParseTreeWalker walker = new ParseTreeWalker();
        EvalListener evalListener =new EvalListener();
        walker.walk(evalListener, tree);
        int result=evalListener.getResult();
        System.out.println(result);
    }
}
