package com.ustu.erdb.modules.persons.api;

import com.ustu.erdb.base.services.impl.EnumerationValueService;
import com.ustu.erdb.base.store.models.EnumerationValue;
import com.ustu.erdb.modules.persons.services.PersonService;
import com.ustu.erdb.modules.persons.services.UserService;
import com.ustu.erdb.modules.persons.store.models.Person;
import com.ustu.erdb.modules.persons.store.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PersonRestController {

    @Autowired
    private PersonService personService;

    @Autowired
    private UserService userService;

    @Autowired
    private EnumerationValueService enumerationValueService;

    @PostMapping("/create-persons")
    public ResponseEntity<Object> createUser(@RequestParam(name = "firstName") String firstName,
                                             @RequestParam(name = "lastName") String lastName,
                                             @RequestParam(name = "middleName", required = false) String middleName,
                                             @RequestParam(name = "typeCode") String typeCode,
                                             @RequestParam(name = "groupCode") String groupCode,
                                             @RequestParam(name = "userId") Long userId) {
        EnumerationValue type = enumerationValueService.getByCode(typeCode);
        EnumerationValue group = enumerationValueService.getByCode(groupCode);
        Person person = Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .middleName(middleName)
                .group(group)
                .type(type)
                .user(userService.getById(userId))
                .build();
        person = personService.create(person);
        return ResponseEntity.ok(Map.of("id", person.getId()));
    }

    @GetMapping("/persons")
    public ResponseEntity<List<Map<String, Object>>> getPersons() {
        List<User> all = userService.getAll(PageRequest.of(0, 1000));

        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> record = new HashMap<>();
        for (User user : all) {
            Person person = personService.getByUser(user);
            record.put("fio", String.format("%s %s %s", person.getFirstName(), person.getLastName(), person.getMiddleName()));
            record.put("group", person.getGroup().getLabel());
            record.put("type", person.getType().getLabel());
            record.put("login", user.getLogin());
            record.put("password", user.getPassword());
        }
        result.add(record);
        return ResponseEntity.ok(result);
    }

}
