package com.epam.esm.gift.service.dto;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class CertificateDto {

    @JsonProperty(access = READ_ONLY)
    Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    String name;

    @Size(min = 3, max = 1000)
    String description;

    @NotNull
    @Positive
    @Digits(integer = 10, fraction = 2)
    BigDecimal price;

    @NotNull
    @Positive
    Integer duration;

    @JsonProperty(access = READ_ONLY)
    LocalDateTime dateTimeCreated;

    @JsonProperty(access = READ_ONLY)
    LocalDateTime dateTimeUpdated;

    List<TagDto> tags;
}
