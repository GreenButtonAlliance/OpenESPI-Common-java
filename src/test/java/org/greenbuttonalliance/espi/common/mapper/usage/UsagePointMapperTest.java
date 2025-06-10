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

package org.greenbuttonalliance.espi.common.mapper.usage;

import org.greenbuttonalliance.espi.common.domain.DateTimeInterval;
import org.greenbuttonalliance.espi.common.domain.ServiceCategory;
import org.greenbuttonalliance.espi.common.domain.usage.*;
import org.greenbuttonalliance.espi.common.dto.usage.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for UsagePointMapper testing complex nested relationships.
 * 
 * Tests the mapping between UsagePointEntity and UsagePointDto including
 * all related entities like meter readings, interval blocks, and usage summaries.
 */
class UsagePointMapperTest {

    private UsagePointMapper usagePointMapper;
    private MeterReadingMapper meterReadingMapper;
    private IntervalBlockMapper intervalBlockMapper;
    private IntervalReadingMapper intervalReadingMapper;
    private ReadingTypeMapper readingTypeMapper;
    private UsageSummaryMapper usageSummaryMapper;
    private ElectricPowerQualitySummaryMapper electricPowerQualitySummaryMapper;
    private ServiceDeliveryPointMapper serviceDeliveryPointMapper;
    private DateTimeIntervalMapper dateTimeIntervalMapper;

    @BeforeEach
    void setUp() {
        // Initialize all mappers
        dateTimeIntervalMapper = Mappers.getMapper(DateTimeIntervalMapper.class);
        readingTypeMapper = Mappers.getMapper(ReadingTypeMapper.class);
        intervalReadingMapper = Mappers.getMapper(IntervalReadingMapper.class);
        intervalBlockMapper = Mappers.getMapper(IntervalBlockMapper.class);
        meterReadingMapper = Mappers.getMapper(MeterReadingMapper.class);
        usageSummaryMapper = Mappers.getMapper(UsageSummaryMapper.class);
        electricPowerQualitySummaryMapper = Mappers.getMapper(ElectricPowerQualitySummaryMapper.class);
        serviceDeliveryPointMapper = Mappers.getMapper(ServiceDeliveryPointMapper.class);
        usagePointMapper = Mappers.getMapper(UsagePointMapper.class);
    }

    @Test
    void testUsagePointEntityToDto_WithComplexNestedRelationships() {
        // Create a complex UsagePointEntity with nested relationships
        UsagePointEntity entity = createComplexUsagePointEntity();

        // Convert to DTO
        UsagePointDto dto = usagePointMapper.toDto(entity);

        // Verify main properties
        assertThat(dto.uuid()).isEqualTo("12345678-1234-1234-1234-123456789012");
        assertThat(dto.description()).isEqualTo("Test Usage Point");
        assertThat(dto.serviceCategory()).isEqualTo(ServiceCategory.ELECTRICITY_SERVICE);
        assertThat(dto.status()).isEqualTo((short) 1);

        // Verify meter readings are mapped
        assertThat(dto.meterReadings()).hasSize(1);
        MeterReadingDto meterReadingDto = dto.meterReadings().get(0);
        assertThat(meterReadingDto.uuid()).isEqualTo("meter-reading-uuid");
        assertThat(meterReadingDto.description()).isEqualTo("Test Meter Reading");

        // Verify nested interval blocks
        assertThat(meterReadingDto.intervalBlocks()).hasSize(1);
        IntervalBlockDto intervalBlockDto = meterReadingDto.intervalBlocks().get(0);
        assertThat(intervalBlockDto.uuid()).isEqualTo("interval-block-uuid");

        // Verify deeply nested interval readings
        assertThat(intervalBlockDto.intervalReadings()).hasSize(2);
        IntervalReadingDto intervalReading1 = intervalBlockDto.intervalReadings().get(0);
        assertThat(intervalReading1.uuid()).isEqualTo("interval-reading-1-uuid");
        assertThat(intervalReading1.value()).isEqualTo(1000L);
        assertThat(intervalReading1.cost()).isEqualTo(50L);

        IntervalReadingDto intervalReading2 = intervalBlockDto.intervalReadings().get(1);
        assertThat(intervalReading2.uuid()).isEqualTo("interval-reading-2-uuid");
        assertThat(intervalReading2.value()).isEqualTo(1200L);
        assertThat(intervalReading2.cost()).isEqualTo(60L);

        // Verify reading type mapping
        assertThat(meterReadingDto.readingType()).isNotNull();
        assertThat(meterReadingDto.readingType().uuid()).isEqualTo("reading-type-uuid");

        // Verify usage summaries are mapped
        assertThat(dto.usageSummaries()).hasSize(1);
        UsageSummaryDto usageSummaryDto = dto.usageSummaries().get(0);
        assertThat(usageSummaryDto.uuid()).isEqualTo("usage-summary-uuid");
        assertThat(usageSummaryDto.billToDate()).isEqualTo(15000L);

        // Verify electric power quality summaries
        assertThat(dto.electricPowerQualitySummaries()).hasSize(1);
        ElectricPowerQualitySummaryDto epqsDto = dto.electricPowerQualitySummaries().get(0);
        assertThat(epqsDto.uuid()).isEqualTo("epqs-uuid");
        assertThat(epqsDto.flickerPlt()).isEqualTo(0.5f);
    }

