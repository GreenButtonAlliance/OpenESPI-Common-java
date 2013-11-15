package org.energyos.espi.common.repositories.jpa;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.repositories.AuthorizationRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AuthorizationRepositoryImpl implements AuthorizationRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public void persist(Authorization authorization) {
        em.persist(authorization);
    }

    @Override
    public List findAllByRetailCustomerId(Long retailCustomerId) {
      return em.createNamedQuery(Authorization.QUERY_FIND_BY_RETAIL_CUSTOMER_ID)
              .setParameter("retailCustomerId", retailCustomerId).getResultList();
    }

    @Override
    public Authorization findByState(String state) {
      return (Authorization)em.createNamedQuery(Authorization.QUERY_FIND_BY_STATE)
              .setParameter("state", state).getSingleResult();
    }

    @Override
    public void merge(Authorization authorization) {
        em.merge(authorization);
    }
}
