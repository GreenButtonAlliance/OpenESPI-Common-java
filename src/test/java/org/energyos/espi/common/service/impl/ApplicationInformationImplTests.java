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

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.repositories.ApplicationInformationRepository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ApplicationInformationImplTests {

    @Test
    public void persist() {
        ApplicationInformationServiceImpl service = new ApplicationInformationServiceImpl();
        ApplicationInformationRepository repository = mock(ApplicationInformationRepository.class);
        ApplicationInformation applicationInformation = new ApplicationInformation();
        service.setRepository(repository);

        service.persist(applicationInformation);

        verify(repository).persist(applicationInformation);
    }

    @Test
    public void findById() {
        ApplicationInformationServiceImpl service = new ApplicationInformationServiceImpl();
        ApplicationInformationRepository repository = mock(ApplicationInformationRepository.class);
        ApplicationInformation applicationInformation = new ApplicationInformation();
        service.setRepository(repository);
        when(repository.findById(1L)).thenReturn(applicationInformation);

        assertEquals(applicationInformation, service.findById(1L));
    }

    @Test
    public void findByClientId() {
        ApplicationInformationServiceImpl service = new ApplicationInformationServiceImpl();
        ApplicationInformationRepository repository = mock(ApplicationInformationRepository.class);
        ApplicationInformation applicationInformation = new ApplicationInformation();
        service.setRepository(repository);
        when(repository.findByClientId("thirdParty")).thenReturn(applicationInformation);

        assertEquals(applicationInformation, service.findByClientId("thirdParty"));
    }

    @Test
    public void findAll() {
        ApplicationInformationServiceImpl service = new ApplicationInformationServiceImpl();
        ApplicationInformationRepository repository = mock(ApplicationInformationRepository.class);
        List<ApplicationInformation> applicationInformationList = new ArrayList<>();

        service.setRepository(repository);
        when(repository.findAll()).thenReturn(applicationInformationList);

        assertEquals(applicationInformationList, service.findAll());
    }
}
