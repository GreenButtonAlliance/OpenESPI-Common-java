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

package org.greenbuttonalliance.espi.common.mapper;

import org.greenbuttonalliance.espi.common.domain.DateTimeInterval;
import org.greenbuttonalliance.espi.common.domain.ServiceCategory;
import org.greenbuttonalliance.espi.common.domain.usage.*;
import org.greenbuttonalliance.espi.common.dto.usage.*;
import org.greenbuttonalliance.espi.common.mapper.usage.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for all MapStruct mappers working together.
 * 
 * Tests the complete Entity-DTO mapping pipeline with full Green Button
 * data structures including all nested relationships.
 */
class MapperIntegrationTest {

    private UsagePointMapper usagePointMapper;

    @BeforeEach
    void setUp() {
        // Initialize the complete mapper chain
        usagePointMapper = Mappers.getMapper(UsagePointMapper.class);
    }

    @Test
    void testCompleteGreenButtonDataStructure_EntityToDto() {
        // Create a complete Green Button data structure
        UsagePointEntity usagePoint = createCompleteGreenButtonStructure();

        // Convert the entire structure to DTO
        UsagePointDto usagePointDto = usagePointMapper.toDto(usagePoint);

        // Verify the complete structure was mapped correctly
        verifyCompleteStructureMapping(usagePointDto);
    }

    @Test
    void testCompleteGreenButtonDataStructure_DtoToEntity() {
        // Create a complete Green Button DTO structure
        UsagePointDto usagePointDto = createCompleteGreenButtonDtoStructure();

        // Convert the entire structure to Entity
        UsagePointEntity usagePointEntity = usagePointMapper.toEntity(usagePointDto);

        // Verify the complete structure was mapped correctly
        verifyCompleteEntityMapping(usagePointEntity);
    }

    @Test
    void testRoundTripMapping_DataIntegrity() {
        // Create original entity
        UsagePointEntity originalEntity = createCompleteGreenButtonStructure();

        // Round trip: Entity -> DTO -> Entity
        UsagePointDto dto = usagePointMapper.toDto(originalEntity);
        UsagePointEntity roundTripEntity = usagePointMapper.toEntity(dto);

        // Verify core data integrity (ignoring JPA-specific fields)
        assertDataIntegrityAfterRoundTrip(originalEntity, roundTripEntity);
    }

    @Test
    void testMapperChain_WithNullValues() {
        // Test null safety throughout the mapper chain
        UsagePointEntity entity = new UsagePointEntity();
        entity.setUuid("null-test-uuid");
        entity.setMeterReadings(null);
        entity.setUsageSummaries(null);
        entity.setElectricPowerQualitySummaries(null);

        // Should not throw exceptions
        UsagePointDto dto = usagePointMapper.toDto(entity);
        UsagePointEntity backToEntity = usagePointMapper.toEntity(dto);

        assertThat(dto.uuid()).isEqualTo("null-test-uuid");
        assertThat(backToEntity.getUuid()).isEqualTo("null-test-uuid");
    }

    @Test
    void testMapperPerformance_LargeDataSet() {
        // Create a large data set for performance testing
        UsagePointEntity largeEntity = createLargeGreenButtonStructure();

        // Time the complete conversion
        long startTime = System.currentTimeMillis();
        UsagePointDto dto = usagePointMapper.toDto(largeEntity);
        long conversionTime = System.currentTimeMillis() - startTime;

        // Verify performance and correctness
        assertThat(dto).isNotNull();
        assertThat(dto.meterReadings()).hasSize(50);
        assertThat(conversionTime).isLessThan(5000); // Should complete in under 5 seconds
        
        // Verify deep structure was mapped
        assertThat(dto.meterReadings().get(0).intervalBlocks()).hasSize(10);
        assertThat(dto.meterReadings().get(0).intervalBlocks().get(0).intervalReadings()).hasSize(50);
    }

