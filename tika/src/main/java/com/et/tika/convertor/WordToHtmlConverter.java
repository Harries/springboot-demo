package com.et.tika.convertor;


import com.et.tika.dto.ConvertedDocumentDTO;
import com.et.tika.exception.DocumentConversionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 *
 */
@Component
@Slf4j
public class WordToHtmlConverter {


    /**
     * Converts a .docx document into HTML markup. This code
     * is based on <a href="http://stackoverflow.com/a/9053258/313554">this StackOverflow</a> answer.
     *
     * @param wordDocument  The converted .docx document.
     * @return
     */
    public ConvertedDocumentDTO convertWordDocumentIntoHtml(MultipartFile wordDocument) {
        log.info("Converting word document: {} into HTML", wordDocument.getOriginalFilename());
        try {
            InputStream input = wordDocument.getInputStream();
            Parser parser = new OOXMLParser();

            StringWriter sw = new StringWriter();
            SAXTransformerFactory factory = (SAXTransformerFactory)
                    SAXTransformerFactory.newInstance();
            TransformerHandler handler = factory.newTransformerHandler();
            handler.getTransformer().setOutputProperty(OutputKeys.ENCODING, "utf-8");
            handler.getTransformer().setOutputProperty(OutputKeys.METHOD, "html");
            handler.getTransformer().setOutputProperty(OutputKeys.INDENT, "yes");
            handler.setResult(new StreamResult(sw));

            Metadata metadata = new Metadata();
            metadata.add(Metadata.CONTENT_TYPE, "text/html;charset=utf-8");
            parser.parse(input, handler, metadata, new ParseContext());
            return new ConvertedDocumentDTO(wordDocument.getOriginalFilename(), sw.toString());
        }
        catch (IOException | SAXException | TransformerException | TikaException ex) {
            log.error("Conversion failed because an exception was thrown", ex);
            throw new DocumentConversionException(ex.getMessage(), ex);
        }
    }
}
