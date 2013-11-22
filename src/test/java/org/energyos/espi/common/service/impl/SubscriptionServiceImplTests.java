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


import org.energyos.espi.common.BaseTest;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.repositories.jpa.SubscriptionRepositoryImpl;
import org.energyos.espi.common.service.ApplicationInformationService;
import org.energyos.espi.common.utils.DateConverter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.util.ArrayList;
import java.util.List;

import static org.energyos.espi.common.test.EspiFactory.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SubscriptionServiceImplTests extends BaseTest {

    @Mock
    private SubscriptionRepositoryImpl repository;

    @Mock
    private OAuth2Authentication authentication;

    @Mock
    private ApplicationInformationService applicationInformationService;

    public SubscriptionServiceImpl service;

    public RetailCustomer retailCustomer;
    public Subscription subscription;
    public OAuth2Request oAuth2Request;


    @Before
    public void before() {
        service = new SubscriptionServiceImpl();
        retailCustomer = newRetailCustomer();
        oAuth2Request = newOAuth2Request("third_party_client");
        service.setRepository(repository);
        service.setApplicationInformationService(applicationInformationService);

        when(authentication.getPrincipal()).thenReturn(retailCustomer);
        when(authentication.getOAuth2Request()).thenReturn(oAuth2Request);
        when(applicationInformationService.findByClientId(oAuth2Request.getClientId())).thenReturn(newApplicationInformation());

        subscription = service.createSubscription(authentication);
    }

    @Test
    public void createSubscription_persistsSubscription() {
        verify(repository).persist(eq(subscription));
    }

    @Test
    public void createSubscription_setsRetailCustomer() {
        assertEquals(retailCustomer, subscription.getRetailCustomer());
    }

    @Test
    public void createSubscription_setsUUID() {
        assertNotNull(subscription.getUUID());
    }

    @Test
    public void createSubscription_setsLastUpdateToEpoch() {
        assertThat(subscription.getLastUpdate(), is(DateConverter.epoch()));
    }

    @Test
    public void findAll() {
        List<Subscription> subscriptions = new ArrayList<>();

        when(repository.findAll()).thenReturn(subscriptions);

        assertEquals(subscriptions, service.findAll());
    }

    @Test
    public void findByHashedId() {
        when(repository.findByHashedId("foo")).thenReturn(subscription);

        assertEquals(subscription, service.findByHashedId("foo"));
    }

}
