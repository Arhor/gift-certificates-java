package com.epam.esm.gift.web.api;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.epam.esm.gift.dto.CertificateDto;
import com.epam.esm.gift.service.BaseService;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final BaseService<CertificateDto, Long> service;

    public CertificateController(final BaseService<CertificateDto, Long> service) {
        this.service = service;
    }

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
                .build(createdCertificate.id());

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