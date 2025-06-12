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

import org.greenbuttonalliance.espi.common.domain.DateTimeInterval;
import org.greenbuttonalliance.espi.common.support.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.greenbuttonalliance.espi.common.support.TestDataBuilder.*;

/**
 * Comprehensive JUnit 5 tests for IntervalReadingEntity.
 * 
 * Tests entity validation, relationships, business logic, and Green Button compliance.
 */
@DisplayName("IntervalReadingEntity Tests")
class IntervalReadingEntityTest {

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
            IntervalReadingEntity entity = intervalReadingEntity()
                .withUuid("12345678-1234-1234-1234-123456789012")
                .withValue(1000L)
                .build();

            Set<ConstraintViolation<IntervalReadingEntity>> violations = validator.validate(entity);

            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("Should fail validation with null UUID")
        void shouldFailValidationWithNullUuid() {
            IntervalReadingEntity entity = intervalReadingEntity()
                .withUuid(null)
                .build();

            Set<ConstraintViolation<IntervalReadingEntity>> violations = validator.validate(entity);

            assertThat(violations).isNotEmpty();
        }

        @Test
        @DisplayName("Should validate with minimal required data")
        void shouldValidateWithMinimalData() {
            IntervalReadingEntity entity = new IntervalReadingEntity();
            entity.setUuid("12345678-1234-1234-1234-123456789012");

            Set<ConstraintViolation<IntervalReadingEntity>> violations = validator.validate(entity);

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
            Long value = 1000L;
            Long cost = 50L;
            Integer currency = 840; // USD

            IntervalReadingEntity entity = intervalReadingEntity()
                .withId(100L)
                .withUuid(uuid)
                .withValue(value)
                .withCost(cost)
                .build();

            assertThat(entity.getId()).isEqualTo(100L);
            assertThat(entity.getUuid()).isEqualTo(uuid);
            assertThat(entity.getValue()).isEqualTo(value);
            assertThat(entity.getCost()).isEqualTo(cost);
            assertThat(entity.getCurrency()).isEqualTo(currency);
        }

        @Test
        @DisplayName("Should handle null optional properties")
        void shouldHandleNullOptionalProperties() {
            IntervalReadingEntity entity = intervalReadingEntity().build();
            entity.setValue(null);
            entity.setCost(null);
            entity.setCurrency(null);
            entity.setTimePeriod(null);

            assertThat(entity.getValue()).isNull();
            assertThat(entity.getCost()).isNull();
            assertThat(entity.getCurrency()).isNull();
            assertThat(entity.getTimePeriod()).isNull();
        }

        @Test
        @DisplayName("Should handle zero values correctly")
        void shouldHandleZeroValues() {
            IntervalReadingEntity entity = intervalReadingEntity()
                .withValue(0L)
                .withCost(0L)
                .build();
            
            entity.setCurrency(0);

            assertThat(entity.getValue()).isEqualTo(0L);
            assertThat(entity.getCost()).isEqualTo(0L);
            assertThat(entity.getCurrency()).isEqualTo(0);
        }

