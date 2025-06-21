
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

package org.greenbuttonalliance.espi.common.service.impl;

import org.greenbuttonalliance.espi.common.domain.legacy.RetailCustomer;
import org.greenbuttonalliance.espi.common.domain.legacy.Subscription;
import org.greenbuttonalliance.espi.common.domain.legacy.UsagePoint;
import org.greenbuttonalliance.espi.common.domain.legacy.atom.EntryType;
import org.greenbuttonalliance.espi.common.repositories.usage.SubscriptionRepository;
import org.greenbuttonalliance.espi.common.repositories.usage.UsagePointRepository;
import org.greenbuttonalliance.espi.common.service.*;
import org.greenbuttonalliance.espi.common.utils.EntryTypeIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private UsagePointRepository usagePointRepository;

	@Autowired
	private ApplicationInformationService applicationInformationService;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private ImportService importService;

	@Autowired
	private RetailCustomerService retailCustomerService;

	@Override
	@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
			jakarta.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	public Subscription createSubscription(OAuth2Authentication authentication) {
		Subscription subscription = new Subscription();
		subscription.setUUID(UUID.randomUUID());

		// Determine requestor's Role
		Set<String> role = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

		if (role.contains("ROLE_USER")) {

			subscription.setApplicationInformation(applicationInformationService
					.findByClientId(authentication.getOAuth2Request().getClientId()));
			subscription.setRetailCustomer((RetailCustomer) authentication.getPrincipal());
			subscription.setUsagePoints(new ArrayList<UsagePoint>());
			// set up the subscription's usagePoints list. Keep in mind that right
			// now this is ALL usage points belonging to the RetailCustomer.
			// TODO - scope this to only a selected (proper) subset of the
			// usagePoints as passed
			// through from the UX or a restful call.
			List<Long> upIds = resourceService.findAllIdsByXPath(subscription
				.getRetailCustomer().getId(), UsagePoint.class);
			Iterator<Long> it = upIds.iterator();
			while (it.hasNext()) {
				UsagePoint usagePoint = resourceService.findById(it.next(),
					UsagePoint.class);
				subscription.getUsagePoints().add(usagePoint);
			}
		} else {
			String clientId = authentication.getOAuth2Request().getClientId();
			String ci = clientId;
			if (ci.indexOf("REGISTRATION_") != -1) {
				if (ci.substring(0, "REGISTRATION_".length()).equals(
						"REGISTRATION_")) {
					ci = ci.substring("REGISTRATION_".length());
				}
			}
			if (ci.indexOf("_admin") != -1) {
				ci = ci.substring(0, ci.indexOf("_admin"));
			}
			subscription.setApplicationInformation(applicationInformationService.findByClientId(ci));
			subscription.setRetailCustomer(retailCustomerService.findById((long) 0));
		}
		subscription.setLastUpdate(new GregorianCalendar());
		subscriptionRepository.save(subscription);

		return subscription;
	}

	@Override
	public Subscription findByHashedId(String hashedId) {
		return subscriptionRepository.findByHashedId(hashedId).orElse(null);
	}

	@Override
	public EntryTypeIterator findEntriesByHashedId(String hashedId) {
		Subscription subscription = subscriptionRepository
				.findByHashedId(hashedId).orElse(null);
		List<Long> subscriptionIds = new ArrayList<Long>();
		subscriptionIds.add(subscription.getId());
		return new EntryTypeIterator(resourceService, subscriptionIds,
				Subscription.class);
	}

	public void setRepository(SubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
	}

	@Override
	public EntryType findEntryType(Long retailCustomerId, Long subscriptionId) {
		EntryType result = null;
		try {
			List<Long> allIds = new ArrayList<Long>();
			allIds.add(subscriptionId);
			result = (new EntryTypeIterator(resourceService, allIds,
					Subscription.class)).nextEntry(Subscription.class);
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public EntryTypeIterator findEntryTypeIterator(Long subscriptionId) {
		EntryTypeIterator result = null;
		try {

			result = (new EntryTypeIterator(resourceService,
					findUsagePointIds(subscriptionId), Subscription.class));
			result.setSubscriptionId(subscriptionId);

		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public void merge(Subscription subscription) {
		subscriptionRepository.save(subscription);
	}

	@Override
	public Subscription findById(Long subscriptionId) {
		return subscriptionRepository.findById(subscriptionId).orElse(null);
	}

	@Override
	public List<Long> findUsagePointIds(Long subscriptionId) {

		List<Long> result = new ArrayList<Long>();
		Subscription subscription = findById(subscriptionId);
		for (UsagePoint up : subscription.getUsagePoints()) {
			result.add(up.getId());
		}
		return result;
	}

	@Override
	public Subscription findByAuthorizationId(Long id) {
		return subscriptionRepository.findByAuthorizationId(id).orElse(null);
	}

	@Override
	@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
			jakarta.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	public Subscription addUsagePoint(Subscription subscription,
			UsagePoint usagePoint) {

		subscription.getUsagePoints().add(usagePoint);
		return subscription;

	}

	@Override
	public Long findRetailCustomerId(Long subscriptionId, Long usagePointId) {
		Long result = null;
		Subscription s = resourceService.findById(subscriptionId,
				Subscription.class);
		result = s.getRetailCustomer().getId();
		if (result.equals(0L)) {
			// we have a subscription that is based upon client credentials
			// now we must find the actual retail customer associated with
			// this particular usagePoint
			result = resourceService.findById(usagePointId, UsagePoint.class)
					.getRetailCustomer().getId();
		}
		s.getAuthorization().getRetailCustomer();
		return result;
	}

	public void setSubscriptionRepository(
			SubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
	}

	public SubscriptionRepository getSubscriptionRepository() {
		return this.subscriptionRepository;
	}

	public void setUsagePointRepository(
			UsagePointRepository usagePointRepository) {
		this.usagePointRepository = usagePointRepository;
	}

	public UsagePointRepository getUsagePointRepository() {
		return this.usagePointRepository;
	}

	public void setApplicationInformationService(
			ApplicationInformationService applicationInformationService) {
		this.applicationInformationService = applicationInformationService;
	}

	public ApplicationInformationService getApplicationInformationService() {
		return this.applicationInformationService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourceService getResourceService() {
		return this.resourceService;
	}

	public void setImportService(ImportService importService) {
		this.importService = importService;
	}

	public ImportService getImportService() {
		return this.importService;
	}

}
