package com.ustu.erdb.modules.persons.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserRequestDto {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    private String personType;
    private String position;
}
