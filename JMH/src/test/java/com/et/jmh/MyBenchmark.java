package com.et.jmh;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@State(Scope.Thread)
public class MyBenchmark {
    private List<String> list;

    @Setup
    public void setup() {
        list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(UUID.randomUUID().toString());
        }
    }

    @TearDown
    public void tearDown() {
        list = null;
    }

    @Benchmark
    public void testSort() {
        Collections.sort(list);
    }
	@Test
	public void testMyBenchmark() throws Exception {
		Options options = new OptionsBuilder()
				.include(MyBenchmark.class.getSimpleName())
				.forks(1)
				.threads(1)
				.warmupIterations(5)
				.measurementIterations(5)
				.mode(Mode.Throughput)
				.build();

		new Runner(options).run();
	}
}