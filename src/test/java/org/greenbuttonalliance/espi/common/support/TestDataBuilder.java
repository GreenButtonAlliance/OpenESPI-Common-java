/*
 *
 *    Copyright (c) 2018-2025 Green Button Alliance, Inc.
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

package org.greenbuttonalliance.espi.common.support;

import org.greenbuttonalliance.espi.common.domain.*;
import org.greenbuttonalliance.espi.common.domain.usage.*;
import org.greenbuttonalliance.espi.common.models.atom.LinkType;
import org.greenbuttonalliance.espi.common.service.EspiIdGeneratorService;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Modern test data builder using builder pattern for Spring Boot 3.5 and JUnit 5.
 * 
 * Provides fluent API for creating test data with sensible defaults and easy customization.
 * Supports both legacy domain objects and new entity objects.
 */
public class TestDataBuilder {

    // Constants for consistent test data - ESPI standard format (UTC with "Z" termination)
    public static final String DEFAULT_DESCRIPTION = "Test Description";
    
    // ESPI-compliant UTC timestamps: "2023-01-01T00:00:00Z" and "2023-01-02T00:00:00Z"
    public static final LocalDateTime DEFAULT_PUBLISHED = LocalDateTime.of(2023, 1, 1, 0, 0, 0, 0);
    public static final LocalDateTime DEFAULT_UPDATED = LocalDateTime.of(2023, 1, 2, 0, 0, 0, 0);
    
    // ESPI timestamp strings for validation (UTC with Z termination)
    public static final String DEFAULT_PUBLISHED_ESPI = "2023-01-01T00:00:00Z";
    public static final String DEFAULT_UPDATED_ESPI = "2023-01-02T00:00:00Z";
    
    public static final Long DEFAULT_START_TIME = 1672531200L; // 2023-01-01T00:00:00Z
    public static final Long DEFAULT_DURATION = 3600L; // 1 hour
    
    // ESPI-compliant ID generation service for deterministic UUIDs
    private static final EspiIdGeneratorService ESPI_ID_GENERATOR = new EspiIdGeneratorService();

    // UsagePointEntity Builder
    public static class UsagePointEntityBuilder {
        private Long id;
        private String resourceId = "test-usage-point-1"; // Used for generating deterministic href and UUID
        private String description = DEFAULT_DESCRIPTION;
        private byte[] roleFlags = "test-role-flags".getBytes();
        private ServiceCategory serviceCategory = new ServiceCategory(ServiceCategory.ELECTRICITY_SERVICE);
        private Short status = 1;
        private LocalDateTime published = DEFAULT_PUBLISHED;
        private LocalDateTime updated = DEFAULT_UPDATED;
        private List<MeterReadingEntity> meterReadings = new ArrayList<>();
        private List<UsageSummaryEntity> usageSummaries = new ArrayList<>();
        private List<ElectricPowerQualitySummaryEntity> electricPowerQualitySummaries = new ArrayList<>();
        private ServiceDeliveryPointEntity serviceDeliveryPoint;
        private List<LinkType> relatedLinks = new ArrayList<>();
        private SummaryMeasurement estimatedLoad;
        private SummaryMeasurement nominalServiceVoltage;
        private SummaryMeasurement ratedCurrent;
        private SummaryMeasurement ratedPower;
        private List<PnodeRefEntity> pnodeRefs = new ArrayList<>();
        private List<AggregatedNodeRefEntity> aggregatedNodeRefs = new ArrayList<>();

