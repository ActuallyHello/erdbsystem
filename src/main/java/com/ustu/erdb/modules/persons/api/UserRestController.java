package com.ustu.erdb.modules.persons.api;

import com.ustu.erdb.base.exceptions.LogicalException;
import com.ustu.erdb.modules.persons.dto.UserRequestDto;
import com.ustu.erdb.modules.persons.services.PersonService;
import com.ustu.erdb.modules.persons.services.UserService;
import com.ustu.erdb.modules.persons.store.models.Person;
import com.ustu.erdb.modules.persons.store.models.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-users")
    public ResponseEntity<Object> createUser(@RequestParam("login") String login, @RequestParam("password") String password) {
        User user = User.builder().login(login).password(password).build();
        user = userService.create(user);
        return ResponseEntity.ok(Map.of("id", user.getId()));
    }
}
