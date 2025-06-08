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

package org.greenbuttonalliance.espi.common.domain;

import org.greenbuttonalliance.espi.common.utils.EspiMarshaller;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jakarta.xml.bind.JAXBException;

public class ElectricPowerQualitySummaryUnmarshallerTests {

	static final String XML_INPUT = "<ElectricPowerQualitySummary xmlns=\"http://naesb.org/espi\">"
			+ "<flickerPlt>1</flickerPlt>"
			+ "<flickerPst>2</flickerPst>"
			+ "<harmonicVoltage>3</harmonicVoltage>"
			+ "<longInterruptions>4</longInterruptions>"
			+ "<mainsVoltage>5</mainsVoltage>"
			+ "<measurementProtocol>6</measurementProtocol>"
			+ "<powerFrequency>7</powerFrequency>"
			+ "<rapidVoltageChanges>8</rapidVoltageChanges>"
			+ "<shortInterruptions>9</shortInterruptions>"
			+ "<summaryInterval>"
			+ "<duration>2119600</duration>"
			+ "<start>2330578000</start>"
			+ "</summaryInterval>"
			+ "<supplyVoltageDips>10</supplyVoltageDips>"
			+ "<supplyVoltageImbalance>11</supplyVoltageImbalance>"
			+ "<supplyVoltageVariations>12</supplyVoltageVariations>"
			+ "<tempOvervoltage>13</tempOvervoltage>"
			+ "</ElectricPowerQualitySummary>";

	private ElectricPowerQualitySummary electricPowerQualitySummary;

	@Before
	public void before() throws JAXBException {
		electricPowerQualitySummary = EspiMarshaller
				.<ElectricPowerQualitySummary> unmarshal(XML_INPUT).getValue();
	}

	@Test
	public void unmarshalsElectricPowerQualitySummary() {
		Assert.assertEquals(ElectricPowerQualitySummary.class,
				electricPowerQualitySummary.getClass());
	}

	@Test
	public void unmarshal_setsFlickerPlt() {
		Assert.assertThat(electricPowerQualitySummary.getFlickerPlt(),
				Matchers.is(1L));
	}

	@Test
	public void unmarshal_setsFlickerPst() {
		Assert.assertThat(electricPowerQualitySummary.getFlickerPst(),
				Matchers.is(2L));
	}

	@Test
	public void unmarshal_setsHarmonicVoltage() {
		Assert.assertThat(electricPowerQualitySummary.getHarmonicVoltage(),
				Matchers.is(3L));
	}

	@Test
	public void unmarshal_setsLongInterruptions() {
		Assert.assertThat(electricPowerQualitySummary.getLongInterruptions(),
				Matchers.is(4L));
	}

	@Test
	public void unmarshal_setsMainsVoltage() {
		Assert.assertThat(electricPowerQualitySummary.getMainsVoltage(),
				Matchers.is(5L));
	}

	@Test
	public void unmarshal_setsMeasurementProtocol() {

		Assert.assertThat(electricPowerQualitySummary.getMeasurementProtocol().toString(),
				Matchers.is("6"));
	}

	@Test
	public void unmarshal_setsPowerFrequency() {
		Assert.assertThat(electricPowerQualitySummary.getPowerFrequency(),
				Matchers.is(7L));
	}

	@Test
	public void unmarshal_setsRapidVoltageChanges() {
		Assert.assertThat(electricPowerQualitySummary.getRapidVoltageChanges(),
				Matchers.is(8L));
	}

	@Test
	public void unmarshal_setsShortInterruptions() {
		Assert.assertThat(electricPowerQualitySummary.getShortInterruptions(),
				Matchers.is(9L));
	}

	@Test
	public void unmarshal_setsSupplyVoltageDips() {
		Assert.assertThat(electricPowerQualitySummary.getSupplyVoltageDips(),
				Matchers.is(10L));
	}

	@Test
	public void unmarshal_setsSupplyVoltageImbalance() {
		Assert.assertThat(electricPowerQualitySummary.getSupplyVoltageImbalance(),
				Matchers.is(11L));
	}

	@Test
	public void unmarshal_setsSupplyVoltageVariations() {
		Assert.assertThat(electricPowerQualitySummary.getSupplyVoltageVariations(),
				Matchers.is(12L));
	}

	@Test
	public void unmarshal_setsTempOvervoltage() {
		Assert.assertThat(electricPowerQualitySummary.getTempOvervoltage(),
				Matchers.is(13L));
	}

	@Test
	public void unmarshal_setsSummaryInterval() {
		Assert.assertThat(electricPowerQualitySummary.getSummaryInterval().getDuration(),
				Matchers.is(2119600L));
		Assert.assertThat(electricPowerQualitySummary.getSummaryInterval().getStart(),
				Matchers.is(2330578000L));
	}
}