        public UsagePointEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public UsagePointEntityBuilder withResourceId(String resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public UsagePointEntityBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public UsagePointEntityBuilder withServiceCategory(ServiceCategory serviceCategory) {
            this.serviceCategory = serviceCategory;
            return this;
        }

        public UsagePointEntityBuilder withStatus(Short status) {
            this.status = status;
            return this;
        }

        public UsagePointEntityBuilder withMeterReading(MeterReadingEntity meterReading) {
            this.meterReadings.add(meterReading);
            return this;
        }

        public UsagePointEntityBuilder withUsageSummary(UsageSummaryEntity usageSummary) {
            this.usageSummaries.add(usageSummary);
            return this;
        }

        public UsagePointEntityBuilder withElectricPowerQualitySummary(ElectricPowerQualitySummaryEntity epqs) {
            this.electricPowerQualitySummaries.add(epqs);
            return this;
        }

        public UsagePointEntityBuilder withServiceDeliveryPoint(ServiceDeliveryPointEntity serviceDeliveryPoint) {
            this.serviceDeliveryPoint = serviceDeliveryPoint;
            return this;
        }

        public UsagePointEntityBuilder withEstimatedLoad(SummaryMeasurement estimatedLoad) {
            this.estimatedLoad = estimatedLoad;
            return this;
        }

        public UsagePointEntityBuilder withNominalServiceVoltage(SummaryMeasurement nominalServiceVoltage) {
            this.nominalServiceVoltage = nominalServiceVoltage;
            return this;
        }

        public UsagePointEntityBuilder withRatedCurrent(SummaryMeasurement ratedCurrent) {
            this.ratedCurrent = ratedCurrent;
            return this;
        }

        public UsagePointEntityBuilder withRatedPower(SummaryMeasurement ratedPower) {
            this.ratedPower = ratedPower;
            return this;
        }

        public UsagePointEntityBuilder withPnodeRef(PnodeRefEntity pnodeRef) {
            this.pnodeRefs.add(pnodeRef);
            return this;
        }

        public UsagePointEntityBuilder withAggregatedNodeRef(AggregatedNodeRefEntity aggregatedNodeRef) {
            this.aggregatedNodeRefs.add(aggregatedNodeRef);
            return this;
        }

        public UsagePointEntity build() {
            UsagePointEntity entity = new UsagePointEntity();
            entity.setId(id);
            entity.setDescription(description);
            entity.setRoleFlags(roleFlags);
            entity.setServiceCategory(serviceCategory);
            entity.setStatus(status);
            entity.setPublished(published);
            entity.setUpdated(updated);
            entity.setMeterReadings(meterReadings);
            entity.setUsageSummaries(usageSummaries);
            entity.setElectricPowerQualitySummaries(electricPowerQualitySummaries);
            entity.setServiceDeliveryPoint(serviceDeliveryPoint);
            entity.setRelatedLinks(relatedLinks);
            entity.setEstimatedLoad(estimatedLoad);
            entity.setNominalServiceVoltage(nominalServiceVoltage);
            entity.setRatedCurrent(ratedCurrent);
            entity.setRatedPower(ratedPower);
            entity.setPnodeRefs(pnodeRefs);
            entity.setAggregatedNodeRefs(aggregatedNodeRefs);
            
            // Generate ESPI-compliant UUID5 based on href
            UUID espiId = ESPI_ID_GENERATOR.generateEspiId("UsagePoint", resourceId);
            entity.setUUID(espiId);
            
            // Set up self link for proper ESPI compliance
            String selfHref = String.format("/espi/1_1/resource/UsagePoint/%s", resourceId);
            entity.setSelfLink(new LinkType("self", selfHref));
            
            return entity;
        }
    }

    // MeterReadingEntity Builder
    public static class MeterReadingEntityBuilder {
        private Long id;
        private String resourceId = "test-meter-reading-1";
        private String description = "Test Meter Reading";
        private LocalDateTime published = DEFAULT_PUBLISHED;
        private LocalDateTime updated = DEFAULT_UPDATED;
        private ReadingTypeEntity readingType;
        private List<IntervalBlockEntity> intervalBlocks = new ArrayList<>();

