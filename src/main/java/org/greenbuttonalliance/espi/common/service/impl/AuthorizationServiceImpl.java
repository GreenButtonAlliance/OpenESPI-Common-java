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
import org.greenbuttonalliance.espi.common.domain.legacy.Subscription;
import org.greenbuttonalliance.espi.common.domain.legacy.atom.EntryType;
import org.greenbuttonalliance.espi.common.domain.usage.AuthorizationEntity;
import org.greenbuttonalliance.espi.common.dto.usage.AuthorizationDto;
import org.greenbuttonalliance.espi.common.mapper.usage.AuthorizationMapper;
import org.greenbuttonalliance.espi.common.repositories.usage.AuthorizationRepository;
import org.greenbuttonalliance.espi.common.repositories.usage.UsagePointRepository;
import org.greenbuttonalliance.espi.common.service.AuthorizationService;
import org.greenbuttonalliance.espi.common.service.DtoExportService;
import org.greenbuttonalliance.espi.common.service.ImportService;
import org.greenbuttonalliance.espi.common.service.ResourceService;
import org.greenbuttonalliance.espi.common.utils.EntryTypeIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

	private final Log logger = LogFactory.getLog(getClass());
	
	private final AuthorizationRepository authorizationRepository;
	private final UsagePointRepository usagePointRepository;
	private final ResourceService resourceService;
	private final ImportService importService;
	private final DtoExportService dtoExportService;
	private final AuthorizationMapper authorizationMapper;

	public AuthorizationServiceImpl(AuthorizationRepository authorizationRepository,
									UsagePointRepository usagePointRepository,
									ResourceService resourceService,
									ImportService importService,
									DtoExportService dtoExportService,
									AuthorizationMapper authorizationMapper) {
		this.authorizationRepository = authorizationRepository;
		this.usagePointRepository = usagePointRepository;
		this.resourceService = resourceService;
		this.importService = importService;
		this.dtoExportService = dtoExportService;
		this.authorizationMapper = authorizationMapper;
	}

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
		return authorizationRepository.findByUuid(uuid).orElse(null);
	}

	@Override
	public Authorization createAuthorization(Subscription subscription,
			String accessToken) {
		// TODO: Implement modern authorization creation
		logger.warn("createAuthorization method needs modern implementation");
		return null;
	}

	@Override
	public Authorization findByState(String state) {
		return authorizationRepository.findByState(state).orElse(null);
	}

	@Override
	public Authorization findByScope(String scope, Long retailCustomerId) {
		return authorizationRepository.findByScope(scope, retailCustomerId).orElse(null);
	}

	@Override
	public List<Authorization> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String entryFor(Authorization authorization) {
		try {
			// TODO: Implement modern DTO export for authorization
			logger.warn("entryFor method needs modern implementation");
			return null;
			
		} catch (Exception e) {
			logger.error("Failed to generate entry for authorization: " + e.getMessage(), e);
			return null;
		}
	}

	@Override
	public Authorization findByURI(String uri) {
		// TODO: Fix this method to work with new repository structure
		// UsagePoint usagePoint = usagePointRepository.findByResourceUri(uri).orElse(null);
		// return usagePoint != null ? usagePoint.getSubscription().getAuthorization() : null;
		return null;
	}

	@Override
	public String feedFor(List<Authorization> authorizations) {
		try {
			// TODO: Implement modern DTO feed export for authorizations
			logger.warn("feedFor method needs modern implementation");
			return null;
			
		} catch (Exception e) {
			logger.error("Failed to generate feed for authorizations: " + e.getMessage(), e);
			return null;
		}
	}

	// persistence management services
	@Override
	public void persist(Authorization authorization) {
		authorizationRepository.save(authorization);
	}

	@Override
	public void merge(Authorization authorization) {
		authorizationRepository.save(authorization);
	}

	// accessor services

	@Override
	public Authorization findById(Long authorizationId) {
		return this.authorizationRepository.findById(authorizationId).orElse(null);
	}

	@Override
	public EntryType findEntryType(Long retailCustomerId, Long authorizationId) {
		EntryType result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understand
			// creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			Authorization authorization = authorizationRepository
					.findById(authorizationId).orElse(null);
			if (authorization != null) {
				temp.add(authorization.getId());
			}
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
					.findById(authorizationId).orElse(null);
			if (authorization != null) {
				temp.add(authorization.getId());
			}
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
		// TODO: Implement modern authorization persistence
		logger.warn("add method needs modern implementation with AuthorizationEntityRepository");
		// authorizationRepository.save(authorization); // For legacy compatibility
	}

	@Override
	public void delete(Authorization authorization) {
		authorizationRepository.deleteById(authorization.getId());
	}

	// import-exportResource services
	@Override
	public Authorization importResource(InputStream stream) {
		try {
			// Use modern ImportService to parse DTO from stream
			importService.importData(stream, null);
			
			// TODO: Extract specific authorization from imported data
			// For now, this is a placeholder as the import service
			// processes multiple resource types and we need to identify
			// which one is the authorization
			logger.info("Authorization import completed using modern DTO processing");
			return null;
			
		} catch (Exception e) {
			logger.error("Failed to import authorization: " + e.getMessage(), e);
			return null;
		}
	}

	@Override
	public Authorization findById(Long retailCustomerId, long authorizationId) {
		return this.authorizationRepository.findById(authorizationId).orElse(null);
	}

	@Override
	public Authorization findByAccessToken(String accessToken) {
		return authorizationRepository.findByAccessToken(accessToken).orElse(null);
	}

	@Override
	public Authorization findByRefreshToken(String refreshToken) {
		return authorizationRepository.findByRefreshToken(refreshToken).orElse(null);
	}

	@Override
	public List<Long> findAllIdsByBulkId(String thirdParty, Long bulkId) {
		return authorizationRepository.findAllIdsByBulkId(thirdParty, bulkId.toString());
	}

	/**
	 * Converts legacy Authorization to modern AuthorizationEntity.
	 * This is a bridge method to support legacy interface while using modern implementation.
	 */
	private AuthorizationEntity convertLegacyToEntity(Authorization legacy) {
		AuthorizationEntity entity = new AuthorizationEntity();
		
		// Map common fields that exist in both legacy and modern entities
		entity.setAccessToken(legacy.getAccessToken());
		entity.setRefreshToken(legacy.getRefreshToken());
		entity.setScope(legacy.getScope());
		entity.setResourceURI(legacy.getResourceURI());
		entity.setAuthorizationURI(legacy.getAuthorizationURI()); // Correct field name
		entity.setThirdParty(legacy.getThirdParty());
		entity.setStatus(legacy.getStatus());
		entity.setExpiresIn(legacy.getExpiresIn());
		entity.setCode(legacy.getCode());
		
		// Note: Some fields like applicationInformationId, retailCustomerId, ppid 
		// may not have direct getters in legacy Authorization - skipping for now
		
		return entity;
	}

}