    private UsagePointEntity createCompleteGreenButtonStructure() {
        UsagePointEntity usagePoint = new UsagePointEntity();
        usagePoint.setId(1L);
        usagePoint.setUuid("complete-usage-point-uuid");
        usagePoint.setDescription("Complete Green Button Usage Point");
        usagePoint.setServiceCategory(ServiceCategory.ELECTRICITY_SERVICE);
        usagePoint.setStatus((short) 1);
        usagePoint.setPublished(OffsetDateTime.now());
        usagePoint.setUpdated(OffsetDateTime.now());

        // Create meter reading with full structure
        MeterReadingEntity meterReading = createCompleteMeterReading();
        usagePoint.setMeterReadings(Arrays.asList(meterReading));

        // Create usage summary
        UsageSummaryEntity usageSummary = createCompleteUsageSummary();
        usagePoint.setUsageSummaries(Arrays.asList(usageSummary));

        // Create electric power quality summary
        ElectricPowerQualitySummaryEntity epqs = createCompleteElectricPowerQualitySummary();
        usagePoint.setElectricPowerQualitySummaries(Arrays.asList(epqs));

        return usagePoint;
    }

    private MeterReadingEntity createCompleteMeterReading() {
        MeterReadingEntity meterReading = new MeterReadingEntity();
        meterReading.setId(2L);
        meterReading.setUuid("complete-meter-reading-uuid");
        meterReading.setDescription("Complete Meter Reading");
        meterReading.setPublished(OffsetDateTime.now());
        meterReading.setUpdated(OffsetDateTime.now());

        // Create reading type
        ReadingTypeEntity readingType = new ReadingTypeEntity();
        readingType.setId(3L);
        readingType.setUuid("complete-reading-type-uuid");
        readingType.setDescription("Complete Reading Type");
        readingType.setKind(12); // Energy
        readingType.setUom(72); // Wh
        readingType.setPowerOfTenMultiplier(3); // kilo
        meterReading.setReadingType(readingType);

        // Create interval block with readings
        IntervalBlockEntity intervalBlock = createCompleteIntervalBlock();
        meterReading.setIntervalBlocks(Arrays.asList(intervalBlock));

        return meterReading;
    }

    private IntervalBlockEntity createCompleteIntervalBlock() {
        IntervalBlockEntity intervalBlock = new IntervalBlockEntity();
        intervalBlock.setId(4L);
        intervalBlock.setUuid("complete-interval-block-uuid");
        intervalBlock.setDescription("Complete Interval Block");
        
        DateTimeInterval interval = new DateTimeInterval();
        interval.setStart(1609459200L);
        interval.setDuration(3600L);
        intervalBlock.setInterval(interval);

        // Create interval readings with quality
        IntervalReadingEntity reading1 = createCompleteIntervalReading("reading-1", 1000L);
        IntervalReadingEntity reading2 = createCompleteIntervalReading("reading-2", 1200L);
        intervalBlock.setIntervalReadings(Arrays.asList(reading1, reading2));

        return intervalBlock;
    }

    private IntervalReadingEntity createCompleteIntervalReading(String uuidSuffix, Long value) {
        IntervalReadingEntity reading = new IntervalReadingEntity();
        reading.setId(5L);
        reading.setUuid("complete-interval-reading-" + uuidSuffix);
        reading.setValue(value);
        reading.setCost(value / 20); // Cost is 5% of value
        reading.setCurrency(840); // USD
        reading.setConsumptionTier(1);
        reading.setTou(0);
        reading.setCpp(0);
        
        DateTimeInterval timePeriod = new DateTimeInterval();
        timePeriod.setStart(1609459200L);
        timePeriod.setDuration(900L); // 15 minutes
        reading.setTimePeriod(timePeriod);

        // Add reading qualities
        ReadingQualityEntity quality = new ReadingQualityEntity();
        quality.setId(6L);
        quality.setUuid("quality-" + uuidSuffix);
        quality.setQuality("VALID");
        reading.setReadingQualities(Arrays.asList(quality));

        return reading;
    }

