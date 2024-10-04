package com.ustu.erdb.base.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModelsController {

    @GetMapping("/")
    public String modelsPage() {
        return "main/models";
    }
}
