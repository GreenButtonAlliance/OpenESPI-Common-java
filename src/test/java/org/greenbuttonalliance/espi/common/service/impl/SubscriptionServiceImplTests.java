/*
 *
 *    Copyright (c) 2018-2021 Green Button Alliance, Inc.
 *
 *    Portions (c) 2013-2018 EnergyOS.org
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package org.greenbuttonalliance.espi.common.service.impl;

import com.google.common.collect.Lists;
import org.greenbuttonalliance.espi.common.domain.RetailCustomer;
import org.greenbuttonalliance.espi.common.domain.Subscription;
import org.greenbuttonalliance.espi.common.repositories.jpa.SubscriptionRepositoryImpl;
import org.greenbuttonalliance.espi.common.service.ApplicationInformationService;
import org.greenbuttonalliance.espi.common.service.UsagePointService;
import org.greenbuttonalliance.espi.common.utils.DateConverter;
import org.greenbuttonalliance.espi.common.utils.EntryTypeIterator;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.greenbuttonalliance.espi.common.support.EspiFactory.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class SubscriptionServiceImplTests {

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

    @Before
	public void before() {
    	MockitoAnnotations.initMocks(this);

    	service = new SubscriptionServiceImpl();
    	applicationInformationService = new ApplicationInformationServiceImpl();
    	repository = mock(SubscriptionRepositoryImpl.class);
    	retailCustomer = newRetailCustomer();
    	oAuth2Request = newOAuth2Request("third_party_client");
    	service.setRepository(repository);

    	when(authentication.getPrincipal()).thenReturn(retailCustomer);
    	when(authentication.getOAuth2Request()).thenReturn(oAuth2Request);
		String clientId = oAuth2Request.getClientId();
    	when(applicationInformationService.findByClientId(clientId)).
				thenReturn(newApplicationInformation());

    	subscription = service.createSubscription(authentication);
    	subscription.setHashedId(UUID.randomUUID().toString());
	}

    @Ignore
	@Test
	public void createSubscription_persistsSubscription() {
    	verify(repository).persist(eq(subscription));
	}

	@Ignore
	@Test
	public void createSubscription_setsRetailCustomer() {
    	assertEquals(retailCustomer, subscription.getRetailCustomer());
	}

	@Ignore
	@Test
	public void createSubscription_setsUUID() {
    assertNotNull(subscription.getUUID());
	}

	@Ignore
	@Test
	public  void createSubscription_setsLastUpdateToEpoch() {
		assertEquals(subscription.getLastUpdate(),
			(DateConverter.epoch()));
    }

//	@Ignore
//	@Test
//	public void findAll() {
//    	List<Subscription> subscriptions = new ArrayList<>();
//
//    	when(repository.findAll()).thenReturn(subscriptions);
//
//    	assertEquals(subscriptions, service.findAll());
//    }

	@Ignore
	@Test public void findByHashedId() {
    	when(repository.findByHashedId("foo")).thenReturn(subscription);

    	assertEquals(subscription, service.findByHashedId("foo"));
    }

	@Ignore
	@Test
	public void findEntriesByHashedId() throws Exception {
    	List<Long> ids = Lists.newArrayList(1L);

    	when(repository.findByHashedId(subscription.getHashedId())).thenReturn(subscription);
    	when(usagePointService.findAllIdsForRetailCustomer(subscription.getRetailCustomer().getId())).
				thenReturn(ids);

    	EntryTypeIterator entries = service.findEntriesByHashedId(subscription.getHashedId());

    	MatcherAssert.assertThat(entries, notNullValue());
    	assertThat(entries.hasNext(), is(true));
    }
}
