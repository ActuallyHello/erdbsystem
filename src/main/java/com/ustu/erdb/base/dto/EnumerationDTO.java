package com.ustu.erdb.base.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class EnumerationDTO {
    private Long id;

    @NotNull(message = "label must be not null!")
    @NotBlank(message = "label must be not empty!")
    private String label;

    @NotNull(message = "code must be not null!")
    @NotBlank(message = "code must be not empty!")
    private String code;

    @Valid
    @Builder.Default
    private List<EnumerationValueDTO> enumerationValueDTOs = new ArrayList<>();
}
