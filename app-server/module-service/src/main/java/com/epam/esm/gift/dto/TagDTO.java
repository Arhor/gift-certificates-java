package com.epam.esm.gift.dto;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TagDTO {

    @JsonProperty(access = READ_ONLY)
    private final Long id;

    @NotBlank
    @Size(min = 3, max = 30)
    private final String name;

    public TagDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder copy() {
        return new Builder(this);
    }

    public static class Builder {
        private Long id;
        private String name;

        private Builder() {
        }

        private Builder(final TagDTO tag) {
            this.id = tag.id;
            this.name = tag.name;
        }

        public Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public TagDTO build() {
            return new TagDTO(
                id,
                name
            );
        }
    }
}