        public MeterReadingEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public MeterReadingEntityBuilder withResourceId(String resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public MeterReadingEntityBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public MeterReadingEntityBuilder withReadingType(ReadingTypeEntity readingType) {
            this.readingType = readingType;
            return this;
        }

        public MeterReadingEntityBuilder withIntervalBlock(IntervalBlockEntity intervalBlock) {
            this.intervalBlocks.add(intervalBlock);
            return this;
        }

        public MeterReadingEntity build() {
            MeterReadingEntity entity = new MeterReadingEntity();
            entity.setId(id);
            entity.setDescription(description);
            entity.setPublished(published);
            entity.setUpdated(updated);
            entity.setReadingType(readingType);
            entity.setIntervalBlocks(intervalBlocks);
            
            // Generate ESPI-compliant UUID5 based on href
            UUID espiId = ESPI_ID_GENERATOR.generateEspiId("MeterReading", resourceId);
            entity.setUUID(espiId);
            
            // Set up self link for proper ESPI compliance
            String selfHref = String.format("/espi/1_1/resource/MeterReading/%s", resourceId);
            entity.setSelfLink(new LinkType("self", selfHref));
            
            return entity;
        }
    }

    // IntervalBlockEntity Builder
    public static class IntervalBlockEntityBuilder {
        private Long id;
        private String resourceId = "test-interval-block-1";
        private String description = "Test Interval Block";
        private LocalDateTime published = DEFAULT_PUBLISHED;
        private LocalDateTime updated = DEFAULT_UPDATED;
        private DateTimeInterval interval = createDefaultDateTimeInterval();
        private List<IntervalReadingEntity> intervalReadings = new ArrayList<>();

        public IntervalBlockEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public IntervalBlockEntityBuilder withResourceId(String resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public IntervalBlockEntityBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public IntervalBlockEntityBuilder withInterval(DateTimeInterval interval) {
            this.interval = interval;
            return this;
        }

        public IntervalBlockEntityBuilder withIntervalReading(IntervalReadingEntity intervalReading) {
            this.intervalReadings.add(intervalReading);
            return this;
        }

        public IntervalBlockEntity build() {
            IntervalBlockEntity entity = new IntervalBlockEntity();
            entity.setId(id);
            entity.setDescription(description);
            entity.setPublished(published);
            entity.setUpdated(updated);
            entity.setInterval(interval);
            entity.setIntervalReadings(intervalReadings);
            
            // Generate ESPI-compliant UUID5 based on href
            UUID espiId = ESPI_ID_GENERATOR.generateEspiId("IntervalBlock", resourceId);
            entity.setUUID(espiId);
            
            // Set up self link for proper ESPI compliance
            String selfHref = String.format("/espi/1_1/resource/IntervalBlock/%s", resourceId);
            entity.setSelfLink(new LinkType("self", selfHref));
            
            return entity;
        }
    }

    // IntervalReadingEntity Builder
    public static class IntervalReadingEntityBuilder {
        private Long id;
        private Long value = 1000L;
        private Long cost = 50L;
        private DateTimeInterval timePeriod = createDefaultDateTimeInterval();
        private List<ReadingQualityEntity> readingQualities = new ArrayList<>();

        public IntervalReadingEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }


        public IntervalReadingEntityBuilder withValue(Long value) {
            this.value = value;
            return this;
        }

        public IntervalReadingEntityBuilder withCost(Long cost) {
            this.cost = cost;
            return this;
        }

        public IntervalReadingEntityBuilder withTimePeriod(DateTimeInterval timePeriod) {
            this.timePeriod = timePeriod;
            return this;
        }

        public IntervalReadingEntityBuilder withReadingQuality(ReadingQualityEntity readingQuality) {
            this.readingQualities.add(readingQuality);
            return this;
        }

        public IntervalReadingEntity build() {
            IntervalReadingEntity entity = new IntervalReadingEntity();
            entity.setId(id);
            entity.setValue(value);
            entity.setCost(cost);
            entity.setTimePeriod(timePeriod);
            entity.setReadingQualities(readingQualities);
            return entity;
        }
    }

    // ReadingTypeEntity Builder
    public static class ReadingTypeEntityBuilder {
        private Long id;
        private String resourceId = "test-reading-type-1";
        private String description = "kWh Energy Reading";
        private String kind = "ENERGY"; // Energy
        private String uom = "WH"; // Wh
        private String powerOfTenMultiplier = "KILO"; // kilo
        private String accumulationBehaviour = "SUMMATION"; // Summation
        private LocalDateTime published = DEFAULT_PUBLISHED;
        private LocalDateTime updated = DEFAULT_UPDATED;

