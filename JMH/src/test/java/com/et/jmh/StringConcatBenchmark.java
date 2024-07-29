package com.et.jmh;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
public class StringConcatBenchmark {
    private String str1;
    private String str2;

    @Setup
    public void setup() {
        str1 = "Hello";
        str2 = "World";
    }

    @TearDown
    public void tearDown() {
        str1 = null;
        str2 = null;
    }

    @Benchmark
    public String testStringConcat() {
        return str1 + " " + str2;
    }
	@Test
	public void testStringConcatBenchmark() throws Exception {
		Options options = new OptionsBuilder()
				.include(StringConcatBenchmark.class.getSimpleName())
				.forks(1)
				.threads(1)
				.warmupIterations(5)
				.measurementIterations(5)
				.mode(Mode.AverageTime)
				.build();

		new Runner(options).run();
	}
}