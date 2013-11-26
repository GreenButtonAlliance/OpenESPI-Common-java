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


import com.sun.syndication.feed.atom.Feed;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.UsagePointRepository;
import org.energyos.espi.common.test.EspiFactory;
import org.energyos.espi.common.utils.ATOMMarshaller;
import org.energyos.espi.common.utils.SubscriptionBuilder;
import org.energyos.espi.common.utils.UsagePointBuilder;
import org.energyos.espi.common.utils.XMLMarshaller;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.energyos.espi.common.test.EspiFactory.newSubscription;
import static org.energyos.espi.common.test.EspiFactory.newUsagePoint;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class UsagePointServiceImplTests {

    public UsagePoint usagePoint;
    private UsagePointServiceImpl service;
    private UsagePointRepository repository;
    private XMLMarshaller xmlMarshaller;
    private UsagePointBuilder usagePointBuilder;
    private ATOMMarshaller marshaller;

    @Before
    public void setup() {
        repository = mock(UsagePointRepository.class);
        marshaller = mock(ATOMMarshaller.class);
        xmlMarshaller = mock(XMLMarshaller.class);
        usagePointBuilder = mock(UsagePointBuilder.class);

        service = new UsagePointServiceImpl();
        service.setRepository(repository);
        service.setUsagePointBuilder(usagePointBuilder);
        service.setMarshaller(marshaller);
        service.setXMLMarshaller(xmlMarshaller);

        usagePoint = EspiFactory.newUsagePoint();
        usagePoint.setId(989879L);
    }

    @Test
    public void findAllByRetailCustomer() {
        RetailCustomer customer = new RetailCustomer();

        service.findAllByRetailCustomer(customer);

        verify(repository, times(1)).findAllByRetailCustomerId(customer.getId());
    }

    @Test
    public void findById() {
        service.findById(usagePoint.getId());

        verify(repository).findById(usagePoint.getId());
    }

    @Test
    public void findByHashedId() {
        service.findByHashedId(usagePoint.getHashedId());
        verify(repository).findByUUID(usagePoint.getUUID());
    }

    @Test
    public void persist() {
        UsagePoint up = new UsagePoint();

        service.persist(up);

        verify(repository).persist(up);
    }

    @Test
    public void importUsagePoint_returnsMarshalledUsagePoint() {
        UsagePoint usagePoint = newUsagePoint();
        InputStream inputStream = mock(InputStream.class);
        EntryType entryType = new EntryType();

        when(xmlMarshaller.unmarshal(inputStream, EntryType.class)).thenReturn(entryType);
        when(usagePointBuilder.newUsagePoint(entryType)).thenReturn(usagePoint);

        UsagePoint returnedUsagePoint = service.importUsagePoint(inputStream);

        verify(repository).createOrReplaceByUUID(usagePoint);
        assertThat(returnedUsagePoint, is(usagePoint));
    }

    @Test
    public void exportUsagePoints() throws Exception {

        RetailCustomer customer = new RetailCustomer();
        customer.setId(1L);

        SubscriptionBuilder subscriptionBuilder = mock(SubscriptionBuilder.class);

        service.setSubscriptionBuilder(subscriptionBuilder);

        Feed atomFeed = mock(Feed.class);

        List<UsagePoint> usagePointList = new ArrayList<>();
        String atomFeedResult = "<?xml version=\"1.0\"?><feed></feed>";


        when(subscriptionBuilder.buildFeed(usagePointList)).thenReturn(atomFeed);
        when(marshaller.marshal(atomFeed)).thenReturn(atomFeedResult);

        assertEquals(atomFeedResult, service.exportUsagePoints(customer));
        verify(subscriptionBuilder).buildFeed(usagePointList);
        verify(marshaller).marshal(atomFeed);
    }

    @Test
    public void exportUsagePointById() throws Exception {
        Long usagePointId = 1L;
        SubscriptionBuilder subscriptionBuilder = mock(SubscriptionBuilder.class);

        service.setSubscriptionBuilder(subscriptionBuilder);

        Feed atomFeed = mock(Feed.class);

        String atomFeedResult = "<?xml version=\"1.0\"?><feed></feed>";

        when(subscriptionBuilder.buildFeed(anyListOf(UsagePoint.class))).thenReturn(atomFeed);
        when(marshaller.marshal(atomFeed)).thenReturn(atomFeedResult);

        assertEquals(atomFeedResult, service.exportUsagePointById(usagePointId));
        verify(subscriptionBuilder).buildFeed(anyListOf(UsagePoint.class));
        verify(marshaller).marshal(atomFeed);
    }

    @Test
    public void createOrReplaceByUUID() {
        UsagePoint usagePoint = new UsagePoint();

        service.createOrReplaceByUUID(usagePoint);

        verify(repository).createOrReplaceByUUID(usagePoint);
    }

    @Test
    public void findAllUpdatedFor() {
        Subscription subscription = newSubscription();
        service.findAllUpdatedFor(subscription);
        verify(repository).findAllUpdatedFor(subscription);
    }

    @Test
    public void deleteByHashedId() {
        when(repository.findByUUID(UUID.fromString(usagePoint.getHashedId()))).thenReturn(usagePoint);

        service.deleteByHashedId(usagePoint.getHashedId());

        verify(repository).deleteById(usagePoint.getId());
    }

    @Test
    public void deleteByHashedId_withUnknownUsagePoint() {
        Long id = usagePoint.getId();
        when(repository.findById(id)).thenReturn(null);

        service.deleteByHashedId(usagePoint.getHashedId());

        verify(repository, never()).deleteById(id);
    }

    @Test
    public void findAllIdsForRetailCustomer() {
        service.findAllIdsForRetailCustomer(1L);

        verify(repository).findAllIdsForRetailCustomer(1L);
    }
}
