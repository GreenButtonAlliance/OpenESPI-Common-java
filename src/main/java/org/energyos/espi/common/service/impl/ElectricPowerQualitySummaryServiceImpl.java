/*
 * Copyright 2013, 2014 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.energyos.espi.common.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.ElectricPowerQualitySummary;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.ElectricPowerQualitySummaryRepository;
import org.energyos.espi.common.service.ElectricPowerQualitySummaryService;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElectricPowerQualitySummaryServiceImpl implements ElectricPowerQualitySummaryService {

    @Autowired
    protected ElectricPowerQualitySummaryRepository electricPowerQualitySummaryRepository;
    
    @Autowired
	private ResourceService resourceService;
    
    @Autowired
    private ImportService importService;
    
    public void setImportService(ImportService importService) {
    	this.importService = importService;
    }
    
    public void setResourceService(ResourceService resourceService) {
    	this.resourceService = resourceService;
    }

    public void setRepository(ElectricPowerQualitySummaryRepository electricPowerQualitySummaryRepository) {
        this.electricPowerQualitySummaryRepository = electricPowerQualitySummaryRepository;
    }


    @Override
    public ElectricPowerQualitySummary findByUUID(UUID uuid) {
        return electricPowerQualitySummaryRepository.findByUUID(uuid);
    }

    public ElectricPowerQualitySummary findById(Long electricPowerQualitySummaryId) {
        return electricPowerQualitySummaryRepository.findById(electricPowerQualitySummaryId);
    }

    @Override
    public void persist(ElectricPowerQualitySummary electricPowerQualitySummary) {
    	electricPowerQualitySummaryRepository.persist(electricPowerQualitySummary);
    }

	@Override
	public List<ElectricPowerQualitySummary> findAllByUsagePoint(
			UsagePoint usagePoint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String feedFor(
			List<ElectricPowerQualitySummary> electricPowerQualitySummarys) {
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
	       electricPowerQualitySummaryRepository.deleteById(electricPowerQualitySummary.getId());
	}

	@Override
	public EntryType findEntryType(Long retailCustomerId, Long usagePointId,
			Long electricPowerQualitySummaryId) {
		EntryType result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understand creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp.add(electricPowerQualitySummaryId);
			result = (new EntryTypeIterator(resourceService, temp, ElectricPowerQualitySummary.class)).nextEntry(ElectricPowerQualitySummary.class);
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId, Long usagePointId) {
		EntryTypeIterator result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understand creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp = resourceService.findAllIds(ElectricPowerQualitySummary.class);
			result = (new EntryTypeIterator(resourceService, temp, ElectricPowerQualitySummary.class));
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public void add(ElectricPowerQualitySummary electricPowerQualitySummary) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ElectricPowerQualitySummary importResource(InputStream stream) {
		try{
		importService.importData(stream, null);
		EntryType entry = importService.getEntries().get(0);
		ElectricPowerQualitySummary electricPowerQualitySummary = entry.getContent().getElectricPowerQualitySummary();
		return electricPowerQualitySummary;
		} catch (Exception e) {
			return null;
		}
	}

}
