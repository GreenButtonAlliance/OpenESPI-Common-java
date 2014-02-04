package org.energyos.espi.common.service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.SubscriptionRepository;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public interface SubscriptionService {
	// TODO: likely deprecated

	Subscription createSubscription(OAuth2Authentication retailCustomer);

	List<Subscription> findAll();

	Subscription findByHashedId(String hashedId);

	EntryTypeIterator findEntriesByHashedId(String hashedId);

	EntryTypeIterator find(String subscriptionHashedId);

	// persistence management services
	public void setRepository(SubscriptionRepository subscriptionRepository);

	public void persist(Subscription subscription);	

	// accessor services
	public Subscription findById(String subscriptionId);

	public EntryType findEntryType(Long retailCustomerId, Long subscriptionId);

	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId);

	public void add(Subscription subscription);

	public void delete(Subscription subscription);
	
	public void merge(Subscription subscription);

	// import-exportResource services
	public Subscription importResource(InputStream stream);

	public Subscription findById(Long subscriptionId);

	public Subscription findById(Long retailCustomerId, Long subscriptionId);

	public Subscription findByUUID(UUID uuid);
	
	public List<Long> findUsagePointIds(Long subscriptionId);

}
