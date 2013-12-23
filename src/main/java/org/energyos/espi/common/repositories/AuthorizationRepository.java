package org.energyos.espi.common.repositories;

import org.energyos.espi.common.domain.Authorization;

import java.util.List;

public interface AuthorizationRepository {

    void persist(Authorization authorization);

    List findAllByRetailCustomerId(Long retailCustomerId);

    Authorization findByState(String state);

    void merge(Authorization authorization);

	Authorization findById(Long authorizationId);

	List<Long> findAllIds();

	List<Long> findAllIds(Long retailCustomerId);
}
