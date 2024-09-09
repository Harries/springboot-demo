package com.et.jet.example;

import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sinks;
import com.hazelcast.jet.pipeline.Sources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.hazelcast.function.Functions.wholeItem;
import static com.hazelcast.jet.Traversers.traverseArray;
import static com.hazelcast.jet.aggregate.AggregateOperations.counting;
import static java.util.Comparator.comparingLong;

/**
 * Demonstrates a simple Word Count job in the Pipeline API. Inserts the
 * text of The Complete Works of William Shakespeare into a Hazelcast
 * IMap, then lets Jet count the words in it and write its findings to
 * another IMap. The example looks at Jet's output and prints the 100 most
 * frequent words.
 */
@Component
public class WordCount {

    private static final String BOOK_LINES = "bookLines";
    private static final String COUNTS = "counts";
    String filepath ="D:/tmp/shakespeare-complete-works.txt";
    @Autowired
    private JetInstance jet;

    private static Pipeline buildPipeline() {
        Pattern delimiter = Pattern.compile("\\W+");
        Pipeline p = Pipeline.create();
        p.readFrom(Sources.<Long, String>map(BOOK_LINES))
         .flatMap(e -> traverseArray(delimiter.split(e.getValue().toLowerCase())))
         .filter(word -> !word.isEmpty())
         .groupingKey(wholeItem())
         .aggregate(counting())
         .writeTo(Sinks.map(COUNTS));
        return p;
    }

    public static void main(String[] args) throws Exception {
        new WordCount().go();
    }

    /**
     * This code illustrates a few more things about Jet, new in 0.5. See comments.
     */
    public void go() {
        try {
            setup();
            System.out.println("\nCounting words... ");
            long start = System.nanoTime();
            Pipeline p = buildPipeline();
            jet.newJob(p).join();
            System.out.println("done in " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) + " milliseconds.");
            Map<String, Long> results = jet.getMap(COUNTS);

            printResults(results);
        } finally {
            Jet.shutdownAll();
        }
    }

    private void setup() {
        //jet = Jet.bootstrappedInstance();
        System.out.println("Loading The Complete Works of William Shakespeare");
        try {
            long[] lineNum = {0};
            Map<Long, String> bookLines = new HashMap<>();
            InputStream stream = new FileInputStream(filepath);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                reader.lines().forEach(line -> bookLines.put(++lineNum[0], line));
            }
            jet.getMap(BOOK_LINES).putAll(bookLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private static void printResults(Map<String, Long> counts) {
        final int limit = 100;

        StringBuilder sb = new StringBuilder(String.format(" Top %d entries are:%n", limit));
        sb.append("/-------+---------\\\n");
        sb.append("| Count | Word    |\n");
        sb.append("|-------+---------|\n");
        counts.entrySet().stream()
                .sorted(comparingLong(Map.Entry<String, Long>::getValue).reversed())
                .limit(limit)
                .forEach(e -> sb.append(String.format("|%6d | %-8s|%n", e.getValue(), e.getKey())));
        sb.append("\\-------+---------/\n");

        System.out.println(sb.toString());
    }
}