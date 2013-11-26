package org.energyos.espi.common.test;

import org.energyos.espi.common.domain.*;
import org.energyos.espi.common.models.atom.LinkType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.*;

public class EspiFactory {

    public static UsagePoint newUsagePointOnly(UUID uuid) {
        UsagePoint usagePoint = new UsagePoint();

        usagePoint.setUUID(uuid);
        usagePoint.setDescription("Electric meter");

        usagePoint.setServiceCategory(new ServiceCategory(ServiceCategory.ELECTRICITY_SERVICE));

        return usagePoint;
    }

    public static UsagePoint newUsagePoint() {
        return newUsagePoint(newRetailCustomer());
    }

    public static UsagePoint newUsagePointWithId(RetailCustomer retailCustomer) {
        UsagePoint usagePoint = newUsagePoint(retailCustomer);
        usagePoint.setId(2L);
        usagePoint.getLocalTimeParameters().setId(1L);

        return usagePoint;
    }

    public static UsagePoint newUsagePointWithId() {
        return newUsagePointWithId(newRetailCustomerWithId());
    }

    private static RetailCustomer newRetailCustomerWithId() {
        RetailCustomer retailCustomer = newRetailCustomer();
        retailCustomer.setId(1L);
        return retailCustomer;
    }

    public static UsagePoint newUsagePoint(RetailCustomer retailCustomer) {
        UsagePoint usagePoint = newSimpleUsagePoint();

        usagePoint.setRetailCustomer(retailCustomer);
        usagePoint.addMeterReading(newMeterReading());
        usagePoint.addElectricPowerUsageSummary(newElectricPowerUsageSummary());
        usagePoint.addElectricPowerQualitySummary(newElectricPowerQualitySummary());
        usagePoint.setLocalTimeParameters(newLocalTimeParameters());

        usagePoint.getRelatedLinks().add(new LinkType("related", usagePoint.getSelfHref() + "/MeterReading"));
        usagePoint.getRelatedLinks().add(new LinkType("related", usagePoint.getSelfHref() + "/ElectricPowerUsageSummary"));
        usagePoint.getRelatedLinks().add(new LinkType("related", usagePoint.getSelfHref() + "/ElectricPowerQualitySummary"));
        usagePoint.setSelfLink(new LinkType("self", usagePoint.getSelfHref()));
        usagePoint.setUpLink(new LinkType("up", usagePoint.getUpHref()));

        GregorianCalendar published = new GregorianCalendar(2012, Calendar.NOVEMBER, 15, 0, 0, 0);
        published.setTimeZone(TimeZone.getTimeZone("UTC"));
        usagePoint.setPublished(published);

        GregorianCalendar updated = new GregorianCalendar(2012, Calendar.OCTOBER, 24, 0, 0, 0);
        updated.setTimeZone(TimeZone.getTimeZone("UTC"));
        usagePoint.setUpdated(updated);

        return usagePoint;
    }

    public static UsagePoint newSimpleUsagePoint() {
        UsagePoint usagePoint = new UsagePoint();

        usagePoint.setUUID(UUID.randomUUID());
        usagePoint.setDescription("Electric meter");

        usagePoint.setRoleFlags("role flags".getBytes());
        usagePoint.setStatus(new Short("5"));

        usagePoint.setServiceCategory(new ServiceCategory(ServiceCategory.ELECTRICITY_SERVICE));
        usagePoint.setServiceDeliveryPoint(new ServiceDeliveryPoint());

        return usagePoint;
    }

    public static MeterReading newMeterReadingWithUsagePoint() {
        return newUsagePoint().getMeterReadings().get(0);
    }

    public static ElectricPowerUsageSummary newElectricPowerUsageSummaryWithUsagePoint() {
        return newUsagePoint().getElectricPowerUsageSummaries().get(0);
    }

    public static ElectricPowerQualitySummary newElectricPowerQualitySummaryWithUsagePoint() {
        return newUsagePoint().getElectricPowerQualitySummaries().get(0);
    }

    public static TimeConfiguration newTimeConfigurationWithUsagePoint() {
        return newUsagePoint().getLocalTimeParameters();
    }

