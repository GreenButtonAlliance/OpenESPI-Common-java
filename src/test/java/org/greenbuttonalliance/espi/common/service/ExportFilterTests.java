/*
 *    Copyright (c) 2018-2020 Green Button Alliance, Inc.
 *
 *    Portions copyright (c) 2013-2018 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.greenbuttonalliance.espi.common.service;

import org.greenbuttonalliance.espi.common.models.atom.EntryType;
import org.greenbuttonalliance.espi.common.utils.DateConverter;
import org.greenbuttonalliance.espi.common.utils.ExportFilter;
import org.junit.Before;
import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ExportFilterTests {

	private Map<String, String> params;
	private ExportFilter exportFilter;

	@Before
	public void setUp() {
		params = new HashMap<>();
	}

	@Test
	public void matches() throws Exception {
		exportFilter = new ExportFilter(params);
		EntryType entry = new EntryType();
		assertThat(exportFilter.matches(entry), is(true));
	}

	@Test
	public void matches_givenPublishedMin() throws Exception {
		params.put("published-min", getXMLTime(51));
		exportFilter = new ExportFilter(params);
		assertThat(exportFilter.matches(getEntry(50)), is(false));
		assertThat(exportFilter.matches(getEntry(52)), is(true));
	}

	@Test
	public void matches_givenPublishedMax() throws Exception {
		params.put("published-max", getXMLTime(51));
		exportFilter = new ExportFilter(params);
		assertThat(exportFilter.matches(getEntry(50)), is(true));
		assertThat(exportFilter.matches(getEntry(52)), is(false));
	}

	@Test
	public void matches_givenUpdatedMin() throws Exception {
		params.put("updated-min", getXMLTime(51));
		exportFilter = new ExportFilter(params);
		assertThat(exportFilter.matches(getEntry(50)), is(false));
		assertThat(exportFilter.matches(getEntry(52)), is(true));
	}

	@Test
	public void matches_givenUpdatedMax() throws Exception {
		params.put("updated-max", getXMLTime(51));
		exportFilter = new ExportFilter(params);
		assertThat(exportFilter.matches(getEntry(50)), is(true));
		assertThat(exportFilter.matches(getEntry(52)), is(false));
	}

	@Test
	public void matches_givenMaxResults() throws Exception {
		params.put("max-results", "1");
		exportFilter = new ExportFilter(params);

		assertThat(exportFilter.matches(getEntry(0)), is(true));
		assertThat(exportFilter.matches(getEntry(0)), is(false));
	}

	@Test
	public void matches_givenStartIndex_zeroBased() throws Exception {
		params.put("start-index", "1");
		exportFilter = new ExportFilter(params);

		assertThat(exportFilter.matches(getEntry(0)), is(true));
		assertThat(exportFilter.matches(getEntry(0)), is(true));
		assertThat(exportFilter.matches(getEntry(0)), is(true));
	}

	@Test
	public void matches_givenStartIndex_andMaxResults() throws Exception {
		params.put("start-index", "1");
		params.put("max-results", "1");

		exportFilter = new ExportFilter(params);

		assertThat(exportFilter.matches(getEntry(0)), is(true));
		assertThat(exportFilter.matches(getEntry(0)), is(false));
		assertThat(exportFilter.matches(getEntry(0)), is(false));
	}

	@Test
	public void matches_givenStartIndex_andMaxResults_andPublishedRange()
			throws Exception {
		params.put("start-index", "1");
		params.put("max-results", "2");
		params.put("published-min", getXMLTime(50));
		params.put("published-max", getXMLTime(50));

		exportFilter = new ExportFilter(params);

		assertThat(exportFilter.matches(getEntry(0)), is(false));
		assertThat(exportFilter.matches(getEntry(50)), is(true));
		assertThat(exportFilter.matches(getEntry(50)), is(true));
		assertThat(exportFilter.matches(getEntry(0)), is(false));
		assertThat(exportFilter.matches(getEntry(50)), is(false));
		assertThat(exportFilter.matches(getEntry(50)), is(false));
	}

	private EntryType getEntry(int secondsFromEpoch) {
		EntryType entry = new EntryType();
		entry.setPublished(DateConverter
				.toDateTimeType(getGregorianCalendar(secondsFromEpoch)));
		entry.setUpdated(DateConverter
				.toDateTimeType(getGregorianCalendar(secondsFromEpoch)));
		return entry;
	}

	private GregorianCalendar getGregorianCalendar(int secondsFromEpoch) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeZone(TimeZone.getTimeZone("UTC"));
		cal.setTimeInMillis(secondsFromEpoch * 1000);
		return cal;
	}

	private String getXMLTime(int millis) throws DatatypeConfigurationException {
		DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
		GregorianCalendar cal = getGregorianCalendar(millis);
		XMLGregorianCalendar xmlGregorianCalendar = datatypeFactory
				.newXMLGregorianCalendar(cal);
		xmlGregorianCalendar.setFractionalSecond(null);
		return xmlGregorianCalendar.toXMLFormat();
	}
}
