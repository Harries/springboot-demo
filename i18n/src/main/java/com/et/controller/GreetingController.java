package com.et.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@RestController
public class GreetingController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping("/greeting")
    public String greeting(HttpServletRequest request, @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage) {
        Locale locale = localeResolver.resolveLocale(request);
        
        // set language by Accept-Language
        if (acceptLanguage != null && !acceptLanguage.isEmpty()) {
            String[] languages = acceptLanguage.split(",");
            String language = languages[0].split(";")[0]; // get the first language
            language = language.trim();
            locale = Locale.forLanguageTag(language);
        }

        return messageSource.getMessage("greeting", null, locale);
    }
}