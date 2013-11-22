package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.repositories.SubscriptionRepository;
import org.energyos.espi.common.service.ApplicationInformationService;
import org.energyos.espi.common.service.SubscriptionService;
import org.energyos.espi.common.utils.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    private SubscriptionRepository repository;

    @Autowired
    private ApplicationInformationService applicationInformationService;

    @Override
    public Subscription createSubscription(OAuth2Authentication authentication) {
        Subscription subscription = new Subscription();
        subscription.setUUID(UUID.randomUUID());
        subscription.setApplicationInformation(applicationInformationService.findByClientId(authentication.getOAuth2Request().getClientId()));
        subscription.setRetailCustomer((RetailCustomer)authentication.getPrincipal());
        subscription.setLastUpdate(DateConverter.epoch());
        repository.persist(subscription);

        return subscription;
    }

    @Override
    public List<Subscription> findAll() {
        return repository.findAll();
    }

    @Override
    public void persist(Subscription subscription) {
        repository.persist(subscription);
    }

    @Override
    public Subscription findByHashedId(String hashedId) {
        return repository.findByHashedId(hashedId);
    }

    public void setRepository(SubscriptionRepository repository) {
        this.repository = repository;
    }

    public void setApplicationInformationService(ApplicationInformationService applicationInformationService) {
        this.applicationInformationService = applicationInformationService;
    }
}
