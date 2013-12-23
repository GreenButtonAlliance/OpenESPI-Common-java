package org.energyos.espi.common.service;

import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.SubscriptionRepository;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface SubscriptionService {
	// TODO: likely deprecated

	Subscription createSubscription(OAuth2Authentication retailCustomer);

	List<Subscription> findAll();

	Subscription findByHashedId(String hashedId);

	EntryTypeIterator findEntriesByHashedId(String hashedId);

	EntryTypeIterator findEntriesByRetailCustomerId(Long retailCustomerId);

	EntryTypeIterator find(String subscriptionHashedId);

	// persistence management services
	void setRepository(SubscriptionRepository subscriptionRepository);

	void persist(Subscription subscription);

	// accessor services
	public Subscription findById(String subscriptionId);

	public EntryType findEntryType(Long retailCustomerId, Long subscriptionId);

	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId);

	public void add(Subscription subscription);

	public void delete(Subscription subscription);

	// import-exportResource services
	public Subscription importResource(InputStream stream);

	Subscription findById(long subscriptionId);

	Subscription findById(Long retailCustomerId, long subscriptionId);

}
