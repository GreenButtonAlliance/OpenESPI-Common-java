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


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.repositories.ResourceRepository;
import org.junit.Test;

public class ResourceServiceImplTests {

    @Test
    public void persist_persistsUsagePoint() {
        ResourceServiceImpl service = new ResourceServiceImpl();
        ResourceRepository repository = mock(ResourceRepository.class);
        service.setRepository(repository);
        UsagePoint usagePoint = new UsagePoint();

        service.persist(usagePoint);

        verify(repository).persist(usagePoint);
    }

    @Test
    public void findByRelatedHref_givenUsagePoint() {
        ResourceServiceImpl service = new ResourceServiceImpl();
        ResourceRepository repository = mock(ResourceRepository.class);
        service.setRepository(repository);
        UsagePoint usagePoint = mock(UsagePoint.class);

        assertThat(service.findByAllParentsHref("href", usagePoint), is(empty()));
    }

    @Test
    public void findByRelatedHref_givenMeterReading() {
        ResourceServiceImpl service = new ResourceServiceImpl();
        ResourceRepository repository = mock(ResourceRepository.class);
        service.setRepository(repository);
        MeterReading meterReading = mock(MeterReading.class);

        service.findByAllParentsHref("href", meterReading);

        verify(repository).findAllParentsByRelatedHref("href", meterReading);
    }

    @Test
    public void findAllRelated() {
        ResourceServiceImpl service = new ResourceServiceImpl();
        ResourceRepository repository = mock(ResourceRepository.class);
        service.setRepository(repository);
        UsagePoint usagePoint = mock(UsagePoint.class);

        service.findAllRelated(usagePoint);

        verify(repository).findAllRelated(usagePoint);
    }

    @Test
    public void findByUUID() {
        ResourceServiceImpl service = new ResourceServiceImpl();
        ResourceRepository repository = mock(ResourceRepository.class);
        service.setRepository(repository);
        UsagePoint usagePoint = mock(UsagePoint.class);

        service.findByUUID(usagePoint.getUUID(), usagePoint.getClass());

        verify(repository).findByUUID(usagePoint.getUUID(), usagePoint.getClass());
    }
}
