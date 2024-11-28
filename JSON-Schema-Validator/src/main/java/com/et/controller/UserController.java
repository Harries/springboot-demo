package com.et.controller;

import com.et.services.JsonSchemaValidatorService;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private JsonSchemaValidatorService jsonSchemaValidatorService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody String userJson) {
        try {
            ProcessingReport report = jsonSchemaValidatorService.validate(userJson);
            if (report.isSuccess()) {
                // process JSON
                return ResponseEntity.ok("User data is valid");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user data: " + report.toString());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing user data: " + e.getMessage());
        }
    }
}