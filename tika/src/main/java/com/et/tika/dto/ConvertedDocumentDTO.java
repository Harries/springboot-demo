package com.et.tika.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 */
public  class ConvertedDocumentDTO {

    private final String contentAsHtml;
    private final String filename;

    public ConvertedDocumentDTO(String filename, String contentAsHtml) {
        this.contentAsHtml = contentAsHtml;
        this.filename = filename;
    }

    public String getContentAsHtml() {
        return contentAsHtml;
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("filename", this.filename)
                .append("contentAsHtml", this.contentAsHtml)
                .toString();
    }
}
