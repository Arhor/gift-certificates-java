package com.epam.esm.gift.repository.model;

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
@Table(name = "tags")
public class Tag implements Entity<Long> {

    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";

    @Id
    @Column(name = COL_ID)
    private Long id;

    @Column(name = COL_NAME)
    private String name;
}