        public ReadingTypeEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ReadingTypeEntityBuilder withResourceId(String resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public ReadingTypeEntityBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ReadingTypeEntityBuilder withKind(String kind) {
            this.kind = kind;
            return this;
        }

        public ReadingTypeEntity build() {
            ReadingTypeEntity entity = new ReadingTypeEntity();
            entity.setId(id);
            entity.setDescription(description);
            entity.setKind(kind);
            entity.setUom(uom);
            entity.setPowerOfTenMultiplier(powerOfTenMultiplier);
            entity.setAccumulationBehaviour(accumulationBehaviour);
            entity.setPublished(published);
            entity.setUpdated(updated);
            
            // Generate ESPI-compliant UUID5 based on href
            UUID espiId = ESPI_ID_GENERATOR.generateEspiId("ReadingType", resourceId);
            entity.setUUID(espiId);
            
            // Set up self link for proper ESPI compliance
            String selfHref = String.format("/espi/1_1/resource/ReadingType/%s", resourceId);
            entity.setSelfLink(new LinkType("self", selfHref));
            
            return entity;
        }
    }

    // UsageSummaryEntity Builder
    public static class UsageSummaryEntityBuilder {
        private Long id;
        private String resourceId = "test-usage-summary-1";
        private String description = "Monthly Usage Summary";
        private Long billToDate = 15000L;
        private String currency = "USD"; // USD
        private DateTimeInterval billingPeriod = createDefaultDateTimeInterval();

        public UsageSummaryEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public UsageSummaryEntityBuilder withResourceId(String resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public UsageSummaryEntityBuilder withBillToDate(Long billToDate) {
            this.billToDate = billToDate;
            return this;
        }

        public UsageSummaryEntity build() {
            UsageSummaryEntity entity = new UsageSummaryEntity();
            entity.setId(id);
            entity.setDescription(description);
            entity.setBillToDate(billToDate);
            entity.setCurrency(currency);
            entity.setBillingPeriod(billingPeriod);
            
            // Generate ESPI-compliant UUID5 based on href
            UUID espiId = ESPI_ID_GENERATOR.generateEspiId("UsageSummary", resourceId);
            entity.setUUID(espiId);
            
            // Set up self link for proper ESPI compliance
            String selfHref = String.format("/espi/1_1/resource/UsageSummary/%s", resourceId);
            entity.setSelfLink(new LinkType("self", selfHref));
            
            return entity;
        }
    }

    // ElectricPowerQualitySummaryEntity Builder
    public static class ElectricPowerQualitySummaryEntityBuilder {
        private Long id;
        private String resourceId = "test-power-quality-1";
        private String description = "Power Quality Summary";
        private Long flickerPlt = 500L;  // 0.5 scaled as Long
        private Long flickerPst = 300L;  // 0.3 scaled as Long

        public ElectricPowerQualitySummaryEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ElectricPowerQualitySummaryEntityBuilder withResourceId(String resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public ElectricPowerQualitySummaryEntityBuilder withFlickerPlt(Long flickerPlt) {
            this.flickerPlt = flickerPlt;
            return this;
        }

        public ElectricPowerQualitySummaryEntity build() {
            ElectricPowerQualitySummaryEntity entity = new ElectricPowerQualitySummaryEntity();
            entity.setId(id);
            entity.setDescription(description);
            entity.setFlickerPlt(flickerPlt);
            entity.setFlickerPst(flickerPst);
            
            // Generate ESPI-compliant UUID5 based on href
            UUID espiId = ESPI_ID_GENERATOR.generateEspiId("ElectricPowerQualitySummary", resourceId);
            entity.setUUID(espiId);
            
            // Set up self link for proper ESPI compliance
            String selfHref = String.format("/espi/1_1/resource/ElectricPowerQualitySummary/%s", resourceId);
            entity.setSelfLink(new LinkType("self", selfHref));
            
            return entity;
        }
    }

