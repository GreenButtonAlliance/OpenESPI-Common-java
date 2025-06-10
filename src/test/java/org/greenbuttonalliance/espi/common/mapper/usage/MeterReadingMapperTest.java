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
 * Test class for MeterReadingMapper testing interval blocks and reading types.
 * 
 * Tests the mapping between MeterReadingEntity and MeterReadingDto including
 * nested interval blocks, interval readings, and reading type relationships.
 */
class MeterReadingMapperTest {

    private MeterReadingMapper meterReadingMapper;
    private IntervalBlockMapper intervalBlockMapper;
    private ReadingTypeMapper readingTypeMapper;
    private IntervalReadingMapper intervalReadingMapper;
    private DateTimeIntervalMapper dateTimeIntervalMapper;

    @BeforeEach
    void setUp() {
        dateTimeIntervalMapper = Mappers.getMapper(DateTimeIntervalMapper.class);
        intervalReadingMapper = Mappers.getMapper(IntervalReadingMapper.class);
        intervalBlockMapper = Mappers.getMapper(IntervalBlockMapper.class);
        readingTypeMapper = Mappers.getMapper(ReadingTypeMapper.class);
        meterReadingMapper = Mappers.getMapper(MeterReadingMapper.class);
    }

    @Test
    void testMeterReadingEntityToDto_WithMultipleIntervalBlocks() {
        // Create entity with multiple interval blocks
        MeterReadingEntity entity = createMeterReadingWithMultipleIntervalBlocks();

        // Convert to DTO
        MeterReadingDto dto = meterReadingMapper.toDto(entity);

        // Verify main properties
        assertThat(dto.uuid()).isEqualTo("meter-reading-test-uuid");
        assertThat(dto.description()).isEqualTo("Multi-Block Meter Reading");

        // Verify multiple interval blocks
        assertThat(dto.intervalBlocks()).hasSize(2);
        
        IntervalBlockDto block1 = dto.intervalBlocks().get(0);
        assertThat(block1.uuid()).isEqualTo("block-1-uuid");
        assertThat(block1.intervalReadings()).hasSize(3);

        IntervalBlockDto block2 = dto.intervalBlocks().get(1);
        assertThat(block2.uuid()).isEqualTo("block-2-uuid");
        assertThat(block2.intervalReadings()).hasSize(2);

        // Verify reading type mapping
        assertThat(dto.readingType()).isNotNull();
        assertThat(dto.readingType().uuid()).isEqualTo("energy-reading-type");
        assertThat(dto.readingType().kind()).isEqualTo(12); // Energy
    }

    @Test
    void testMeterReadingEntityToDto_WithReadingQualities() {
        // Create entity with reading qualities
        MeterReadingEntity entity = createMeterReadingWithReadingQualities();

        // Convert to DTO
        MeterReadingDto dto = meterReadingMapper.toDto(entity);

        // Verify interval readings have quality information
        assertThat(dto.intervalBlocks()).hasSize(1);
        IntervalBlockDto blockDto = dto.intervalBlocks().get(0);
        assertThat(blockDto.intervalReadings()).hasSize(1);
        
        IntervalReadingDto readingDto = blockDto.intervalReadings().get(0);
        assertThat(readingDto.readingQualities()).hasSize(2);
        assertThat(readingDto.readingQualities().get(0).quality()).isEqualTo("VALID");
        assertThat(readingDto.readingQualities().get(1).quality()).isEqualTo("ESTIMATED");
    }

    @Test
    void testMeterReadingDtoToEntity_UpdateExistingEntity() {
        // Create original entity
        MeterReadingEntity originalEntity = createBasicMeterReadingEntity();
        originalEntity.setId(100L); // Existing ID

        // Create updated DTO
        MeterReadingDto updatedDto = new MeterReadingDto(
            100L, "meter-reading-test-uuid", 
            OffsetDateTime.now(), OffsetDateTime.now(),
            null, null, null,
            "Updated Description", 
            createBasicReadingTypeDto(), 
            Arrays.asList(createBasicIntervalBlockDto())
        );

        // Update entity with DTO data
        meterReadingMapper.updateEntity(updatedDto, originalEntity);

        // Verify that ID is preserved but other fields are updated
        assertThat(originalEntity.getId()).isEqualTo(100L);
        assertThat(originalEntity.getDescription()).isEqualTo("Updated Description");
        assertThat(originalEntity.getUuid()).isEqualTo("meter-reading-test-uuid");
    }

    @Test
    void testNullSafeMapping() {
        // Test with null collections
        MeterReadingEntity entity = new MeterReadingEntity();
        entity.setUuid("null-safe-test");
        entity.setDescription("Null Safe Test");
        entity.setIntervalBlocks(null);
        entity.setReadingType(null);

        // Should not throw exception
        MeterReadingDto dto = meterReadingMapper.toDto(entity);

        assertThat(dto.uuid()).isEqualTo("null-safe-test");
        assertThat(dto.description()).isEqualTo("Null Safe Test");
        assertThat(dto.intervalBlocks()).isNull();
        assertThat(dto.readingType()).isNull();
    }

