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

package org.energyos.espi.common.service;

import java.io.InputStream;
import java.util.UUID;

import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.ReadingType;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.ReadingTypeRepository;
import org.energyos.espi.common.utils.EntryTypeIterator;

public interface ReadingTypeService {
	// TODO: likely deprecated

	ReadingType findByUUID(UUID uuid);

	String feedFor(ReadingType readingType);

	String entryFor(ReadingType readingType);

	void associateByUUID(MeterReading meterReading, UUID uuid);

	void deleteById(long readingTypeId);

	// persistence management services
	public void setReadingTypeRepository(ReadingTypeRepository repository);

	public void persist(ReadingType readingType);

	// accessor services
	public ReadingType findById(Long readingTypeId);

	public EntryType findEntryType(Long retailCustomerId, Long usagePointId,Long meterReadingId,
			Long readingTypeId);

	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId, Long usagePointId);

	public void add(ReadingType readingType);

	public void delete(ReadingType readingType);

	// import-exportResource services
	public ReadingType importResource(InputStream stream);

}
