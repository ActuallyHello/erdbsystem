package com.ustu.erdb.modules.persons.dto;

import com.ustu.erdb.base.dto.EnumerationValueDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private EnumerationValueDTO personType;
}
