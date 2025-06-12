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

import org.greenbuttonalliance.espi.common.domain.ServiceCategory;
import org.greenbuttonalliance.espi.common.models.atom.LinkType;
import org.greenbuttonalliance.espi.common.support.SpringBootTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.greenbuttonalliance.espi.common.support.TestDataBuilder.*;

/**
 * Comprehensive JUnit 5 tests for UsagePointEntity using modern testing practices.
 * 
 * Tests entity validation, relationships, business logic, and edge cases.
 */
@DisplayName("UsagePointEntity Tests")
class UsagePointEntityTest extends SpringBootTestBase {

    @Nested
    @DisplayName("Entity Validation Tests")
    class ValidationTests {

        @Test
        @DisplayName("Should validate successfully with all required fields")
        void shouldValidateWithRequiredFields() {
            UsagePointEntity entity = usagePointEntity()
                .withResourceId("test-usage-point-1")
                .withDescription("Valid Usage Point")
                .build();

            Set<ConstraintViolation<UsagePointEntity>> violations = validator.validate(entity);

            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("Should fail validation with null service category")
        void shouldFailValidationWithNullServiceCategory() {
            ServiceCategory nullCategory = null;
            UsagePointEntity entity = usagePointEntity()
                .withServiceCategory(nullCategory)
                .build();

            Set<ConstraintViolation<UsagePointEntity>> violations = validator.validate(entity);

            assertThat(violations).isNotEmpty();
        }

        @Test
        @DisplayName("Should validate with optional fields null")
        void shouldValidateWithOptionalFieldsNull() {
            UsagePointEntity entity = usagePointEntity()
                .withDescription("Minimal Usage Point")
                .build();

            Set<ConstraintViolation<UsagePointEntity>> violations = validator.validate(entity);

            assertThat(violations).isEmpty();
        }
    }

    @Nested
    @DisplayName("Entity Properties Tests")
    class PropertiesTests {

        @Test
        @DisplayName("Should set and get all basic properties correctly")
        void shouldSetAndGetBasicProperties() {
            String description = "Test Usage Point";
            ServiceCategory serviceCategory = new ServiceCategory(ServiceCategory.ELECTRICITY_SERVICE);
            Short status = 1;

            UsagePointEntity entity = usagePointEntity()
                .withResourceId("test-usage-point-1")
                .withDescription(description)
                .withServiceCategory(serviceCategory)
                .withStatus(status)
                .build();

            assertThat(entity.getUUID()).isNotNull(); // UUID5 generated from resource ID
            assertThat(entity.getDescription()).isEqualTo(description);
            assertThat(entity.getServiceCategory()).isEqualTo(serviceCategory);
            assertThat(entity.getStatus()).isEqualTo(status);
        }

        @Test
        @DisplayName("Should handle role flags as byte array")
        void shouldHandleRoleFlags() {
            byte[] roleFlags = "test-role-flags".getBytes();
            
            UsagePointEntity entity = usagePointEntity().build();
            entity.setRoleFlags(roleFlags);

            assertThat(entity.getRoleFlags()).isEqualTo(roleFlags);
        }

        @Test
        @DisplayName("Should handle published and updated timestamps with ESPI compliance")
        void shouldHandleTimestamps() {
            UsagePointEntity entity = usagePointEntity().build();

            // Verify ESPI-compliant default timestamps
            assertThat(entity.getPublished()).isEqualTo(DEFAULT_PUBLISHED);
            assertThat(entity.getUpdated()).isEqualTo(DEFAULT_UPDATED);
            
            // Verify ESPI string format conversion
            String publishedEspi = toEspiTimestamp(entity.getPublished());
            String updatedEspi = toEspiTimestamp(entity.getUpdated());
            
            assertThat(publishedEspi).endsWith("Z");
            assertThat(updatedEspi).endsWith("Z");
            assertThat(publishedEspi).isEqualTo(DEFAULT_PUBLISHED_ESPI);
            assertThat(updatedEspi).isEqualTo(DEFAULT_UPDATED_ESPI);
        }
    }

    @Nested
    @DisplayName("Relationship Tests")
    class RelationshipTests {

