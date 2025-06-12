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

import org.greenbuttonalliance.espi.common.domain.ServiceCategory;
import org.greenbuttonalliance.espi.common.domain.usage.UsagePointEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.greenbuttonalliance.espi.common.support.TestDataBuilder.*;

/**
 * Tests for the TestDataBuilder to verify it creates valid entities.
 */
@DisplayName("TestDataBuilder Tests")
class TestDataBuilderTest {

    @Test
    @DisplayName("Should create valid UsagePointEntity with defaults")
    void shouldCreateValidUsagePointEntity() {
        UsagePointEntity entity = usagePointEntity().build();
        
        assertThat(entity).isNotNull();
        assertThat(entity.getDescription()).isEqualTo("Test Description");
        assertThat(entity.getServiceCategory()).isNotNull();
        assertThat(entity.getServiceCategory().getKind()).isEqualTo(ServiceCategory.ELECTRICITY_SERVICE);
        assertThat(entity.getStatus()).isEqualTo((short) 1);
    }

    @Test
    @DisplayName("Should create UsagePointEntity with custom values")
    void shouldCreateUsagePointEntityWithCustomValues() {
        ServiceCategory customCategory = new ServiceCategory(ServiceCategory.GAS_SERVICE);
        
        UsagePointEntity entity = usagePointEntity()
            .withDescription("Custom Usage Point")
            .withServiceCategory(customCategory)
            .withStatus((short) 2)
            .build();
        
        assertThat(entity.getDescription()).isEqualTo("Custom Usage Point");
        assertThat(entity.getServiceCategory().getKind()).isEqualTo(ServiceCategory.GAS_SERVICE);
        assertThat(entity.getStatus()).isEqualTo((short) 2);
    }

    @Test
    @DisplayName("Should create complete UsagePointEntity with all relationships")
    void shouldCreateCompleteUsagePointEntity() {
        UsagePointEntity entity = completeUsagePointEntity();
        
        assertThat(entity).isNotNull();
        assertThat(entity.getUUID()).isNotNull();
        assertThat(entity.getMeterReadings()).isNotEmpty();
        assertThat(entity.getUsageSummaries()).isNotEmpty();
        assertThat(entity.getElectricPowerQualitySummaries()).isNotEmpty();
    }

    @Test
    @DisplayName("Should use ESPI-compliant timestamp format")
    void shouldUseEspiCompliantTimestampFormat() {
        UsagePointEntity entity = usagePointEntity().build();
        
        // Verify timestamps are set to ESPI-compliant values
        assertThat(entity.getPublished()).isEqualTo(DEFAULT_PUBLISHED);
        assertThat(entity.getUpdated()).isEqualTo(DEFAULT_UPDATED);
        
        // Verify ESPI string format conversion
        String publishedEspi = toEspiTimestamp(entity.getPublished());
        String updatedEspi = toEspiTimestamp(entity.getUpdated());
        
        assertThat(publishedEspi).isEqualTo(DEFAULT_PUBLISHED_ESPI); // "2023-01-01T00:00:00Z"
        assertThat(updatedEspi).isEqualTo(DEFAULT_UPDATED_ESPI);     // "2023-01-02T00:00:00Z"
        
        // Verify the strings end with "Z" (UTC termination)
        assertThat(publishedEspi).endsWith("Z");
        assertThat(updatedEspi).endsWith("Z");
    }

    @Test
    @DisplayName("Should convert between ESPI timestamp format and LocalDateTime")
    void shouldConvertBetweenEspiTimestampAndLocalDateTime() {
        // Test conversion from LocalDateTime to ESPI string
        LocalDateTime testTime = LocalDateTime.of(2023, 6, 15, 14, 30, 45);
        String espiString = toEspiTimestamp(testTime);
        assertThat(espiString).isEqualTo("2023-06-15T14:30:45Z");
        
        // Test conversion from ESPI string back to LocalDateTime
        LocalDateTime convertedBack = fromEspiTimestamp(espiString);
        assertThat(convertedBack).isEqualTo(testTime);
        
        // Test with default values
        assertThat(fromEspiTimestamp(DEFAULT_PUBLISHED_ESPI)).isEqualTo(DEFAULT_PUBLISHED);
        assertThat(fromEspiTimestamp(DEFAULT_UPDATED_ESPI)).isEqualTo(DEFAULT_UPDATED);
    }
}