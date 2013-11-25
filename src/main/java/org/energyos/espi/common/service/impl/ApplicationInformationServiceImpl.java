package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.repositories.ApplicationInformationRepository;
import org.energyos.espi.common.service.ApplicationInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApplicationInformationServiceImpl implements ApplicationInformationService {

    @Autowired
    private ApplicationInformationRepository repository;

    @Override
    public List<ApplicationInformation> findAll() {
        return repository.findAll();
    }

    @Override
    public ApplicationInformation findById(Long id) {
        return repository.findById(id);
    }

    public void setRepository(ApplicationInformationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void persist(ApplicationInformation applicationInformation) {
        repository.persist(applicationInformation);
    }

    @Override
    public ApplicationInformation findByClientId(String clientId) {
        return repository.findByClientId(clientId);
    }

    @Override
    public ApplicationInformation findByDataCustodianClientId(String dataCustodianClientId) {
        return repository.findByDataCustodianClientId(dataCustodianClientId);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        return findByClientId(clientId);
    }
}
