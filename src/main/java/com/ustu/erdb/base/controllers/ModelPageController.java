package com.ustu.erdb.base.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModelPageController {

    @GetMapping("/")
    public String modelsPage() {
        return "main/models";
    }

    @GetMapping("/create-model")
    public String createModelPage() {
        return "main/create-model";
    }
}
