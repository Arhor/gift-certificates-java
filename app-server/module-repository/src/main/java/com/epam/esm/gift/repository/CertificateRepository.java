package com.epam.esm.gift.repository;

import java.util.List;
import java.util.Map;

import com.epam.esm.gift.repository.bootstrap.ColumnProperty;
import com.epam.esm.gift.repository.model.Certificate;

public interface CertificateRepository extends BaseRepository<Certificate, Long> {

    void updatePartially(Long certificateId, List<Map.Entry<ColumnProperty, Object>> diffs);
}
