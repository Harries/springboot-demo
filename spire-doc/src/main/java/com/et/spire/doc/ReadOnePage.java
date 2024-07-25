package com.et.spire.doc;

import com.spire.doc.*;
import com.spire.doc.pages.*;
import com.spire.doc.documents.*;

public class ReadOnePage {
    public static void main(String[] args) {
        // Create a new document object
        Document document = new Document();

        // Load document content from the specified file
        document.loadFromFile("/Users/liuhaihua/tmp/WordDocument.docx");

        // Create a fixed layout document object
        FixedLayoutDocument layoutDoc = new FixedLayoutDocument(document);

        // Get the first page
        FixedLayoutPage page = layoutDoc.getPages().get(0);

        // Get the section where the page is located
        Section section = page.getSection();

        // Get the first paragraph of the page
        Paragraph paragraphStart = page.getColumns().get(0).getLines().getFirst().getParagraph();
        int startIndex = 0;
        if (paragraphStart != null) {
            // Get the index of the paragraph in the section
            startIndex = section.getBody().getChildObjects().indexOf(paragraphStart);
        }

        // Get the last paragraph of the page
        Paragraph paragraphEnd = page.getColumns().get(0).getLines().getLast().getParagraph();

        int endIndex = 0;
        if (paragraphEnd != null) {
            // Get the index of the paragraph in the section
            endIndex = section.getBody().getChildObjects().indexOf(paragraphEnd);
        }

        // Create a new document object
        Document newdoc = new Document();

        // Add a new section
        Section newSection = newdoc.addSection();

        // Clone the properties of the original section to the new section
        section.cloneSectionPropertiesTo(newSection);

        // Copy the content of the original document's page to the new document
        for (int i = startIndex; i <=endIndex; i++)
        {
            newSection.getBody().getChildObjects().add(section.getBody().getChildObjects().get(i).deepClone());
        }

        // Save the new document to the specified file
        newdoc.saveToFile("/Users/liuhaihua/tmp/Content of One Page.docx", FileFormat.Docx);

        // Close and release the new document
        newdoc.close();
        newdoc.dispose();

        // Close and release the original document
        document.close();
        document.dispose();
    }
}