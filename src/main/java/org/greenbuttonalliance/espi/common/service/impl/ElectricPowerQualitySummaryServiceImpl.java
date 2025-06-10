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

import org.greenbuttonalliance.espi.common.domain.ElectricPowerQualitySummary;
// ElectricPowerUsageSummary removed - deprecated resource
import org.greenbuttonalliance.espi.common.domain.UsagePoint;
import org.greenbuttonalliance.espi.common.models.atom.EntryType;
import org.greenbuttonalliance.espi.common.repositories.ElectricPowerQualitySummaryRepository;
import org.greenbuttonalliance.espi.common.service.ElectricPowerQualitySummaryService;
import org.greenbuttonalliance.espi.common.service.ImportService;
import org.greenbuttonalliance.espi.common.service.ResourceService;
import org.greenbuttonalliance.espi.common.utils.EntryTypeIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class ElectricPowerQualitySummaryServiceImpl implements
		ElectricPowerQualitySummaryService {

	@Autowired
	private ElectricPowerQualitySummaryRepository electricPowerQualitySummaryRepository;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private ImportService importService;

	@Override
	public ElectricPowerQualitySummary findByUUID(UUID uuid) {
		return electricPowerQualitySummaryRepository.findByUuid(uuid).orElse(null);
	}

	public ElectricPowerQualitySummary findById(
			Long electricPowerQualitySummaryId) {
		return electricPowerQualitySummaryRepository
				.findById(electricPowerQualitySummaryId).orElse(null);
	}

	@Override
	public void persist(ElectricPowerQualitySummary electricPowerQualitySummary) {
		electricPowerQualitySummaryRepository
				.save(electricPowerQualitySummary);
	}

	@Override
	public List<ElectricPowerQualitySummary> findAllByUsagePoint(
			UsagePoint usagePoint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String feedFor(
			List<ElectricPowerQualitySummary> electricPowerQualitySummaries) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String entryFor(
			ElectricPowerQualitySummary electricPowerQualitySummary) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void associateByUUID(UsagePoint usagePoint, UUID uuid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(ElectricPowerQualitySummary electricPowerQualitySummary) {
		electricPowerQualitySummaryRepository
				.deleteById(electricPowerQualitySummary.getId());
	}

	@Override
	public EntryType findEntryType(Long retailCustomerId, Long usagePointId,
			Long electricPowerQualitySummaryId) {
		EntryType result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understand
			// creation of an EntryType
			List<Long> temp;
			// ElectricPowerUsageSummary removed - deprecated resource  
			// temp = resourceService.findAllIdsByXPath(retailCustomerId,
			//		usagePointId, ElectricPowerUsageSummary.class);
			temp = new java.util.ArrayList<>();
			// temp.add(electricPowerQualitySummaryId);
			if (temp.contains(electricPowerQualitySummaryId)) {
				temp.clear();
				temp.add(electricPowerQualitySummaryId);
			} else {
				temp.clear();
			}
			result = (new EntryTypeIterator(resourceService, temp,
					ElectricPowerQualitySummary.class))
					.nextEntry(ElectricPowerQualitySummary.class);
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if not found
		}
		return result;
	}

	@Override
	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId,
			Long usagePointId) {
		EntryTypeIterator result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understand
			// creation of an EntryType
			List<Long> temp;
			// temp =
			// resourceService.findAllIds(ElectricPowerQualitySummary.class);
			temp = resourceService.findAllIdsByXPath(retailCustomerId,
					usagePointId, ElectricPowerQualitySummary.class);

			result = (new EntryTypeIterator(resourceService, temp,
					ElectricPowerQualitySummary.class));
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if not found
		}
		return result;
	}

	@Override
	public void add(ElectricPowerQualitySummary electricPowerQualitySummary) {
		// TODO Auto-generated method stub

	}

	@Override
	public ElectricPowerQualitySummary importResource(InputStream stream) {
		try {
			importService.importData(stream, null);
			EntryType entry = importService.getEntries().get(0);
			return entry
					.getContent().getElectricPowerQualitySummary();
		} catch (Exception e) {
			return null;
		}
	}

//	public void setElectricPowerQualitySummaryRepository(
//			ElectricPowerQualitySummaryRepository electricPowerQualitySummaryRepository) {
//		this.electricPowerQualitySummaryRepository = electricPowerQualitySummaryRepository;
//	}

	public ElectricPowerQualitySummaryRepository getElectricPowerQualitySummaryRepository() {
		return this.electricPowerQualitySummaryRepository;
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
