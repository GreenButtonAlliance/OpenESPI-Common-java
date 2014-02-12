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

import static org.energyos.espi.common.test.Asserts.assertXpathValue;
import static org.energyos.espi.common.test.EspiFactory.newElectricPowerUsageSummaryWithUsagePoint;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlTransient;

import org.custommonkey.xmlunit.exceptions.XpathException;
import org.energyos.espi.common.atom.XMLTest;
import org.energyos.espi.common.support.TestUtils;
import org.energyos.espi.common.utils.EspiMarshaller;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.sun.syndication.io.FeedException;

public class ElectricPowerUsageSummaryTests extends XMLTest {

    private String xml;
    static final String XML_INPUT =
        "<ElectricPowerUsageSummary xmlns=\"http://naesb.org/espi\">" +
            "<billLastPeriod>1</billLastPeriod>" +
            "<billToDate>2</billToDate>" +
            "<costAdditionalLastPeriod>3</costAdditionalLastPeriod>" +
            "<currency>currency</currency>" +
            "<qualityOfReading>qualityOfReading</qualityOfReading>" +
            "<statusTimeStamp>4</statusTimeStamp>" +
            "<ratchetDemandPeriod>" +
                "<duration>1119600</duration>" +
                "<start>1119601</start>" +
            "</ratchetDemandPeriod>" +
            "<billingPeriod>" +
                "<duration>1119602</duration>" +
                "<start>1119603</start>" +
            "</billingPeriod>" +
            "<currentBillingPeriodOverAllConsumption>" +
                "<powerOfTenMultiplier>PowerOfTenMultiplier</powerOfTenMultiplier>" +
                "<timeStamp>1331784000</timeStamp>" +
                "<uom>Uom</uom>" +
                "<value>93018</value>" +
            "</currentBillingPeriodOverAllConsumption>" +
            "<currentDayLastYearNetConsumption>" +
                "<powerOfTenMultiplier>PowerOfTenMultiplier</powerOfTenMultiplier>" +
                "<timeStamp>1331784000</timeStamp>" +
                "<uom>Uom</uom>" +
                "<value>93018</value>" +
            "</currentDayLastYearNetConsumption>" +
            "<currentDayNetConsumption>" +
                "<powerOfTenMultiplier>PowerOfTenMultiplier</powerOfTenMultiplier>" +
                "<timeStamp>1331784000</timeStamp>" +
                "<uom>Uom</uom>" +
                "<value>93018</value>" +
            "</currentDayNetConsumption>" +
            "<currentDayOverallConsumption>" +
                "<powerOfTenMultiplier>PowerOfTenMultiplier</powerOfTenMultiplier>" +
                "<timeStamp>1331784000</timeStamp>" +
                "<uom>Uom</uom>" +
                "<value>93018</value>" +
            "</currentDayOverallConsumption>" +
            "<peakDemand>" +
                "<powerOfTenMultiplier>PowerOfTenMultiplier</powerOfTenMultiplier>" +
                "<timeStamp>1331784000</timeStamp>" +
                "<uom>Uom</uom>" +
                "<value>93018</value>" +
            "</peakDemand>" +
            "<previousDayLastYearOverallConsumption>" +
                "<powerOfTenMultiplier>PowerOfTenMultiplier</powerOfTenMultiplier>" +
                "<timeStamp>1331784000</timeStamp>" +
                "<uom>Uom</uom>" +
                "<value>93018</value>" +
            "</previousDayLastYearOverallConsumption>" +
            "<previousDayNetConsumption>" +
                "<powerOfTenMultiplier>PowerOfTenMultiplier</powerOfTenMultiplier>" +
                "<timeStamp>1331784000</timeStamp>" +
                "<uom>Uom</uom>" +
                "<value>93018</value>" +
            "</previousDayNetConsumption>" +
            "<previousDayOverallConsumption>" +
                "<powerOfTenMultiplier>PowerOfTenMultiplier</powerOfTenMultiplier>" +
                "<timeStamp>1331784000</timeStamp>" +
                "<uom>Uom</uom>" +
                "<value>93018</value>" +
            "</previousDayOverallConsumption>" +
            "<ratchetDemand>" +
                "<powerOfTenMultiplier>PowerOfTenMultiplier</powerOfTenMultiplier>" +
                "<timeStamp>1331784000</timeStamp>" +
                "<uom>Uom</uom>" +
                "<value>93018</value>" +
            "</ratchetDemand>" +
        "</ElectricPowerUsageSummary>";