    // ReadingQualityEntity Builder
    public static class ReadingQualityEntityBuilder {
        private Long id;
        private String resourceId = "test-resource-" + System.currentTimeMillis() % 1000;
        private String quality = "valid";

        public ReadingQualityEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ReadingQualityEntityBuilder withQuality(String quality) {
            this.quality = quality;
            return this;
        }

        public ReadingQualityEntity build() {
            ReadingQualityEntity entity = new ReadingQualityEntity();
            entity.setId(id);
            entity.setQuality(quality);
            return entity;
        }
    }

    // Legacy Domain Object Builders (for backward compatibility)
    public static class ApplicationInformationBuilder {
        private Long id;
        private UUID uuid = UUID.randomUUID();
        private String thirdPartyApplicationName = "Test Application";
        private String clientId = "test-client-id";
        private String dataCustodianId = "test-data-custodian-id";
        private String clientSecret = "test-client-secret";

        public ApplicationInformationBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ApplicationInformationBuilder withClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public ApplicationInformationBuilder withDataCustodianId(String dataCustodianId) {
            this.dataCustodianId = dataCustodianId;
            return this;
        }

        public ApplicationInformation build() {
            ApplicationInformation app = new ApplicationInformation();
            app.setId(id);
            app.setUUID(uuid);
            app.setThirdPartyApplicationName(thirdPartyApplicationName);
            app.setClientId(clientId);
            app.setDataCustodianId(dataCustodianId);
            app.setClientSecret(clientSecret);
            app.setThirdPartyNotifyUri("http://localhost:8080/ThirdParty/espi/1_1/Notification");
            app.setAuthorizationServerAuthorizationEndpoint("http://localhost:8080/DataCustodian/oauth/authorize");
            app.setAuthorizationServerTokenEndpoint("http://localhost:8080/DataCustodian/oauth/token");
            app.setDataCustodianResourceEndpoint("http://localhost:8080/DataCustodian/espi/1_1/resource");
            app.setRedirectUri("http://localhost:8080/ThirdParty/espi/1_1/OAuthCallBack");
            return app;
        }
    }

    // Factory methods for builders
    public static UsagePointEntityBuilder usagePointEntity() {
        return new UsagePointEntityBuilder();
    }

    public static MeterReadingEntityBuilder meterReadingEntity() {
        return new MeterReadingEntityBuilder();
    }

    public static IntervalBlockEntityBuilder intervalBlockEntity() {
        return new IntervalBlockEntityBuilder();
    }

    public static IntervalReadingEntityBuilder intervalReadingEntity() {
        return new IntervalReadingEntityBuilder();
    }

    public static ReadingTypeEntityBuilder readingTypeEntity() {
        return new ReadingTypeEntityBuilder();
    }

    public static UsageSummaryEntityBuilder usageSummaryEntity() {
        return new UsageSummaryEntityBuilder();
    }

    public static ElectricPowerQualitySummaryEntityBuilder electricPowerQualitySummaryEntity() {
        return new ElectricPowerQualitySummaryEntityBuilder();
    }

    public static ReadingQualityEntityBuilder readingQualityEntity() {
        return new ReadingQualityEntityBuilder();
    }

    public static ApplicationInformationBuilder applicationInformation() {
        return new ApplicationInformationBuilder();
    }

    public static PnodeRefEntityBuilder pnodeRefEntity() {
        return new PnodeRefEntityBuilder();
    }

    public static AggregatedNodeRefEntityBuilder aggregatedNodeRefEntity() {
        return new AggregatedNodeRefEntityBuilder();
    }

    public static SummaryMeasurementBuilder summaryMeasurement() {
        return new SummaryMeasurementBuilder();
    }

