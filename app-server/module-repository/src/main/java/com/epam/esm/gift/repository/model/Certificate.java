package com.epam.esm.gift.repository.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.epam.esm.gift.repository.annotation.Column;
import com.epam.esm.gift.repository.annotation.Id;
import com.epam.esm.gift.repository.annotation.Table;

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
}
