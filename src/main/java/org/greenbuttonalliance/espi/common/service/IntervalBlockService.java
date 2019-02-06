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

package org.greenbuttonalliance.espi.common.service;

import org.greenbuttonalliance.espi.common.domain.IntervalBlock;
import org.greenbuttonalliance.espi.common.domain.MeterReading;
import org.greenbuttonalliance.espi.common.models.atom.EntryType;
import org.greenbuttonalliance.espi.common.repositories.IntervalBlockRepository;
import org.greenbuttonalliance.espi.common.utils.EntryTypeIterator;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public interface IntervalBlockService {

	List<IntervalBlock> findAllByMeterReadingId(Long meterReadingId);

	String feedFor(List<IntervalBlock> intervalBlocks);

	IntervalBlock findByURI(String uri);

	String entryFor(IntervalBlock intervalBlock);

	void associateByUUID(MeterReading meterReading, UUID uuid);

	List<IntervalBlock> findAllByMeterReading(MeterReading meterReading);

	public void setRepository(IntervalBlockRepository repository);

	public void persist(IntervalBlock intervalBlock);

	public IntervalBlock findById(long retailCustomerId, long usagePointId,
			long meterReadingId, long intervalBlockId);

	public EntryType findEntryType(Long retailCustomerId, Long usagePointId,
			Long meterReadingId, Long intervalBlockId);

	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId,
			Long usagePointId, Long meterReadingId);

	public void delete(IntervalBlock intervalBlock);

	public void add(IntervalBlock intervalBlock);

	public IntervalBlock importResource(InputStream stream);

	IntervalBlock findById(long intervalBlockId);

}
