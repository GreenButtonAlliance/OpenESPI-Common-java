/*
 * Copyright 2013, 2014 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.common.service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.SubscriptionRepository;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public interface SubscriptionService {

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
	
	public List<EntryTypeIterator> findEntryTypeIterator(List<Long> subscriptions);

	public void delete(Subscription subscription);
	
	public void merge(Subscription subscription);

	// import-exportResource services
	public Subscription importResource(InputStream stream);

	public Subscription findById(Long subscriptionId);
	
	public List<Long> findByBulkId(Long bulkId);

	public Subscription findById(Long retailCustomerId, Long subscriptionId);

	public Subscription findByUUID(UUID uuid);
	
	public List<Long> findUsagePointIds(Long subscriptionId);

	public Subscription findByAuthorizationId(Long id);

	public Subscription addUsagePoint(Subscription subscription, UsagePoint usagePoint);

}