    public static IntervalBlock newIntervalBlockWithUsagePoint() {
        return newUsagePoint().getMeterReadings().get(0).getIntervalBlocks().get(0);
    }

    public static RetailCustomer newRetailCustomer() {
        RetailCustomer retailCustomer = new RetailCustomer();
        retailCustomer.setFirstName(("First" + UUID.randomUUID()).substring(0, 29));
        retailCustomer.setLastName(("Last" + UUID.randomUUID()).substring(0, 29));
        retailCustomer.setUsername(("Username" + UUID.randomUUID()).substring(0, 29));

        return retailCustomer;
    }

    public static MeterReading newMeterReading() {
        MeterReading meterReading = new MeterReading();

        meterReading.setUUID(UUID.randomUUID());
        meterReading.setDescription("Electricity consumption");

        meterReading.addIntervalBlock(newIntervalBlock());
        meterReading.addIntervalBlock(newIntervalBlock());
        meterReading.addIntervalBlock(newIntervalBlock());

        meterReading.setReadingType(newReadingType());
        meterReading.setPublished(newCalendar(2012, 10, 21));
        meterReading.setUpdated(newCalendar(2012, 10, 28));

        return meterReading;
    }

    public static TimeConfiguration newLocalTimeParameters() {
        TimeConfiguration timeConfiguration = new TimeConfiguration();

        timeConfiguration.setDescription("DST For North America");
        timeConfiguration.setUUID(UUID.randomUUID());

        timeConfiguration.setDstEndRule("foo".getBytes());
        timeConfiguration.setDstOffset(1000L);
        timeConfiguration.setDstStartRule("bar".getBytes());
        timeConfiguration.setTzOffset(1234L);
        timeConfiguration.setPublished(newCalendar(2012, 10, 21));
        timeConfiguration.setUpdated(newCalendar(2012, 10, 28));

        return timeConfiguration;
    }

    public static ReadingType newReadingType() {
        ReadingType readingType = new ReadingType();

        readingType.setDescription("Energy Delivered (kWh)");
        readingType.setUUID(UUID.randomUUID());

        RationalNumber argument = new RationalNumber();
        argument.setNumerator(new BigInteger("1"));
        argument.setDenominator(new BigInteger("3"));

        ReadingInterharmonic interharmonic = new ReadingInterharmonic();
        interharmonic.setNumerator(new BigInteger("1"));
        interharmonic.setDenominator(new BigInteger("6"));

        readingType.setAccumulationBehaviour("accumulationBehaviour");
        readingType.setCommodity("commodity");
        readingType.setDataQualifier("dataQualifier");
        readingType.setFlowDirection("flowDirection");
        readingType.setIntervalLength(10L);
        readingType.setKind("kind");
        readingType.setPhase("phase");
        readingType.setPowerOfTenMultiplier("multiplier");
        readingType.setTimeAttribute("timeAttribute");
        readingType.setUom("uom");
        readingType.setConsumptionTier("consumptionTier");
        readingType.setCpp("cpp");
        readingType.setCurrency("currency");
        readingType.setTou("tou");
        readingType.setAggregate("aggregate");
        readingType.setArgument(argument);
        readingType.setInterharmonic(interharmonic);
        readingType.setMeasuringPeriod("measuringPeriod");
        readingType.setPublished(newCalendar(2012, 10, 21));
        readingType.setUpdated(newCalendar(2012, 10, 28));

        return readingType;
    }

    public static IntervalBlock newIntervalBlock() {
        IntervalBlock intervalBlock = new IntervalBlock();

        DateTimeInterval interval = new DateTimeInterval();
        interval.setDuration(86400L);
        interval.setStart(1330578000L);

        intervalBlock.addIntervalReading(newIntervalReading());
        intervalBlock.addIntervalReading(newIntervalReading());

        intervalBlock.setUUID(UUID.randomUUID());
        intervalBlock.setInterval(interval);

        intervalBlock.setPublished(newCalendar(2012, 10, 21));
        intervalBlock.setUpdated(newCalendar(2012, 10, 28));

        return intervalBlock;
    }

