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

import com.sun.syndication.io.FeedException;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.energyos.espi.common.atom.XMLTest;
import org.energyos.espi.common.support.TestUtils;
import org.energyos.espi.common.utils.EspiMarshaller;
import org.energyos.espi.common.utils.XMLMarshaller;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlTransient;
import java.io.IOException;

import static org.energyos.espi.common.test.Asserts.assertXpathValue;
import static org.energyos.espi.common.test.EspiFactory.newElectricPowerQualitySummaryWithUsagePoint;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/test-context.xml")
public class ElectricPowerQualitySummaryTests extends XMLTest {

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
    private String xml;
    @Autowired
    @Qualifier("atomMarshaller")
    private Jaxb2Marshaller marshaller;

    @Before
    public void before() throws JAXBException, FeedException {
        XMLMarshaller xmlMarshaller = new XMLMarshaller();
        xmlMarshaller.setMarshaller(marshaller);

        electricPowerQualitySummary = xmlMarshaller.unmarshal(XML_INPUT, ElectricPowerQualitySummary.class);
        xml = EspiMarshaller.marshal(newElectricPowerQualitySummaryWithUsagePoint());
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

    @Test
    public void usagePoint_hasTransientAnnotation() {
        TestUtils.assertAnnotationPresent(ElectricPowerQualitySummary.class, "usagePoint", XmlTransient.class);
    }

    @Test
    public void marshal_setsFlickerPlt() throws SAXException, IOException, XpathException, FeedException {
        assertXpathValue("1", "/espi:ElectricPowerQualitySummary/espi:flickerPlt", xml);
    }

    @Test
    public void marshal_setsFlickerPst() throws FeedException, SAXException, IOException, XpathException {
        assertXpathValue("2", "/espi:ElectricPowerQualitySummary/espi:flickerPst", xml);
    }

    @Test
    public void marshal_setsHarmonicVoltage() throws SAXException, IOException, XpathException {
        assertXpathValue("3", "/espi:ElectricPowerQualitySummary/espi:harmonicVoltage", xml);
    }

    @Test
    public void marshal_setsLongInterruptions() throws SAXException, IOException, XpathException {
        assertXpathValue("4", "/espi:ElectricPowerQualitySummary/espi:longInterruptions", xml);
    }

    @Test
    public void marshal_setsMainsVoltage() throws SAXException, IOException, XpathException {
        assertXpathValue("5", "/espi:ElectricPowerQualitySummary/espi:mainsVoltage", xml);
    }

    @Test
    public void marshal_setsMeasurementProtocol() throws SAXException, IOException, XpathException {
        assertXpathValue("6", "/espi:ElectricPowerQualitySummary/espi:measurementProtocol", xml);
    }

    @Test
    public void marshal_setsPowerFrequency() throws SAXException, IOException, XpathException {
        assertXpathValue("7", "/espi:ElectricPowerQualitySummary/espi:powerFrequency", xml);
    }

    @Test
    public void marshal_setsRapidVoltageChanges() throws SAXException, IOException, XpathException {
        assertXpathValue("8", "/espi:ElectricPowerQualitySummary/espi:rapidVoltageChanges", xml);
    }

    @Test
    public void marshal_setsShortInterruptions() throws SAXException, IOException, XpathException {
        assertXpathValue("9", "/espi:ElectricPowerQualitySummary/espi:shortInterruptions", xml);
    }

    @Test
    public void marshal_setsSupplyVoltageDips() throws SAXException, IOException, XpathException {
        assertXpathValue("10", "/espi:ElectricPowerQualitySummary/espi:supplyVoltageDips", xml);
    }

    @Test
    public void marshal_setsSupplyVoltageImbalance() throws SAXException, IOException, XpathException {
        assertXpathValue("11", "/espi:ElectricPowerQualitySummary/espi:supplyVoltageImbalance", xml);
    }

    @Test
    public void marshal_setsSupplyVoltageVariations() throws SAXException, IOException, XpathException {
        assertXpathValue("12", "/espi:ElectricPowerQualitySummary/espi:supplyVoltageVariations", xml);
    }

    @Test
    public void marshal_setsTempOvervoltage() throws SAXException, IOException, XpathException {
        assertXpathValue("13", "/espi:ElectricPowerQualitySummary/espi:tempOvervoltage", xml);
    }

    @Test
    public void marshal_setsSummaryInterval() throws SAXException, IOException, XpathException {
        assertXpathValue("2119600", "/espi:ElectricPowerQualitySummary/espi:summaryInterval/espi:duration", xml);
        assertXpathValue("2330578000", "/espi:ElectricPowerQualitySummary/espi:summaryInterval/espi:start", xml);
    }
}