package com.epam.esm.gift.repository;

import java.util.List;
import java.util.Optional;

import com.epam.esm.gift.repository.model.Tag;

public interface TagRepository extends BaseRepository<Tag, Long> {

    Optional<Tag> findTagByName(String name);

    List<Tag> findTagByNames(List<String> names);

    List<Tag> findTagsByCertificateId(Long certificateId);

    void addTagsToCertificate(Long certificateId, List<Tag> tags);

    void removeAllTagsFromCertificate(Long certificateId);
}