        @Test
        @DisplayName("Should manage meter readings relationship")
        void shouldManageMeterReadings() {
            MeterReadingEntity meterReading1 = meterReadingEntity()
                .withResourceId("meter-1")
                .build();
            
            MeterReadingEntity meterReading2 = meterReadingEntity()
                .withResourceId("meter-2")
                .build();

            UsagePointEntity entity = usagePointEntity()
                .withMeterReading(meterReading1)
                .withMeterReading(meterReading2)
                .build();

            assertThat(entity.getMeterReadings()).hasSize(2);
            assertThat(entity.getMeterReadings()).containsExactly(meterReading1, meterReading2);
            
            // Verify ESPI compliance - each meter reading should have deterministic UUID
            assertThat(meterReading1.getUUID()).isNotNull();
            assertThat(meterReading2.getUUID()).isNotNull();
            assertThat(meterReading1.getUUID()).isNotEqualTo(meterReading2.getUUID());
        }

        @Test
        @DisplayName("Should manage usage summaries relationship")
        void shouldManageUsageSummaries() {
            UsageSummaryEntity summary1 = usageSummaryEntity()
                .withResourceId("summary-1")
                .build();
            
            UsageSummaryEntity summary2 = usageSummaryEntity()
                .withResourceId("summary-2")
                .build();

            UsagePointEntity entity = usagePointEntity()
                .withUsageSummary(summary1)
                .withUsageSummary(summary2)
                .build();

            assertThat(entity.getUsageSummaries()).hasSize(2);
            assertThat(entity.getUsageSummaries()).containsExactly(summary1, summary2);
        }

        @Test
        @DisplayName("Should manage electric power quality summaries relationship")
        void shouldManageElectricPowerQualitySummaries() {
            ElectricPowerQualitySummaryEntity epqs = electricPowerQualitySummaryEntity()
                .withResourceId("epqs-1")
                .build();

            UsagePointEntity entity = usagePointEntity()
                .withElectricPowerQualitySummary(epqs)
                .build();

            assertThat(entity.getElectricPowerQualitySummaries()).hasSize(1);
            assertThat(entity.getElectricPowerQualitySummaries()).contains(epqs);
        }

        @Test
        @DisplayName("Should handle service delivery point relationship")
        void shouldHandleServiceDeliveryPoint() {
            ServiceDeliveryPointEntity sdp = serviceDeliveryPointEntity()
                .withResourceId("sdp-1")
                .build();

            UsagePointEntity entity = usagePointEntity()
                .withServiceDeliveryPoint(sdp)
                .build();

            assertThat(entity.getServiceDeliveryPoint()).isEqualTo(sdp);
        }

        @Test
        @DisplayName("Should initialize empty collections for relationships")
        void shouldInitializeEmptyCollections() {
            UsagePointEntity entity = new UsagePointEntity();

            assertThat(entity.getMeterReadings()).isNotNull().isEmpty();
            assertThat(entity.getUsageSummaries()).isNotNull().isEmpty();
            assertThat(entity.getElectricPowerQualitySummaries()).isNotNull().isEmpty();
            assertThat(entity.getRelatedLinks()).isNotNull().isEmpty();
        }
    }

    @Nested
    @DisplayName("Related Links Tests")
    class RelatedLinksTests {

        @Test
        @DisplayName("Should manage related links collection")
        void shouldManageRelatedLinks() {
            LinkType link1 = new LinkType("related", "/usage-points/1/meter-readings");
            LinkType link2 = new LinkType("related", "/usage-points/1/usage-summaries");

            UsagePointEntity entity = usagePointEntity().build();
            entity.getRelatedLinks().add(link1);
            entity.getRelatedLinks().add(link2);

            assertThat(entity.getRelatedLinks()).hasSize(2);
            assertThat(entity.getRelatedLinks()).containsExactly(link1, link2);
        }

        @Test
        @DisplayName("Should allow modification of related links")
        void shouldAllowModificationOfRelatedLinks() {
            LinkType link = new LinkType("related", "/test-link");

            UsagePointEntity entity = usagePointEntity().build();
            entity.getRelatedLinks().add(link);

            assertThat(entity.getRelatedLinks()).hasSize(1);

            entity.getRelatedLinks().remove(link);
            assertThat(entity.getRelatedLinks()).isEmpty();
        }
    }

