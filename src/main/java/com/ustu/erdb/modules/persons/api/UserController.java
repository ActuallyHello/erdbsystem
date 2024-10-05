package com.ustu.erdb.modules.persons.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/create-person")
public class UserController {

    @PostMapping
    public ResponseEntity<Object> createPerson() {
        return null;
    }
}
