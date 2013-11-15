package org.energyos.espi.common.service;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.Subscription;

import java.util.List;

public interface AuthorizationService {
    List<Authorization> findAllByRetailCustomerId(Long retailCustomerId);

    Authorization createAuthorization(Subscription subscription, String accessToken);

    void persist(Authorization authorization);

    Authorization findByState(String state);

    void merge(Authorization authorization);

    Authorization findByURI(String uri);
}
