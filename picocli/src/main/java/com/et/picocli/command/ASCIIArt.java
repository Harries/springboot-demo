package com.et.picocli.command; /**
 * ASCII Art: Basic Picocli based sample application
 * Explanation: <a href="https://picocli.info/quick-guide.html#_basic_example_asciiart">Picocli quick guide</a>
 * Source Code: <a href="https://github.com/remkop/picocli/blob/master/picocli-examples/src/main/java/picocli/examples/i18n/I18NDemo.java">GitHub</a> 
 * @author Andreas Deininger
 */
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

@Command(name = "ASCIIArt", version = "ASCIIArt 1.0", mixinStandardHelpOptions = true) // |1|
public class ASCIIArt implements Runnable { // |2|

    @Option(names = { "-s", "--font-size" }, description = "Font size") // |3|
    int fontSize = 14;

    @Parameters(paramLabel = "<word>", defaultValue = "Hello, picocli",  // |4|
               description = "Words to be translated into ASCII art.")
    private String[] words = { "Hello,", "picocli" }; // |5|

    @Override
    public void run() { // |6|
        // https://stackoverflow.com/questions/7098972/ascii-art-java
        BufferedImage image = new BufferedImage(144, 32, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setFont(new Font("Dialog", Font.PLAIN, fontSize));
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.drawString(String.join(" ", words), 6, 24);

        for (int y = 0; y < 32; y++) {
            StringBuilder builder = new StringBuilder();
            for (int x = 0; x < 144; x++)
                builder.append(image.getRGB(x, y) == -16777216 ? " " : image.getRGB(x, y) == -1 ? "#" : "*");
            if (builder.toString().trim().isEmpty()) continue;
            System.out.println(builder);
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new ASCIIArt()).execute(args); // |7|
        System.exit(exitCode); // |8|
    }
}