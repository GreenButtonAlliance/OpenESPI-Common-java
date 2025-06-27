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

import org.greenbuttonalliance.espi.common.domain.legacy.ApplicationInformation;
import org.greenbuttonalliance.espi.common.domain.usage.ApplicationInformationEntity;
import org.greenbuttonalliance.espi.common.dto.usage.ApplicationInformationDto;
import org.greenbuttonalliance.espi.common.mapper.usage.ApplicationInformationMapper;
import org.greenbuttonalliance.espi.common.repositories.usage.ApplicationInformationRepository;
import org.greenbuttonalliance.espi.common.service.ApplicationInformationService;
import org.greenbuttonalliance.espi.common.service.ImportService;
import org.greenbuttonalliance.espi.common.service.ResourceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
		jakarta.persistence.NoResultException.class,
		org.springframework.dao.EmptyResultDataAccessException.class })
public class ApplicationInformationServiceImpl implements
		ApplicationInformationService {

	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private ApplicationInformationRepository applicationInformationRepository;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private ImportService importService;
	
	@Autowired
	private ApplicationInformationMapper applicationInformationMapper;

	@Override
	public List<ApplicationInformation> findByKind(String kind) {
		// For now, this legacy method returns empty list
		// Modern applications should use repository methods directly with entities
		logger.warn("Legacy findByKind method called - consider using repository directly");
		return new ArrayList<>();
	}

	@Override
	public ApplicationInformation findByClientId(String clientId) {
		Assert.notNull(clientId, "clientID is required");
		
		// Find modern entity by client ID
		java.util.Optional<ApplicationInformationEntity> entity = applicationInformationRepository.findByClientId(clientId);
		
		if (entity.isPresent()) {
			// Convert modern entity to legacy domain object
			return convertEntityToLegacy(entity.get());
		}
		
		return null;
	}

	@Override
	public ApplicationInformation findByDataCustodianClientId(
			String dataCustodianClientId) {
		Assert.notNull(dataCustodianClientId, "dataCustodianClientId is required");
		
		// For now, this legacy method returns null
		// Modern applications should use repository methods directly with entities
		logger.warn("Legacy findByDataCustodianClientId method called - consider using repository directly");
		
		return null;
	}

	@Override
	public ApplicationInformation importResource(InputStream stream) {

		ApplicationInformation applicationInformation = null;
		try {
			importService.importData(stream, null);
			// TODO: Implement modern import logic for ApplicationInformation
			// Legacy getContent().getApplicationInformation() no longer supported
			applicationInformation = null; // Placeholder
		} catch (Exception e) {

 		}
		return applicationInformation;
	}

	public void setApplicationInformationRepository(
			ApplicationInformationRepository applicationInformationRepository) {
		this.applicationInformationRepository = applicationInformationRepository;
	}

	public ApplicationInformationRepository getApplicationInformationRepository() {
		return this.applicationInformationRepository;
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

	/**
	 * Converts modern ApplicationInformationEntity to legacy ApplicationInformation.
	 * This is a bridge method to support legacy interface while using modern implementation.
	 */
	private ApplicationInformation convertEntityToLegacy(ApplicationInformationEntity entity) {
		ApplicationInformation legacy = new ApplicationInformation();
		
		// Map common fields
		legacy.setId((long) entity.getId().hashCode()); // Convert UUID to hashCode for legacy compatibility
		legacy.setClientId(entity.getClientId());
		legacy.setClientSecret(entity.getClientSecret());
		legacy.setClientName(entity.getClientName());
		legacy.setThirdPartyApplicationName(entity.getThirdPartyApplicationName());
		legacy.setThirdPartyApplicationDescription(entity.getThirdPartyApplicationDescription());
		legacy.setDataCustodianBulkRequestURI(entity.getDataCustodianBulkRequestURI());
		legacy.setDataCustodianResourceEndpoint(entity.getDataCustodianResourceEndpoint());
		legacy.setAuthorizationServerAuthorizationEndpoint(entity.getAuthorizationServerAuthorizationEndpoint());
		legacy.setAuthorizationServerTokenEndpoint(entity.getAuthorizationServerTokenEndpoint());
		legacy.setAuthorizationServerRegistrationEndpoint(entity.getAuthorizationServerRegistrationEndpoint());
		// Skip ThirdPartyNotifyURI - method not available in legacy
		legacy.setThirdPartyLoginScreenURI(entity.getThirdPartyLoginScreenURI());
		// Skip DataCustodianScopeSelectionScreenURI - method not available in legacy
		legacy.setClientUri(entity.getClientUri());
		legacy.setLogoUri(entity.getLogoUri());
		legacy.setPolicyUri(entity.getPolicyUri());
		legacy.setRedirectUri(entity.getRedirectUri());
		legacy.setSoftwareId(entity.getSoftwareId());
		legacy.setSoftwareVersion(entity.getSoftwareVersion());
		legacy.setContacts(entity.getContacts());
		legacy.setTokenEndpointAuthMethod(entity.getTokenEndpointAuthMethod());
		legacy.setResponseTypes(entity.getResponseTypes());
		legacy.setRegistrationAccessToken(entity.getRegistrationAccessToken());
		legacy.setRegistrationClientUri(entity.getRegistrationClientUri());
		legacy.setGrantTypes(entity.getGrantTypes());
		legacy.setScope(entity.getScope());
		legacy.setClientIdIssuedAt(entity.getClientIdIssuedAt());
		legacy.setClientSecretExpiresAt(entity.getClientSecretExpiresAt());
		legacy.setDataCustodianApplicationStatus(entity.getDataCustodianApplicationStatus());
		legacy.setThirdPartyApplicationStatus(entity.getThirdPartyApplicationStatus());
		legacy.setThirdPartyApplicationType(entity.getThirdPartyApplicationType());
		legacy.setThirdPartyApplicationUse(entity.getThirdPartyApplicationUse());
		legacy.setThirdPartyPhone(entity.getThirdPartyPhone());
		legacy.setAuthorizationServerUri(entity.getAuthorizationServerUri());
		
		return legacy;
	}

}
