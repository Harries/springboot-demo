package com.et.graalvm.js;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class RemoteJSTest {
    public static void main(String[] args) throws IOException, ScriptException {
        URL jsUrl = new URL("https://example.com/script.js"); // js文件的URL
        URLConnection connection = jsUrl.openConnection();
        InputStream inputStream = connection.getInputStream(); // 获取js文件的流

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n"); // 将js文件的内容存入StringBuilder
        }
        reader.close();
        inputStream.close();

        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("nashorn"); // 获取Nashorn引擎
        String script = sb.toString(); // js文件的内容
        engine.eval(script); // 运行js文件

        Object result = engine.eval("hello()"); // 调用js文件中名为"hello"的函数
        System.out.println(result); // 输出结果
    }
}