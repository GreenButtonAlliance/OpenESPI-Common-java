package org.energyos.espi.common.repositories.jpa;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.repositories.SubscriptionRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
    public void merge(Subscription subscription) {
    	em.merge(subscription);
    }

    @Override
    public Subscription findByUUID(UUID uuid) {
        return (Subscription) em.createNamedQuery(Subscription.QUERY_FIND_BY_UUID)
                .setParameter("uuid", uuid.toString().toUpperCase())
                .getSingleResult();
    }

    @SuppressWarnings("unchecked")
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
	public Subscription findById(Long id) {
        return (Subscription)em.createNamedQuery(Subscription.QUERY_FIND_BY_ID)
                .setParameter("id", id).getSingleResult();
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
	    Subscription subscription = findById(id);
	    Set<UsagePoint> ups = subscription.getUsagePoints();
	    Iterator<UsagePoint> it = ups.iterator();
	    while (it.hasNext()) {
	    	UsagePoint up = it.next();
	    	up.removeSubscription(subscription);
	    	em.persist(em.contains(up)? up : em.merge(up));
	    }	    
	    em.remove(em.contains(subscription) ? subscription : em.merge(subscription));	
	}

}
