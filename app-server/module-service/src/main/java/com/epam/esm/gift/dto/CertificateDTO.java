package com.epam.esm.gift.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CertificateDTO(
    Long id,
    String name,
    String description,
    BigDecimal price,
    Integer duration,
    LocalDateTime dateTimeCreated,
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
            this.tags = certificate.tags;
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
            this.tags = List.copyOf(tags);
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
