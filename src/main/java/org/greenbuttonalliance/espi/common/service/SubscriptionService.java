/*
 *
 *    Copyright (c) 2018-2021 Green Button Alliance, Inc.
 *
 *    Portions (c) 2013-2018 EnergyOS.org
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package org.greenbuttonalliance.espi.common.service;

import org.greenbuttonalliance.espi.common.domain.legacy.Subscription;
import org.greenbuttonalliance.espi.common.domain.legacy.UsagePoint;
import org.greenbuttonalliance.espi.common.domain.legacy.atom.EntryType;
import org.greenbuttonalliance.espi.common.repositories.usage.SubscriptionRepository;
import org.greenbuttonalliance.espi.common.utils.EntryTypeIterator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.List;

public interface SubscriptionService {

	Subscription createSubscription(OAuth2Authentication retailCustomer);

	Subscription findByHashedId(String hashedId);

	EntryTypeIterator findEntriesByHashedId(String hashedId);

	void setRepository(SubscriptionRepository subscriptionRepository);

	EntryType findEntryType(Long retailCustomerId, Long subscriptionId);

	EntryTypeIterator findEntryTypeIterator(Long retailCustomerId);

	void merge(Subscription subscription);

	Subscription findById(Long subscriptionId);

	List<Long> findUsagePointIds(Long subscriptionId);

	Subscription findByAuthorizationId(Long id);

	Subscription addUsagePoint(Subscription subscription,
							   UsagePoint usagePoint);

	Long findRetailCustomerId(Long subscriptionId, Long usagePointId);

}
