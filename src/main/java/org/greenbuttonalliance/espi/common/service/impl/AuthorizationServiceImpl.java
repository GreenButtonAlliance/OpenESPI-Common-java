/*
 *     Copyright (c) 2018-2019 Green Button Alliance, Inc.
 *
 *     Portions copyright (c) 2013-2018 EnergyOS.org
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

import org.greenbuttonalliance.espi.common.domain.Authorization;
import org.greenbuttonalliance.espi.common.domain.Subscription;
import org.greenbuttonalliance.espi.common.domain.UsagePoint;
import org.greenbuttonalliance.espi.common.models.atom.EntryType;
import org.greenbuttonalliance.espi.common.repositories.AuthorizationRepository;
import org.greenbuttonalliance.espi.common.repositories.UsagePointRepository;
import org.greenbuttonalliance.espi.common.service.AuthorizationService;
import org.greenbuttonalliance.espi.common.service.ImportService;
import org.greenbuttonalliance.espi.common.service.ResourceService;
import org.greenbuttonalliance.espi.common.utils.EntryTypeIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

	@Autowired
	private AuthorizationRepository authorizationRepository;

	@Autowired
	private UsagePointRepository usagePointRepository;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private ImportService importService;

	@Override
	public List<Authorization> findAllByRetailCustomerId(Long retailCustomerId) {
		return authorizationRepository
				.findAllByRetailCustomerId(retailCustomerId);
	}

	@Override
	public List<Long> findAllIdsByApplicationInformationId(
			Long applicationInformationId) {
		return authorizationRepository
				.findAllIdsByApplicationInformationId(applicationInformationId);
	}

	@Override
	public Authorization findByUUID(UUID uuid) {
		return authorizationRepository.findByUUID(uuid);
	}

	@Override
	public Authorization createAuthorization(Subscription subscription,
			String accessToken) {
		Authorization authorization = new Authorization();
		authorization.setUUID(UUID.randomUUID());
		authorizationRepository.persist(authorization);

		return authorization;
	}

	@Override
	public Authorization findByState(String state) {
		return authorizationRepository.findByState(state);
	}

	@Override
	public Authorization findByScope(String scope, Long retailCustomerId) {
		return authorizationRepository.findByScope(scope, retailCustomerId);
	}

	@Override
	public List<Authorization> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String entryFor(Authorization authorization) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Authorization findByURI(String uri) {
		UsagePoint usagePoint = usagePointRepository.findByURI(uri);
		return usagePoint.getSubscription().getAuthorization();
	}

	@Override
	public String feedFor(List<Authorization> authorizations) {
		// TODO Auto-generated method stub
		return null;
	}

	// persistence management services
	@Override
	public void persist(Authorization authorization) {
		authorizationRepository.persist(authorization);
	}

	@Override
	public void merge(Authorization authorization) {
		authorizationRepository.merge(authorization);
	}

	// accessor services

	@Override
	public Authorization findById(Long authorizationId) {
		return this.authorizationRepository.findById(authorizationId);
	}

	@Override
	public EntryType findEntryType(Long retailCustomerId, Long authorizationId) {
		EntryType result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understand
			// creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			Authorization authorization = authorizationRepository
					.findById(authorizationId);
			temp.add(authorization.getId());
			result = (new EntryTypeIterator(resourceService, temp,
					Authorization.class)).nextEntry(Authorization.class);
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
			// TODO - this is sub-optimal (but defers the need to understand
			// creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp = authorizationRepository.findAllIds(retailCustomerId);
			result = (new EntryTypeIterator(resourceService, temp,
					Authorization.class));
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public EntryType findRoot(Long authorizationId) {
		EntryType result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understand
			// creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			Authorization authorization = authorizationRepository
					.findById(authorizationId);
			temp.add(authorization.getId());
			result = (new EntryTypeIterator(resourceService, temp,
					Authorization.class)).next();
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
			temp = authorizationRepository.findAllIds();
			result = (new EntryTypeIterator(resourceService, temp,
					Authorization.class));
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public void add(Authorization authorization) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Authorization authorization) {
		authorizationRepository.deleteById(authorization.getId());
	}

	// import-exportResource services
	@Override
	public Authorization importResource(InputStream stream) {
		Authorization authorization = null;
		try {
			importService.importData(stream, null);
			authorization = importService.getEntries().get(0).getContent()
					.getAuthorization();
		} catch (Exception e) {

		}
		return authorization;
	}

	@Override
	public Authorization findById(Long retailCustomerId, long authorizationId) {
		return this.authorizationRepository.findById(authorizationId);
	}

	@Override
	public Authorization findByAccessToken(String accessToken) {
		return authorizationRepository.findByAccessToken(accessToken);

	}

	@Override
	public Authorization findByRefreshToken(String refreshToken) {
		return authorizationRepository.findByRefreshToken(refreshToken);
	}

	@Override
	public List<Long> findAllIdsByBulkId(String thirdParty, Long bulkId) {
		return authorizationRepository.findAllIdsByBulkId(thirdParty, bulkId);
	}

	public void setAuthorizationRepository(
			AuthorizationRepository authorizationRepository) {
		this.authorizationRepository = authorizationRepository;
	}

	public AuthorizationRepository getAuthorizationRepository() {
		return this.authorizationRepository;
	}

	public void setUsagePointRepository(
			UsagePointRepository usagePointRepository) {
		this.usagePointRepository = usagePointRepository;
	}

	public UsagePointRepository getUsagePointRepository() {
		return this.usagePointRepository;
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