    // Convenience methods for complex test scenarios
    public static UsagePointEntity completeUsagePointEntity() {
        ReadingTypeEntity readingType = readingTypeEntity().build();
        
        IntervalReadingEntity reading1 = intervalReadingEntity()
            .withValue(1000L)
            .withCost(50L)
            .build();
        
        IntervalReadingEntity reading2 = intervalReadingEntity()
            .withValue(1200L)
            .withCost(60L)
            .build();
        
        IntervalBlockEntity intervalBlock = intervalBlockEntity()
            .withIntervalReading(reading1)
            .withIntervalReading(reading2)
            .build();
        
        MeterReadingEntity meterReading = meterReadingEntity()
            .withReadingType(readingType)
            .withIntervalBlock(intervalBlock)
            .build();
        
        UsageSummaryEntity usageSummary = usageSummaryEntity().build();
        ElectricPowerQualitySummaryEntity epqs = electricPowerQualitySummaryEntity().build();
        
        return usagePointEntity()
            .withMeterReading(meterReading)
            .withUsageSummary(usageSummary)
            .withElectricPowerQualitySummary(epqs)
            .build();
    }

    // Utility methods
    
    /**
     * Converts LocalDateTime to ESPI-compliant UTC timestamp string with "Z" termination.
     * 
     * @param localDateTime the LocalDateTime to convert (assumed to be UTC)
     * @return ESPI-formatted timestamp string (e.g., "2023-01-01T00:00:00Z")
     */
    public static String toEspiTimestamp(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.atZone(ZoneId.of("UTC")).format(java.time.format.DateTimeFormatter.ISO_INSTANT);
    }
    
    /**
     * Parses ESPI-compliant UTC timestamp string to LocalDateTime.
     * 
     * @param espiTimestamp the ESPI timestamp string (e.g., "2023-01-01T00:00:00Z")
     * @return LocalDateTime representation
     */
    public static LocalDateTime fromEspiTimestamp(String espiTimestamp) {
        if (espiTimestamp == null || espiTimestamp.trim().isEmpty()) {
            return null;
        }
        return ZonedDateTime.parse(espiTimestamp).toLocalDateTime();
    }
    
    /**
     * Creates a ServiceDeliveryPointEntity builder.
     */
    public static ServiceDeliveryPointEntityBuilder serviceDeliveryPointEntity() {
        return new ServiceDeliveryPointEntityBuilder();
    }

    // ServiceDeliveryPointEntity Builder
    public static class ServiceDeliveryPointEntityBuilder {
        private Long id;
        private String resourceId = "test-sdp-1";
        private String description = "Service Delivery Point";

        public ServiceDeliveryPointEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ServiceDeliveryPointEntityBuilder withResourceId(String resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public ServiceDeliveryPointEntityBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ServiceDeliveryPointEntity build() {
            ServiceDeliveryPointEntity entity = new ServiceDeliveryPointEntity();
            entity.setId(id);
            entity.setDescription(description);
            entity.setPublished(DEFAULT_PUBLISHED);
            entity.setUpdated(DEFAULT_UPDATED);
            
            // Generate ESPI-compliant UUID5 based on href
            UUID espiId = ESPI_ID_GENERATOR.generateEspiId("ServiceDeliveryPoint", resourceId);
            entity.setUUID(espiId);
            
            // Set up self link for proper ESPI compliance
            String selfHref = String.format("/espi/1_1/resource/ServiceDeliveryPoint/%s", resourceId);
            entity.setSelfLink(new LinkType("self", selfHref));
            
            return entity;
        }
    }

    // PnodeRefEntity Builder
    public static class PnodeRefEntityBuilder {
        private Long id;
        private String apnodeType = "LOAD";
        private String ref = "PNODE_001";
        private Long startEffectiveDate = DEFAULT_START_TIME;
        private Long endEffectiveDate;
        private UsagePointEntity usagePoint;

        public PnodeRefEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public PnodeRefEntityBuilder withApnodeType(String apnodeType) {
            this.apnodeType = apnodeType;
            return this;
        }

        public PnodeRefEntityBuilder withRef(String ref) {
            this.ref = ref;
            return this;
        }

        public PnodeRefEntityBuilder withStartEffectiveDate(Long startEffectiveDate) {
            this.startEffectiveDate = startEffectiveDate;
            return this;
        }

        public PnodeRefEntityBuilder withEndEffectiveDate(Long endEffectiveDate) {
            this.endEffectiveDate = endEffectiveDate;
            return this;
        }

