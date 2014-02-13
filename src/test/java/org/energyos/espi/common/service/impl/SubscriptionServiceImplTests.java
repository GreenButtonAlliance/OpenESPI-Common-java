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
import org.energyos.espi.common.service.UsagePointService;
import org.mockito.Mock;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

public class SubscriptionServiceImplTests extends BaseTest {

    @Mock
    private SubscriptionRepositoryImpl repository;

    @Mock
    private OAuth2Authentication authentication;

    @Mock
    private ApplicationInformationService applicationInformationService;

    @Mock
    private UsagePointService usagePointService;

    public SubscriptionServiceImpl service;

    public RetailCustomer retailCustomer;
    public Subscription subscription;
    public OAuth2Request oAuth2Request;


// TODO - dropped this out as the ApplicationInformation integration is not complete
// with "third_party_client" not be related to an existing ApInfo object
    /* start of comment

    @Before
    public void before() {
        service = new SubscriptionServiceImpl();
        applicationInformationService = new ApplicationInformationServiceImpl();
        repository = mock(SubscriptionRepositoryImpl.class);
        retailCustomer = newRetailCustomer();
        oAuth2Request = newOAuth2Request("third_party_client");
        service.setRepository(repository);
       

        when(authentication.getPrincipal()).thenReturn(retailCustomer);
        when(authentication.getOAuth2Request()).thenReturn(oAuth2Request);
        when(applicationInformationService.findByClientId(oAuth2Request.getClientId())).thenReturn(newApplicationInformation());

        subscription = service.createSubscription(authentication);
        subscription.setHashedId(UUID.randomUUID().toString());
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

    @Test
    public void findEntriesByHashedId() throws Exception {
        List<Long> ids = Lists.newArrayList(1L);

        when(repository.findByHashedId(subscription.getHashedId())).thenReturn(subscription);
        when(usagePointService.findAllIdsForRetailCustomer(subscription.getRetailCustomer().getId())).thenReturn(ids);

        EntryTypeIterator entries = service.findEntriesByHashedId(subscription.getHashedId());

        assertThat(entries, is(not(nullValue())));
        assertThat(entries.hasNext(), is(true));
    }
End of Comment    */
}
