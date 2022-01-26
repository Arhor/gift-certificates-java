package com.epam.esm.gift.repository;

import java.util.List;
import java.util.Map;

import com.epam.esm.gift.model.Certificate;
import com.epam.esm.gift.repository.bootstrap.ColumnProperty;

public interface CertificateRepository extends BaseRepository<Certificate, Long> {

    void updatePartially(Long certificateId, List<Map.Entry<ColumnProperty, Object>> diffs);
}