package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.domain.DataCustodian;
import org.energyos.espi.common.repositories.DataCustodianRepository;
import org.energyos.espi.common.service.DataCustodianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DataCustodianServiceImpl implements DataCustodianService {

    @Autowired
    private DataCustodianRepository repository;

    @Override
    public DataCustodian findById(Long dataCustodianId) {
        return repository.findById(dataCustodianId);
    }

    @Override
    public List<DataCustodian> findAll() {
        return repository.findAll();
    }

    public void setRepository(DataCustodianRepository repository) {
        this.repository = repository;
    }

    public void persist(DataCustodian dataCustodian) {
        repository.persist(dataCustodian);
    }

    @Override
    public DataCustodian findByClientId(String clientId) {
        return repository.findByClientId(clientId);
    }
}
