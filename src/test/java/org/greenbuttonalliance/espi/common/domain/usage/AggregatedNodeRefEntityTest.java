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
 * Tests for AggregatedNodeRefEntity covering validation, relationships, and business logic.
 */
@DisplayName("AggregatedNodeRefEntity Tests")
class AggregatedNodeRefEntityTest extends SpringBootTestBase {

    @Nested
    @DisplayName("Entity Validation Tests")
    class ValidationTests {

        @Test
        @DisplayName("Should validate successfully with required fields")
        void shouldValidateWithRequiredFields() {
            AggregatedNodeRefEntity entity = new AggregatedNodeRefEntity();
            entity.setAnodeType("ZONE");
            entity.setRef("ANODE_001");
            entity.setStartEffectiveDate(1672531200L);

            Set<ConstraintViolation<AggregatedNodeRefEntity>> violations = validator.validate(entity);

            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("Should fail validation with null ref")
        void shouldFailValidationWithNullRef() {
            AggregatedNodeRefEntity entity = new AggregatedNodeRefEntity();
            entity.setAnodeType("ZONE");
            entity.setStartEffectiveDate(1672531200L);
            // ref is null

            Set<ConstraintViolation<AggregatedNodeRefEntity>> violations = validator.validate(entity);

            assertThat(violations).isNotEmpty();
        }

        @Test
        @DisplayName("Should validate with optional fields null")
        void shouldValidateWithOptionalFieldsNull() {
            AggregatedNodeRefEntity entity = new AggregatedNodeRefEntity();
            entity.setRef("ANODE_001");
            // anodeType, startEffectiveDate, endEffectiveDate, pnodeRef are null

            Set<ConstraintViolation<AggregatedNodeRefEntity>> violations = validator.validate(entity);

            assertThat(violations).isEmpty();
        }
    }

    @Nested
    @DisplayName("Entity Properties Tests")
    class PropertiesTests {

        @Test
        @DisplayName("Should set and get all properties correctly")
        void shouldSetAndGetAllProperties() {
            String anodeType = "REGION";
            String ref = "ANODE_002";
            Long startEffectiveDate = 1672531200L;
            Long endEffectiveDate = 1672617600L;

            AggregatedNodeRefEntity entity = new AggregatedNodeRefEntity();
            entity.setAnodeType(anodeType);
            entity.setRef(ref);
            entity.setStartEffectiveDate(startEffectiveDate);
            entity.setEndEffectiveDate(endEffectiveDate);

            assertThat(entity.getAnodeType()).isEqualTo(anodeType);
            assertThat(entity.getRef()).isEqualTo(ref);
            assertThat(entity.getStartEffectiveDate()).isEqualTo(startEffectiveDate);
            assertThat(entity.getEndEffectiveDate()).isEqualTo(endEffectiveDate);
        }

        @Test
        @DisplayName("Should handle null dates")
        void shouldHandleNullDates() {
            AggregatedNodeRefEntity entity = new AggregatedNodeRefEntity();
            entity.setRef("ANODE_003");

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

            AggregatedNodeRefEntity entity = new AggregatedNodeRefEntity();
            entity.setRef("ANODE_001");
            entity.setUsagePoint(usagePoint);

            assertThat(entity.getUsagePoint()).isEqualTo(usagePoint);
        }

        @Test
        @DisplayName("Should manage pricing node reference relationship")
        void shouldManagePricingNodeReferenceRelationship() {
            PnodeRefEntity pnodeRef = new PnodeRefEntity();
            pnodeRef.setRef("PNODE_001");
            pnodeRef.setApnodeType("LOAD");

            AggregatedNodeRefEntity entity = new AggregatedNodeRefEntity();
            entity.setRef("ANODE_001");
            entity.setPnodeRef(pnodeRef);

            assertThat(entity.getPnodeRef()).isEqualTo(pnodeRef);
        }

        @Test
        @DisplayName("Should handle bidirectional relationship with usage point")
        void shouldHandleBidirectionalRelationship() {
            UsagePointEntity usagePoint = new UsagePointEntity();
            AggregatedNodeRefEntity aggregatedNodeRef = new AggregatedNodeRefEntity();
            aggregatedNodeRef.setRef("ANODE_001");
            aggregatedNodeRef.setAnodeType("ZONE");

            // Set up bidirectional relationship
            aggregatedNodeRef.setUsagePoint(usagePoint);
            usagePoint.getAggregatedNodeRefs().add(aggregatedNodeRef);

            assertThat(aggregatedNodeRef.getUsagePoint()).isEqualTo(usagePoint);
            assertThat(usagePoint.getAggregatedNodeRefs()).contains(aggregatedNodeRef);
        }

        @Test
        @DisplayName("Should maintain complex relationships with pricing nodes")
        void shouldMaintainComplexRelationshipsWithPricingNodes() {
            UsagePointEntity usagePoint = new UsagePointEntity();
            
            PnodeRefEntity pnodeRef = new PnodeRefEntity();
            pnodeRef.setRef("PNODE_001");
            pnodeRef.setApnodeType("LOAD");
            pnodeRef.setUsagePoint(usagePoint);
            
            AggregatedNodeRefEntity aggregatedNodeRef = new AggregatedNodeRefEntity();
            aggregatedNodeRef.setRef("ANODE_001");
            aggregatedNodeRef.setAnodeType("ZONE");
            aggregatedNodeRef.setUsagePoint(usagePoint);
            aggregatedNodeRef.setPnodeRef(pnodeRef);

            // Verify all relationships are maintained
            assertThat(aggregatedNodeRef.getUsagePoint()).isEqualTo(usagePoint);
            assertThat(aggregatedNodeRef.getPnodeRef()).isEqualTo(pnodeRef);
            assertThat(pnodeRef.getUsagePoint()).isEqualTo(usagePoint);
        }
    }

