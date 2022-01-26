package com.epam.esm.gift.model;

import java.util.Objects;
import java.util.StringJoiner;

import com.epam.esm.gift.annotation.Column;
import com.epam.esm.gift.annotation.Id;
import com.epam.esm.gift.annotation.Table;

@Table(name = "tags")
public class Tag implements Entity<Long> {

    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";

    @Id
    @Column(name = COL_ID)
    private Long id;

    @Column(name = COL_NAME)
    private String name;

    public Tag() {
    }

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder copy() {
        return new Builder(this);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Tag)) {
            return false;
        }
        var tag = (Tag) obj;
        return Objects.equals(id, tag.id)
            && Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Tag.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .add("name='" + name + "'")
            .toString();
    }

    public static class Builder {
        private Long id;
        private String name;

        private Builder() {
        }

        private Builder(final Tag tag) {
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

        public Tag build() {
            var tag = new Tag();
            tag.id = id;
            tag.name = name;
            return tag;
        }
    }
}