        public PnodeRefEntityBuilder withUsagePoint(UsagePointEntity usagePoint) {
            this.usagePoint = usagePoint;
            return this;
        }

        public PnodeRefEntity build() {
            PnodeRefEntity entity = new PnodeRefEntity();
            entity.setId(id);
            entity.setApnodeType(apnodeType);
            entity.setRef(ref);
            entity.setStartEffectiveDate(startEffectiveDate);
            entity.setEndEffectiveDate(endEffectiveDate);
            entity.setUsagePoint(usagePoint);
            return entity;
        }
    }

    // AggregatedNodeRefEntity Builder
    public static class AggregatedNodeRefEntityBuilder {
        private Long id;
        private String anodeType = "ZONE";
        private String ref = "ANODE_001";
        private Long startEffectiveDate = DEFAULT_START_TIME;
        private Long endEffectiveDate;
        private PnodeRefEntity pnodeRef;
        private UsagePointEntity usagePoint;

        public AggregatedNodeRefEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public AggregatedNodeRefEntityBuilder withAnodeType(String anodeType) {
            this.anodeType = anodeType;
            return this;
        }

        public AggregatedNodeRefEntityBuilder withRef(String ref) {
            this.ref = ref;
            return this;
        }

        public AggregatedNodeRefEntityBuilder withStartEffectiveDate(Long startEffectiveDate) {
            this.startEffectiveDate = startEffectiveDate;
            return this;
        }

        public AggregatedNodeRefEntityBuilder withEndEffectiveDate(Long endEffectiveDate) {
            this.endEffectiveDate = endEffectiveDate;
            return this;
        }

        public AggregatedNodeRefEntityBuilder withPnodeRef(PnodeRefEntity pnodeRef) {
            this.pnodeRef = pnodeRef;
            return this;
        }

        public AggregatedNodeRefEntityBuilder withUsagePoint(UsagePointEntity usagePoint) {
            this.usagePoint = usagePoint;
            return this;
        }

        public AggregatedNodeRefEntity build() {
            AggregatedNodeRefEntity entity = new AggregatedNodeRefEntity();
            entity.setId(id);
            entity.setAnodeType(anodeType);
            entity.setRef(ref);
            entity.setStartEffectiveDate(startEffectiveDate);
            entity.setEndEffectiveDate(endEffectiveDate);
            entity.setPnodeRef(pnodeRef);
            entity.setUsagePoint(usagePoint);
            return entity;
        }
    }

    // SummaryMeasurement Builder
    public static class SummaryMeasurementBuilder {
        private String powerOfTenMultiplier = "NONE";
        private Long timeStamp;
        private String uom = "WH";
        private Long value = 1000L;
        private String readingTypeRef;

        public SummaryMeasurementBuilder withPowerOfTenMultiplier(String powerOfTenMultiplier) {
            this.powerOfTenMultiplier = powerOfTenMultiplier;
            return this;
        }

        public SummaryMeasurementBuilder withTimeStamp(Long timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public SummaryMeasurementBuilder withUom(String uom) {
            this.uom = uom;
            return this;
        }

        public SummaryMeasurementBuilder withValue(Long value) {
            this.value = value;
            return this;
        }

        public SummaryMeasurementBuilder withReadingTypeRef(String readingTypeRef) {
            this.readingTypeRef = readingTypeRef;
            return this;
        }

        public SummaryMeasurement build() {
            SummaryMeasurement measurement = new SummaryMeasurement();
            measurement.setPowerOfTenMultiplier(powerOfTenMultiplier);
            measurement.setTimeStamp(timeStamp);
            measurement.setUom(uom);
            measurement.setValue(value);
            measurement.setReadingTypeRef(readingTypeRef);
            return measurement;
        }
    }

    /**
     * Creates a DateTimeInterval with ESPI-compliant defaults.
     */
    private static DateTimeInterval createDefaultDateTimeInterval() {
        DateTimeInterval interval = new DateTimeInterval();
        interval.setStart(DEFAULT_START_TIME);
        interval.setDuration(DEFAULT_DURATION);
        return interval;
    }
}