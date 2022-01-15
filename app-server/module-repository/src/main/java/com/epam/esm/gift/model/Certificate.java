package com.epam.esm.gift.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

import com.epam.esm.gift.annotation.Column;
import com.epam.esm.gift.annotation.Id;
import com.epam.esm.gift.annotation.Table;

@Table(name = "certificates")
public class Certificate implements Entity<Long>, Auditable<LocalDateTime> {

    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_PRICE = "price";
    public static final String COL_DURATION = "duration";
    public static final String COL_DATE_TIME_CREATED = "date_time_created";
    public static final String COL_DATE_TIME_UPDATED = "date_time_updated";

    @Id
    @Column(name = COL_ID)
    private Long id;

    @Column(name = COL_NAME)
    private String name;

    @Column(name = COL_DESCRIPTION)
    private String description;

    @Column(name = COL_PRICE)
    private BigDecimal price;

    @Column(name = COL_DURATION)
    private Integer duration;

    @Column(name = COL_DATE_TIME_CREATED)
    private LocalDateTime dateTimeCreated;

    @Column(name = COL_DATE_TIME_UPDATED)
    private LocalDateTime dateTimeUpdated;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(LocalDateTime dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public LocalDateTime getDateTimeUpdated() {
        return dateTimeUpdated;
    }

    public void setDateTimeUpdated(LocalDateTime dateTimeUpdated) {
        this.dateTimeUpdated = dateTimeUpdated;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Certificate certificate)) {
            return false;
        }
        return Objects.equals(id, certificate.id)
            && Objects.equals(dateTimeCreated, certificate.dateTimeCreated)
            && Objects.equals(duration, certificate.duration)
            && Objects.equals(price, certificate.price)
            && Objects.equals(description, certificate.description)
            && Objects.equals(name, certificate.name)
            && Objects.equals(dateTimeUpdated, certificate.dateTimeUpdated);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (dateTimeCreated != null ? dateTimeCreated.hashCode() : 0);
        result = 31 * result + (dateTimeUpdated != null ? dateTimeUpdated.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Certificate.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .add("name='" + name + "'")
            .add("description='" + description + "'")
            .add("price=" + price)
            .add("duration=" + duration)
            .add("dateTimeCreated=" + dateTimeCreated)
            .add("dateTimeUpdated=" + dateTimeUpdated)
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

        private Builder() {
        }

        private Builder(final Certificate certificate) {
            this.id = certificate.id;
            this.name = certificate.name;
            this.description = certificate.description;
            this.price = certificate.price;
            this.duration = certificate.duration;
            this.dateTimeCreated = certificate.dateTimeCreated;
            this.dateTimeUpdated = certificate.dateTimeUpdated;
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

        public Certificate build() {
            var certificate = new Certificate();
            certificate.id = id;
            certificate.name = name;
            certificate.description = description;
            certificate.price = price;
            certificate.duration = duration;
            certificate.dateTimeCreated = dateTimeCreated;
            certificate.dateTimeUpdated = dateTimeUpdated;
            return certificate;
        }
    }
}