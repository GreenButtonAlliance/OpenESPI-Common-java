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

package org.greenbuttonalliance.espi.common.service;

import org.greenbuttonalliance.espi.common.domain.legacy.MeterReading;
import org.greenbuttonalliance.espi.common.domain.legacy.ReadingType;
import org.greenbuttonalliance.espi.common.domain.legacy.atom.EntryType;
import org.greenbuttonalliance.espi.common.repositories.usage.ReadingTypeRepository;
import org.greenbuttonalliance.espi.common.utils.EntryTypeIterator;

import java.io.InputStream;
import java.util.UUID;

public interface ReadingTypeService {

	ReadingType findByUUID(UUID uuid);

	String feedFor(ReadingType readingType);

	String entryFor(ReadingType readingType);

	void associateByUUID(MeterReading meterReading, UUID uuid);

	void deleteById(long readingTypeId);

	void setReadingTypeRepository(ReadingTypeRepository repository);

	void persist(ReadingType readingType);

	ReadingType findById(Long readingTypeId);

	EntryType findEntryType(Long retailCustomerId, Long usagePointId, Long meterReadingId,
							Long readingTypeId);

	EntryTypeIterator findEntryTypeIterator(Long retailCustomerId, Long usagePointId);

	void add(ReadingType readingType);

	void delete(ReadingType readingType);

	ReadingType importResource(InputStream stream);

}
