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

import org.greenbuttonalliance.espi.common.support.SpringBootTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for PnodeRefEntity covering validation, relationships, and business logic.
 */
@DisplayName("PnodeRefEntity Tests")
class PnodeRefEntityTest extends SpringBootTestBase {

    @Nested
    @DisplayName("Entity Validation Tests")
    class ValidationTests {

        @Test
        @DisplayName("Should validate successfully with required fields")
        void shouldValidateWithRequiredFields() {
            PnodeRefEntity entity = new PnodeRefEntity();
            entity.setApnodeType("LOAD");
            entity.setRef("PNODE_001");
            entity.setStartEffectiveDate(1672531200L);

            Set<ConstraintViolation<PnodeRefEntity>> violations = validator.validate(entity);

            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("Should fail validation with null ref")
        void shouldFailValidationWithNullRef() {
            PnodeRefEntity entity = new PnodeRefEntity();
            entity.setApnodeType("LOAD");
            entity.setStartEffectiveDate(1672531200L);
            // ref is null

            Set<ConstraintViolation<PnodeRefEntity>> violations = validator.validate(entity);

            assertThat(violations).isNotEmpty();
        }

        @Test
        @DisplayName("Should validate with optional fields null")
        void shouldValidateWithOptionalFieldsNull() {
            PnodeRefEntity entity = new PnodeRefEntity();
            entity.setRef("PNODE_001");
            // apnodeType, startEffectiveDate, endEffectiveDate are null

            Set<ConstraintViolation<PnodeRefEntity>> violations = validator.validate(entity);

            assertThat(violations).isEmpty();
        }
    }

    @Nested
    @DisplayName("Entity Properties Tests")
    class PropertiesTests {

        @Test
        @DisplayName("Should set and get all properties correctly")
        void shouldSetAndGetAllProperties() {
            String apnodeType = "GENERATOR";
            String ref = "PNODE_002";
            Long startEffectiveDate = 1672531200L;
            Long endEffectiveDate = 1672617600L;

            PnodeRefEntity entity = new PnodeRefEntity();
            entity.setApnodeType(apnodeType);
            entity.setRef(ref);
            entity.setStartEffectiveDate(startEffectiveDate);
            entity.setEndEffectiveDate(endEffectiveDate);

            assertThat(entity.getApnodeType()).isEqualTo(apnodeType);
            assertThat(entity.getRef()).isEqualTo(ref);
            assertThat(entity.getStartEffectiveDate()).isEqualTo(startEffectiveDate);
            assertThat(entity.getEndEffectiveDate()).isEqualTo(endEffectiveDate);
        }

        @Test
        @DisplayName("Should handle null dates")
        void shouldHandleNullDates() {
            PnodeRefEntity entity = new PnodeRefEntity();
            entity.setRef("PNODE_003");

            assertThat(entity.getStartEffectiveDate()).isNull();
            assertThat(entity.getEndEffectiveDate()).isNull();
        }
    }

    @Nested
    @DisplayName("Relationship Tests")
    class RelationshipTests {

        @Test
        @DisplayName("Should manage usage point relationship")
        void shouldManageUsagePointRelationship() {
            UsagePointEntity usagePoint = new UsagePointEntity();
            usagePoint.setDescription("Test Usage Point");

            PnodeRefEntity entity = new PnodeRefEntity();
            entity.setRef("PNODE_001");
            entity.setUsagePoint(usagePoint);

            assertThat(entity.getUsagePoint()).isEqualTo(usagePoint);
        }

        @Test
        @DisplayName("Should handle bidirectional relationship with usage point")
        void shouldHandleBidirectionalRelationship() {
            UsagePointEntity usagePoint = new UsagePointEntity();
            PnodeRefEntity pnodeRef = new PnodeRefEntity();
            pnodeRef.setRef("PNODE_001");
            pnodeRef.setApnodeType("LOAD");

            // Set up bidirectional relationship
            pnodeRef.setUsagePoint(usagePoint);
            usagePoint.getPnodeRefs().add(pnodeRef);

            assertThat(pnodeRef.getUsagePoint()).isEqualTo(usagePoint);
            assertThat(usagePoint.getPnodeRefs()).contains(pnodeRef);
        }
    }

    @Nested
    @DisplayName("Business Logic Tests")
    class BusinessLogicTests {

        @Test
        @DisplayName("Should support all pricing node types")
        void shouldSupportAllPricingNodeTypes() {
            String[] nodeTypes = {"LOAD", "GENERATOR", "INTERFACE", "HUB", "ZONE"};

            for (String nodeType : nodeTypes) {
                PnodeRefEntity entity = new PnodeRefEntity();
                entity.setApnodeType(nodeType);
                entity.setRef("PNODE_" + nodeType);

                assertThat(entity.getApnodeType()).isEqualTo(nodeType);
            }
        }

        @Test
        @DisplayName("Should handle effective date ranges")
        void shouldHandleEffectiveDateRanges() {
            Long startDate = 1672531200L; // 2023-01-01
            Long endDate = 1675209600L;   // 2023-02-01

            PnodeRefEntity entity = new PnodeRefEntity();
            entity.setRef("PNODE_001");
            entity.setStartEffectiveDate(startDate);
            entity.setEndEffectiveDate(endDate);

            assertThat(entity.getStartEffectiveDate()).isEqualTo(startDate);
            assertThat(entity.getEndEffectiveDate()).isEqualTo(endDate);
            assertThat(entity.getStartEffectiveDate()).isLessThan(entity.getEndEffectiveDate());
        }

        @Test
        @DisplayName("Should handle open-ended effective dates")
        void shouldHandleOpenEndedEffectiveDates() {
            PnodeRefEntity entity = new PnodeRefEntity();
            entity.setRef("PNODE_001");
            entity.setStartEffectiveDate(1672531200L);
            // endEffectiveDate remains null for open-ended

            assertThat(entity.getStartEffectiveDate()).isNotNull();
            assertThat(entity.getEndEffectiveDate()).isNull();
        }
    }

    @Nested
    @DisplayName("Edge Cases Tests")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle very long reference strings")
        void shouldHandleVeryLongReferenceStrings() {
            String longRef = "PNODE_" + "A".repeat(250);
            
            PnodeRefEntity entity = new PnodeRefEntity();
            entity.setRef(longRef);

            assertThat(entity.getRef()).isEqualTo(longRef);
        }

        @Test
        @DisplayName("Should handle special characters in reference")
        void shouldHandleSpecialCharactersInReference() {
            String specialRef = "PNODE_TEST-001_$%&";
            
            PnodeRefEntity entity = new PnodeRefEntity();
            entity.setRef(specialRef);
            entity.setApnodeType("LOAD");

            assertThat(entity.getRef()).isEqualTo(specialRef);
        }

        @Test
        @DisplayName("Should handle boundary timestamp values")
        void shouldHandleBoundaryTimestampValues() {
            Long minTimestamp = 0L;
            Long maxTimestamp = Long.MAX_VALUE;

            PnodeRefEntity entity = new PnodeRefEntity();
            entity.setRef("PNODE_BOUNDARY");
            entity.setStartEffectiveDate(minTimestamp);
            entity.setEndEffectiveDate(maxTimestamp);

            assertThat(entity.getStartEffectiveDate()).isEqualTo(minTimestamp);
            assertThat(entity.getEndEffectiveDate()).isEqualTo(maxTimestamp);
        }
    }
}