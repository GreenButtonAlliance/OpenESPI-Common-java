package org.energyos.espi.common.service;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.Subscription;

public interface AuthorizationService {
    Authorization createAuthorization(Subscription subscription, String accessToken);

    void persist(Authorization authorization);
}
