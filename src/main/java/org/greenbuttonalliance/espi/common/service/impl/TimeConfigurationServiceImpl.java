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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.greenbuttonalliance.espi.common.domain.legacy.RetailCustomer;
import org.greenbuttonalliance.espi.common.domain.legacy.TimeConfiguration;
import org.greenbuttonalliance.espi.common.domain.legacy.UsagePoint;
import org.greenbuttonalliance.espi.common.domain.legacy.atom.EntryType;
import org.greenbuttonalliance.espi.common.repositories.usage.TimeConfigurationRepository;
import org.greenbuttonalliance.espi.common.service.ImportService;
import org.greenbuttonalliance.espi.common.service.ResourceService;
import org.greenbuttonalliance.espi.common.service.TimeConfigurationService;
import org.greenbuttonalliance.espi.common.utils.EntryTypeIterator;
import org.greenbuttonalliance.espi.common.utils.ExportFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TimeConfigurationServiceImpl implements TimeConfigurationService {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	protected TimeConfigurationRepository timeConfigurationRepository;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private ImportService importService;

	public void setImportService(ImportService importService) {
		this.importService = importService;
	}

	public ImportService getImportService() {
		return this.importService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourceService getResourceService() {
		return this.resourceService;
	}

	public void setRepository(
			TimeConfigurationRepository timeConfigurationRepository) {
		this.timeConfigurationRepository = timeConfigurationRepository;
	}

	public TimeConfigurationRepository getRepository() {
		return timeConfigurationRepository;
	}

	@Override
	public TimeConfiguration findByUUID(UUID uuid) {
		return timeConfigurationRepository.findByUuid(uuid).orElse(null);
	}

	public TimeConfiguration findById(Long timeConfigurationId) {
		return timeConfigurationRepository.findById(timeConfigurationId).orElse(null);
	}

	@Override
	public void persist(TimeConfiguration timeConfiguration) {
		timeConfigurationRepository.save(timeConfiguration);
	}

	@Override
	public List<TimeConfiguration> findAllByRetailCustomer(
			RetailCustomer retailCustomer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String feedFor(List<TimeConfiguration> timeConfiguration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String entryFor(TimeConfiguration timeConfiguration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void associateByUUID(UsagePoint usagePoint, UUID uuid) {
		// TODO Auto-generated method stub

	}

	@Override
	public TimeConfiguration importTimeConfiguration(InputStream stream) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(long timeConfigurationId) {
		timeConfigurationRepository.deleteById(timeConfigurationId);
	}

	@Override
	public UsagePoint getUsagePoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntryType findEntryType(Long retailCustomerId, Long usagePointId,
			Long timeConfigurationId, ExportFilter params) {
		EntryType result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understand
			// creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp.add(timeConfigurationId);
			result = (new EntryTypeIterator(resourceService, temp,
					TimeConfiguration.class))
					.nextEntry(TimeConfiguration.class);
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId,
			Long usagePointId, ExportFilter params) {
		EntryTypeIterator result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understan
			// creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp = resourceService.findAllIds(TimeConfiguration.class);
			result = (new EntryTypeIterator(resourceService, temp,
					TimeConfiguration.class));
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public void add(TimeConfiguration timeConfiguration) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(TimeConfiguration timeConfiguration) {
		timeConfigurationRepository.deleteById(timeConfiguration.getId());
	}

	@Override
	public TimeConfiguration importResource(InputStream stream) {
		TimeConfiguration timeConfiguration = null;
		try {
			importService.importData(stream, null);
			// TODO: Implement modern import logic using DTOs
			// Legacy getContent().getLocalTimeParameters() no longer supported
			timeConfiguration = null; // Placeholder

		} catch (Exception e) {
			if(logger.isErrorEnabled()) {
				logger.error("**** importResource Exception: " + e.toString() + "&n");
				e.printStackTrace();
			}
		}
		return timeConfiguration;
	}

}
