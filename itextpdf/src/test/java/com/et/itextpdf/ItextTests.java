package com.et.itextpdf;

import com.itextpdf.html2pdf.HtmlConverter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class ItextTests {
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


    public static void main(String[] args) throws IOException {
        String ORIG = "/uploads/input.html";
        final String OUTPUT_FOLDER = "/myfiles/";
        File htmlSource = new File(ORIG);
        File pdfDest = new File(OUTPUT_FOLDER + "output.pdf");
        HtmlConverter.convertToPdf(new FileInputStream(htmlSource), new FileOutputStream(pdfDest));
    }
}

