package org.energyos.espi.common.service;

import org.energyos.espi.common.domain.ThirdParty;

import java.util.List;

public interface ThirdPartyService {

    public List<ThirdParty> findAll();

    ThirdParty findById(Long id);

    void persist(ThirdParty thirdParty);

    ThirdParty findByClientId(String thirdPartyClientId);
}
