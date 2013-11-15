package org.energyos.espi.common.service;

import org.energyos.espi.common.domain.DataCustodian;

import java.util.List;

public interface DataCustodianService {
    DataCustodian findById(Long dataCustodianId);

    List<DataCustodian> findAll();

    void persist(DataCustodian dataCustodian);

    DataCustodian findByClientId(String clientId);
}
