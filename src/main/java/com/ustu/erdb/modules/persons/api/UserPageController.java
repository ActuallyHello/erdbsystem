package com.ustu.erdb.modules.persons.api;

import com.ustu.erdb.base.exceptions.LogicalException;
import com.ustu.erdb.modules.persons.services.PersonService;
import com.ustu.erdb.modules.persons.services.UserService;
import com.ustu.erdb.modules.persons.store.models.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.http.HttpClient;
import java.util.Arrays;
import java.util.Map;

@Controller
public class UserPageController {

//    @GetMapping("/login")
//    public String personsPage(HttpSession httpSession, Model model) {
//        httpSession.invalidate();
//        model.addAttribute("userid", null);
//        model.addAttribute("personid", null);
//        return "persons/login";
//    }

    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;

//    @PostMapping("/login")
//    public String login(@RequestParam("login") String login, @RequestParam("password") String password, HttpSession httpSession, Model model) {
//        try {
//            User user = userService.getByLoginAndPassword(login, password);
//            httpSession.setAttribute("userid", user.getId());
//            httpSession.setAttribute("personid", personService.getByUser(user).getId());
//            return "redirect:/";
//        } catch (LogicalException exception) {
//            System.out.println("ERROR!");
//            System.out.println(Arrays.toString(exception.getStackTrace()));
//            model.addAttribute("error", "Неверные данные пользователя!");
//            return "persons/login";
//        }
//    }

}
