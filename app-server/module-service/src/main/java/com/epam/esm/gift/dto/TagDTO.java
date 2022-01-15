package com.epam.esm.gift.dto;

public record TagDTO(
    Long id,
    String name
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