    @Test
    void testUsagePointDtoToEntity_WithComplexNestedRelationships() {
        // Create a complex UsagePointDto
        UsagePointDto dto = createComplexUsagePointDto();

        // Convert to Entity
        UsagePointEntity entity = usagePointMapper.toEntity(dto);

        // Verify main properties
        assertThat(entity.getUuid()).isEqualTo("12345678-1234-1234-1234-123456789012");
        assertThat(entity.getDescription()).isEqualTo("Test Usage Point");
        assertThat(entity.getServiceCategory()).isEqualTo(ServiceCategory.ELECTRICITY_SERVICE);
        assertThat(entity.getStatus()).isEqualTo((short) 1);

        // Verify that JPA-specific fields are ignored
        assertThat(entity.getId()).isNull();
        assertThat(entity.getRelatedLinks()).isNull();

        // Verify meter readings conversion
        assertThat(entity.getMeterReadings()).hasSize(1);
        MeterReadingEntity meterReadingEntity = entity.getMeterReadings().get(0);
        assertThat(meterReadingEntity.getUuid()).isEqualTo("meter-reading-uuid");

        // Verify nested interval blocks conversion
        assertThat(meterReadingEntity.getIntervalBlocks()).hasSize(1);
        IntervalBlockEntity intervalBlockEntity = meterReadingEntity.getIntervalBlocks().get(0);
        assertThat(intervalBlockEntity.getUuid()).isEqualTo("interval-block-uuid");

        // Verify deeply nested interval readings conversion
        assertThat(intervalBlockEntity.getIntervalReadings()).hasSize(2);
        IntervalReadingEntity intervalReading1 = intervalBlockEntity.getIntervalReadings().get(0);
        assertThat(intervalReading1.getUuid()).isEqualTo("interval-reading-1-uuid");
        assertThat(intervalReading1.getValue()).isEqualTo(1000L);
    }

    @Test
    void testBidirectionalMapping_PreservesDataIntegrity() {
        // Create original entity
        UsagePointEntity originalEntity = createComplexUsagePointEntity();

        // Convert to DTO and back to entity
        UsagePointDto dto = usagePointMapper.toDto(originalEntity);
        UsagePointEntity convertedEntity = usagePointMapper.toEntity(dto);

        // Verify core data integrity (ignoring JPA-specific fields)
        assertThat(convertedEntity.getUuid()).isEqualTo(originalEntity.getUuid());
        assertThat(convertedEntity.getDescription()).isEqualTo(originalEntity.getDescription());
        assertThat(convertedEntity.getServiceCategory()).isEqualTo(originalEntity.getServiceCategory());
        assertThat(convertedEntity.getStatus()).isEqualTo(originalEntity.getStatus());

        // Verify nested data integrity
        assertThat(convertedEntity.getMeterReadings()).hasSize(originalEntity.getMeterReadings().size());
        assertThat(convertedEntity.getUsageSummaries()).hasSize(originalEntity.getUsageSummaries().size());
        assertThat(convertedEntity.getElectricPowerQualitySummaries())
            .hasSize(originalEntity.getElectricPowerQualitySummaries().size());
    }

    private UsagePointEntity createComplexUsagePointEntity() {
        UsagePointEntity entity = new UsagePointEntity();
        entity.setId(1L);
        entity.setUuid("12345678-1234-1234-1234-123456789012");
        entity.setDescription("Test Usage Point");
        entity.setServiceCategory(ServiceCategory.ELECTRICITY_SERVICE);
        entity.setStatus((short) 1);
        entity.setPublished(OffsetDateTime.now());
        entity.setUpdated(OffsetDateTime.now());

        // Create meter reading with nested relationships
        MeterReadingEntity meterReading = createMeterReadingEntity();
        entity.setMeterReadings(Arrays.asList(meterReading));

        // Create usage summary
        UsageSummaryEntity usageSummary = createUsageSummaryEntity();
        entity.setUsageSummaries(Arrays.asList(usageSummary));

        // Create electric power quality summary
        ElectricPowerQualitySummaryEntity epqs = createElectricPowerQualitySummaryEntity();
        entity.setElectricPowerQualitySummaries(Arrays.asList(epqs));

        return entity;
    }

