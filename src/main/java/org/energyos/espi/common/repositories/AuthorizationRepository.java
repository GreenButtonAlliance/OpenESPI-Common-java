package org.energyos.espi.common.repositories;

import org.energyos.espi.common.domain.Authorization;

public interface AuthorizationRepository {
    void persist(Authorization authorization);
}
