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

package org.greenbuttonalliance.espi.common.domain.usage;

import org.greenbuttonalliance.espi.common.support.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.OffsetDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.greenbuttonalliance.espi.common.support.TestDataBuilder.*;

/**
 * Comprehensive JUnit 5 tests for MeterReadingEntity.
 * 
 * Tests entity validation, relationships, business logic, and data integrity.
 */
@DisplayName("MeterReadingEntity Tests")
class MeterReadingEntityTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Nested
    @DisplayName("Entity Validation Tests")
    class ValidationTests {

        @Test
        @DisplayName("Should validate successfully with required fields")
        void shouldValidateWithRequiredFields() {
            MeterReadingEntity entity = meterReadingEntity()
                .withUuid("12345678-1234-1234-1234-123456789012")
                .withDescription("Valid Meter Reading")
                .build();

            Set<ConstraintViolation<MeterReadingEntity>> violations = validator.validate(entity);

            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("Should fail validation with null UUID")
        void shouldFailValidationWithNullUuid() {
            MeterReadingEntity entity = meterReadingEntity()
                .withUuid(null)
                .build();

            Set<ConstraintViolation<MeterReadingEntity>> violations = validator.validate(entity);

            assertThat(violations).isNotEmpty();
        }

        @Test
        @DisplayName("Should validate with minimal required data")
        void shouldValidateWithMinimalData() {
            MeterReadingEntity entity = new MeterReadingEntity();
            entity.setUuid("12345678-1234-1234-1234-123456789012");
            entity.setDescription("Minimal Meter Reading");

            Set<ConstraintViolation<MeterReadingEntity>> violations = validator.validate(entity);

            assertThat(violations).isEmpty();
        }
    }

    @Nested
    @DisplayName("Entity Properties Tests")
    class PropertiesTests {

        @Test
        @DisplayName("Should set and get all basic properties correctly")
        void shouldSetAndGetBasicProperties() {
            String uuid = "12345678-1234-1234-1234-123456789012";
            String description = "Test Meter Reading";
            OffsetDateTime published = OffsetDateTime.now().minusDays(1);
            OffsetDateTime updated = OffsetDateTime.now();

            MeterReadingEntity entity = meterReadingEntity()
                .withId(100L)
                .withUuid(uuid)
                .withDescription(description)
                .build();
            
            entity.setPublished(published);
            entity.setUpdated(updated);

            assertThat(entity.getId()).isEqualTo(100L);
            assertThat(entity.getUuid()).isEqualTo(uuid);
            assertThat(entity.getDescription()).isEqualTo(description);
            assertThat(entity.getPublished()).isEqualTo(published);
            assertThat(entity.getUpdated()).isEqualTo(updated);
        }

        @Test
        @DisplayName("Should handle null optional properties")
        void shouldHandleNullOptionalProperties() {
            MeterReadingEntity entity = meterReadingEntity().build();
            entity.setPublished(null);
            entity.setUpdated(null);
            entity.setReadingType(null);

            assertThat(entity.getPublished()).isNull();
            assertThat(entity.getUpdated()).isNull();
            assertThat(entity.getReadingType()).isNull();
        }
    }

    @Nested
    @DisplayName("Reading Type Relationship Tests")
    class ReadingTypeRelationshipTests {

        @Test
        @DisplayName("Should manage reading type relationship")
        void shouldManageReadingTypeRelationship() {
            ReadingTypeEntity readingType = readingTypeEntity()
                .withUuid("reading-type-uuid")
                .withDescription("kWh Energy Reading")
                .withKind(12) // Energy
                .build();

            MeterReadingEntity entity = meterReadingEntity()
                .withReadingType(readingType)
                .build();

            assertThat(entity.getReadingType()).isEqualTo(readingType);
            assertThat(entity.getReadingType().getUuid()).isEqualTo("reading-type-uuid");
            assertThat(entity.getReadingType().getKind()).isEqualTo(12);
        }

        @Test
        @DisplayName("Should allow null reading type")
        void shouldAllowNullReadingType() {
            MeterReadingEntity entity = meterReadingEntity()
                .withReadingType(null)
                .build();

            assertThat(entity.getReadingType()).isNull();
        }

        @Test
        @DisplayName("Should allow changing reading type")
        void shouldAllowChangingReadingType() {
            ReadingTypeEntity originalType = readingTypeEntity()
                .withKind(12) // Energy
                .build();
            
            ReadingTypeEntity newType = readingTypeEntity()
                .withKind(38) // Power
                .build();

            MeterReadingEntity entity = meterReadingEntity()
                .withReadingType(originalType)
                .build();

            assertThat(entity.getReadingType()).isEqualTo(originalType);

            entity.setReadingType(newType);
            assertThat(entity.getReadingType()).isEqualTo(newType);
        }
    }

    @Nested
    @DisplayName("Interval Blocks Relationship Tests")
    class IntervalBlocksRelationshipTests {

        @Test
        @DisplayName("Should manage interval blocks collection")
        void shouldManageIntervalBlocksCollection() {
            IntervalBlockEntity block1 = intervalBlockEntity()
                .withUuid("block-1-uuid")
                .withDescription("Block 1")
                .build();
            
            IntervalBlockEntity block2 = intervalBlockEntity()
                .withUuid("block-2-uuid")
                .withDescription("Block 2")
                .build();

            MeterReadingEntity entity = meterReadingEntity()
                .withIntervalBlock(block1)
                .withIntervalBlock(block2)
                .build();

            assertThat(entity.getIntervalBlocks()).hasSize(2);
            assertThat(entity.getIntervalBlocks()).containsExactly(block1, block2);
        }

        @Test
        @DisplayName("Should initialize empty interval blocks collection")
        void shouldInitializeEmptyIntervalBlocksCollection() {
            MeterReadingEntity entity = new MeterReadingEntity();

            assertThat(entity.getIntervalBlocks()).isNotNull().isEmpty();
        }

        @Test
        @DisplayName("Should allow modification of interval blocks collection")
        void shouldAllowModificationOfIntervalBlocksCollection() {
            IntervalBlockEntity block = intervalBlockEntity().build();

            MeterReadingEntity entity = meterReadingEntity().build();
            entity.getIntervalBlocks().add(block);

            assertThat(entity.getIntervalBlocks()).hasSize(1);
            assertThat(entity.getIntervalBlocks()).contains(block);

            entity.getIntervalBlocks().remove(block);
            assertThat(entity.getIntervalBlocks()).isEmpty();
        }

        @Test
        @DisplayName("Should handle multiple interval blocks with complex data")
        void shouldHandleMultipleIntervalBlocksWithComplexData() {
            // Create interval readings for the blocks
            IntervalReadingEntity reading1 = intervalReadingEntity()
                .withValue(1000L)
                .withCost(50L)
                .build();
            
            IntervalReadingEntity reading2 = intervalReadingEntity()
                .withValue(1200L)
                .withCost(60L)
                .build();

            IntervalBlockEntity block1 = intervalBlockEntity()
                .withIntervalReading(reading1)
                .build();
            
            IntervalBlockEntity block2 = intervalBlockEntity()
                .withIntervalReading(reading2)
                .build();

            MeterReadingEntity entity = meterReadingEntity()
                .withIntervalBlock(block1)
                .withIntervalBlock(block2)
                .build();

            assertThat(entity.getIntervalBlocks()).hasSize(2);
            
            // Verify nested data integrity
            assertThat(entity.getIntervalBlocks().get(0).getIntervalReadings()).hasSize(1);
            assertThat(entity.getIntervalBlocks().get(1).getIntervalReadings()).hasSize(1);
            
            assertThat(entity.getIntervalBlocks().get(0).getIntervalReadings().get(0).getValue())
                .isEqualTo(1000L);
            assertThat(entity.getIntervalBlocks().get(1).getIntervalReadings().get(0).getValue())
                .isEqualTo(1200L);
        }
    }

    @Nested
    @DisplayName("Business Logic Tests")
    class BusinessLogicTests {

        @Test
        @DisplayName("Should maintain data consistency with reading type and interval blocks")
        void shouldMaintainDataConsistencyWithReadingTypeAndIntervalBlocks() {
            ReadingTypeEntity readingType = readingTypeEntity()
                .withKind(12) // Energy
                .withUom(72) // Wh
                .build();

            IntervalReadingEntity reading = intervalReadingEntity()
                .withValue(1000L) // 1000 Wh
                .build();

            IntervalBlockEntity block = intervalBlockEntity()
                .withIntervalReading(reading)
                .build();

            MeterReadingEntity entity = meterReadingEntity()
                .withReadingType(readingType)
                .withIntervalBlock(block)
                .build();

            // Verify that reading type and interval data are consistent
            assertThat(entity.getReadingType().getKind()).isEqualTo(12); // Energy
            assertThat(entity.getReadingType().getUom()).isEqualTo(72); // Wh
            assertThat(entity.getIntervalBlocks().get(0).getIntervalReadings().get(0).getValue())
                .isEqualTo(1000L); // 1000 Wh
        }

        @Test
        @DisplayName("Should support Green Button compliance requirements")
        void shouldSupportGreenButtonComplianceRequirements() {
            // Green Button requires specific UUID format
            String greenButtonUuid = "12345678-1234-1234-1234-123456789012";
            
            // Create ESPI-compliant reading type
            ReadingTypeEntity readingType = readingTypeEntity()
                .withKind(12) // Energy
                .withUom(72) // Wh
                .withDescription("Energy Delivered (kWh)")
                .build();

            MeterReadingEntity entity = meterReadingEntity()
                .withUuid(greenButtonUuid)
                .withDescription("Electricity consumption")
                .withReadingType(readingType)
                .build();

            // Verify ESPI compliance
            assertThat(entity.getUuid()).matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
            assertThat(entity.getDescription()).isNotBlank();
            assertThat(entity.getReadingType()).isNotNull();
            assertThat(entity.getReadingType().getKind()).isNotNull();
        }
    }

    @Nested
    @DisplayName("Edge Cases and Error Handling")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle empty collections gracefully")
        void shouldHandleEmptyCollectionsGracefully() {
            MeterReadingEntity entity = meterReadingEntity().build();

            assertThat(entity.getIntervalBlocks()).isEmpty();
            
            // Should be able to add to empty collections
            IntervalBlockEntity block = intervalBlockEntity().build();
            entity.getIntervalBlocks().add(block);
            
            assertThat(entity.getIntervalBlocks()).hasSize(1);
        }

        @Test
        @DisplayName("Should handle very long descriptions")
        void shouldHandleVeryLongDescriptions() {
            String longDescription = "This is a very long description that might exceed normal database field limits. ".repeat(10);
            
            MeterReadingEntity entity = meterReadingEntity()
                .withDescription(longDescription)
                .build();

            assertThat(entity.getDescription()).isEqualTo(longDescription);
        }

        @Test
        @DisplayName("Should handle special characters in description")
        void shouldHandleSpecialCharactersInDescription() {
            String specialDescription = "Meter Reading with <XML>, \"quotes\", & ampersands, 'apostrophes', émojis: ⚡";
            
            MeterReadingEntity entity = meterReadingEntity()
                .withDescription(specialDescription)
                .build();

            assertThat(entity.getDescription()).isEqualTo(specialDescription);
        }

        @Test
        @DisplayName("Should handle timestamp edge cases")
        void shouldHandleTimestampEdgeCases() {
            OffsetDateTime veryOldDate = OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, OffsetDateTime.now().getOffset());
            OffsetDateTime futureDate = OffsetDateTime.of(2099, 12, 31, 23, 59, 59, 0, OffsetDateTime.now().getOffset());

            MeterReadingEntity entity = meterReadingEntity().build();
            entity.setPublished(veryOldDate);
            entity.setUpdated(futureDate);

            assertThat(entity.getPublished()).isEqualTo(veryOldDate);
            assertThat(entity.getUpdated()).isEqualTo(futureDate);
        }
    }

    @Nested
    @DisplayName("Integration with Other Entities")
    class IntegrationTests {

        @Test
        @DisplayName("Should integrate properly with complete usage point hierarchy")
        void shouldIntegrateWithCompleteUsagePointHierarchy() {
            // Create a complete meter reading as part of usage point
            ReadingTypeEntity readingType = readingTypeEntity().build();
            
            IntervalReadingEntity reading = intervalReadingEntity().build();
            IntervalBlockEntity block = intervalBlockEntity()
                .withIntervalReading(reading)
                .build();
            
            MeterReadingEntity meterReading = meterReadingEntity()
                .withReadingType(readingType)
                .withIntervalBlock(block)
                .build();

            UsagePointEntity usagePoint = usagePointEntity()
                .withMeterReading(meterReading)
                .build();

            // Verify complete integration
            assertThat(usagePoint.getMeterReadings()).hasSize(1);
            assertThat(usagePoint.getMeterReadings().get(0)).isEqualTo(meterReading);
            assertThat(usagePoint.getMeterReadings().get(0).getReadingType()).isEqualTo(readingType);
            assertThat(usagePoint.getMeterReadings().get(0).getIntervalBlocks().get(0)).isEqualTo(block);
            assertThat(usagePoint.getMeterReadings().get(0).getIntervalBlocks().get(0).getIntervalReadings().get(0)).isEqualTo(reading);
        }
    }
}