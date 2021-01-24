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

package org.greenbuttonalliance.espi.common.service;

import org.greenbuttonalliance.espi.common.domain.Subscription;
import org.greenbuttonalliance.espi.common.models.atom.EntryType;
import org.greenbuttonalliance.espi.common.repositories.UsagePointRepository;
import org.greenbuttonalliance.espi.common.repositories.jpa.UsagePointRepositoryImpl;
import org.greenbuttonalliance.espi.common.service.impl.ExportServiceImpl;
import org.greenbuttonalliance.espi.common.service.impl.ResourceServiceImpl;
import org.greenbuttonalliance.espi.common.service.impl.UsagePointServiceImpl;
import org.greenbuttonalliance.espi.common.support.TestUtils2;
import org.greenbuttonalliance.espi.common.utils.DateConverter;
import org.greenbuttonalliance.espi.common.utils.EntryTypeIterator;
import org.greenbuttonalliance.espi.common.utils.ExportFilter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.Result;
import java.io.ByteArrayOutputStream;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.greenbuttonalliance.espi.common.support.EspiFactory.newRetailCustomer;
import static org.greenbuttonalliance.espi.common.support.EspiFactory.newSubscription;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
//import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class ExportServiceTests {
	@Mock
	private UsagePointService usagePointService;

	@Mock
	private UsagePointRepository usagePointRepository;

	@Mock
	private SubscriptionService subscriptionService;

	@Mock
	private ResourceService resourceService;

	@Mock
	private Jaxb2Marshaller fragmentMarshaller;
	@Mock
	private EntryTypeIterator entries;

	private ExportServiceImpl exportService;
	private Subscription subscription;
	private ByteArrayOutputStream stream;

	private ExportFilter exportFilter = new ExportFilter(
			new HashMap<>());

	// TODO - Refactor SetUp to resolve null repository value in ResourceServiceImpl class
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		TestUtils2.setupXMLUnit();

		exportService = new ExportServiceImpl();
		usagePointService = new UsagePointServiceImpl();
		resourceService = new ResourceServiceImpl();
		usagePointRepository = new UsagePointRepositoryImpl();

		subscription = newSubscription();
		subscription.setRetailCustomer(newRetailCustomer());

		// set up the UsagePoint Service (need full initializer in the absence
		// of @Autowired)
		usagePointService.setRepository(usagePointRepository);

		exportService.setSubscriptionService(subscriptionService);
		exportService.setJaxb2Marshaller(fragmentMarshaller);
		// set up the ExportService
		// exportService.setUsagePointService(usagePointService);

		stream = new ByteArrayOutputStream();

		when(subscriptionService.findByHashedId(subscription.getHashedId()))
				.thenReturn(subscription);
		when(
				subscriptionService.findEntriesByHashedId(subscription
						.getHashedId())).thenReturn(entries);
	}

	//TODO - Resolve issue with null repository value in ResourceServiceImpl findById method for repository
	@Ignore
	@Test
	public void exportSubscription_addsTheXMLProlog() throws Exception {
		exportService.exportSubscription(subscription.getHashedId(), stream,
				exportFilter);

		assertThat(stream.toString(),
				containsString("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"));
	}

	//TODO - Resolve issue with null repository value in ResourceServiceImpl findById method for repository
	@Ignore
	@Test
	public void exportSubscription_addsTheFeed() throws Exception {
		exportService.exportSubscription(subscription.getHashedId(), stream,
				exportFilter);

		assertXpathExists("/:feed", stream.toString());
	}

	//TODO - Resolve issue with null repository value in ResourceServiceImpl findById method for repository
	@Ignore
	@Test
	public void exportSubscription_addsEntries() throws Exception {
		when(entries.hasNext()).thenReturn(true).thenReturn(true)
				.thenReturn(false);

		exportService.exportSubscription(subscription.getHashedId(), stream,
				exportFilter);

		verify(entries, times(2)).next();
	}

	// TODO - need a way to cleanly initialize the more complex services (in the absence of @Autowired)

	//TODO - Resolve issue with null repository value in ResourceServiceImpl findById method for repository
	@Ignore
	@Test
	public void exportUsagePoints() throws Exception {
		Long retailCustomerId = 1L;

		when(subscriptionService.findEntryTypeIterator(retailCustomerId))
				.thenReturn(mock(EntryTypeIterator.class));

		exportService.exportUsagePoints(anyLong(), retailCustomerId,
				new ByteArrayOutputStream(), new ExportFilter(
						new HashMap<>()));

		verify(subscriptionService).findEntryTypeIterator(retailCustomerId);

	}

	//TODO - Resolve issue with null repository value in ResourceServiceImpl findById method for repository
	@Ignore
	@Test
	public void exportSubscription_filtersEntries() throws Exception {
		EntryType goodEntry = getEntry(50);
		EntryType badEntry = getEntry(100);

		when(
				subscriptionService.findEntriesByHashedId(subscription
						.getHashedId())).thenReturn(entries);

		when(entries.hasNext()).thenReturn(true).thenReturn(true)
				.thenReturn(false);

		when(entries.next()).thenReturn(badEntry).thenReturn(goodEntry);

		Map<String, String> params = new HashMap<>();
		params.put("published-min", getXMLTime(50));
		params.put("published-max", getXMLTime(51));

		exportService.exportSubscription(subscription.getHashedId(), stream,
				new ExportFilter(params));

		verify(fragmentMarshaller).marshal(eq(goodEntry), any(Result.class));
		verify(fragmentMarshaller, never()).marshal(eq(badEntry),
				any(Result.class));
	}

	private String getXMLTime(int millis) throws DatatypeConfigurationException {
		DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
		GregorianCalendar cal = getGregorianCalendar(millis);
		XMLGregorianCalendar xmlGregorianCalendar = datatypeFactory
				.newXMLGregorianCalendar(cal);
		xmlGregorianCalendar.setFractionalSecond(null);
		return xmlGregorianCalendar.toXMLFormat();
	}

	private EntryType getEntry(int secondsFromEpoch) {
		EntryType entry = new EntryType();
		entry.setPublished(DateConverter
				.toDateTimeType(getGregorianCalendar(secondsFromEpoch)));
		return entry;
	}

	private GregorianCalendar getGregorianCalendar(int secondsFromEpoch) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeZone(TimeZone.getTimeZone("UTC"));
		cal.setTimeInMillis(secondsFromEpoch * 1000);
		return cal;
	}
}
