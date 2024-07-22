package com.et.spire.doc;

import com.spire.doc.*;
import com.spire.doc.documents.*;
import java.util.*;

public class ModifyContentControlInBody {
    public static void main(String[] args) {
        // Create a new document object
        Document doc = new Document();

        // Load document content from file
        doc.loadFromFile("/Users/liuhaihua/tmp/WordDocument.docx");

        // Get the body of the document
        Body body = doc.getSections().get(0).getBody();

        // Create lists for paragraphs and tables
        List<Paragraph> paragraphs = new ArrayList<>();
        List<Table> tables = new ArrayList<>();
        for (int i = 0; i < body.getChildObjects().getCount(); i++) {

            // Get the document object
            DocumentObject documentObject = body.getChildObjects().get(i);

            // If it is a StructureDocumentTag object
            if (documentObject instanceof StructureDocumentTag) {
                StructureDocumentTag structureDocumentTag = (StructureDocumentTag) documentObject;

                // If the tag is "c1" or the alias is "c1"
                if (structureDocumentTag.getSDTProperties().getTag().equals("c1") || structureDocumentTag.getSDTProperties().getAlias().equals("c1")) {
                    for (int j = 0; j < structureDocumentTag.getChildObjects().getCount(); j++) {
                        // If it is a paragraph object
                        if (structureDocumentTag.getChildObjects().get(j) instanceof Paragraph) {
                            Paragraph paragraph = (Paragraph) structureDocumentTag.getChildObjects().get(j);
                            paragraphs.add(paragraph);
                        }

                        // If it is a table object
                        if (structureDocumentTag.getChildObjects().get(j) instanceof Table) {
                            Table table = (Table) structureDocumentTag.getChildObjects().get(j);
                            tables.add(table);
                        }
                    }
                }
            }
        }

        // Modify the text content of the first paragraph
        paragraphs.get(0).setText("Chengdu E-iceblue Co., Ltd. is committed to providing JAVA component development products for developers.");

        // Reset the cells of the first table to 5 rows and 4 columns
        tables.get(0).resetCells(5, 4);

        // Save the modified document to a file
        doc.saveToFile("/Users/liuhaihua/tmp/Modify Content Controls in Word Document Body.docx", FileFormat.Docx_2016);

        // Close the document and release document resources
        doc.close();
        doc.dispose();
    }
}