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

package org.greenbuttonalliance.espi.common.domain;

import org.greenbuttonalliance.espi.common.support.EspiFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Modernized JUnit 5 tests for IntervalBlock domain entity.
 * 
 * Tests validation, relationships, and business logic for interval blocks.
 */
@DisplayName("IntervalBlock Tests")
class IntervalBlockTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Nested
    @DisplayName("Entity Validation Tests")
    class ValidationTests {

        @Test
        @DisplayName("Should validate successfully with factory-created interval block")
        void shouldValidateWithFactoryCreatedIntervalBlock() {
            IntervalBlock intervalBlock = EspiFactory.newIntervalBlock();

            Set<ConstraintViolation<IntervalBlock>> violations = validator.validate(intervalBlock);

            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("Should validate with minimal required data")
        void shouldValidateWithMinimalRequiredData() {
            IntervalBlock intervalBlock = new IntervalBlock();
            intervalBlock.setUUID(java.util.UUID.randomUUID());

            Set<ConstraintViolation<IntervalBlock>> violations = validator.validate(intervalBlock);

            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("Should fail validation with null UUID")
        void shouldFailValidationWithNullUuid() {
            IntervalBlock intervalBlock = new IntervalBlock();
            intervalBlock.setUUID(null);

            Set<ConstraintViolation<IntervalBlock>> violations = validator.validate(intervalBlock);

            assertThat(violations).isNotEmpty();
        }
    }

    @Nested
    @DisplayName("Entity Properties Tests")
    class PropertiesTests {

        @Test
        @DisplayName("Should set and get all basic properties correctly")
        void shouldSetAndGetBasicProperties() {
            IntervalBlock intervalBlock = EspiFactory.newIntervalBlock();

            assertThat(intervalBlock.getUUID()).isNotNull();
            assertThat(intervalBlock.getInterval()).isNotNull();
            assertThat(intervalBlock.getPublished()).isNotNull();
            assertThat(intervalBlock.getUpdated()).isNotNull();
        }

        @Test
        @DisplayName("Should handle interval readings collection")
        void shouldHandleIntervalReadingsCollection() {
            IntervalBlock intervalBlock = EspiFactory.newIntervalBlock();

            assertThat(intervalBlock.getIntervalReadings()).isNotNull();
            assertThat(intervalBlock.getIntervalReadings()).hasSize(2); // Factory creates 2 readings
        }

        @Test
        @DisplayName("Should handle date time interval")
        void shouldHandleDateTimeInterval() {
            IntervalBlock intervalBlock = EspiFactory.newIntervalBlock();
            DateTimeInterval interval = intervalBlock.getInterval();

            assertThat(interval).isNotNull();
            assertThat(interval.getDuration()).isEqualTo(86400L); // 1 day as set by factory
            assertThat(interval.getStart()).isEqualTo(1330578000L); // Factory timestamp
        }
    }

    @Nested
    @DisplayName("Relationship Management Tests")
    class RelationshipTests {

        @Test
        @DisplayName("Should manage interval readings relationship correctly")
        void shouldManageIntervalReadingsRelationship() {
            IntervalBlock intervalBlock = new IntervalBlock();
            IntervalReading reading1 = EspiFactory.newIntervalReading();
            IntervalReading reading2 = EspiFactory.newIntervalReading();

            intervalBlock.addIntervalReading(reading1);
            intervalBlock.addIntervalReading(reading2);

            assertThat(intervalBlock.getIntervalReadings()).hasSize(2);
            assertThat(intervalBlock.getIntervalReadings()).contains(reading1, reading2);
        }

        @Test
        @DisplayName("Should initialize empty collections")
        void shouldInitializeEmptyCollections() {
            IntervalBlock intervalBlock = new IntervalBlock();

            assertThat(intervalBlock.getIntervalReadings()).isNotNull().isEmpty();
        }

        @Test
        @DisplayName("Should allow modification of interval readings")
        void shouldAllowModificationOfIntervalReadings() {
            IntervalBlock intervalBlock = new IntervalBlock();
            IntervalReading reading = EspiFactory.newIntervalReading();

            intervalBlock.addIntervalReading(reading);
            assertThat(intervalBlock.getIntervalReadings()).hasSize(1);

            intervalBlock.getIntervalReadings().remove(reading);
            assertThat(intervalBlock.getIntervalReadings()).isEmpty();
        }
    }

    @Nested
    @DisplayName("Business Logic Tests")
    class BusinessLogicTests {

        @Test
        @DisplayName("Should support Green Button data structure requirements")
        void shouldSupportGreenButtonDataStructureRequirements() {
            IntervalBlock intervalBlock = EspiFactory.newIntervalBlock();

            // Verify Green Button compliance
            assertThat(intervalBlock.getUUID()).isNotNull();
            assertThat(intervalBlock.getInterval()).isNotNull();
            assertThat(intervalBlock.getInterval().getStart()).isNotNull();
            assertThat(intervalBlock.getInterval().getDuration()).isNotNull();
            assertThat(intervalBlock.getIntervalReadings()).isNotEmpty();
        }

        @Test
        @DisplayName("Should handle typical smart meter interval scenarios")
        void shouldHandleTypicalSmartMeterIntervalScenarios() {
            IntervalBlock intervalBlock = EspiFactory.newIntervalBlock();
            
            // Verify typical 24-hour interval block
            assertThat(intervalBlock.getInterval().getDuration()).isEqualTo(86400L); // 24 hours
            assertThat(intervalBlock.getIntervalReadings()).hasSize(2); // Sample readings
            
            // Each reading should have meaningful data
            for (IntervalReading reading : intervalBlock.getIntervalReadings()) {
                assertThat(reading.getValue()).isNotNull();
                assertThat(reading.getCost()).isNotNull();
                assertThat(reading.getTimePeriod()).isNotNull();
            }
        }

        @Test
        @DisplayName("Should maintain data integrity across relationships")
        void shouldMaintainDataIntegrityAcrossRelationships() {
            IntervalBlock intervalBlock = EspiFactory.newIntervalBlock();
            
            // Verify that interval readings are properly associated
            for (IntervalReading reading : intervalBlock.getIntervalReadings()) {
                assertThat(reading.getTimePeriod()).isNotNull();
                assertThat(reading.getTimePeriod().getStart()).isNotNull();
                assertThat(reading.getTimePeriod().getDuration()).isNotNull();
                
                // Each reading should fall within the interval block timeframe
                Long blockStart = intervalBlock.getInterval().getStart();
                Long blockEnd = blockStart + intervalBlock.getInterval().getDuration();
                Long readingStart = reading.getTimePeriod().getStart();
                
                assertThat(readingStart).isGreaterThanOrEqualTo(blockStart);
                assertThat(readingStart).isLessThan(blockEnd);
            }
        }
    }

    @Nested
    @DisplayName("Edge Cases and Error Handling")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle null interval gracefully")
        void shouldHandleNullIntervalGracefully() {
            IntervalBlock intervalBlock = new IntervalBlock();
            intervalBlock.setUUID(java.util.UUID.randomUUID());
            intervalBlock.setInterval(null);

            // Should not throw exception
            assertThat(intervalBlock.getInterval()).isNull();
        }

        @Test
        @DisplayName("Should handle empty interval readings collection")
        void shouldHandleEmptyIntervalReadingsCollection() {
            IntervalBlock intervalBlock = new IntervalBlock();
            intervalBlock.setUUID(java.util.UUID.randomUUID());

            assertThat(intervalBlock.getIntervalReadings()).isEmpty();
            
            // Should be able to add readings to empty collection
            IntervalReading reading = EspiFactory.newIntervalReading();
            intervalBlock.addIntervalReading(reading);
            
            assertThat(intervalBlock.getIntervalReadings()).hasSize(1);
        }

        @Test
        @DisplayName("Should handle very large intervals")
        void shouldHandleVeryLargeIntervals() {
            IntervalBlock intervalBlock = new IntervalBlock();
            intervalBlock.setUUID(java.util.UUID.randomUUID());
            
            DateTimeInterval largeInterval = new DateTimeInterval();
            largeInterval.setStart(0L); // Unix epoch
            largeInterval.setDuration(31536000L); // 1 year in seconds
            
            intervalBlock.setInterval(largeInterval);

            assertThat(intervalBlock.getInterval().getStart()).isEqualTo(0L);
            assertThat(intervalBlock.getInterval().getDuration()).isEqualTo(31536000L);
        }
    }

    @Nested
    @DisplayName("Integration with Meter Reading")
    class IntegrationTests {

        @Test
        @DisplayName("Should integrate properly with meter reading")
        void shouldIntegrateProperlyWithMeterReading() {
            MeterReading meterReading = EspiFactory.newMeterReading();
            
            assertThat(meterReading.getIntervalBlocks()).isNotEmpty();
            
            IntervalBlock intervalBlock = meterReading.getIntervalBlocks().get(0);
            assertThat(intervalBlock.getUUID()).isNotNull();
            assertThat(intervalBlock.getInterval()).isNotNull();
            assertThat(intervalBlock.getIntervalReadings()).isNotEmpty();
        }
    }
}