    @Test
    void testComplexNestedStructure_Performance() {
        // Create a large nested structure for performance testing
        MeterReadingEntity entity = createLargeNestedStructure();

        // Time the conversion (basic performance check)
        long startTime = System.currentTimeMillis();
        MeterReadingDto dto = meterReadingMapper.toDto(entity);
        long conversionTime = System.currentTimeMillis() - startTime;

        // Verify conversion completed successfully
        assertThat(dto).isNotNull();
        assertThat(dto.intervalBlocks()).hasSize(10);
        assertThat(conversionTime).isLessThan(1000); // Should complete in under 1 second
        
        // Verify first and last blocks to ensure deep mapping worked
        assertThat(dto.intervalBlocks().get(0).intervalReadings()).hasSize(100);
        assertThat(dto.intervalBlocks().get(9).intervalReadings()).hasSize(100);
    }

    private MeterReadingEntity createMeterReadingWithMultipleIntervalBlocks() {
        MeterReadingEntity entity = new MeterReadingEntity();
        entity.setId(1L);
        entity.setUuid("meter-reading-test-uuid");
        entity.setDescription("Multi-Block Meter Reading");
        entity.setPublished(OffsetDateTime.now());
        entity.setUpdated(OffsetDateTime.now());

        // Create reading type
        ReadingTypeEntity readingType = new ReadingTypeEntity();
        readingType.setUuid("energy-reading-type");
        readingType.setKind(12); // Energy
        readingType.setDescription("kWh Energy");
        entity.setReadingType(readingType);

        // Create first interval block
        IntervalBlockEntity block1 = createIntervalBlock("block-1-uuid", 3);
        IntervalBlockEntity block2 = createIntervalBlock("block-2-uuid", 2);

        entity.setIntervalBlocks(Arrays.asList(block1, block2));
        return entity;
    }

    private MeterReadingEntity createMeterReadingWithReadingQualities() {
        MeterReadingEntity entity = createBasicMeterReadingEntity();
        
        // Add reading qualities to interval reading
        IntervalBlockEntity block = entity.getIntervalBlocks().get(0);
        IntervalReadingEntity reading = block.getIntervalReadings().get(0);
        
        ReadingQualityEntity quality1 = new ReadingQualityEntity();
        quality1.setUuid("quality-1-uuid");
        quality1.setQuality("VALID");
        
        ReadingQualityEntity quality2 = new ReadingQualityEntity();
        quality2.setUuid("quality-2-uuid");
        quality2.setQuality("ESTIMATED");
        
        reading.setReadingQualities(Arrays.asList(quality1, quality2));
        return entity;
    }

    private MeterReadingEntity createBasicMeterReadingEntity() {
        MeterReadingEntity entity = new MeterReadingEntity();
        entity.setId(1L);
        entity.setUuid("basic-meter-reading");
        entity.setDescription("Basic Meter Reading");
        
        IntervalBlockEntity block = createIntervalBlock("basic-block", 1);
        entity.setIntervalBlocks(Arrays.asList(block));
        
        return entity;
    }

    private IntervalBlockEntity createIntervalBlock(String uuid, int readingCount) {
        IntervalBlockEntity block = new IntervalBlockEntity();
        block.setUuid(uuid);
        block.setDescription("Test Block: " + uuid);
        
        DateTimeInterval interval = new DateTimeInterval();
        interval.setStart(1609459200L);
        interval.setDuration(3600L);
        block.setInterval(interval);

        // Create interval readings
        List<IntervalReadingEntity> readings = Arrays.asList(new IntervalReadingEntity[readingCount]);
        for (int i = 0; i < readingCount; i++) {
            IntervalReadingEntity reading = new IntervalReadingEntity();
            reading.setUuid(uuid + "-reading-" + i);
            reading.setValue(1000L + (i * 100));
            reading.setCost(50L + (i * 5));
            readings.set(i, reading);
        }
        
        block.setIntervalReadings(readings);
        return block;
    }

    private MeterReadingEntity createLargeNestedStructure() {
        MeterReadingEntity entity = new MeterReadingEntity();
        entity.setUuid("large-structure-test");
        entity.setDescription("Large Nested Structure");

        // Create 10 interval blocks, each with 100 readings
        List<IntervalBlockEntity> blocks = Arrays.asList(new IntervalBlockEntity[10]);
        for (int i = 0; i < 10; i++) {
            blocks.set(i, createIntervalBlock("large-block-" + i, 100));
        }
        
        entity.setIntervalBlocks(blocks);
        return entity;
    }

    private ReadingTypeDto createBasicReadingTypeDto() {
        return new ReadingTypeDto();
    }

    private IntervalBlockDto createBasicIntervalBlockDto() {
        return new IntervalBlockDto();
    }
}