/*
 *     Copyright (c) 2018-2019 Green Button Alliance, Inc.
 *
 *     Portions copyright (c) 2013-2018 EnergyOS.org
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

package org.greenbuttonalliance.espi.common.domain;

import org.greenbuttonalliance.espi.common.utils.EspiMarshaller;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;

import static org.junit.Assert.assertEquals;

public class UsageSummaryMarshallingTests {
	static final String XML_INPUT = "<UsageSummary xmlns=\"http://naesb.org/espi\">"
			+ "<billLastPeriod>1</billLastPeriod>"
			+ "<billToDate>2</billToDate>"
			+ "<costAdditionalLastPeriod>3</costAdditionalLastPeriod>"
			+ "<currency>currency</currency>"
			+ "<qualityOfReading>qualityOfReading</qualityOfReading>"
			+ "<statusTimeStamp>4</statusTimeStamp>"
			+ "<ratchetDemandPeriod>"
			+ "<duration>1119600</duration>"
			+ "<start>1119601</start>"
			+ "</ratchetDemandPeriod>"
			+ "<billingPeriod>"
			+ "<duration>1119602</duration>"
			+ "<start>1119603</start>"
			+ "</billingPeriod>"
			+ "<currentBillingPeriodOverAllConsumption>"
			+ "<powerOfTenMultiplier>PowerOfTenMultiplier</powerOfTenMultiplier>"
			+ "<timeStamp>1331784000</timeStamp>"
			+ "<uom>Uom</uom>"
			+ "<value>93018</value>"
			+ "</currentBillingPeriodOverAllConsumption>"
			+ "<currentDayLastYearNetConsumption>"
			+ "<powerOfTenMultiplier>PowerOfTenMultiplier</powerOfTenMultiplier>"
			+ "<timeStamp>1331784000</timeStamp>"
			+ "<uom>Uom</uom>"
			+ "<value>93018</value>"
			+ "</currentDayLastYearNetConsumption>"
			+ "<currentDayNetConsumption>"
			+ "<powerOfTenMultiplier>PowerOfTenMultiplier</powerOfTenMultiplier>"
			+ "<timeStamp>1331784000</timeStamp>"
			+ "<uom>Uom</uom>"
			+ "<value>93018</value>"
			+ "</currentDayNetConsumption>"
			+ "<currentDayOverallConsumption>"
			+ "<powerOfTenMultiplier>PowerOfTenMultiplier</powerOfTenMultiplier>"
			+ "<timeStamp>1331784000</timeStamp>"
			+ "<uom>Uom</uom>"
			+ "<value>93018</value>"
			+ "</currentDayOverallConsumption>"
			+ "<peakDemand>"
			+ "<powerOfTenMultiplier>PowerOfTenMultiplier</powerOfTenMultiplier>"
			+ "<timeStamp>1331784000</timeStamp>"
			+ "<uom>Uom</uom>"
			+ "<value>93018</value>"
			+ "</peakDemand>"
			+ "<previousDayLastYearOverallConsumption>"
			+ "<powerOfTenMultiplier>PowerOfTenMultiplier</powerOfTenMultiplier>"
			+ "<timeStamp>1331784000</timeStamp>"
			+ "<uom>Uom</uom>"
			+ "<value>93018</value>"
			+ "</previousDayLastYearOverallConsumption>"
			+ "<previousDayNetConsumption>"
			+ "<powerOfTenMultiplier>PowerOfTenMultiplier</powerOfTenMultiplier>"
			+ "<timeStamp>1331784000</timeStamp>"
			+ "<uom>Uom</uom>"
			+ "<value>93018</value>"
			+ "</previousDayNetConsumption>"
			+ "<previousDayOverallConsumption>"
			+ "<powerOfTenMultiplier>PowerOfTenMultiplier</powerOfTenMultiplier>"
			+ "<timeStamp>1331784000</timeStamp>"
			+ "<uom>Uom</uom>"
			+ "<value>93018</value>"
			+ "</previousDayOverallConsumption>"
			+ "<ratchetDemand>"
			+ "<powerOfTenMultiplier>PowerOfTenMultiplier</powerOfTenMultiplier>"
			+ "<timeStamp>1331784000</timeStamp>"
			+ "<uom>Uom</uom>"
			+ "<value>93018</value>"
			+ "</ratchetDemand>"
			+ "</UsageSummary>";

	private UsageSummary usageSummary;

	@Before
	public void before() throws JAXBException {
		usageSummary = EspiMarshaller.<UsageSummary> unmarshal(XML_INPUT).getValue();
	}

	@Test
	public void unmarshalsUsageSummary() {
		assertEquals(UsageSummary.class,
				usageSummary.getClass());
	}

	@Test
	public void unmarshal_setsBillLastPeriod() {
		assertEquals(1L, usageSummary.getBillLastPeriod()
				.longValue());
	}

	@Test
	public void unmarshal_setsBillToDate() {
		assertEquals(2L, usageSummary.getBillToDate().longValue());
	}

	@Test
	public void unmarshal_setsCostAdditionalLastPeriod() {
		assertEquals(3L, usageSummary
				.getCostAdditionalLastPeriod().longValue());
	}

	@Test
	public void unmarshal_setsCurrency() {
		assertEquals("currency", usageSummary.getCurrency());
	}

	@Test
	public void unmarshal_setsQualityOfReading() {
		assertEquals("qualityOfReading",
				usageSummary.getQualityOfReading());
	}

	@Test
	public void unmarshal_setsStatusTimeStamp() {
		assertEquals(4L, usageSummary.getStatusTimeStamp());
	}

	@Test
	public void unmarshal_setsRatchetDemandPeriod() {
		assertEquals(1119600L, usageSummary
				.getRatchetDemandPeriod().getDuration().longValue());
		assertEquals(1119601L, usageSummary
				.getRatchetDemandPeriod().getStart().longValue());
	}

	@Test
	public void unmarshal_setsBillingPeriod() {
		assertEquals(1119602L, usageSummary.getBillingPeriod()
				.getDuration().longValue());
		assertEquals(1119603L, usageSummary.getBillingPeriod()
				.getStart().longValue());
	}

	@Test
	public void unmarshal_setsCurrentBillingPeriodOverAllConsumption() {
		assertSummaryMeasurement(usageSummary
				.getCurrentBillingPeriodOverAllConsumption());
	}

	@Test
	public void unmarshal_setsCurrentDayLastYearNetConsumption() {
		assertSummaryMeasurement(usageSummary
				.getCurrentDayLastYearNetConsumption());
	}

	@Test
	public void unmarshal_setsCurrentDayNetConsumption() {
		assertSummaryMeasurement(usageSummary
				.getCurrentDayNetConsumption());
	}

	@Test
	public void unmarshal_setsCurrentDayOverallConsumption() {
		assertSummaryMeasurement(usageSummary
				.getCurrentDayOverallConsumption());
	}

	@Test
	public void unmarshal_setsPeakDemand() {
		assertSummaryMeasurement(usageSummary.getPeakDemand());
	}

	@Test
	public void unmarshal_setsPreviousDayLastYearOverallConsumption() {
		assertSummaryMeasurement(usageSummary
				.getPreviousDayLastYearOverallConsumption());
	}

	@Test
	public void unmarshal_setsPreviousDayNetConsumption() {
		assertSummaryMeasurement(usageSummary
				.getPreviousDayNetConsumption());
	}

	@Test
	public void unmarshal_setsPreviousDayOverallConsumption() {
		assertSummaryMeasurement(usageSummary
				.getPreviousDayOverallConsumption());
	}

	@Test
	public void unmarshal_setsRatchetDemand() {
		assertSummaryMeasurement(usageSummary.getRatchetDemand());
	}

	private void assertSummaryMeasurement(SummaryMeasurement sm) {
		assertEquals("PowerOfTenMultiplier", sm.getPowerOfTenMultiplier());
		assertEquals(1331784000L, sm.getTimeStamp().longValue());
		assertEquals("Uom", sm.getUom());
		assertEquals(93018L, sm.getValue().longValue());
	}
}
