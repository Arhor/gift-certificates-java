package com.epam.esm.gift.repository.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "certificates")
public class Certificate implements BaseEntity<Long>, Auditable<LocalDateTime> {

    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_PRICE = "price";
    public static final String COL_DURATION = "duration";
    public static final String COL_DATE_TIME_CREATED = "date_time_created";
    public static final String COL_DATE_TIME_UPDATED = "date_time_updated";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @CreatedDate
    @Column(name = COL_DATE_TIME_CREATED)
    private LocalDateTime dateTimeCreated;

    @LastModifiedDate
    @Column(name = COL_DATE_TIME_UPDATED)
    private LocalDateTime dateTimeUpdated;

    @ManyToMany
    @JoinTable(
        name = "certificates_has_tags",
        joinColumns = @JoinColumn(name = "certificate_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;
}
