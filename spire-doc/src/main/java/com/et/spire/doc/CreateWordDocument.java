package com.et.spire.doc;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Section;
import com.spire.doc.documents.HorizontalAlignment;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.ParagraphStyle;

import java.awt.*;

public class CreateWordDocument {

    public static void main(String[] args) {

        //Create a Document object
        Document doc = new Document();

        //Add a section
        Section section = doc.addSection();

        //Set the page margins
        section.getPageSetup().getMargins().setAll(40f);

        //Add a paragraph as title
        Paragraph titleParagraph = section.addParagraph();
        titleParagraph.appendText("Introduction of Spire.Doc for Java");

        //Add two paragraphs as body
        Paragraph bodyParagraph_1 = section.addParagraph();
        bodyParagraph_1.appendText("Spire.Doc for Java is a professional Word API that empowers Java applications to " +
                "create, convert, manipulate and print Word documents without dependency on Microsoft Word.");

        Paragraph bodyParagraph_2 = section.addParagraph();
        bodyParagraph_2.appendText("By using this multifunctional library, developers are able to process copious tasks " +
                "effortlessly, such as inserting image, hyperlink, digital signature, bookmark and watermark, setting " +
                "header and footer, creating table, setting background image, and adding footnote and endnote.");

        //Create and apply a style for title paragraph
        ParagraphStyle style1 = new ParagraphStyle(doc);
        style1.setName("titleStyle");
        style1.getCharacterFormat().setBold(true);;
        style1.getCharacterFormat().setTextColor(Color.BLUE);
        style1.getCharacterFormat().setFontName("Times New Roman");
        style1.getCharacterFormat().setFontSize(12f);
        doc.getStyles().add(style1);
        titleParagraph.applyStyle("titleStyle");

        //Create and apply a style for body paragraphs
        ParagraphStyle style2 = new ParagraphStyle(doc);
        style2.setName("paraStyle");
        style2.getCharacterFormat().setFontName("Times New Roman");
        style2.getCharacterFormat().setFontSize(12);
        doc.getStyles().add(style2);
        bodyParagraph_1.applyStyle("paraStyle");
        bodyParagraph_2.applyStyle("paraStyle");

        //Set the horizontal alignment of paragraphs
        titleParagraph.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
        bodyParagraph_1.getFormat().setHorizontalAlignment(HorizontalAlignment.Justify);
        bodyParagraph_2.getFormat().setHorizontalAlignment(HorizontalAlignment.Justify);

        //Set the first line indent
        bodyParagraph_1.getFormat().setFirstLineIndent(30) ;
        bodyParagraph_2.getFormat().setFirstLineIndent(30);

        //Set the after spacing
        titleParagraph.getFormat().setAfterSpacing(10);
        bodyParagraph_1.getFormat().setAfterSpacing(10);

        //Save to file
        doc.saveToFile("/Users/liuhaihua/tmp/WordDocument.docx", FileFormat.Docx_2013);
        doc.close();
    }
}