package com.ustu.erdb.modules.persons.api;

import com.ustu.erdb.modules.persons.dto.UserRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PersonController {

    @PostMapping("/create-persons")
    public ResponseEntity<Object> createUser(@RequestBody UserRequestDto userRequestDTO) {
        System.out.println("here");
        return ResponseEntity.ok(Map.of("id", 1));
    }

}
