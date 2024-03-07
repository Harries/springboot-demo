package com.et.graalvm.js;

import org.junit.Test;

import javax.script.*;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName com.et.graalvm.js.JSTest
 * @Description todo
 * @date 2024年03月07日 9:47
 */

public class JSTest {
    @Test
    public  void test10(){
        // 创建JavaScript引擎
        ScriptEngine jsEngine = new ScriptEngineManager().getEngineByName("js");
//        ScriptEngine jsEngine = new ScriptEngineManager().getEngineByExtension("js");
//        ScriptEngine jsEngine = new ScriptEngineManager().getEngineByMimeType("text/javascript");
        // 方式一，默认设置变量
        jsEngine.put("hello", "jack");
        // 方式二，使用binding设置变量
        SimpleBindings bindings = new SimpleBindings();
        bindings.put("hello","world");
        jsEngine.setBindings(bindings, ScriptContext.GLOBAL_SCOPE);
        // 方式三，使用Context设置变量
        ScriptContext scriptContext = jsEngine.getContext();
        scriptContext.setAttribute("hello", "polo", ScriptContext.ENGINE_SCOPE);
        try {
            // 方式一，直接调用
            jsEngine.eval("print(hello)");
            // 方式二，编译调用(需要重复调用，建议先编译后调用)
            if (jsEngine instanceof Compilable){
                CompiledScript compileScript = ((Compilable) jsEngine).compile("print(hello)");
                if (compileScript != null){
                    for (int i = 0; i < 10; i++) {
                        compileScript.eval();
                    }
                }
            }
            Invocable invocable = ((Invocable)jsEngine);
            // 方式三，使用JavaScript中的顶层方法
            jsEngine.eval("function greet(name) { print('Hello, ' + name); } ");
            invocable.invokeFunction("greet", "tom");
            // 方式四，使用某个对象的方法
            jsEngine.eval("var obj = { getGreeting: function(name){ return 'hello, ' + name; } } ");
            Object obj = jsEngine.get("obj");
            Object result = invocable.invokeMethod(obj, "getGreeting", "kitty");
            System.out.println(result);

        } catch (ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
