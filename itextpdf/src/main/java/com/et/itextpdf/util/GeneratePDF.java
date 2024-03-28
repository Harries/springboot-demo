package com.et.itextpdf.util;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;


public class GeneratePDF {

    public static void main(String[] args) throws FileNotFoundException, MalformedURLException {

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter("demo.pdf",
                new WriterProperties().addUAXmpMetadata().setPdfVersion(PdfVersion.PDF_1_7)));
        Document document = new Document(pdfDoc, PageSize.A4.rotate());
        pdfDoc.getCatalog().setViewerPreferences(new PdfViewerPreferences().setDisplayDocTitle(true));
        pdfDoc.getCatalog().setLang(new PdfString("en-IN"));
        pdfDoc.getDocumentInfo().setTitle("SpringHow Tutorials");
        Paragraph p = new Paragraph();
        p.setFontSize(18);
        p.add("Lorum ipsum some text before image. Lorum ipsum some text before image. Lorum ipsum some text before image. Lorum ipsum some text before image. Lorum ipsum some text before image. Lorum ipsum some text before image. Lorum ipsum some text before image. Lorum ipsum some text before image. ");

        Image img = new Image(ImageDataFactory.create("photo.jpg"));
        img.getAccessibilityProperties().setAlternateDescription("Orange");
        p.add(img);

        p.add("Lorum ipsum some text after image. Lorum ipsum some text after image. Lorum ipsum some text after image. Lorum ipsum some text after image. Lorum ipsum some text after image. Lorum ipsum some text after image. Lorum ipsum some text after image. Lorum ipsum some text after image. Lorum ipsum some text after image. ");

        document.add(p);
        Table table = new Table(4);
        table.setWidth(UnitValue.createPercentValue(100));
        table.addHeaderCell("Product");
        table.addHeaderCell("QTY");
        table.addHeaderCell("Price");
        table.addHeaderCell("Total");
        table.addCell("Jeans");
        table.addCell("2");
        table.addCell("10.99");
        table.addCell("20.98");
        table.addCell("Shirt");
        table.addCell("2");
        table.addCell("7.99");
        table.addCell("14.98");
        document.add(table);
        document.close();
    }
}
