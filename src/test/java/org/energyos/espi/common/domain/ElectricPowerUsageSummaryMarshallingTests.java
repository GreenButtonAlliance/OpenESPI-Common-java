package org.energyos.espi.common.domain;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.JAXBException;

import org.energyos.espi.common.utils.EspiMarshaller;
import org.junit.Before;
import org.junit.Test;

public class ElectricPowerUsageSummaryMarshallingTests {
    static final String XML_INPUT = "<ElectricPowerUsageSummary xmlns=\"http://naesb.org/espi\">" +
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
    public void before() throws JAXBException {
        electricPowerUsageSummary = EspiMarshaller.<ElectricPowerUsageSummary>unmarshal(XML_INPUT).getValue();
    }

    @Test
    public void unmarshalsElectricPowerUsageSummary() {
        assertEquals(ElectricPowerUsageSummary.class, electricPowerUsageSummary.getClass());
    }

    @Test
    public void unmarshal_setsBillLastPeriod() {
        assertEquals(1L, electricPowerUsageSummary.getBillLastPeriod().longValue());
    };

    @Test
    public void unmarshal_setsBillToDate() {
        assertEquals(2L, electricPowerUsageSummary.getBillToDate().longValue());
    };

    @Test
    public void unmarshal_setsCostAdditionalLastPeriod() {
        assertEquals(3L, electricPowerUsageSummary.getCostAdditionalLastPeriod().longValue());
    };

    @Test
    public void unmarshal_setsCurrency() {
        assertEquals("currency", electricPowerUsageSummary.getCurrency());
    };

    @Test
    public void unmarshal_setsQualityOfReading() {
        assertEquals("qualityOfReading", electricPowerUsageSummary.getQualityOfReading());
    };

    @Test
    public void unmarshal_setsStatusTimeStamp() {
        assertEquals(4L, electricPowerUsageSummary.getStatusTimeStamp());
    };

    @Test
    public void unmarshal_setsRatchetDemandPeriod() {
        assertEquals(1119600L, electricPowerUsageSummary.getRatchetDemandPeriod().getDuration().longValue());
        assertEquals(1119601L, electricPowerUsageSummary.getRatchetDemandPeriod().getStart().longValue());
    };

    @Test
    public void unmarshal_setsBillingPeriod() {
        assertEquals(1119602L, electricPowerUsageSummary.getBillingPeriod().getDuration().longValue());
        assertEquals(1119603L, electricPowerUsageSummary.getBillingPeriod().getStart().longValue());
    };

    @Test
    public void unmarshal_setsCurrentBillingPeriodOverAllConsumption() {
        assertSummaryMeasurement(electricPowerUsageSummary.getCurrentBillingPeriodOverAllConsumption());
    };

    @Test
    public void unmarshal_setsCurrentDayLastYearNetConsumption() {
        assertSummaryMeasurement(electricPowerUsageSummary.getCurrentDayLastYearNetConsumption());
    };

    @Test
    public void unmarshal_setsCurrentDayNetConsumption() {
        assertSummaryMeasurement(electricPowerUsageSummary.getCurrentDayNetConsumption());
    };

    @Test
    public void unmarshal_setsCurrentDayOverallConsumption() {
        assertSummaryMeasurement(electricPowerUsageSummary.getCurrentDayOverallConsumption());
    };

    @Test
    public void unmarshal_setsPeakDemand() {
        assertSummaryMeasurement(electricPowerUsageSummary.getPeakDemand());
    };

    @Test
    public void unmarshal_setsPreviousDayLastYearOverallConsumption() {
        assertSummaryMeasurement(electricPowerUsageSummary.getPreviousDayLastYearOverallConsumption());
    };

    @Test
    public void unmarshal_setsPreviousDayNetConsumption() {
        assertSummaryMeasurement(electricPowerUsageSummary.getPreviousDayNetConsumption());
    };

    @Test
    public void unmarshal_setsPreviousDayOverallConsumption() {
        assertSummaryMeasurement(electricPowerUsageSummary.getPreviousDayOverallConsumption());
    };

    @Test
    public void unmarshal_setsRatchetDemand() {
        assertSummaryMeasurement(electricPowerUsageSummary.getRatchetDemand());
    };

    private void assertSummaryMeasurement(SummaryMeasurement sm) {
        assertEquals("PowerOfTenMultiplier", sm.getPowerOfTenMultiplier());
        assertEquals(1331784000L, sm.getTimeStamp().longValue());
        assertEquals("Uom", sm.getUom());
        assertEquals(93018L, sm.getValue().longValue());
    }
}
