package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.domain.ThirdParty;
import org.energyos.espi.common.repositories.ThirdPartyRepository;
import org.energyos.espi.common.service.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ThirdPartyServiceImpl implements ThirdPartyService {

    @Autowired
    private ThirdPartyRepository repository;

    @Override
    public List<ThirdParty> findAll() {
        return repository.findAll();
    }

    @Override
    public ThirdParty findById(Long id) {
        return repository.findById(id);
    }

    public void setRepository(ThirdPartyRepository repository) {
        this.repository = repository;
    }

    @Override
    public void persist(ThirdParty thirdParty) {
        repository.persist(thirdParty);
    }

    @Override
    public ThirdParty findByClientId(String clientId) {
        return repository.findByClientId(clientId);
    }
}
