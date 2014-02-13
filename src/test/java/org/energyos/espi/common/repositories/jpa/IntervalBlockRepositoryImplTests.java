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

package org.energyos.espi.common.repositories.jpa;


import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.repositories.IntervalBlockRepository;
import org.energyos.espi.common.repositories.MeterReadingRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/test-context.xml")
@Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

public class IntervalBlockRepositoryImplTests {

    @Autowired
    protected IntervalBlockRepository repository;
    @Autowired
    protected MeterReadingRepository meterReadingRepository;

    @Test
    public void findByMeterReadingId_returnsIntervalBlocks() {
        MeterReading meterReading = new MeterReading();
        meterReading.setUUID(UUID.randomUUID());

        IntervalBlock intervalBlock = new IntervalBlock();
        intervalBlock.setUUID(UUID.randomUUID());

        IntervalBlock intervalBlock1 = new IntervalBlock();
        intervalBlock1.setUUID(UUID.randomUUID());

        meterReading.addIntervalBlock(intervalBlock);
        meterReading.addIntervalBlock(intervalBlock1);

        meterReadingRepository.persist(meterReading);

        assertEquals(2, repository.findAllByMeterReadingId(meterReading.getId()).size());
    }
}