    @Nested
    @DisplayName("Business Logic Tests")
    class BusinessLogicTests {

        @Test
        @DisplayName("Should support all aggregated node types")
        void shouldSupportAllAggregatedNodeTypes() {
            String[] nodeTypes = {"ZONE", "REGION", "AREA", "MARKET", "SYSTEM"};

            for (String nodeType : nodeTypes) {
                AggregatedNodeRefEntity entity = new AggregatedNodeRefEntity();
                entity.setAnodeType(nodeType);
                entity.setRef("ANODE_" + nodeType);

                assertThat(entity.getAnodeType()).isEqualTo(nodeType);
            }
        }

        @Test
        @DisplayName("Should handle effective date ranges")
        void shouldHandleEffectiveDateRanges() {
            Long startDate = 1672531200L; // 2023-01-01
            Long endDate = 1675209600L;   // 2023-02-01

            AggregatedNodeRefEntity entity = new AggregatedNodeRefEntity();
            entity.setRef("ANODE_001");
            entity.setStartEffectiveDate(startDate);
            entity.setEndEffectiveDate(endDate);

            assertThat(entity.getStartEffectiveDate()).isEqualTo(startDate);
            assertThat(entity.getEndEffectiveDate()).isEqualTo(endDate);
            assertThat(entity.getStartEffectiveDate()).isLessThan(entity.getEndEffectiveDate());
        }

        @Test
        @DisplayName("Should maintain integrity with nested pricing node references")
        void shouldMaintainIntegrityWithNestedPricingNodeReferences() {
            // Create a scenario where aggregated node contains pricing node with overlapping dates
            Long commonStart = 1672531200L;
            Long aggregatedEnd = 1675209600L;
            Long pnodeEnd = 1674604800L; // Earlier end date for pricing node
            
            PnodeRefEntity pnodeRef = new PnodeRefEntity();
            pnodeRef.setRef("PNODE_001");
            pnodeRef.setApnodeType("LOAD");
            pnodeRef.setStartEffectiveDate(commonStart);
            pnodeRef.setEndEffectiveDate(pnodeEnd);
            
            AggregatedNodeRefEntity entity = new AggregatedNodeRefEntity();
            entity.setRef("ANODE_001");
            entity.setAnodeType("ZONE");
            entity.setStartEffectiveDate(commonStart);
            entity.setEndEffectiveDate(aggregatedEnd);
            entity.setPnodeRef(pnodeRef);

            // Verify the structure maintains logical consistency
            assertThat(entity.getStartEffectiveDate()).isEqualTo(pnodeRef.getStartEffectiveDate());
            assertThat(entity.getEndEffectiveDate()).isGreaterThan(pnodeRef.getEndEffectiveDate());
            assertThat(entity.getPnodeRef()).isEqualTo(pnodeRef);
        }
    }

    @Nested
    @DisplayName("Edge Cases Tests")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle very long reference strings")
        void shouldHandleVeryLongReferenceStrings() {
            String longRef = "ANODE_" + "A".repeat(250);
            
            AggregatedNodeRefEntity entity = new AggregatedNodeRefEntity();
            entity.setRef(longRef);

            assertThat(entity.getRef()).isEqualTo(longRef);
        }

        @Test
        @DisplayName("Should handle special characters in reference")
        void shouldHandleSpecialCharactersInReference() {
            String specialRef = "ANODE_TEST-001_$%&";
            
            AggregatedNodeRefEntity entity = new AggregatedNodeRefEntity();
            entity.setRef(specialRef);
            entity.setAnodeType("ZONE");

            assertThat(entity.getRef()).isEqualTo(specialRef);
        }

        @Test
        @DisplayName("Should handle null pricing node reference gracefully")
        void shouldHandleNullPricingNodeReferenceGracefully() {
            AggregatedNodeRefEntity entity = new AggregatedNodeRefEntity();
            entity.setRef("ANODE_001");
            entity.setAnodeType("ZONE");
            // pnodeRef remains null

            assertThat(entity.getPnodeRef()).isNull();
            assertThat(entity.getRef()).isNotNull();
            assertThat(entity.getAnodeType()).isNotNull();
        }

        @Test
        @DisplayName("Should handle boundary timestamp values")
        void shouldHandleBoundaryTimestampValues() {
            Long minTimestamp = 0L;
            Long maxTimestamp = Long.MAX_VALUE;

            AggregatedNodeRefEntity entity = new AggregatedNodeRefEntity();
            entity.setRef("ANODE_BOUNDARY");
            entity.setStartEffectiveDate(minTimestamp);
            entity.setEndEffectiveDate(maxTimestamp);

            assertThat(entity.getStartEffectiveDate()).isEqualTo(minTimestamp);
            assertThat(entity.getEndEffectiveDate()).isEqualTo(maxTimestamp);
        }
    }
}