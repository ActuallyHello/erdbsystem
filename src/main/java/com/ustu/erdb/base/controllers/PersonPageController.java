package com.ustu.erdb.base.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class PersonPageController {

    @GetMapping("/create-person")
    public String createPersonPage() {
        return "persons/create-person";
    }

    @GetMapping("/person")
    public String personPage() {
        return "persons/persons";
    }

}
