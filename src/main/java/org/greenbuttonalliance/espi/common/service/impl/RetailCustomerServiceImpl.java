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

import org.greenbuttonalliance.espi.common.domain.legacy.Authorization;
import org.greenbuttonalliance.espi.common.domain.legacy.RetailCustomer;
import org.greenbuttonalliance.espi.common.domain.legacy.ServiceCategory;
import org.greenbuttonalliance.espi.common.domain.legacy.Subscription;
import org.greenbuttonalliance.espi.common.domain.legacy.UsagePoint;
import org.greenbuttonalliance.espi.common.repositories.usage.RetailCustomerRepository;
import org.greenbuttonalliance.espi.common.service.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class RetailCustomerServiceImpl implements RetailCustomerService {

	private final RetailCustomerRepository retailCustomerRepository;
	private final ResourceService resourceService;
	private final ImportService importService;
	private final AuthorizationService authorizationService;
	private final SubscriptionService subscriptionService;
	private final UsagePointService usagePointService;

	public RetailCustomerServiceImpl(RetailCustomerRepository retailCustomerRepository,
									 ResourceService resourceService,
									 ImportService importService,
									 AuthorizationService authorizationService,
									 SubscriptionService subscriptionService,
									 UsagePointService usagePointService) {
		this.retailCustomerRepository = retailCustomerRepository;
		this.resourceService = resourceService;
		this.importService = importService;
		this.authorizationService = authorizationService;
		this.subscriptionService = subscriptionService;
		this.usagePointService = usagePointService;
	}

	@Override
	public List<RetailCustomer> findAll() {
		return retailCustomerRepository.findAll();
	}

	@Override
	public void persist(RetailCustomer customer) {
		if (customer.getUUID() == null) {
			customer.setUUID(UUID.randomUUID());
		}
		retailCustomerRepository.save(customer);
	}

	@Override
	public RetailCustomer findById(Long id) {
		return retailCustomerRepository.findById(id).orElse(null);
	}

	@Override
	public RetailCustomer findById(String retailCustomerId) {
		try {
			Long id = Long.parseLong(retailCustomerId);
			return retailCustomerRepository.findById(id).orElse(null);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	public RetailCustomer findByHashedId(Long retailCustomerId) {
		return findById(retailCustomerId);
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		try {
			return retailCustomerRepository.findByUsername(username).orElse(null);
		} catch (EmptyResultDataAccessException x) {
			throw new UsernameNotFoundException("Unable to find user");
		}
	}

	@Override
	public void add(RetailCustomer retailCustomer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(RetailCustomer retailCustomer) {
		retailCustomerRepository.deleteById(retailCustomer.getId());

	}

	@Override
	public RetailCustomer importResource(InputStream stream) {
		RetailCustomer retailCustomer = null;
		try {
			importService.importData(stream, null);
			// TODO - Make RetailCustomer inherit from IdentifiedObject and add
			// RetailCustomer to the entrytype structure

			// EntryType entry = importService.getEntries().get(0);
			// RetailCustomer retailCustomer =
			// entry.getContent().getRetailCustomer();
			// persist(retailCustomer);
		} catch (Exception e) {
			System.out.printf(
					"**** RetailCustomerService:importResource Failed %s\n",
					e.toString());
		}
		return retailCustomer;
	}

	@Transactional
	@Override
	public Subscription associateByUUID(Long retailCustomerId, UUID uuid) {
		Subscription subscription = null;
		RetailCustomer retailCustomer = null;
		UsagePoint usagePoint = null;

		try {
			retailCustomer = resourceService.findById(retailCustomerId,
					RetailCustomer.class);
			try {

				// TODO: Fix this method call
			usagePoint = null;

			} catch (Exception e) {

				usagePoint = new UsagePoint();
				usagePoint.setUUID(uuid);
				usagePoint.setDescription("A Temporary UsagePoint Description");
				usagePoint.setServiceCategory(new ServiceCategory(
						ServiceCategory.ELECTRICITY_SERVICE));
				// TODO: Use UsagePointService instead
				// usagePointService.persist(usagePoint);

			}
			usagePoint.setRetailCustomer(retailCustomer);
			// TODO: Use specific service instead
			// service.persist(usagePoint);

			// now see if there are any authorizations for this information
			//
			try {

				for (Authorization authorization : authorizationService
						.findAllByRetailCustomerId(retailCustomer.getId())) {

					String resourceUri = authorization.getResourceURI();
					if (resourceUri == null) {

						authorization
								.setResourceURI(authorization
										.getApplicationInformation()
										.getDataCustodianResourceEndpoint()
										+ "/Batch/Subscription/"
										+ subscription.getId());
						// TODO: Use specific service instead
						// authorizationService.persist(authorization);

					}

					subscription = subscriptionService
							.findByAuthorizationId(authorization.getId());

					subscription.getUsagePoints().add(usagePoint);
					// TODO: Use specific service instead
					// subscriptionService.persist(subscription);
				}
			} catch (Exception e) {
				// we get here if we don't have a subscription
				return null;
			}

		} catch (Exception e) {
			System.out.printf("****Error Associating UsagePoint: %s - %s\n",
					retailCustomer.toString(), usagePoint.toString());
		}

		return subscription;
	}


}