    @Nested
    @DisplayName("Service Category Tests")
    class ServiceCategoryTests {

        @Test
        @DisplayName("Should support all service category types")
        void shouldSupportAllServiceCategoryTypes() {
            ServiceCategory[] categories = {
                new ServiceCategory(ServiceCategory.ELECTRICITY_SERVICE),
                new ServiceCategory(ServiceCategory.GAS_SERVICE),
                new ServiceCategory(ServiceCategory.WATER_SERVICE),
                new ServiceCategory(ServiceCategory.TIME_SERVICE),
                new ServiceCategory(ServiceCategory.HEAT_SERVICE)
            };

            for (ServiceCategory category : categories) {
                UsagePointEntity entity = usagePointEntity()
                    .withServiceCategory(category)
                    .build();

                assertThat(entity.getServiceCategory()).isEqualTo(category);
            }
        }
    }

    @Nested
    @DisplayName("Business Logic Tests")
    class BusinessLogicTests {

        @Test
        @DisplayName("Should maintain data integrity in complex scenarios")
        void shouldMaintainDataIntegrityInComplexScenarios() {
            // Create a complex usage point with all relationships
            UsagePointEntity entity = completeUsagePointEntity();

            assertThat(entity.getUUID()).isNotNull();
            assertThat(entity.getDescription()).isNotNull();
            assertThat(entity.getServiceCategory().getKind()).isEqualTo(ServiceCategory.ELECTRICITY_SERVICE);
            assertThat(entity.getStatus()).isEqualTo((short) 1);

            assertThat(entity.getMeterReadings()).isNotEmpty();
            assertThat(entity.getUsageSummaries()).isNotEmpty();
            assertThat(entity.getElectricPowerQualitySummaries()).isNotEmpty();

            // Verify nested relationships are properly connected
            MeterReadingEntity meterReading = entity.getMeterReadings().get(0);
            assertThat(meterReading.getReadingType()).isNotNull();
            assertThat(meterReading.getIntervalBlocks()).isNotEmpty();

            IntervalBlockEntity intervalBlock = meterReading.getIntervalBlocks().get(0);
            assertThat(intervalBlock.getIntervalReadings()).isNotEmpty();
        }

        @Test
        @DisplayName("Should handle null relationships gracefully")
        void shouldHandleNullRelationshipsGracefully() {
            UsagePointEntity entity = usagePointEntity().build();

            // Should not throw exceptions when accessing null relationships
            assertThat(entity.getServiceDeliveryPoint()).isNull();
            
            // Collections should be initialized but empty
            assertThat(entity.getMeterReadings()).isEmpty();
            assertThat(entity.getUsageSummaries()).isEmpty();
            assertThat(entity.getElectricPowerQualitySummaries()).isEmpty();
        }
    }

    @Nested
    @DisplayName("Edge Cases and Error Handling")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle very long descriptions")
        void shouldHandleVeryLongDescriptions() {
            String longDescription = "A".repeat(500);
            
            UsagePointEntity entity = usagePointEntity()
                .withDescription(longDescription)
                .build();

            assertThat(entity.getDescription()).hasSize(500);
        }

        @Test
        @DisplayName("Should handle special characters in UUID and description")
        void shouldHandleSpecialCharacters() {
            String description = "Test & <Special> \"Characters\" 'in' Description";
            
            UsagePointEntity entity = usagePointEntity()
                .withDescription(description)
                .build();

            assertThat(entity.getDescription()).isEqualTo(description);
        }

        @Test
        @DisplayName("Should handle boundary values for status")
        void shouldHandleBoundaryValuesForStatus() {
            Short[] statusValues = {0, 1, 32767, -32768}; // Short min/max values
            
            for (Short status : statusValues) {
                UsagePointEntity entity = usagePointEntity()
                    .withStatus(status)
                    .build();

                assertThat(entity.getStatus()).isEqualTo(status);
            }
        }
    }
}