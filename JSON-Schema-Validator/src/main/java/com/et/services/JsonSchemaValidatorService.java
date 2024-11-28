package com.et.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JsonSchemaValidatorService {

    private final JsonSchema schema;

    public JsonSchemaValidatorService() throws IOException, ProcessingException {
        JsonNode schemaNode = JsonLoader.fromResource("/schemas/user-schema.json");
        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        this.schema = factory.getJsonSchema(schemaNode);
    }

    public ProcessingReport validate(String jsonData) throws IOException, ProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode dataNode = mapper.readTree(jsonData);
        return schema.validate(dataNode);
    }
}