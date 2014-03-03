package org.energyos.espi.common.service.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.repositories.ApplicationInformationRepository;
import org.junit.Before;
import org.junit.Test;

public class ApplicationInformationServiceImplTest {
    private ApplicationInformationServiceImpl service;
    private ApplicationInformationRepository repository;

    @Before
    public void before() throws Exception {
        service = new ApplicationInformationServiceImpl();
        repository = mock(ApplicationInformationRepository.class);
        service.setApplicationInformationRepository(repository);
    }

    @Test
    public void findAll() throws Exception {
        service.findAll();

        verify(repository).findAll();
    }

    @Test
    public void findById() throws Exception {
        service.findById(1L);

        verify(repository).findById(1L);
    }

    @Test
    public void persist() throws Exception {
        ApplicationInformation applicationInformation = new ApplicationInformation();

        service.persist(applicationInformation);

        verify(repository).persist(applicationInformation);
    }

    @Test
    public void findByClientId() throws Exception {
        service.findByClientId("99");

        verify(repository).findByClientId("99");
    }

    @Test
    public void findByDataCustodianClientId() throws Exception {
        service.findByDataCustodianClientId("39");

        verify(repository).findByDataCustodianClientId("39");
    }

//    @Test
//    public void loadClientByClientId() throws Exception {
//        service.loadClientByClientId("99");
//
//        verify(repository).findByClientId("99");
//    }
}
