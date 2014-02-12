/*
 * Copyright 2013 EnergyOS.org
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


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.repositories.MeterReadingRepository;
import org.energyos.espi.common.service.MeterReadingService;
import org.junit.Before;
import org.junit.Test;

public class MeterReadingServiceImplTests {

    private MeterReadingService service;
    private MeterReadingRepository repository;

    @Before
    public void setup() {
        repository = mock(MeterReadingRepository.class);
        service = new MeterReadingServiceImpl();
        service.setRepository(repository);
    }

    @Test
    public void persist_persistsMeterReading() {
        MeterReading meterReading = new MeterReading();

        service.persist(meterReading);

        verify(repository).persist(meterReading);
    }

    @Test
    public void findById_returnsMeterReading() {
        service.findById(1L, 1L, 1L);
        verify(repository).findById(1L);
    }
}
