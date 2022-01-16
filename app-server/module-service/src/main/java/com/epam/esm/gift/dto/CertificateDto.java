package com.epam.esm.gift.dto;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.apache.commons.collections4.ListUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CertificateDto {

    @JsonProperty(access = READ_ONLY)
    private final Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    private final String name;

    @Size(min = 3, max = 1000)
    private final String description;

    @NotNull
    @Positive
    @Digits(integer = 10, fraction = 2)
    private final BigDecimal price;

    @NotNull
    @Positive
    private final Integer duration;

    @JsonProperty(access = READ_ONLY)
    private final LocalDateTime dateTimeCreated;

    @JsonProperty(access = READ_ONLY)
    private final LocalDateTime dateTimeUpdated;

    private final List<TagDto> tags;

    public CertificateDto(Long id, String name, String description, BigDecimal price, Integer duration, LocalDateTime dateTimeCreated, LocalDateTime dateTimeUpdated, List<TagDto> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.dateTimeCreated = dateTimeCreated;
        this.dateTimeUpdated = dateTimeUpdated;
        this.tags = tags;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public BigDecimal price() {
        return price;
    }

    public Integer duration() {
        return duration;
    }

    public LocalDateTime dateTimeCreated() {
        return dateTimeCreated;
    }

    public LocalDateTime dateTimeUpdated() {
        return dateTimeUpdated;
    }

    public List<TagDto> tags() {
        return tags;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getDuration() {
        return duration;
    }

    public LocalDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }

    public LocalDateTime getDateTimeUpdated() {
        return dateTimeUpdated;
    }

    public List<TagDto> getTags() {
        return tags;
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
        var that = (CertificateDto) o;
        return Objects.equals(id, that.id)
            && Objects.equals(name, that.name)
            && Objects.equals(description, that.description)
            && Objects.equals(price, that.price)
            && Objects.equals(duration, that.duration)
            && Objects.equals(dateTimeCreated, that.dateTimeCreated)
            && Objects.equals(dateTimeUpdated, that.dateTimeUpdated)
            && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, duration, dateTimeCreated, dateTimeUpdated, tags);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CertificateDto.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .add("name='" + name + "'")
            .add("description='" + description + "'")
            .add("price=" + price)
            .add("duration=" + duration)
            .add("dateTimeCreated=" + dateTimeCreated)
            .add("dateTimeUpdated=" + dateTimeUpdated)
            .add("tags=" + tags)
            .toString();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Integer duration;
        private LocalDateTime dateTimeCreated;
        private LocalDateTime dateTimeUpdated;
        private List<TagDto> tags;

        private Builder() {
        }

        private Builder(final CertificateDto certificate) {
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

        public Builder tags(final List<TagDto> tags) {
            this.tags = ListUtils.emptyIfNull(tags);
            return this;
        }

        public CertificateDto build() {
            return new CertificateDto(
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
