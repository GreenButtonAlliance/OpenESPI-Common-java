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

import com.sun.syndication.io.FeedException;
import org.greenbuttonalliance.espi.common.domain.usage.RetailCustomerEntity;
import org.greenbuttonalliance.espi.common.domain.usage.SubscriptionEntity;
import org.greenbuttonalliance.espi.common.domain.usage.UsagePointEntity;
import org.greenbuttonalliance.espi.common.repositories.usage.ResourceRepository;
import org.greenbuttonalliance.espi.common.repositories.usage.UsagePointRepository;
import org.greenbuttonalliance.espi.common.service.ImportService;
import org.greenbuttonalliance.espi.common.service.ResourceService;
import org.greenbuttonalliance.espi.common.service.UsagePointService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Modern UsagePoint service implementation using entity classes.
 * Legacy ATOM feed and entry processing removed for Spring Boot 3.5 compatibility.
 */
@Service
@Transactional
public class UsagePointServiceImpl implements UsagePointService {

	private final UsagePointRepository usagePointRepository;
	private final ResourceRepository resourceRepository;
	private final ResourceService resourceService;
	private final ImportService importService;

	public UsagePointServiceImpl(UsagePointRepository usagePointRepository,
								ResourceRepository resourceRepository,
								ResourceService resourceService,
								ImportService importService) {
		this.usagePointRepository = usagePointRepository;
		this.resourceRepository = resourceRepository;
		this.resourceService = resourceService;
		this.importService = importService;
	}

	@Override
	public void setRepository(UsagePointRepository usagePointRepository) {
		// Repository is injected via constructor
	}

	@Override
	public void setResourceService(ResourceService resourceService) {
		// ResourceService is injected via constructor
	}

	@Override
	public List<UsagePointEntity> findAllByRetailCustomer(RetailCustomerEntity customer) {
		// TODO: Implement entity to domain conversion
		return new ArrayList<>();
	}

	@Override
	public UsagePointEntity findById(Long usagePointId) {
		// TODO: Implement entity to domain conversion
		return null;
	}

	@Override
	public UsagePointEntity findById(Long retailCustomerId, Long usagePointId) {
		// TODO: if needed, this needs to be scoped down to the RetailCustomer
		// collection and implement entity to domain conversion
		return null;
	}

	@Override
	public void persist(UsagePointEntity up) {
		// TODO: Implement domain to entity conversion
		// this.usagePointRepository.save(up);
	}

	@Override
	public void createOrReplaceByUUID(UsagePointEntity usagePoint) {
		// TODO: Implement this logic in service layer with entity conversion
		// Check if exists by UUID, then save or update
	}

	@Override
	public void associateByUUID(RetailCustomerEntity retailCustomer, UUID uuid) {
		// TODO: Implement this logic in service layer
		// Find usage point by UUID and set retail customer
	}

	@Override
	public UsagePointEntity findByUUID(UUID uuid) {
		// TODO: Implement entity to domain conversion
		return null;
	}

	@Override
	public UsagePointEntity findByHashedId(String usagePointHashedId) {
		return findByUUID(UUID.fromString(usagePointHashedId));
	}

	@Override
	public List<UsagePointEntity> findAllUpdatedFor(SubscriptionEntity subscription) {
		// TODO: Implement this logic using findAllUpdatedAfter with subscription timestamp
		return new ArrayList<>();
	}

	@Override
	public void deleteByHashedId(String usagePointHashedId) {
		UsagePointEntity usagePoint = findByHashedId(usagePointHashedId);
		if (usagePoint != null) {
			usagePointRepository.deleteById(usagePoint.getId());
		}
	}

	@Override
	public List<Long> findAllIdsForRetailCustomer(Long id) {
		return usagePointRepository
				.findAllIdsByRetailCustomerId(id);
	}

	@Override
	public String feedFor(List<UsagePointEntity> usagePoints) throws FeedException {
		// TODO: Implement modern feed generation
		return null;
	}

	@Override
	public String entryFor(UsagePointEntity usagePoint) {
		// TODO: Implement modern entry generation
		return null;
	}

	@Override
	public List<UsagePointEntity> findAllByRetailCustomer(Long retailCustomerId) {
		// TODO: Implement entity to domain conversion
		return new ArrayList<>();
	}

	@Override
	public void add(UsagePointEntity usagePoint) {
		// TODO: Implement add logic
	}

	@Override
	public void delete(UsagePointEntity usagePoint) {
		usagePointRepository.deleteById(usagePoint.getId());
	}

	@Override
	public UsagePointEntity importResource(InputStream stream) {
		try {
			// Use the modern ImportService to parse the stream
			importService.importData(stream, null);
			
			// Get the parsed usage points
			List<UsagePointEntity> usagePoints = importService.getEntries();
			
			if (usagePoints != null && !usagePoints.isEmpty()) {
				// Return the first usage point (typical for single resource import)
				return usagePoints.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			// Log error and return null
			return null;
		}
	}

	// Legacy methods removed - incompatible with Spring Boot 3.5
	// The following methods used legacy EntryType and are no longer supported:
	// - findEntryType(Long retailCustomerId, Long usagePointId)
	// - findEntryTypeIterator()
	// - findEntryType(Long usagePointId)  
	// - findEntryTypeIterator(Long retailCustomerId)
	// - findEntryTypeIterator(Long retailCustomerId, Long usagePointId)
}