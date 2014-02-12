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

import org.energyos.espi.common.repositories.IntervalBlockRepository;
import org.energyos.espi.common.service.IntervalBlockService;
import org.junit.Test;

public class IntervalBlockServiceImplTests {

    @Test
    public void findAllByMeterReading_returnsIntervalBlocks() {
        IntervalBlockRepository repository = mock(IntervalBlockRepository.class);
        IntervalBlockService service = new IntervalBlockServiceImpl();
        service.setRepository(repository);

        service.findAllByMeterReadingId(1L);

        verify(repository).findAllByMeterReadingId(1L);
    }
}