    public static IntervalReading newIntervalReading() {
        IntervalReading intervalReading = new IntervalReading();

        DateTimeInterval timePeriod = new DateTimeInterval();
        timePeriod.setDuration(86401L);
        timePeriod.setStart(1330578001L);

        intervalReading.setCost(100L);
        intervalReading.setValue(6L);

        intervalReading.addReadingQuality(_readingQuality("quality1"));
        intervalReading.addReadingQuality(_readingQuality("quality2"));

        intervalReading.setTimePeriod(timePeriod);


        return intervalReading;
    }

    private static ReadingQuality _readingQuality(String quality) {
        ReadingQuality readingQuality = new ReadingQuality();
        readingQuality.setQuality(quality);

        return readingQuality;
    }

    public static ElectricPowerUsageSummary newElectricPowerUsageSummary() {
        ElectricPowerUsageSummary summary = new ElectricPowerUsageSummary();

        summary.setUUID(UUID.randomUUID());
        summary.setDescription("Usage Summary");
        summary.setBillingPeriod(new DateTimeInterval(1119600L, 1119600L));
        summary.setPublished(new GregorianCalendar(2012, 10, 24, 0, 0, 0));
        summary.setUpdated(new GregorianCalendar(2012, 10, 24, 0, 0, 0));
        summary.setBillLastPeriod(15303000L);
        summary.setBillToDate(1135000L);
        summary.setCostAdditionalLastPeriod(1346000L);
        summary.setCurrency("840");

        SummaryMeasurement summaryMeasurement = new SummaryMeasurement("0", 1331784000L, "72", 93018L);

        summary.setCurrentBillingPeriodOverAllConsumption(summaryMeasurement);
        summary.setQualityOfReading("14");
        summary.setStatusTimeStamp(1331784000L);
        summary.setCurrentDayLastYearNetConsumption(summaryMeasurement);
        summary.setCurrentDayNetConsumption(summaryMeasurement);
        summary.setCurrentDayOverallConsumption(summaryMeasurement);
        summary.setPeakDemand(summaryMeasurement);
        summary.setPreviousDayLastYearOverallConsumption(summaryMeasurement);
        summary.setPreviousDayNetConsumption(summaryMeasurement);
        summary.setPreviousDayOverallConsumption(summaryMeasurement);
        summary.setRatchetDemand(summaryMeasurement);
        summary.setRatchetDemandPeriod(new DateTimeInterval(1119600L, 1119600L));
        summary.setPublished(newCalendar(2012, 10, 21));
        summary.setUpdated(newCalendar(2012, 10, 28));

        return summary;
    }

    public static ElectricPowerQualitySummary newElectricPowerQualitySummary() {
        ElectricPowerQualitySummary summary = new ElectricPowerQualitySummary();

        summary.setUUID(UUID.randomUUID());
        summary.setDescription("Quality Summary");
        summary.setFlickerPlt(1L);
        summary.setFlickerPst(2L);
        summary.setHarmonicVoltage(3L);
        summary.setLongInterruptions(4L);
        summary.setMainsVoltage(5L);
        summary.setMeasurementProtocol((short) 6);
        summary.setPowerFrequency(7L);
        summary.setRapidVoltageChanges(8L);
        summary.setShortInterruptions(9L);

        DateTimeInterval summaryInterval = new DateTimeInterval();
        summaryInterval.setDuration(2119600L);
        summaryInterval.setStart(2330578000L);

        summary.setSummaryInterval(summaryInterval);
        summary.setSupplyVoltageDips(10L);
        summary.setSupplyVoltageImbalance(11L);
        summary.setSupplyVoltageVariations(12L);
        summary.setTempOvervoltage(13L);
        summary.setPublished(newCalendar(2012, 10, 21));
        summary.setUpdated(newCalendar(2012, 10, 28));

        return summary;
    }

    public static Authorization newAuthorization() {
        return newAuthorization(newRetailCustomer(), newApplicationInformation());
    }

