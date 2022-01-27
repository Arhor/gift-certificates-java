package com.epam.esm.gift.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.epam.esm.gift.service.BaseService;
import com.epam.esm.gift.service.dto.TagDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TagController {

    private final BaseService<TagDto, Long> service;

    @GetMapping
    public List<TagDto> getAllTags() {
        return service.findAll();
    }

    @GetMapping("/{tagId}")
    public TagDto getTagById(@PathVariable final Long tagId) {
        return service.findOne(tagId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TagDto> createNewTag(@Validated @RequestBody final TagDto tag) {
        var createdTag = service.create(tag);

        var location =
            ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .build(createdTag.getId());

        return ResponseEntity.created(location).body(createdTag);
    }

    @DeleteMapping("/{tagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable final Long tagId) {
        service.deleteById(tagId);
    }
}
