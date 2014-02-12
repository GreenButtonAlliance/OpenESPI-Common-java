package org.energyos.espi.common.repositories.jpa;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBException;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.repositories.AuthorizationRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

public class AuthorizationRepositoryImpl implements AuthorizationRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    @Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

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
    public Authorization findByScope(String scope, Long retailCustomerId) {
    	return (Authorization)em.createNamedQuery(Authorization.QUERY_FIND_BY_SCOPE)
    			.setParameter("scope", scope)
    			.setParameter("retailCustomerId", retailCustomerId)
    			.getSingleResult();
    }

    @Override
    @Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

    public void merge(Authorization authorization) {
        em.merge(authorization);
    }

	@Override
	public Authorization findById(Long authorizationId) {
        return (Authorization)em.createNamedQuery(Authorization.QUERY_FIND_BY_ID)
                .setParameter("id", authorizationId).getSingleResult();
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
    @Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

	public void deleteById(Long id) {
		em.remove(findById(id));
	}

	@Override
	public void createOrReplaceByUUID(Authorization authorization) {
		// TODO Auto-generated method stub
		
	}
}
