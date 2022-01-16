package com.epam.esm.gift.service.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.esm.gift.mapper.CertificateEntityMapper;
import com.epam.esm.gift.mapper.TagEntityMapper;
import com.epam.esm.gift.repository.CertificateRepository;
import com.epam.esm.gift.repository.TagRepository;

@ExtendWith(MockitoExtension.class)
public class CertificateServiceImplTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private CertificateEntityMapper certificateMapper;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagEntityMapper tagMapper;

    @InjectMocks
    private CertificateServiceImpl certificateService;
}
