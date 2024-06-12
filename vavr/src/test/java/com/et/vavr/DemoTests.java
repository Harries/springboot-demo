package com.et.vavr;

import cn.hutool.core.util.StrUtil;
import io.vavr.*;
import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.Function;
import java.util.function.IntBinaryOperator;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoTests {
    private Logger log = LoggerFactory.getLogger(getClass());


    @Before
    public void before()  {
        log.info("init some data");
    }
    @After
    public void after(){
        log.info("clean some data");
    }
    @Test
    public void execute()  {
        log.info("your method test Code");

    }
    //https://www.someget.cn/java/2021/11/21/java-java8_02.html
    @Test
    public void test1() {
        Option<Object> noneOption = Option.of(null);
        Option<Object> someOption = Option.of("val");

        //空被包装成None；非空被包装成Some
        Assert.assertEquals("None", noneOption.toString());
        Assert.assertEquals("Some(val)", someOption.toString());

        //使用Option内部的值需要get
        Assert.assertEquals("valss", someOption.get() + "ss");

        //Null时可以设置默认值
        Assert.assertEquals("baeldung", noneOption.getOrElse("baeldung"));
        Assert.assertEquals("val", someOption.getOrElse("baeldung"));
    }

    @Test
    public void test2() {
        Tuple3<String, Integer, Double> java8 = Tuple.of("Java", 8, 1.8);
        //https://www.twblogs.net/a/5d4051e1bd9eee51fbf98d8a/?lang=zh-cn
        String element1 = java8._1;
        int element2 = java8._2;
        double element3 = java8._3;

        Assert.assertEquals("Java", element1);
        Assert.assertEquals(8, element2);
        Assert.assertEquals(1.8, element3, 0.01);
    }
    @Test
    public void test3() {
        Try<Integer> result = Try.of(() -> 1 / 0);

        //执行结果是否成功
        Assert.assertFalse(result.isSuccess());
        Assert.assertTrue(result.isFailure());

        //获取结果
        Integer orElse = result.getOrElse(-1);
        Assert.assertEquals(Integer.valueOf(-1), orElse);

        //finally
        Try<Integer> result1 = Try.of(() -> 1 / 0).andFinallyTry(() -> System.out.println("资源释放"));

        //重新包装一个异常抛出
        result.getOrElseThrow(() -> new ArithmeticException("除数为0"));
    }
    @Test
    public void test4() {
        IntBinaryOperator intBinaryOperator = (int even, int odd) -> even + odd;
        int i = intBinaryOperator.applyAsInt(3, 3);
        Assert.assertEquals(6, i);

        //jdk默认的Function
        Function<Integer, Integer> f = (Integer x) -> x * x;
        Integer apply = f.apply(3);
        Assert.assertEquals(9, apply.intValue());

        //测试4个的
        Function4<String, String, String, String, String> concat = (a, b, c, d) -> a + b + c + d;
        String finalString = concat.apply("你", "好", "Vavr", "。");
        Assert.assertEquals("你好Vavr。", finalString);
    }
    @Test
    public void test5() {
        List<Integer> list1 = List.of(1, 2, 3);
//        list1.add(2); // 这里list1是不可变的，add方法没有被隐藏，直接报错

        io.vavr.collection.List<Integer> list2 = io.vavr.collection.List.of(1, 2, 3);
        io.vavr.collection.List<Integer> list3 = list2.append(4);//list2不会改变，返回值是一个新的list集合

        System.out.println(list2); // 输出：List(1, 2, 3)
        System.out.println(list3); // 输出：List(1, 2, 3, 4)

        //使用一些vavr提供的函数进行计算
        int sum = io.vavr.collection.List.of(1, 2, 3).sum().intValue();
        Assert.assertEquals(6, sum);
    }
    @Test
    public void test6(){
        Lazy<Double> lazy = Lazy.of(Math::random);
        Assert.assertFalse(lazy.isEvaluated()); // 函数并不会被执行

        double val1 = lazy.get();
        Assert.assertTrue(lazy.isEvaluated());

        double val2 = lazy.get();// 读取缓存
        Assert.assertEquals(val1, val2, 0.1);
    }
    @Test
    public void test7() {
        int input = 2;
        String output = API.Match(input).of(
                API.Case(API.$(1), "one"),
                API.Case(API.$(2), "two"),
                API.Case(API.$(3), "three"),
                API.Case(API.$(), "?"));

        Assert.assertEquals("two", output);


        //java switch case
        String op = null;
        switch (input) {
            case 1:
                op = "one";
                break;
            case 2:
                op = "two";
                break;
            case 3:
                op = "three";
                break;
            default:
                op = "?";
        }
        Assert.assertEquals("two", op);
    }
}

