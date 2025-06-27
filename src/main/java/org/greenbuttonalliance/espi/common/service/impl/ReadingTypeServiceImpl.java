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

import org.greenbuttonalliance.espi.common.domain.legacy.MeterReading;
import org.greenbuttonalliance.espi.common.domain.legacy.ReadingType;
import org.greenbuttonalliance.espi.common.domain.legacy.atom.EntryType;
import org.greenbuttonalliance.espi.common.repositories.usage.ReadingTypeRepository;
import org.greenbuttonalliance.espi.common.service.ImportService;
import org.greenbuttonalliance.espi.common.service.ReadingTypeService;
import org.greenbuttonalliance.espi.common.service.ResourceService;
import org.greenbuttonalliance.espi.common.utils.EntryTypeIterator;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReadingTypeServiceImpl implements ReadingTypeService {

	private final ReadingTypeRepository readingTypeRepository;
	private final ResourceService resourceService;
	private final ImportService importService;

	public ReadingTypeServiceImpl(ReadingTypeRepository readingTypeRepository,
								  ResourceService resourceService,
								  ImportService importService) {
		this.readingTypeRepository = readingTypeRepository;
		this.resourceService = resourceService;
		this.importService = importService;
	}

	@Override
	public void setReadingTypeRepository(ReadingTypeRepository readingTypeRepository) {
		// No-op: constructor injection used, but interface requires this method  
	}

	@Override
	public ReadingType findByUUID(UUID uuid) {
		return readingTypeRepository.findByUuid(uuid).orElse(null);
	}

	public ReadingType findById(Long readingTypeId) {
		return readingTypeRepository.findById(readingTypeId).orElse(null);
	}

	@Override
	public void persist(ReadingType readingType) {
		readingTypeRepository.save(readingType);
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
	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId,
			Long usagePointId) {
		EntryTypeIterator result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understand
			// creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp = resourceService.findAllIds(ReadingType.class);
			result = (new EntryTypeIterator(resourceService, temp,
					ReadingType.class));
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public EntryType findEntryType(Long retailCustomerId, Long usagePointId,
			Long meterReadingId, Long readingTypeId) {
		EntryType result = null;
		try {
			List<Long> allIds = new ArrayList<Long>();
			allIds.add(readingTypeId);
			result = (new EntryTypeIterator(resourceService, allIds,
					ReadingType.class)).nextEntry(ReadingType.class);
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
		try {
			importService.importData(stream, null);
			// TODO: Implement modern import logic using DTOs
			// Legacy getContent().getReadingType() no longer supported
			ReadingType readingType = null; // Placeholder
			return readingType;
		} catch (Exception e) {
			return null;
		}
	}


}