        @Test
        @DisplayName("Should handle negative values correctly")
        void shouldHandleNegativeValues() {
            IntervalReadingEntity entity = intervalReadingEntity()
                .withValue(-500L) // Net metering or power export
                .withCost(-25L)   // Credit or refund
                .build();

            assertThat(entity.getValue()).isEqualTo(-500L);
            assertThat(entity.getCost()).isEqualTo(-25L);
        }
    }

    @Nested
    @DisplayName("Time Period Tests")
    class TimePeriodTests {

        @Test
        @DisplayName("Should manage time period correctly")
        void shouldManageTimePeriod() {
            DateTimeInterval timePeriod = new DateTimeInterval();
            timePeriod.setStart(1672531200L); // 2023-01-01T00:00:00Z
            timePeriod.setDuration(900L);     // 15 minutes

            IntervalReadingEntity entity = intervalReadingEntity()
                .withTimePeriod(timePeriod)
                .build();

            assertThat(entity.getTimePeriod()).isEqualTo(timePeriod);
            assertThat(entity.getTimePeriod().getStart()).isEqualTo(1672531200L);
            assertThat(entity.getTimePeriod().getDuration()).isEqualTo(900L);
        }

        @Test
        @DisplayName("Should handle various time period durations")
        void shouldHandleVariousTimePeriodDurations() {
            Long[] durations = {
                300L,   // 5 minutes
                900L,   // 15 minutes
                1800L,  // 30 minutes
                3600L,  // 1 hour
                86400L  // 1 day
            };

            for (Long duration : durations) {
                DateTimeInterval timePeriod = new DateTimeInterval();
                timePeriod.setStart(1672531200L);
                timePeriod.setDuration(duration);

                IntervalReadingEntity entity = intervalReadingEntity()
                    .withTimePeriod(timePeriod)
                    .build();

                assertThat(entity.getTimePeriod().getDuration()).isEqualTo(duration);
            }
        }

        @Test
        @DisplayName("Should handle time period with epoch timestamps")
        void shouldHandleTimePeriodWithEpochTimestamps() {
            DateTimeInterval timePeriod = new DateTimeInterval();
            timePeriod.setStart(0L);          // Unix epoch
            timePeriod.setDuration(2147483647L); // Max int value

            IntervalReadingEntity entity = intervalReadingEntity()
                .withTimePeriod(timePeriod)
                .build();

            assertThat(entity.getTimePeriod().getStart()).isEqualTo(0L);
            assertThat(entity.getTimePeriod().getDuration()).isEqualTo(2147483647L);
        }
    }

    @Nested
    @DisplayName("Reading Quality Relationship Tests")
    class ReadingQualityRelationshipTests {

        @Test
        @DisplayName("Should manage reading qualities collection")
        void shouldManageReadingQualitiesCollection() {
            ReadingQualityEntity quality1 = readingQualityEntity()
                .withQuality("valid")
                .build();
            
            ReadingQualityEntity quality2 = readingQualityEntity()
                .withQuality("estimated")
                .build();

            IntervalReadingEntity entity = intervalReadingEntity()
                .withReadingQuality(quality1)
                .withReadingQuality(quality2)
                .build();

            assertThat(entity.getReadingQualities()).hasSize(2);
            assertThat(entity.getReadingQualities()).containsExactly(quality1, quality2);
        }

        @Test
        @DisplayName("Should initialize empty reading qualities collection")
        void shouldInitializeEmptyReadingQualitiesCollection() {
            IntervalReadingEntity entity = new IntervalReadingEntity();

            assertThat(entity.getReadingQualities()).isNotNull().isEmpty();
        }

        @Test
        @DisplayName("Should allow modification of reading qualities collection")
        void shouldAllowModificationOfReadingQualitiesCollection() {
            ReadingQualityEntity quality = readingQualityEntity()
                .withQuality("valid")
                .build();

            IntervalReadingEntity entity = intervalReadingEntity().build();
            entity.getReadingQualities().add(quality);

            assertThat(entity.getReadingQualities()).hasSize(1);
            assertThat(entity.getReadingQualities()).contains(quality);

            entity.getReadingQualities().remove(quality);
            assertThat(entity.getReadingQualities()).isEmpty();
        }

        @Test
        @DisplayName("Should support bidirectional relationship management")
        void shouldSupportBidirectionalRelationshipManagement() {
            ReadingQualityEntity quality = readingQualityEntity().build();
            IntervalReadingEntity entity = intervalReadingEntity().build();

            // Test the addReadingQuality method that should manage bidirectional relationship
            entity.addReadingQuality(quality);

            assertThat(entity.getReadingQualities()).contains(quality);
            assertThat(quality.getIntervalReading()).isEqualTo(entity);
        }

        @Test
        @DisplayName("Should handle duplicate reading qualities gracefully")
        void shouldHandleDuplicateReadingQualitiesGracefully() {
            ReadingQualityEntity quality = readingQualityEntity().build();
            IntervalReadingEntity entity = intervalReadingEntity().build();

            // Add the same quality twice
            entity.addReadingQuality(quality);
            entity.addReadingQuality(quality);

            // Should only contain one instance
            assertThat(entity.getReadingQualities()).hasSize(1);
            assertThat(entity.getReadingQualities()).contains(quality);
        }
    }

    @Nested
    @DisplayName("Currency and Cost Tests")
    class CurrencyAndCostTests {

        @Test
        @DisplayName("Should support various currency codes")
        void shouldSupportVariousCurrencyCodes() {
            Integer[] currencyCodes = {
                840, // USD
                978, // EUR
                826, // GBP
                392, // JPY
                124  // CAD
            };

            for (Integer currencyCode : currencyCodes) {
                IntervalReadingEntity entity = intervalReadingEntity()
                    .withCost(1000L)
                    .build();
                entity.setCurrency(currencyCode);

                assertThat(entity.getCurrency()).isEqualTo(currencyCode);
            }
        }

        @Test
        @DisplayName("Should handle cost calculations correctly")
        void shouldHandleCostCalculations() {
            // Test realistic cost scenarios
            IntervalReadingEntity entity1 = intervalReadingEntity()
                .withValue(1000L)  // 1000 Wh = 1 kWh
                .withCost(12500L)  // $0.125 per kWh (12.5 cents)
                .build();

            IntervalReadingEntity entity2 = intervalReadingEntity()
                .withValue(500L)   // 500 Wh = 0.5 kWh
                .withCost(6250L)   // $0.125 per kWh (6.25 cents)
                .build();

            assertThat(entity1.getValue()).isEqualTo(1000L);
            assertThat(entity1.getCost()).isEqualTo(12500L);
            assertThat(entity2.getValue()).isEqualTo(500L);
            assertThat(entity2.getCost()).isEqualTo(6250L);

            // Verify proportional cost relationship
            double rate1 = (double) entity1.getCost() / entity1.getValue();
            double rate2 = (double) entity2.getCost() / entity2.getValue();
            assertThat(rate1).isEqualTo(rate2);
        }
    }

    @Nested
    @DisplayName("Business Logic Tests")
    class BusinessLogicTests {

        @Test
        @DisplayName("Should support Green Button compliance requirements")
        void shouldSupportGreenButtonComplianceRequirements() {
            String greenButtonUuid = "12345678-1234-1234-1234-123456789012";
            
            IntervalReadingEntity entity = intervalReadingEntity()
                .withUuid(greenButtonUuid)
                .withValue(1000L)
                .withCost(12500L)
                .build();

            // Verify ESPI compliance
            assertThat(entity.getUuid()).matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
            assertThat(entity.getValue()).isNotNull();
            assertThat(entity.getCost()).isNotNull();
        }

        @Test
        @DisplayName("Should handle typical smart meter reading scenarios")
        void shouldHandleTypicalSmartMeterReadingScenarios() {
            // 15-minute interval reading
            DateTimeInterval interval = new DateTimeInterval();
            interval.setStart(1672531200L); // 2023-01-01T00:00:00Z
            interval.setDuration(900L);     // 15 minutes

            IntervalReadingEntity entity = intervalReadingEntity()
                .withValue(250L)   // 250 Wh for 15 minutes (1 kWh/hour rate)
                .withCost(3125L)   // $0.125 per kWh rate
                .withTimePeriod(interval)
                .build();

            assertThat(entity.getValue()).isEqualTo(250L);
            assertThat(entity.getCost()).isEqualTo(3125L);
            assertThat(entity.getTimePeriod().getDuration()).isEqualTo(900L);

            // Verify reasonable power consumption rate
            double hourlyRate = (double) entity.getValue() * 4; // 4 intervals per hour
            assertThat(hourlyRate).isEqualTo(1000.0); // 1 kWh/hour
        }

        @Test
        @DisplayName("Should handle net metering scenarios")
        void shouldHandleNetMeteringScenarios() {
            // Solar export during day
            IntervalReadingEntity exportReading = intervalReadingEntity()
                .withValue(-500L)  // Negative value indicates export
                .withCost(-6250L)  // Credit for exported energy
                .build();

            // Consumption during night
            IntervalReadingEntity consumptionReading = intervalReadingEntity()
                .withValue(750L)   // Positive value indicates consumption
                .withCost(9375L)   // Cost for consumed energy
                .build();

            assertThat(exportReading.getValue()).isNegative();
            assertThat(exportReading.getCost()).isNegative();
            assertThat(consumptionReading.getValue()).isPositive();
            assertThat(consumptionReading.getCost()).isPositive();
        }
    }

    @Nested
    @DisplayName("Edge Cases and Error Handling")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle very large values")
        void shouldHandleVeryLargeValues() {
            Long largeValue = Long.MAX_VALUE / 2;
            Long largeCost = Long.MAX_VALUE / 2;

            IntervalReadingEntity entity = intervalReadingEntity()
                .withValue(largeValue)
                .withCost(largeCost)
                .build();

            assertThat(entity.getValue()).isEqualTo(largeValue);
            assertThat(entity.getCost()).isEqualTo(largeCost);
        }

        @Test
        @DisplayName("Should handle very small negative values")
        void shouldHandleVerySmallNegativeValues() {
            Long smallNegativeValue = Long.MIN_VALUE / 2;
            Long smallNegativeCost = Long.MIN_VALUE / 2;

            IntervalReadingEntity entity = intervalReadingEntity()
                .withValue(smallNegativeValue)
                .withCost(smallNegativeCost)
                .build();

            assertThat(entity.getValue()).isEqualTo(smallNegativeValue);
            assertThat(entity.getCost()).isEqualTo(smallNegativeCost);
        }

        @Test
        @DisplayName("Should handle invalid currency codes gracefully")
        void shouldHandleInvalidCurrencyCodesGracefully() {
            IntervalReadingEntity entity = intervalReadingEntity().build();
            entity.setCurrency(9999); // Invalid currency code

            assertThat(entity.getCurrency()).isEqualTo(9999);
        }

        @Test
        @DisplayName("Should handle time period edge cases")
        void shouldHandleTimePeriodEdgeCases() {
            DateTimeInterval edgeCaseInterval = new DateTimeInterval();
            edgeCaseInterval.setStart(Long.MAX_VALUE);
            edgeCaseInterval.setDuration(0L); // Zero duration

            IntervalReadingEntity entity = intervalReadingEntity()
                .withTimePeriod(edgeCaseInterval)
                .build();

            assertThat(entity.getTimePeriod().getStart()).isEqualTo(Long.MAX_VALUE);
            assertThat(entity.getTimePeriod().getDuration()).isEqualTo(0L);
        }
    }

    @Nested
    @DisplayName("Integration with Other Entities")
    class IntegrationTests {

        @Test
        @DisplayName("Should integrate properly with interval block hierarchy")
        void shouldIntegrateWithIntervalBlockHierarchy() {
            ReadingQualityEntity quality = readingQualityEntity()
                .withQuality("valid")
                .build();

            IntervalReadingEntity reading = intervalReadingEntity()
                .withReadingQuality(quality)
                .build();

            IntervalBlockEntity block = intervalBlockEntity()
                .withIntervalReading(reading)
                .build();

            MeterReadingEntity meterReading = meterReadingEntity()
                .withIntervalBlock(block)
                .build();

            // Verify complete integration
            assertThat(meterReading.getIntervalBlocks()).hasSize(1);
            assertThat(meterReading.getIntervalBlocks().get(0).getIntervalReadings()).hasSize(1);
            assertThat(meterReading.getIntervalBlocks().get(0).getIntervalReadings().get(0)).isEqualTo(reading);
            assertThat(reading.getReadingQualities()).contains(quality);
            assertThat(quality.getIntervalReading()).isEqualTo(reading);
        }
    }
}