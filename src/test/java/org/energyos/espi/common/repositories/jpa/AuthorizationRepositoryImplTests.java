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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.repositories.AuthorizationRepository;
import org.energyos.espi.common.repositories.RetailCustomerRepository;
import org.energyos.espi.common.test.EspiPersistenceFactory;
import org.junit.Assert;
import org.junit.Ignore;
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

public class AuthorizationRepositoryImplTests {

    @Autowired
    AuthorizationRepository repository;

    @Autowired
    RetailCustomerRepository retailCustomerRepository;

    @Autowired
    EspiPersistenceFactory espiPersistenceFactory;

    @Test
    @Ignore
    public void findAllByRetailCustomerId() {
        RetailCustomer retailCustomer = espiPersistenceFactory.createRetailCustomer();
        Authorization authorization = espiPersistenceFactory.createAuthorization(retailCustomer);

        repository.persist(authorization);

        assertTrue(repository.findAllByRetailCustomerId(retailCustomer.getId()).size() == 1);
    }

    @Test
    @Ignore
    public void findByState() {
        Authorization authorization = espiPersistenceFactory.createAuthorization();

        repository.persist(authorization);

        Assert.assertEquals(authorization, repository.findByState(authorization.getState()));
    }

    @Test
    @Ignore
    public void persist() {
        Authorization authorization = espiPersistenceFactory.createAuthorization();

        repository.persist(authorization);

        assertNotNull(authorization.getId());
    }

    @Test
    @Ignore
    public void merge() {
        Authorization authorization = espiPersistenceFactory.createAuthorization();

        repository.persist(authorization);

        assertNotNull(authorization.getId());

        Authorization authorization1 = repository.findByState(authorization.getState());

        authorization1.setAccessToken(UUID.randomUUID().toString());

        repository.merge(authorization1);
    }
}
