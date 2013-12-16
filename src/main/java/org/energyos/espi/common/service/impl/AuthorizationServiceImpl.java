package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.repositories.AuthorizationRepository;
import org.energyos.espi.common.repositories.UsagePointRepository;
import org.energyos.espi.common.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    @Autowired
    private AuthorizationRepository repository;

    @Autowired
    private UsagePointRepository usagePointRepository;

    @Override
    public List<Authorization> findAllByRetailCustomerId(Long retailCustomerId) {
        return repository.findAllByRetailCustomerId(retailCustomerId);
    }

    @Override
    public void persist(Authorization authorization) {
        repository.persist(authorization);
    }

    @Override
    public void merge(Authorization authorization) {
        repository.merge(authorization);
    }

    @Override
    public Authorization findByURI(String uri) {
        UsagePoint usagePoint = usagePointRepository.findByURI(uri);
        return usagePoint.getSubscription().getAuthorization();
    }

    @Override
    public Authorization findByState(String state) {
        return repository.findByState(state);
    }

    public void setRepository(AuthorizationRepository repository) {
        this.repository = repository;
    }

    public void setUsagePointRepository(UsagePointRepository usagePointRepository) {
        this.usagePointRepository = usagePointRepository;
    }

    @Override
    public Authorization createAuthorization(Subscription subscription, String accessToken) {
        Authorization authorization = new Authorization();
        authorization.setUUID(UUID.randomUUID());
        authorization.setAccessToken(accessToken);
        authorization.setResourceURI(Routes.DATA_CUSTODIAN_SUBSCRIPTION.replace("{SubscriptionID}", subscription.getUUID().toString()));
        repository.persist(authorization);

        return authorization;
    }
}
