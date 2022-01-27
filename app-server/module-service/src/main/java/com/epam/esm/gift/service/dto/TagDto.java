package com.epam.esm.gift.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class TagDto {

    Long id;

    @NotBlank
    @Size(min = 3, max = 30)
    String name;
}
