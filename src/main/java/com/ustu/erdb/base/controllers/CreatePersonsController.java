package com.ustu.erdb.base.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreatePersonsController {

    @GetMapping("/create-person")
    public String createPersonPage() {
        return "persons/create-person";
    }

}
