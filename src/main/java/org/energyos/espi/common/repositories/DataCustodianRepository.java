package org.energyos.espi.common.repositories;

import org.energyos.espi.common.domain.DataCustodian;

import java.util.List;

public interface DataCustodianRepository {
    DataCustodian findById(Long dataCustodianId);

    List<DataCustodian> findAll();

    void persist(DataCustodian dataCustodian);

    DataCustodian findByClientId(String clientId);
}
