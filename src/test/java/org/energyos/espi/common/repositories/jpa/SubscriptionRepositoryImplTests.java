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

import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.repositories.SubscriptionRepository;
import org.energyos.espi.common.test.EspiPersistenceFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/test-context.xml")
@Transactional
public class SubscriptionRepositoryImplTests {
    @Autowired
    private EspiPersistenceFactory factory;

    @Autowired
    private SubscriptionRepository repository;

    @Test
    public void persist() {
        Subscription subscription = factory.createSubscription();

        assertNotNull(subscription.getId());
    }

    @Test
    public void findAll() throws Exception {
        factory.createSubscription();

        assertEquals(1, repository.findAll().size());
    }

    @Test
    public void findByHashedId() {
        Subscription subscription = factory.createSubscription();

        assertEquals(subscription.getId(), repository.findByHashedId(subscription.getHashedId()).getId());
    }
}
