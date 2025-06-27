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
import org.greenbuttonalliance.espi.common.domain.legacy.atom.EntryType;
import org.greenbuttonalliance.espi.common.repositories.usage.MeterReadingRepository;
import org.greenbuttonalliance.espi.common.service.ImportService;
import org.greenbuttonalliance.espi.common.service.MeterReadingService;
import org.greenbuttonalliance.espi.common.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class MeterReadingServiceImpl implements MeterReadingService {

	@Autowired
	private ImportService importService;

	@Autowired
	protected MeterReadingRepository meterReadingRepository;

	@Autowired
	private ResourceService resourceService;

	@Override
	public MeterReading findById(Long retailCustomerId, Long usagePointId,
			Long meterReadingId) {
		// TODO need to scope to the retailCustomer.usagePoint.meterReading
		// for now, just do it the old way
		return meterReadingRepository.findById(meterReadingId).orElse(null);
	}

	@Override
	public MeterReading importResource(InputStream stream) {
		try {
			importService.importData(stream, null);
			// TODO: Implement modern import logic using DTOs
			// Legacy getContent().getMeterReading() no longer supported
			MeterReading meterReading = null; // Placeholder
			return meterReading;
		} catch (Exception e) {

			return null;
		}
	}

	public void setImportService(ImportService importService) {
		this.importService = importService;
	}

	public ImportService getImportService() {
		return this.importService;
	}

	@Override
	public void setMeterReadingRepository(
			MeterReadingRepository meterReadingRepository) {
		this.meterReadingRepository = meterReadingRepository;
	}

	public MeterReadingRepository getMeterReadingRepository() {
		return this.meterReadingRepository;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourceService getResourceService() {
		return this.resourceService;
	}

}
