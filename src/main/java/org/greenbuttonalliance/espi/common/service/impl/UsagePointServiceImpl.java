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
import org.greenbuttonalliance.espi.common.domain.RetailCustomer;
import org.greenbuttonalliance.espi.common.domain.Subscription;
import org.greenbuttonalliance.espi.common.domain.UsagePoint;
import org.greenbuttonalliance.espi.common.domain.usage.UsagePointEntity;
import org.greenbuttonalliance.espi.common.models.atom.EntryType;
import org.greenbuttonalliance.espi.common.repositories.ResourceRepository;
import org.greenbuttonalliance.espi.common.repositories.UsagePointRepository;
import org.greenbuttonalliance.espi.common.service.ImportService;
import org.greenbuttonalliance.espi.common.service.ResourceService;
import org.greenbuttonalliance.espi.common.service.UsagePointService;
import org.greenbuttonalliance.espi.common.utils.EntryTypeIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
		jakarta.persistence.NoResultException.class,
		org.springframework.dao.EmptyResultDataAccessException.class })
public class UsagePointServiceImpl implements UsagePointService {

	private final UsagePointRepository usagePointRepository;
	private final ImportService importService;
	private final ResourceRepository repository;
	private final ResourceService resourceService;

	public UsagePointServiceImpl(UsagePointRepository usagePointRepository,
								 ImportService importService,
								 ResourceRepository repository,
								 ResourceService resourceService) {
		this.usagePointRepository = usagePointRepository;
		this.importService = importService;
		this.repository = repository;
		this.resourceService = resourceService;
	}


	@Override
	public void setResourceService(ResourceService resourceService) {
		// No-op: constructor injection used, but interface requires this method
	}

	@Override
	public void setRepository(UsagePointRepository usagePointRepository) {
		// No-op: constructor injection used, but interface requires this method
	}

	@Override
	public List<UsagePoint> findAllByRetailCustomer(RetailCustomer customer) {
		// TODO: Implement entity to domain conversion
		return new ArrayList<>();
	}

	@Override
	public UsagePoint findById(Long usagePointId) {
		// TODO: Implement entity to domain conversion
		return null;
	}

	@Override
	public UsagePoint findById(Long retailCustomerId, Long usagePointId) {
		// TODO: if needed, this needs to be scoped down to the RetailCustomer
		// collection and implement entity to domain conversion
		return null;
	}

	@Override
	public void persist(UsagePoint up) {
		// TODO: Implement domain to entity conversion
		// this.usagePointRepository.save(up);
	}

	@Override
	public void createOrReplaceByUUID(UsagePoint usagePoint) {
		// TODO: Implement this logic in service layer with entity conversion
		// Check if exists by UUID, then save or update
	}

	@Override
	public void associateByUUID(RetailCustomer retailCustomer, UUID uuid) {
		// TODO: Implement this logic in service layer
		// Find usage point by UUID and set retail customer
	}

	@Override
	public UsagePoint findByUUID(UUID uuid) {
		// TODO: Implement entity to domain conversion
		return null;
	}

	@Override
	public UsagePoint findByHashedId(String usagePointHashedId) {
		return findByUUID(UUID.fromString(usagePointHashedId));
	}

	@Override
	public List<UsagePoint> findAllUpdatedFor(Subscription subscription) {
		// TODO: Implement this logic using findAllUpdatedAfter with subscription timestamp
		return new ArrayList<>();
	}

	@Override
	public void deleteByHashedId(String usagePointHashedId) {
		UsagePoint usagePoint = findByHashedId(usagePointHashedId);
		if (usagePoint != null) {
			usagePointRepository.deleteById(usagePoint.getId());
		}
	}

	@Override
	public List<Long> findAllIdsForRetailCustomer(Long retailCustomerId) {
		return usagePointRepository
				.findAllIdsByRetailCustomerId(retailCustomerId);
	}

	@Override
	public String feedFor(List<UsagePoint> usagePoints) throws FeedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String entryFor(UsagePoint usagePoint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsagePoint> findAllByRetailCustomer(Long retailCustomerId) {
		// TODO: Implement entity to domain conversion
		return new ArrayList<>();
	}

	@Override
	public void add(UsagePoint usagePoint) {
		// TODO Auto-generated method stub
	}

	@Override
	public void delete(UsagePoint usagePoint) {

		usagePointRepository.deleteById(usagePoint.getId());

	}

	@Override
	public UsagePoint importResource(InputStream stream) {

		UsagePoint usagePoint = null;
		try {
			importService.importData(stream, null);
			usagePoint = importService.getEntries().get(0).getContent()
					.getUsagePoint();

		} catch (Exception e) {

		}
		return usagePoint;
	}

	@Override
	public EntryType findEntryType(Long retailCustomerId, Long usagePointId) {
		EntryType result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understand
			// creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp.add(usagePointId);
			findAllIdsForRetailCustomer(retailCustomerId);
			result = (new EntryTypeIterator(resourceService, temp,
					UsagePoint.class)).next();
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public EntryTypeIterator findEntryTypeIterator() {
		EntryTypeIterator result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understand
			// creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp = usagePointRepository.findAllIds();
			result = new EntryTypeIterator(resourceService, temp,
					UsagePoint.class);
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public EntryType findEntryType(Long usagePointId) {
		EntryType result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understan
			// creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp = usagePointRepository.findAllIds();
			result = (new EntryTypeIterator(resourceService, temp,
					UsagePoint.class)).nextEntry(UsagePoint.class);
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId) {
		EntryTypeIterator result = null;
		try {
			List<Long> allIdsForRetailCustomer = findAllIdsForRetailCustomer(retailCustomerId);
			result = new EntryTypeIterator(resourceService,
					allIdsForRetailCustomer, UsagePoint.class);
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found

			result = null;
		}
		return result;
	}

	@Override
	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId,
			Long usagePointId) {
		EntryTypeIterator result = null;
		List<Long> temp = new ArrayList<Long>();
		temp.add(usagePointId);
		try {
			// make the call to insure it is a valid usagePointId
			resourceService.findIdByXPath(retailCustomerId, usagePointId,
					UsagePoint.class);
			result = (new EntryTypeIterator(resourceService, temp,
					UsagePoint.class));
		} catch (Exception e) {
			System.out
					.printf("****UsagePointService: Seraching for an invalide UsagePointId - %d: %s\n",
							usagePointId, e.toString());
			result = null;
		}
		return result;
	}
}
