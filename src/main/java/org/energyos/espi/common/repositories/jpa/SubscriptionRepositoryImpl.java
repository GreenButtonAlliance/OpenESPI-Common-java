package org.energyos.espi.common.repositories.jpa;

import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.repositories.SubscriptionRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    @PersistenceContext
    protected EntityManager em;

    public void setEntityManager(EntityManager em)
      {
      this.em = em;
      }

    @Override
    @Transactional
    public void persist(Subscription subscription) {
        if (subscription.getHashedId() == null) subscription.setHashedId(UUID.randomUUID().toString());
        em.persist(subscription);
    }

    @Override
    public List<Subscription> findAll() {
        return (List<Subscription>)this.em.createNamedQuery(Subscription.QUERY_FIND_ALL).getResultList();
    }

    @Override
    public Subscription findByHashedId(String hashedId) {
        return (Subscription)em.createNamedQuery(Subscription.QUERY_FIND_BY_HASHED_ID)
                .setParameter("hashedId", hashedId)
                .getSingleResult();
    }

	@Override
	public Subscription findById(Long subscriptionId) {
        return em.find(Subscription.class, subscriptionId);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
	    Subscription r = findById(id);
	    em.remove(em.contains(r) ? r : em.merge(r));
		
	}
}
