package com.epam.esm.gift.dto;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.apache.commons.collections4.ListUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CertificateDTO(
    @JsonProperty(access = READ_ONLY)
    Long id,

    @NotBlank
    @Size(min = 3, max = 100)
    String name,

    @Size(min = 3, max = 1000)
    String description,

    @NotNull
    @Positive
    @Digits(integer = 10, fraction = 2)
    BigDecimal price,

    @NotNull
    @Positive
    Integer duration,

    @JsonProperty(access = READ_ONLY)
    LocalDateTime dateTimeCreated,

    @JsonProperty(access = READ_ONLY)
    LocalDateTime dateTimeUpdated,

    List<TagDTO> tags
) {

    public static Builder builder() {
        return new Builder();
    }

    public Builder copy() {
        return new Builder(this);
    }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Integer duration;
        private LocalDateTime dateTimeCreated;
        private LocalDateTime dateTimeUpdated;
        private List<TagDTO> tags;

        private Builder() {
        }

        private Builder(final CertificateDTO certificate) {
            this.id = certificate.id;
            this.name = certificate.name;
            this.description = certificate.description;
            this.price = certificate.price;
            this.duration = certificate.duration;
            this.dateTimeCreated = certificate.dateTimeCreated;
            this.dateTimeUpdated = certificate.dateTimeUpdated;
            this.tags = ListUtils.emptyIfNull(certificate.tags);
        }

        public Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        public Builder price(final BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder duration(final Integer duration) {
            this.duration = duration;
            return this;
        }

        public Builder dateTimeCreated(final LocalDateTime dateTimeCreated) {
            this.dateTimeCreated = dateTimeCreated;
            return this;
        }

        public Builder dateTimeUpdated(final LocalDateTime dateTimeUpdated) {
            this.dateTimeUpdated = dateTimeUpdated;
            return this;
        }

        public Builder tags(final List<TagDTO> tags) {
            this.tags = ListUtils.emptyIfNull(tags);
            return this;
        }

        public CertificateDTO build() {
            return new CertificateDTO(
                id,
                name,
                description,
                price,
                duration,
                dateTimeCreated,
                dateTimeUpdated,
                tags
            );
        }
    }
}
