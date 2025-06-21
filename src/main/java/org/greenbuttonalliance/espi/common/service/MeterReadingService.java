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
import org.greenbuttonalliance.espi.common.repositories.usage.MeterReadingRepository;

import java.io.InputStream;

/**
 * MeterReading Service
 * 
 * @author jat1
 *
 */
public interface MeterReadingService {

	void setMeterReadingRepository(
			MeterReadingRepository meterReadingRepository);

	MeterReading findById(Long retailCustomerId, Long usagePointId,
						  Long meterReadingId);

	MeterReading importResource(InputStream stream);

}
