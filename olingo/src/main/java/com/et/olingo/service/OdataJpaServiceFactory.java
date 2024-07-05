package com.et.olingo.service;

import org.springframework.stereotype.Component;

@Component
public class OdataJpaServiceFactory extends CustomODataServiceFactory {
    //need this wrapper class for the spring framework, otherwise we face issues when auto wiring directly the CustomODataServiceFactory
}
