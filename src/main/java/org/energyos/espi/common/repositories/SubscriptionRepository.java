package org.energyos.espi.common.repositories;

import org.energyos.espi.common.domain.Subscription;

import java.util.List;

public interface SubscriptionRepository {
    void persist(Subscription subscription);

    List<Subscription> findAll();

    Subscription findByHashedId(String hashedId);
}
