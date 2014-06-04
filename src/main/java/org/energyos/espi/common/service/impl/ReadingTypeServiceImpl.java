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

import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.ReadingType;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.ReadingTypeRepository;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.ReadingTypeService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReadingTypeServiceImpl implements ReadingTypeService {

    @Autowired
    private ReadingTypeRepository repository;
    
    @Autowired
	private ResourceService resourceService;
    
    @Autowired
    private ImportService importService;

    @Autowired
	private ReadingTypeRepository readingTypeRepository;
    
    @Override
    public ReadingType findByUUID(UUID uuid) {
        return repository.findByUUID(uuid);
    }

    public ReadingType findById(Long readingTypeId) {
        return repository.findById(readingTypeId);
    }

    @Override
    public void persist(ReadingType readingType) {
        repository.persist(readingType);
    }

	@Override
	public String feedFor(ReadingType readingType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String entryFor(ReadingType readingType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void associateByUUID(MeterReading meterReading, UUID uuid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(long readingTypeId) {
		readingTypeRepository.deleteById(readingTypeId);
	}

	@Override
	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId, Long usagePointId) {
		EntryTypeIterator result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understand creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp = resourceService.findAllIds(ReadingType.class);
			result = (new EntryTypeIterator(resourceService, temp, ReadingType.class));
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;	
	}

	@Override
	public EntryType findEntryType(Long retailCustomerId, Long usagePointId, Long meterReadingId, Long readingTypeId) {
		EntryType result = null;
		try {
			List<Long> allIds = new ArrayList<Long>();
			allIds.add(readingTypeId);
			result = (new EntryTypeIterator(resourceService, allIds, ReadingType.class)).nextEntry(ReadingType.class);
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public void add(ReadingType readingType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ReadingType readingType) {
	       readingTypeRepository.deleteById(readingType.getId());
	}

	@Override
	public ReadingType importResource(InputStream stream) {
		try{
		importService.importData(stream, null);
		EntryType entry = importService.getEntries().get(0);
		ReadingType readingType = entry.getContent().getReadingType();
		return readingType;
		} catch (Exception e) {
			return null;
		}
	}

   public void setReadingTypeRepository(ReadingTypeRepository repository) {
        this.repository = repository;
   }

   public ReadingTypeRepository getReadingTypeRepository () {
        return this.repository;
   }
   public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
   }

   public ResourceService getResourceService () {
        return this.resourceService;
   }
   public void setImportService(ImportService importService) {
        this.importService = importService;
   }

   public ImportService getImportService () {
        return this.importService;
   }

}