    public static Authorization newAuthorization(RetailCustomer retailCustomer, ApplicationInformation applicationInformation) {
        Authorization authorization = new Authorization();

        authorization.setAccessToken("accessToken" + System.currentTimeMillis());
        authorization.setAuthorizationServer("http://DataCustodian" + System.currentTimeMillis() + ".example.com");
        authorization.setSubscriptionURI(Routes.getDataCustodianRESTSubscriptionGetURL(newRandomString()));
        authorization.setThirdParty("thirdParty" + System.currentTimeMillis());
        authorization.setState("state" + UUID.randomUUID());
        authorization.setRetailCustomer(retailCustomer);
        authorization.setApplicationInformation(applicationInformation);
        authorization.setUUID(UUID.randomUUID());

        return authorization;
    }

    public static BatchList newBatchList() {
        BatchList batchList = new BatchList();
        batchList.getResources().add("some resource uri");
        return batchList;
    }

    public static ApplicationInformation newApplicationInformation() {
        ApplicationInformation applicationInformation = new ApplicationInformation();
        applicationInformation.setUUID(UUID.randomUUID());
        applicationInformation.setThirdPartyApplicationName("Name" + newRandomString());
        applicationInformation.setDataCustodianThirdPartyId("ClientId" + newRandomString());
        applicationInformation.setDataCustodianId("DataCustodianId" + newRandomString());
        applicationInformation.setThirdPartyDefaultNotifyResource("http://example.com:8080/ThirdParty/espi/1_1/Notification");
        applicationInformation.setDataCustodianAuthorizationResource("http://example.com:8080/ThirdParty/oauth/authorize");
        applicationInformation.setDataCustodianTokenResource("http://example.com:8080/ThirdParty/oauth/token");
        applicationInformation.setThirdPartyDefaultOAuthCallback("http://example.com:8080/ThirdParty/espi/1_1/OAuthCallBack");
        applicationInformation.setDataCustodianThirdPartySecret("Secret" + newRandomString());
        applicationInformation.getScope().add("FB=4_5_15;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13");
        applicationInformation.getScope().add("FB=4_5_16;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13");

        return applicationInformation;
    }

    private static String newRandomString() {
        return UUID.randomUUID().toString();
    }

    public static Subscription newSubscription() {
        Subscription subscription = new Subscription();
        subscription.setUUID(UUID.randomUUID());
        subscription.setHashedId(newRandomString());
        subscription.setRetailCustomer(newRetailCustomer());
        subscription.setApplicationInformation(newApplicationInformation());

        return subscription;
    }

    public static Subscription newSubscription(RetailCustomer retailCustomer, ApplicationInformation applicationInformation) {
        Subscription subscription = new Subscription();
        subscription.setUUID(UUID.randomUUID());
        subscription.setRetailCustomer(retailCustomer);
        subscription.setApplicationInformation(applicationInformation);

        return subscription;
    }

    public static Subscription newSubscription(RetailCustomer retailCustomer) {
        Subscription subscription = new Subscription();
        subscription.setUUID(UUID.randomUUID());
        subscription.setRetailCustomer(retailCustomer);
        subscription.setApplicationInformation(newApplicationInformation());

        return subscription;
    }

    public static Authorization newAuthorization(Subscription subscription) {
        Authorization authorization = new Authorization();
        authorization.setUUID(UUID.randomUUID());
        authorization.setAccessToken(newRandomString());
        authorization.setResource("/Resource/" + newRandomString());
        authorization.setState(newRandomString());
        return authorization;
    }

    public static ServiceCategory newServiceCategory() {
        return new ServiceCategory(ServiceCategory.ELECTRICITY_SERVICE);
    }

    public static GregorianCalendar newCalendar(int year, int month, int date) {
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+00:00"));
        calendar.set(year, month, date, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.getTime();
        return calendar;
    }

    public static OAuth2Request newOAuth2Request(String clientId) {
        return new OAuth2Request(
                new HashMap<String, String>(),
                clientId,
                new ArrayList<GrantedAuthority>(),
                true,
                new HashSet<String>(),
                new HashSet<String>(),
                "redirect",
                new HashMap<String, Serializable>()
        );
    }

    public static OAuth2Request newOAuth2Request() {
        return newOAuth2Request("client");
    }
}
