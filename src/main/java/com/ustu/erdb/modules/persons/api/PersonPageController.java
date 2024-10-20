package com.ustu.erdb.modules.persons.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonPageController {

    @GetMapping("/person")
    public String personsPage() {
        return "persons/persons";
    }

    @GetMapping("/create-person")
    public String createPersonPage() {
        return "persons/create-person";
    }

}