    private ElectricPowerUsageSummary electricPowerUsageSummary;

    @Before
    public void before() throws JAXBException, FeedException {
        xml = EspiMarshaller.marshal(newElectricPowerUsageSummaryWithUsagePoint());
        electricPowerUsageSummary = EspiMarshaller.<ElectricPowerUsageSummary>unmarshal(XML_INPUT).getValue();
    }

    @Test
    public void unmarshalsElectricPowerUsageSummary() {
        assertEquals(ElectricPowerUsageSummary.class, electricPowerUsageSummary.getClass());
    }

    @Test
    public void unmarshal_setsBillLastPeriod() {
        assertEquals(1L, electricPowerUsageSummary.getBillLastPeriod().longValue());
    }

    @Test
    public void unmarshal_setsBillToDate() {
        assertEquals(2L, electricPowerUsageSummary.getBillToDate().longValue());
    }

    @Test
    public void unmarshal_setsCostAdditionalLastPeriod() {
        assertEquals(3L, electricPowerUsageSummary.getCostAdditionalLastPeriod().longValue());
    }

    @Test
    public void unmarshal_setsCurrency() {
        assertEquals("currency", electricPowerUsageSummary.getCurrency());
    }

    @Test
    public void unmarshal_setsQualityOfReading() {
        assertEquals("qualityOfReading", electricPowerUsageSummary.getQualityOfReading());
    }

    @Test
    public void unmarshal_setsStatusTimeStamp() {
        assertEquals(4L, electricPowerUsageSummary.getStatusTimeStamp());
    }

    @Test
    public void unmarshal_setsRatchetDemandPeriod() {
        assertEquals(1119600L, electricPowerUsageSummary.getRatchetDemandPeriod().getDuration().longValue());
        assertEquals(1119601L, electricPowerUsageSummary.getRatchetDemandPeriod().getStart().longValue());
    }

    @Test
    public void unmarshal_setsBillingPeriod() {
        assertEquals(1119602L, electricPowerUsageSummary.getBillingPeriod().getDuration().longValue());
        assertEquals(1119603L, electricPowerUsageSummary.getBillingPeriod().getStart().longValue());
    }

    @Test
    public void unmarshal_setsCurrentBillingPeriodOverAllConsumption() {
        assertSummaryMeasurement(electricPowerUsageSummary.getCurrentBillingPeriodOverAllConsumption());
    }

    @Test
    public void unmarshal_setsCurrentDayLastYearNetConsumption() {
        assertSummaryMeasurement(electricPowerUsageSummary.getCurrentDayLastYearNetConsumption());
    }

    @Test
    public void unmarshal_setsCurrentDayNetConsumption() {
        assertSummaryMeasurement(electricPowerUsageSummary.getCurrentDayNetConsumption());
    }

    @Test
    public void unmarshal_setsCurrentDayOverallConsumption() {
        assertSummaryMeasurement(electricPowerUsageSummary.getCurrentDayOverallConsumption());
    }

    @Test
    public void unmarshal_setsPeakDemand() {
        assertSummaryMeasurement(electricPowerUsageSummary.getPeakDemand());
    }

    @Test
    public void unmarshal_setsPreviousDayLastYearOverallConsumption() {
        assertSummaryMeasurement(electricPowerUsageSummary.getPreviousDayLastYearOverallConsumption());
    }

    @Test
    public void unmarshal_setsPreviousDayNetConsumption() {
        assertSummaryMeasurement(electricPowerUsageSummary.getPreviousDayNetConsumption());
    }

    @Test
    public void unmarshal_setsPreviousDayOverallConsumption() {
        assertSummaryMeasurement(electricPowerUsageSummary.getPreviousDayOverallConsumption());
    }

    @Test
    public void unmarshal_setsRatchetDemand() {
        assertSummaryMeasurement(electricPowerUsageSummary.getRatchetDemand());
    }

    @Test
    public void marshal_setsBillingPeriod() throws JAXBException, SAXException, IOException, XpathException {
        assertXpathValue("1119600", "/espi:ElectricPowerUsageSummary/espi:billingPeriod/espi:duration", xml);
        assertXpathValue("1119600", "/espi:ElectricPowerUsageSummary/espi:billingPeriod/espi:start", xml);
    }

