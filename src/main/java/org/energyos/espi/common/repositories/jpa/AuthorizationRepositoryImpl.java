package org.energyos.espi.common.repositories.jpa;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.repositories.AuthorizationRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AuthorizationRepositoryImpl implements AuthorizationRepository {

    @PersistenceContext
    protected EntityManager em;

    @Transactional
    public void persist(Authorization authorization) {
        em.persist(authorization);
    }
}
