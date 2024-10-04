package com.ustu.erdb.base.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class EnumerationValueDTO {
    private Long id;

    @NotNull(message = "label must be not null!")
    @NotBlank(message = "label must be not empty!")
    private String label;

    @NotNull(message = "code must be not null!")
    @NotBlank(message = "code must be not empty!")
    private String code;

    @NotNull(message = "enumerationId must be not null!")
    @Min(value = 1, message = "enumerationId cannot be less than 1!")
    private Long enumerationId;
}
