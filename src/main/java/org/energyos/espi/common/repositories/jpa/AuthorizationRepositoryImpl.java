package org.energyos.espi.common.repositories.jpa;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.repositories.AuthorizationRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class AuthorizationRepositoryImpl implements AuthorizationRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    @Transactional
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
    @Transactional
    public void merge(Authorization authorization) {
        em.merge(authorization);
    }

	@Override
	public Authorization findById(Long authorizationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Long> findAllIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Long> findAllIds(Long retailCustomerId) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public Authorization findByUUID(UUID uuid) {
        return (Authorization) em.createNamedQuery(Authorization.QUERY_FIND_BY_UUID)
                .setParameter("uuid", uuid.toString().toUpperCase())
                .getSingleResult();
    }

	@Override
        @Transactional
	public void deleteById(Long id) {
	       em.remove(findById(id));
	}

	@Override
	public void createOrReplaceByUUID(Authorization authorization) {
		// TODO Auto-generated method stub
		
	}
}
