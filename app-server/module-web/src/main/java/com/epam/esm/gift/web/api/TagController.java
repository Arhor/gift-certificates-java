package com.epam.esm.gift.web.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.epam.esm.gift.dto.TagDTO;
import com.epam.esm.gift.service.BaseService;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final BaseService<TagDTO, Long> service;

    public TagController(final BaseService<TagDTO, Long> service) {
        this.service = service;
    }

    @GetMapping
    public List<TagDTO> getAllTags() {
        return service.findAll();
    }

    @GetMapping("/{tagId}")
    public TagDTO getTagById(@PathVariable final Long tagId) {
        return service.findOne(tagId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TagDTO> createNewTag(@RequestBody final TagDTO tag) {
        var createdTag = service.create(tag);

        var location =
            ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .build(createdTag.id());

        return ResponseEntity.created(location).body(createdTag);
    }

    @DeleteMapping("/{tagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable final Long tagId) {
        service.deleteById(tagId);
    }
}