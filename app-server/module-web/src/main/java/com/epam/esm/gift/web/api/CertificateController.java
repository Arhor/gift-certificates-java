package com.epam.esm.gift.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.epam.esm.gift.service.BaseService;
import com.epam.esm.gift.service.dto.CertificateDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CertificateController {

    private final BaseService<CertificateDto, Long> service;

    @GetMapping
    public List<CertificateDto> getAllTags() {
        return service.findAll();
    }

    @GetMapping("/{certificateId}")
    public CertificateDto getTagById(@PathVariable final Long certificateId) {
        return service.findOne(certificateId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CertificateDto> createCertificate(@Validated @RequestBody final CertificateDto certificate) {
        var createdCertificate = service.create(certificate);

        var location =
            ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .build(createdCertificate.getId());

        return ResponseEntity.created(location).body(createdCertificate);
    }

    @PatchMapping
    public CertificateDto updateCertificate(@Validated @RequestBody final CertificateDto certificate) {
        return service.update(certificate);
    }

    @DeleteMapping("/{certificateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable final Long certificateId) {
        service.deleteById(certificateId);
    }
}
