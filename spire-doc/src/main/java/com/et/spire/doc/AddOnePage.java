package com.et.spire.doc;

import com.spire.doc.*;
import com.spire.doc.documents.*;

public class AddOnePage {
    public static void main(String[] args) {
        // Create a new document object
        Document document = new Document();

        // Load a sample document from a file
        document.loadFromFile("/Users/liuhaihua/tmp/WordDocument.docx");

        // Get the body of the last section of the document
        Body body = document.getLastSection().getBody();

        // Insert a page break after the last paragraph in the body
        body.getLastParagraph().appendBreak(BreakType.Page_Break);

        // Create a new paragraph style
        ParagraphStyle paragraphStyle = new ParagraphStyle(document);
        paragraphStyle.setName("CustomParagraphStyle1");
        paragraphStyle.getParagraphFormat().setLineSpacing(12);
        paragraphStyle.getParagraphFormat().setAfterSpacing(8);
        paragraphStyle.getCharacterFormat().setFontName("Microsoft YaHei");
        paragraphStyle.getCharacterFormat().setFontSize(12);

        // Add the paragraph style to the document's style collection
        document.getStyles().add(paragraphStyle);

        // Create a new paragraph and set the text content
        Paragraph paragraph = new Paragraph(document);
        paragraph.appendText("Thank you for using our Spire.Doc for Java product. The trial version will add a red watermark to the generated result document and only supports converting the first 10 pages to other formats. Upon purchasing and applying a license, these watermarks will be removed, and the functionality restrictions will be lifted.");

        // Apply the paragraph style
        paragraph.applyStyle(paragraphStyle.getName());

        // Add the paragraph to the body's content collection
        body.getChildObjects().add(paragraph);

        // Create another new paragraph and set the text content
        paragraph = new Paragraph(document);
        paragraph.appendText("To fully experience our product, we provide a one-month temporary license for each of our customers for free. Please send an email to sales@e-iceblue.com, and we will send the license to you within one working day.");

        // Apply the paragraph style
        paragraph.applyStyle(paragraphStyle.getName());

        // Add the paragraph to the body's content collection
        body.getChildObjects().add(paragraph);

        // Save the document to a specified path
        document.saveToFile("/Users/liuhaihua/tmp/Add a Page.docx", FileFormat.Docx);

        // Close the document
        document.close();

        // Dispose of the document object's resources
        document.dispose();
    }
}