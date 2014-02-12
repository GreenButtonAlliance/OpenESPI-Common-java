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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import org.energyos.espi.common.BaseTest;
import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.repositories.AuthorizationRepository;
import org.energyos.espi.common.test.EspiFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

public class AuthorizationServiceImplTests extends BaseTest {

    @Mock
    public AuthorizationRepository repository;
    public Subscription subscription;
    public AuthorizationServiceImpl service;
    public Authorization authorization;

    @Before
    public void before() {
        subscription = EspiFactory.newSubscription(EspiFactory.newRetailCustomer());
        service = new AuthorizationServiceImpl();
        service.setAuthorizationRepository(repository);

        authorization = service.createAuthorization(subscription, "accessToken");
    }

    @Test
    public void createAuthorization_persistsAuthorization() {
        verify(repository).persist(eq(authorization));
    }

    @Test
    @Ignore
    public void createAuthorization_setsResource() {
        assertEquals(Routes.DATA_CUSTODIAN_SUBSCRIPTION.replace("{SubscriptionID}", subscription.getUUID().toString()), authorization.getResourceURI());
    }

    @Test
    @Ignore
    public void createAuthorization_setsAccessToken() {
        assertEquals("accessToken", authorization.getAccessToken());
    }

    @Test
    public void createAuthorization_setsUUID() {
        assertNotNull(authorization.getUUID());
    }
}
