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

package org.energyos.espi.common.domain;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.JAXBException;

import org.energyos.espi.common.utils.EspiMarshaller;
import org.junit.Before;
import org.junit.Test;

import com.sun.syndication.io.FeedException;

public class ElectricPowerQualitySummaryMarshallingTests {

    static final String XML_INPUT =
        "<ElectricPowerQualitySummary xmlns=\"http://naesb.org/espi\">" +
            "<flickerPlt>1</flickerPlt>" +
            "<flickerPst>2</flickerPst>" +
            "<harmonicVoltage>3</harmonicVoltage>" +
            "<longInterruptions>4</longInterruptions>" +
            "<mainsVoltage>5</mainsVoltage>" +
            "<measurementProtocol>6</measurementProtocol>" +
            "<powerFrequency>7</powerFrequency>" +
            "<rapidVoltageChanges>8</rapidVoltageChanges>" +
            "<shortInterruptions>9</shortInterruptions>" +
            "<summaryInterval>" +
                "<duration>2119600</duration>" +
                "<start>2330578000</start>" +
            "</summaryInterval>" +
            "<supplyVoltageDips>10</supplyVoltageDips>" +
            "<supplyVoltageImbalance>11</supplyVoltageImbalance>" +
            "<supplyVoltageVariations>12</supplyVoltageVariations>" +
            "<tempOvervoltage>13</tempOvervoltage>" +
        "</ElectricPowerQualitySummary>";

    private ElectricPowerQualitySummary electricPowerQualitySummary;

    @Before
    public void before() throws JAXBException, FeedException {
        electricPowerQualitySummary = EspiMarshaller.<ElectricPowerQualitySummary>unmarshal(XML_INPUT).getValue();
    }

    @Test
    public void unmarshalsElectricPowerQualitySummary() {
        assertEquals(ElectricPowerQualitySummary.class, electricPowerQualitySummary.getClass());
    }

    @Test
    public void unmarshal_setsFlickerPlt() {
        assertEquals(1L, electricPowerQualitySummary.getFlickerPlt().longValue());
    }

    @Test
    public void unmarshal_setsFlickerPst() {
        assertEquals(2L, electricPowerQualitySummary.getFlickerPst().longValue());
    }

    @Test
    public void unmarshal_setsHarmonicVoltage() {
        assertEquals(3L, electricPowerQualitySummary.getHarmonicVoltage().longValue());
    }

    @Test
    public void unmarshal_setsLongInterruptions() {
       assertEquals(4L, electricPowerQualitySummary.getLongInterruptions().longValue());
    }

    @Test
    public void unmarshal_setsMainsVoltage() {
        assertEquals(5L, electricPowerQualitySummary.getMainsVoltage().longValue());
    }

    @Test
    public void unmarshal_setsMeasurementProtocol() {
        assertEquals(6, electricPowerQualitySummary.getMeasurementProtocol().shortValue());
    }

    @Test
    public void unmarshal_setsPowerFrequency() {
        assertEquals(7L, electricPowerQualitySummary.getPowerFrequency().longValue());
    }

    @Test
    public void unmarshal_setsRapidVoltageChanges() {
        assertEquals(8L, electricPowerQualitySummary.getRapidVoltageChanges().longValue());
    }

    @Test
    public void unmarshal_setsShortInterruptions() {
        assertEquals(9L, electricPowerQualitySummary.getShortInterruptions().longValue());
    }

    @Test
    public void unmarshal_setsSupplyVoltageDips() {
        assertEquals(10L, electricPowerQualitySummary.getSupplyVoltageDips().longValue());
    }

    @Test
    public void unmarshal_setsSupplyVoltageImbalance() {
        assertEquals(11L, electricPowerQualitySummary.getSupplyVoltageImbalance().longValue());
    }

    @Test
    public void unmarshal_setsSupplyVoltageVariations() {
        assertEquals(12L, electricPowerQualitySummary.getSupplyVoltageVariations().longValue());
    }

    @Test
    public void unmarshal_setsTempOvervoltage() {
        assertEquals(13L, electricPowerQualitySummary.getTempOvervoltage().longValue());
    }

    @Test
    public void unmarshal_setsSummaryInterval() {
        assertEquals(2119600L, electricPowerQualitySummary.getSummaryInterval().getDuration().longValue());
        assertEquals(2330578000L, electricPowerQualitySummary.getSummaryInterval().getStart().longValue());
    }
}