package org.energyos.espi.common.service;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.List;

public interface ApplicationInformationService {

    public List<ApplicationInformation> findAll();

    ApplicationInformation findById(Long id);

    void persist(ApplicationInformation applicationInformation);

    ApplicationInformation findByClientId(String clientId);

    ClientDetails loadClientByClientId(String clientId);
}