    @Test
    public void marshal_setsBillLastPeriod() throws JAXBException, SAXException, IOException, XpathException {
        assertXpathValue("15303000", "/espi:ElectricPowerUsageSummary/espi:billLastPeriod", xml);
    }

    @Test
    public void marshal_setsBillToDate() throws JAXBException, SAXException, IOException, XpathException {
        assertXpathValue("1135000", "/espi:ElectricPowerUsageSummary/espi:billToDate", xml);
    }

    @Test
    public void marshal_setsCostAdditionalLastPeriod() throws JAXBException, SAXException, IOException, XpathException {
        assertXpathValue("1346000", "/espi:ElectricPowerUsageSummary/espi:costAdditionalLastPeriod", xml);
    }

    @Test
    public void marshal_setsCurrency() throws JAXBException, SAXException, IOException, XpathException {
        assertXpathValue("840", "/espi:ElectricPowerUsageSummary/espi:currency", xml);
    }

    @Test
    public void marshal_setsCurrentBillingPeriodOverAllConsumption() throws JAXBException, SAXException, IOException, XpathException {
        assertXpathValue("0", "/espi:ElectricPowerUsageSummary/espi:currentBillingPeriodOverAllConsumption/espi:powerOfTenMultiplier", xml);
        assertXpathValue("1331784000", "/espi:ElectricPowerUsageSummary/espi:currentBillingPeriodOverAllConsumption/espi:timeStamp", xml);
        assertXpathValue("72", "/espi:ElectricPowerUsageSummary/espi:currentBillingPeriodOverAllConsumption/espi:uom", xml);
        assertXpathValue("93018", "/espi:ElectricPowerUsageSummary/espi:currentBillingPeriodOverAllConsumption/espi:value", xml);
    }

    @Test
    public void marshal_setsQualityOfReading() throws JAXBException, SAXException, IOException, XpathException {
        assertXpathValue("14", "/espi:ElectricPowerUsageSummary/espi:qualityOfReading", xml);
    }

    @Test
    public void marshal_setsStatusTimeStamp() throws JAXBException, SAXException, IOException, XpathException {
        assertXpathValue("1331784000", "/espi:ElectricPowerUsageSummary/espi:statusTimeStamp", xml);
    }

    @Test
    public void marshal_setsCurrentDayLastYearNetConsumption() throws JAXBException, SAXException, IOException, XpathException {
        assertXpathValue("0", "/espi:ElectricPowerUsageSummary/espi:currentDayLastYearNetConsumption/espi:powerOfTenMultiplier", xml);
        assertXpathValue("1331784000", "/espi:ElectricPowerUsageSummary/espi:currentDayLastYearNetConsumption/espi:timeStamp", xml);
        assertXpathValue("72", "/espi:ElectricPowerUsageSummary/espi:currentDayLastYearNetConsumption/espi:uom", xml);
        assertXpathValue("93018", "/espi:ElectricPowerUsageSummary/espi:currentDayLastYearNetConsumption/espi:value", xml);
    }

    @Test
    public void marshal_setsCurrentDayNetConsumption() throws JAXBException, SAXException, IOException, XpathException {
        assertXpathValue("0", "/espi:ElectricPowerUsageSummary/espi:currentDayNetConsumption/espi:powerOfTenMultiplier", xml);
        assertXpathValue("1331784000", "/espi:ElectricPowerUsageSummary/espi:currentDayNetConsumption/espi:timeStamp", xml);
        assertXpathValue("72", "/espi:ElectricPowerUsageSummary/espi:currentDayNetConsumption/espi:uom", xml);
        assertXpathValue("93018", "/espi:ElectricPowerUsageSummary/espi:currentDayNetConsumption/espi:value", xml);
    }

    @Test
    public void marshal_setsCurrentDayOverallConsumption() throws JAXBException, SAXException, IOException, XpathException {
        assertXpathValue("0", "/espi:ElectricPowerUsageSummary/espi:currentDayOverallConsumption/espi:powerOfTenMultiplier", xml);
        assertXpathValue("1331784000", "/espi:ElectricPowerUsageSummary/espi:currentDayOverallConsumption/espi:timeStamp", xml);
        assertXpathValue("72", "/espi:ElectricPowerUsageSummary/espi:currentDayOverallConsumption/espi:uom", xml);
        assertXpathValue("93018", "/espi:ElectricPowerUsageSummary/espi:currentDayOverallConsumption/espi:value", xml);
    }

