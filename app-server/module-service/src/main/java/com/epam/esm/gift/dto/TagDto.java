package com.epam.esm.gift.dto;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

import java.util.Objects;
import java.util.StringJoiner;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TagDto {

    @JsonProperty(access = READ_ONLY)
    private final Long id;

    @NotBlank
    @Size(min = 3, max = 30)
    private final String name;

    public TagDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder copy() {
        return new Builder(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (TagDto) o;
        return Objects.equals(id, that.id)
            && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TagDto.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .add("name='" + name + "'")
            .toString();
    }

    public static class Builder {
        private Long id;
        private String name;

        private Builder() {
        }

        private Builder(final TagDto tag) {
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

        public TagDto build() {
            return new TagDto(
                id,
                name
            );
        }
    }
}