    private UsageSummaryEntity createCompleteUsageSummary() {
        UsageSummaryEntity usageSummary = new UsageSummaryEntity();
        usageSummary.setId(7L);
        usageSummary.setUuid("complete-usage-summary-uuid");
        usageSummary.setDescription("Complete Usage Summary");
        usageSummary.setBillToDate(15000L);
        usageSummary.setBillLastPeriod(14000L);
        usageSummary.setCurrency(840); // USD
        
        DateTimeInterval billingPeriod = new DateTimeInterval();
        billingPeriod.setStart(1609459200L);
        billingPeriod.setDuration(2592000L); // 30 days
        usageSummary.setBillingPeriod(billingPeriod);

        return usageSummary;
    }

    private ElectricPowerQualitySummaryEntity createCompleteElectricPowerQualitySummary() {
        ElectricPowerQualitySummaryEntity epqs = new ElectricPowerQualitySummaryEntity();
        epqs.setId(8L);
        epqs.setUuid("complete-epqs-uuid");
        epqs.setDescription("Complete Electric Power Quality Summary");
        epqs.setFlickerPlt(0.5f);
        epqs.setFlickerPst(0.3f);
        epqs.setHarmonicVoltage(2.1f);
        epqs.setLongInterruptions(0L);
        epqs.setMainsVoltage(240.0f);
        epqs.setPowerFrequency(60.0f);
        epqs.setShortInterruptions(1L);

        DateTimeInterval summaryInterval = new DateTimeInterval();
        summaryInterval.setStart(1609459200L);
        summaryInterval.setDuration(86400L); // 24 hours
        epqs.setSummaryInterval(summaryInterval);

        return epqs;
    }

    private UsagePointDto createCompleteGreenButtonDtoStructure() {
        MeterReadingDto meterReading = new MeterReadingDto(
            null, "dto-meter-reading-uuid", OffsetDateTime.now(), OffsetDateTime.now(),
            null, null, null, "DTO Meter Reading", 
            new ReadingTypeDto(), Arrays.asList(new IntervalBlockDto())
        );

        return new UsagePointDto(
            "dto-usage-point-uuid",
            "DTO Usage Point",
            new byte[]{1, 2, 3, 4},
            ServiceCategory.ELECTRICITY_SERVICE,
            (short) 1,
            new ServiceDeliveryPointDto(),
            Arrays.asList(meterReading),
            Arrays.asList(new UsageSummaryDto()),
            Arrays.asList(new ElectricPowerQualitySummaryDto())
        );
    }

    private UsagePointEntity createLargeGreenButtonStructure() {
        UsagePointEntity usagePoint = new UsagePointEntity();
        usagePoint.setUuid("large-structure-uuid");
        usagePoint.setDescription("Large Green Button Structure");
        usagePoint.setServiceCategory(ServiceCategory.ELECTRICITY_SERVICE);

        // Create 50 meter readings, each with 10 interval blocks, each with 50 readings
        MeterReadingEntity[] meterReadings = new MeterReadingEntity[50];
        for (int i = 0; i < 50; i++) {
            meterReadings[i] = createLargeMeterReading("large-meter-" + i);
        }
        usagePoint.setMeterReadings(Arrays.asList(meterReadings));

        return usagePoint;
    }

    private MeterReadingEntity createLargeMeterReading(String uuid) {
        MeterReadingEntity meterReading = new MeterReadingEntity();
        meterReading.setUuid(uuid);
        meterReading.setDescription("Large Meter Reading: " + uuid);

        // Create 10 interval blocks
        IntervalBlockEntity[] blocks = new IntervalBlockEntity[10];
        for (int i = 0; i < 10; i++) {
            blocks[i] = createLargeIntervalBlock(uuid + "-block-" + i);
        }
        meterReading.setIntervalBlocks(Arrays.asList(blocks));

        return meterReading;
    }