    private MeterReadingEntity createMeterReadingEntity() {
        MeterReadingEntity entity = new MeterReadingEntity();
        entity.setId(2L);
        entity.setUuid("meter-reading-uuid");
        entity.setDescription("Test Meter Reading");
        entity.setPublished(OffsetDateTime.now());
        entity.setUpdated(OffsetDateTime.now());

        // Create reading type
        ReadingTypeEntity readingType = createReadingTypeEntity();
        entity.setReadingType(readingType);

        // Create interval block with readings
        IntervalBlockEntity intervalBlock = createIntervalBlockEntity();
        entity.setIntervalBlocks(Arrays.asList(intervalBlock));

        return entity;
    }

    private IntervalBlockEntity createIntervalBlockEntity() {
        IntervalBlockEntity entity = new IntervalBlockEntity();
        entity.setId(3L);
        entity.setUuid("interval-block-uuid");
        entity.setDescription("Test Interval Block");
        
        DateTimeInterval interval = new DateTimeInterval();
        interval.setStart(1609459200L); // 2021-01-01
        interval.setDuration(3600L); // 1 hour
        entity.setInterval(interval);

        // Create interval readings
        IntervalReadingEntity reading1 = createIntervalReadingEntity("interval-reading-1-uuid", 1000L, 50L);
        IntervalReadingEntity reading2 = createIntervalReadingEntity("interval-reading-2-uuid", 1200L, 60L);
        entity.setIntervalReadings(Arrays.asList(reading1, reading2));

        return entity;
    }

    private IntervalReadingEntity createIntervalReadingEntity(String uuid, Long value, Long cost) {
        IntervalReadingEntity entity = new IntervalReadingEntity();
        entity.setUuid(uuid);
        entity.setValue(value);
        entity.setCost(cost);
        entity.setCurrency(840); // USD
        
        DateTimeInterval timePeriod = new DateTimeInterval();
        timePeriod.setStart(1609459200L);
        timePeriod.setDuration(900L); // 15 minutes
        entity.setTimePeriod(timePeriod);

        return entity;
    }

    private ReadingTypeEntity createReadingTypeEntity() {
        ReadingTypeEntity entity = new ReadingTypeEntity();
        entity.setId(4L);
        entity.setUuid("reading-type-uuid");
        entity.setDescription("kWh Energy Reading");
        entity.setKind(12); // Energy
        entity.setUom(72); // Wh
        entity.setPowerOfTenMultiplier(3); // kilo
        return entity;
    }

    private UsageSummaryEntity createUsageSummaryEntity() {
        UsageSummaryEntity entity = new UsageSummaryEntity();
        entity.setId(5L);
        entity.setUuid("usage-summary-uuid");
        entity.setDescription("Monthly Usage Summary");
        entity.setBillToDate(15000L);
        entity.setCurrency(840); // USD
        return entity;
    }

    private ElectricPowerQualitySummaryEntity createElectricPowerQualitySummaryEntity() {
        ElectricPowerQualitySummaryEntity entity = new ElectricPowerQualitySummaryEntity();
        entity.setId(6L);
        entity.setUuid("epqs-uuid");
        entity.setDescription("Power Quality Summary");
        entity.setFlickerPlt(0.5f);
        entity.setFlickerPst(0.3f);
        return entity;
    }

    private UsagePointDto createComplexUsagePointDto() {
        MeterReadingDto meterReading = new MeterReadingDto(
            null, "meter-reading-uuid", null, null, null, null, null, "Test Meter Reading",
            new ReadingTypeDto(), Arrays.asList(new IntervalBlockDto())
        );

        return new UsagePointDto(
            "12345678-1234-1234-1234-123456789012",
            "Test Usage Point",
            null, // roleFlags
            ServiceCategory.ELECTRICITY_SERVICE,
            (short) 1,
            null, // serviceDeliveryPoint
            Arrays.asList(meterReading),
            Arrays.asList(new UsageSummaryDto()),
            Arrays.asList(new ElectricPowerQualitySummaryDto())
        );
    }
}