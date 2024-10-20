package com.ustu.erdb.modules.models.api;

import com.ustu.erdb.modules.persons.services.PersonService;
import com.ustu.erdb.modules.persons.store.models.Person;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ModelPageController {

    @Autowired
    private PersonService personService;

    @GetMapping("/")
    public String modelsPage(HttpSession httpSession, Model model) {

        if (httpSession.getAttribute("personid") != null) {
            model.addAttribute("personid", httpSession.getAttribute("personid"));
            Person person = personService.getById((Long) httpSession.getAttribute("personid"));
            model.addAttribute("firstName", person.getFirstName());
            model.addAttribute("lastName", person.getLastName());
        } else {
            return "redirect:/login";
        }
        return "main/models";
    }

//    @GetMapping("/create-model")
//    public String createModelPage(HttpSession httpSession, Model model, @RequestParam(value = "isTaskResult", required = false) Boolean isTaskResult) {
//        if (isTaskResult == null) {
//            isTaskResult = false;
//        }
//        if (httpSession.getAttribute("personid") != null) {
//            model.addAttribute("personId", httpSession.getAttribute("personid"));
//            Person person = personService.getById((Long) httpSession.getAttribute("personid"));
//            model.addAttribute("firstName", person.getFirstName());
//            model.addAttribute("lastName", person.getLastName());
//            model.addAttribute("isTaskResult", isTaskResult);
//        } else {
//            return "redirect:/login";
//        }
//        return "main/create-model";
//    }


//    @GetMapping("/{id}")
//    public String detailModelPage(@PathVariable("id") Long id, HttpSession httpSession, Model model) {
//        if (httpSession.getAttribute("personid") != null) {
//            model.addAttribute("personId", httpSession.getAttribute("personid"));
//            model.addAttribute("modelId", id);
//            Person person = personService.getById((Long) httpSession.getAttribute("personid"));
//            model.addAttribute("firstName", person.getFirstName());
//            model.addAttribute("lastName", person.getLastName());
//            model.addAttribute("isTeacher", person.getType().getCode().equalsIgnoreCase("teacher"));
//        } else {
//            return "redirect:/login";
//        }
//        System.out.println("GO TO MODEL");
//        return "main/model";
//    }
}