    private IntervalBlockEntity createLargeIntervalBlock(String uuid) {
        IntervalBlockEntity block = new IntervalBlockEntity();
        block.setUuid(uuid);
        
        DateTimeInterval interval = new DateTimeInterval();
        interval.setStart(1609459200L);
        interval.setDuration(3600L);
        block.setInterval(interval);

        // Create 50 interval readings
        IntervalReadingEntity[] readings = new IntervalReadingEntity[50];
        for (int i = 0; i < 50; i++) {
            readings[i] = new IntervalReadingEntity();
            readings[i].setUuid(uuid + "-reading-" + i);
            readings[i].setValue(1000L + i);
        }
        block.setIntervalReadings(Arrays.asList(readings));

        return block;
    }

    private void verifyCompleteStructureMapping(UsagePointDto dto) {
        assertThat(dto.uuid()).isEqualTo("complete-usage-point-uuid");
        assertThat(dto.description()).isEqualTo("Complete Green Button Usage Point");
        assertThat(dto.serviceCategory()).isEqualTo(ServiceCategory.ELECTRICITY_SERVICE);

        // Verify meter readings
        assertThat(dto.meterReadings()).hasSize(1);
        MeterReadingDto meterReading = dto.meterReadings().get(0);
        assertThat(meterReading.uuid()).isEqualTo("complete-meter-reading-uuid");

        // Verify nested structure
        assertThat(meterReading.intervalBlocks()).hasSize(1);
        IntervalBlockDto intervalBlock = meterReading.intervalBlocks().get(0);
        assertThat(intervalBlock.intervalReadings()).hasSize(2);

        // Verify usage summaries
        assertThat(dto.usageSummaries()).hasSize(1);
        assertThat(dto.usageSummaries().get(0).billToDate()).isEqualTo(15000L);

        // Verify power quality summaries
        assertThat(dto.electricPowerQualitySummaries()).hasSize(1);
        assertThat(dto.electricPowerQualitySummaries().get(0).flickerPlt()).isEqualTo(0.5f);
    }

    private void verifyCompleteEntityMapping(UsagePointEntity entity) {
        assertThat(entity.getUuid()).isEqualTo("dto-usage-point-uuid");
        assertThat(entity.getDescription()).isEqualTo("DTO Usage Point");
        assertThat(entity.getServiceCategory()).isEqualTo(ServiceCategory.ELECTRICITY_SERVICE);

        // Verify JPA fields are properly ignored
        assertThat(entity.getId()).isNull();
        assertThat(entity.getRelatedLinks()).isNull();

        // Verify collections mapped
        assertThat(entity.getMeterReadings()).hasSize(1);
        assertThat(entity.getUsageSummaries()).hasSize(1);
        assertThat(entity.getElectricPowerQualitySummaries()).hasSize(1);
    }

    private void assertDataIntegrityAfterRoundTrip(UsagePointEntity original, UsagePointEntity roundTrip) {
        // Core fields should match
        assertThat(roundTrip.getUuid()).isEqualTo(original.getUuid());
        assertThat(roundTrip.getDescription()).isEqualTo(original.getDescription());
        assertThat(roundTrip.getServiceCategory()).isEqualTo(original.getServiceCategory());
        assertThat(roundTrip.getStatus()).isEqualTo(original.getStatus());

        // Collection sizes should match
        assertThat(roundTrip.getMeterReadings()).hasSameSizeAs(original.getMeterReadings());
        assertThat(roundTrip.getUsageSummaries()).hasSameSizeAs(original.getUsageSummaries());
        assertThat(roundTrip.getElectricPowerQualitySummaries())
            .hasSameSizeAs(original.getElectricPowerQualitySummaries());

        // JPA IDs should be null (not preserved in DTO round trip)
        assertThat(roundTrip.getId()).isNull();
    }
}