package com.et.itextpdf.controller;

import com.et.itextpdf.pojo.Order;
import com.et.itextpdf.util.OrderHelper;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("/orders")
public class PDFController {


    @Autowired
    ServletContext servletContext;

    private final TemplateEngine templateEngine;

    public PDFController(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @RequestMapping(path = "/")
    public String getOrderPage(Model model) {
        Order order = OrderHelper.getOrder();
        model.addAttribute("orderEntry", order);
        return "order";
    }

    @RequestMapping(path = "/pdf")
    public ResponseEntity<?> getPDF(HttpServletRequest request, HttpServletResponse response) throws IOException {

        /* Do Business Logic*/

        Order order = OrderHelper.getOrder();

        /* Create HTML using Thymeleaf template Engine */

        WebContext context = new WebContext(request, response, servletContext);
        context.setVariable("orderEntry", order);
        String orderHtml = templateEngine.process("order", context);

        /* Setup Source and target I/O streams */

        ByteArrayOutputStream target = new ByteArrayOutputStream();
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8088");
        /* Call convert method */
        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);

        /* extract output as bytes */
        byte[] bytes = target.toByteArray();


        /* Send the response as downloadable PDF */

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=order.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);

    }

}
