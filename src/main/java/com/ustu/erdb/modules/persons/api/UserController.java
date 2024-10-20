package com.ustu.erdb.modules.persons.api;

import com.ustu.erdb.base.store.models.EnumerationValue;
import com.ustu.erdb.base.store.repository.EnumerationValueRepository;
import com.ustu.erdb.modules.persons.store.models.Person;
import com.ustu.erdb.modules.persons.store.models.User;
import com.ustu.erdb.modules.persons.store.repository.PersonRepository;
import com.ustu.erdb.modules.persons.store.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {

    private static final String REGISTER_PAGE = "persons/registration";
    private static final String LOGIN_PAGE = "persons/login";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnumerationValueRepository enumerationValueRepository;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/registration")
    public String getRegisterUserPage(HttpSession httpSession, Model model) {
        httpSession.setAttribute("personid", null);
        httpSession.setAttribute("userid", null);
        model.addAttribute("title", "Регистрация");
        return REGISTER_PAGE;
    }

    @PostMapping("/registration")
    public String registerUserPage(HttpSession httpSession, Model model,
                                   @RequestParam String login,
                                   @RequestParam String password,
                                   @RequestParam String firstName,
                                   @RequestParam String lastName,
                                   @RequestParam String middleName,
                                   @RequestParam String group) {
        model.addAttribute("title", "Регистрация");

        if (userRepository.existsByLogin(login)) {
            model.addAttribute("error", "Пользователь с таким логином уже существует!");
            System.out.println("HERE");
            return REGISTER_PAGE;
        }
        try {
            User user = User.builder()
                    .login(login)
                    .password(password)
                    .build();
            user = userRepository.save(user);

            EnumerationValue personGroup = enumerationValueRepository.findByCode(group)
                    .orElseThrow(() -> new RuntimeException("Не найдена группа пользователя " + group));
            EnumerationValue personType = enumerationValueRepository.findByCode("student")
                    .orElseThrow(() -> new RuntimeException("Не найден тип для пользователя Студент"));

            Person person = Person.builder()
                    .user(user)
                    .firstName(firstName)
                    .lastName(lastName)
                    .middleName(middleName)
                    .type(personType)
                    .group(personGroup)
                    .build();
            person = personRepository.save(person);

            httpSession.setAttribute("userid", user.getId());
            httpSession.setAttribute("personid", person.getId());
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при создании пользователя! " + e.getMessage());
            return REGISTER_PAGE;
        }
    }

    @GetMapping("/login")
    public String getLoginPage(HttpSession httpSession, Model model) {
        httpSession.setAttribute("personid", null);
        httpSession.setAttribute("userid", null);
        model.addAttribute("title", "Авторизация");
        return LOGIN_PAGE;
    }

    @PostMapping("/login")
    public String loginPage(HttpSession httpSession, Model model,
                            @RequestParam String login,
                            @RequestParam String password) {
        try {
            User user = userRepository.findByLoginAndPassword(login, password)
                    .orElseThrow(() -> new RuntimeException("Пользователя с данным логином и паролем не существует!"));
            Person person = personRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Пользователь не привязан к человеку!"));
            httpSession.setAttribute("userid", user.getId());
            httpSession.setAttribute("personid", person.getId());
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при авторизации! " + e.getMessage());
            return LOGIN_PAGE;
        }

    }
}
