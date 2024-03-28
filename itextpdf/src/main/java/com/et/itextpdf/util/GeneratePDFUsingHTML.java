package com.et.itextpdf.util;


import com.itextpdf.html2pdf.HtmlConverter;

import java.io.File;
import java.io.IOException;

public class GeneratePDFUsingHTML {

    public static void main(String[] args) throws IOException {

        HtmlConverter.convertToPdf(new File("D:\\IdeaProjects\\ETFramework\\itextpdf\\src\\main\\resources\\templates\\pdf-input.html"),new File("D:\\tmp\\demo-html.pdf"));
    }
}
