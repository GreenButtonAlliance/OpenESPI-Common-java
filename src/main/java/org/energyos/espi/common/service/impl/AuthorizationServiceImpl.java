package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.repositories.AuthorizationRepository;
import org.energyos.espi.common.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    @Autowired
    private AuthorizationRepository repository;

    public void setRepository(AuthorizationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Authorization createAuthorization(Subscription subscription, String accessToken) {
        Authorization authorization = new Authorization();
        authorization.setUUID(UUID.randomUUID());
        authorization.setAccessToken(accessToken);
        authorization.setResource(Routes.DATA_CUSTODIAN_SUBSCRIPTION.replace("{SubscriptionID}", subscription.getUUID().toString()));
        repository.persist(authorization);

        return authorization;
    }

    @Override
    public void persist(Authorization authorization) {
        repository.persist(authorization);
    }
}
