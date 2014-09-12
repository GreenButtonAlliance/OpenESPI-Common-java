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

import java.util.List;

import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.SubscriptionRepository;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public interface SubscriptionService {

	Subscription createSubscription(OAuth2Authentication retailCustomer);

	Subscription findByHashedId(String hashedId);

	EntryTypeIterator findEntriesByHashedId(String hashedId);

	public void setRepository(SubscriptionRepository subscriptionRepository);

	public EntryType findEntryType(Long retailCustomerId, Long subscriptionId);

	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId);

	public void merge(Subscription subscription);

	public Subscription findById(Long subscriptionId);

	public List<Long> findUsagePointIds(Long subscriptionId);

	public Subscription findByAuthorizationId(Long id);

	public Subscription addUsagePoint(Subscription subscription,
			UsagePoint usagePoint);

	public Long findRetailCustomerId(Long subscriptionId, Long usagePointId);

}