    @Test
    public void marshal_setsPeakDemand() throws JAXBException, SAXException, IOException, XpathException {
        assertXpathValue("0", "/espi:ElectricPowerUsageSummary/espi:peakDemand/espi:powerOfTenMultiplier", xml);
        assertXpathValue("1331784000", "/espi:ElectricPowerUsageSummary/espi:peakDemand/espi:timeStamp", xml);
        assertXpathValue("72", "/espi:ElectricPowerUsageSummary/espi:peakDemand/espi:uom", xml);
        assertXpathValue("93018", "/espi:ElectricPowerUsageSummary/espi:peakDemand/espi:value", xml);
    }

    @Test
    public void marshal_setsPreviousDayLastYearOverallConsumption() throws JAXBException, SAXException, IOException, XpathException {
        assertXpathValue("0", "/espi:ElectricPowerUsageSummary/espi:previousDayLastYearOverallConsumption/espi:powerOfTenMultiplier", xml);
        assertXpathValue("1331784000", "/espi:ElectricPowerUsageSummary/espi:previousDayLastYearOverallConsumption/espi:timeStamp", xml);
        assertXpathValue("72", "/espi:ElectricPowerUsageSummary/espi:previousDayLastYearOverallConsumption/espi:uom", xml);
        assertXpathValue("93018", "/espi:ElectricPowerUsageSummary/espi:previousDayLastYearOverallConsumption/espi:value", xml);
    }

    @Test
    public void marshal_setsPreviousDayNetConsumption() throws JAXBException, SAXException, IOException, XpathException {
        assertXpathValue("0", "/espi:ElectricPowerUsageSummary/espi:previousDayNetConsumption/espi:powerOfTenMultiplier", xml);
        assertXpathValue("1331784000", "/espi:ElectricPowerUsageSummary/espi:previousDayNetConsumption/espi:timeStamp", xml);
        assertXpathValue("72", "/espi:ElectricPowerUsageSummary/espi:previousDayNetConsumption/espi:uom", xml);
        assertXpathValue("93018", "/espi:ElectricPowerUsageSummary/espi:previousDayNetConsumption/espi:value", xml);
    }

    @Test
    public void marshal_setsPreviousDayOverallConsumption() throws JAXBException, SAXException, IOException, XpathException {
        assertXpathValue("0", "/espi:ElectricPowerUsageSummary/espi:previousDayOverallConsumption/espi:powerOfTenMultiplier", xml);
        assertXpathValue("1331784000", "/espi:ElectricPowerUsageSummary/espi:previousDayOverallConsumption/espi:timeStamp", xml);
        assertXpathValue("72", "/espi:ElectricPowerUsageSummary/espi:previousDayOverallConsumption/espi:uom", xml);
        assertXpathValue("93018", "/espi:ElectricPowerUsageSummary/espi:previousDayOverallConsumption/espi:value", xml);
    }

    @Test
    public void marshal_setsRatchetDemand() throws JAXBException, SAXException, IOException, XpathException {
        assertXpathValue("0", "/espi:ElectricPowerUsageSummary/espi:ratchetDemand/espi:powerOfTenMultiplier", xml);
        assertXpathValue("1331784000", "/espi:ElectricPowerUsageSummary/espi:ratchetDemand/espi:timeStamp", xml);
        assertXpathValue("72", "/espi:ElectricPowerUsageSummary/espi:ratchetDemand/espi:uom", xml);
        assertXpathValue("93018", "/espi:ElectricPowerUsageSummary/espi:ratchetDemand/espi:value", xml);
    }

    @Test
    public void marshal_setsRatchetDemandPeriod() throws JAXBException, SAXException, IOException, XpathException {
        assertXpathValue("1119600", "/espi:ElectricPowerUsageSummary/espi:ratchetDemandPeriod/espi:duration", xml);
        assertXpathValue("1119600", "/espi:ElectricPowerUsageSummary/espi:ratchetDemandPeriod/espi:start", xml);
    }

    @Test
    public void usagePoint_hasTransientAnnotation() {
        TestUtils.assertAnnotationPresent(ElectricPowerUsageSummary.class, "usagePoint", XmlTransient.class);
    }

    private void assertSummaryMeasurement(SummaryMeasurement sm) {
        assertEquals("PowerOfTenMultiplier", sm.getPowerOfTenMultiplier());
        assertEquals(1331784000L, sm.getTimeStamp().longValue());
        assertEquals("Uom", sm.getUom());
        assertEquals(93018L, sm.getValue().longValue());
    }
}