package com.et.controller;


import com.et.redis.document.Student;
import com.et.redis.document.StudentRepository;
import com.et.redis.hash.Person;
import com.et.redis.hash.PersonRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class WebController {

    private PersonRepository personRepository;
    private StudentRepository studentRepository;

    public WebController(PersonRepository personRepository, StudentRepository studentRepository) {
        this.personRepository = personRepository;
        this.studentRepository = studentRepository;
    }

    @PostMapping("/person")
    public Person save(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @GetMapping("/person")
    public Person get(@PathParam("name") String name, @PathParam("searchLastName") String searchLastName) {
        if (name != null)
            return this.personRepository.findByName(name)
                    .orElseThrow(() -> new RuntimeException("person not found"));

        if (searchLastName != null)
            return this.personRepository.searchByLastName(searchLastName)
                    .orElseThrow(() -> new RuntimeException("person not found"));

        return null;
    }

// ---- Student Endpoints
    @PostMapping("/student")
    public Student saveStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @GetMapping("/student")
    public Student getStudent(@PathParam("name") String name, @PathParam("searchLastName") String searchLastName) {
        if (name != null)
            return this.studentRepository.findByName(name)
                    .orElseThrow(() -> new RuntimeException("Student not found"));

        if (searchLastName != null)
            return this.studentRepository.searchByLastName(searchLastName)
                    .orElseThrow(() -> new RuntimeException("Student not found"));

        return null;
    }
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity handleError(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }


}
