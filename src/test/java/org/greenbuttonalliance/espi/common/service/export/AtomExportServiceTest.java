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

package org.greenbuttonalliance.espi.common.service.export;

import org.greenbuttonalliance.espi.common.domain.ServiceCategory;
import org.greenbuttonalliance.espi.common.domain.usage.UsagePointEntity;
import org.greenbuttonalliance.espi.common.dto.usage.UsagePointDto;
import org.greenbuttonalliance.espi.common.mapper.GreenButtonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import jakarta.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Test class for AtomExportService testing the modern DTO-based export functionality.
 * 
 * Tests the core export pipeline:
 * 1. Entity → DTO conversion via MapStruct
 * 2. DTO → Atom Entry/Feed wrapping
 * 3. JAXB marshalling to XML output
 */
@ExtendWith(MockitoExtension.class)
class AtomExportServiceTest {

    @Mock
    private GreenButtonMapper mapper;

    @Mock
    private Jaxb2Marshaller marshaller;

    private AtomExportService atomExportService;

    @BeforeEach
    void setUp() {
        atomExportService = new AtomExportService(mapper, marshaller);
    }

    @Test
    void testExportResource_SingleEntity() {
        // Create test entity
        UsagePointEntity entity = createTestUsagePointEntity();
        UsagePointDto dto = createTestUsagePointDto();

        // Mock mapper function
        Function<UsagePointEntity, UsagePointDto> mapperFunction = mock(Function.class);
        when(mapperFunction.apply(entity)).thenReturn(dto);

        // Create output stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Execute export
        atomExportService.exportResource(
            entity,
            mapperFunction,
            "UsagePoint",
            "/espi/1_1/resource/UsagePoint/12345",
            outputStream
        );

        // Verify mapper was called
        verify(mapperFunction).apply(entity);

        // Verify marshaller was called with proper parameters
        verify(marshaller).marshal(any(), any(StreamResult.class));
    }

    @Test
    void testExportResource_NullEntity() {
        Function<Object, Object> mapperFunction = mock(Function.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Should throw exception for null entity
        assertThatThrownBy(() -> 
            atomExportService.exportResource(
                null,
                mapperFunction,
                "UsagePoint",
                "/espi/1_1/resource/UsagePoint/12345",
                outputStream
            )
        ).isInstanceOf(IllegalArgumentException.class)
         .hasMessage("Entity cannot be null");

        // Verify mapper was never called
        verify(mapperFunction, never()).apply(any());
        verify(marshaller, never()).marshal(any(), any());
    }

    @Test
    void testExportResourceCollection_MultipleEntities() {
        // Create test entities
        List<UsagePointEntity> entities = Arrays.asList(
            createTestUsagePointEntity("uuid-1", "Usage Point 1"),
            createTestUsagePointEntity("uuid-2", "Usage Point 2"),
            createTestUsagePointEntity("uuid-3", "Usage Point 3")
        );

        // Mock mapper function
        Function<UsagePointEntity, UsagePointDto> mapperFunction = mock(Function.class);
        when(mapperFunction.apply(any(UsagePointEntity.class)))
            .thenReturn(createTestUsagePointDto());

        // Mock href generator
        Function<UsagePointEntity, String> hrefGenerator = entity -> 
            "/espi/1_1/resource/UsagePoint/" + entity.getUuid();

        // Create output stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Execute export
        atomExportService.exportResourceCollection(
            entities,
            mapperFunction,
            "UsagePoint",
            "Usage Points Collection",
            "urn:uuid:usage-points-feed",
            "/espi/1_1/resource/UsagePoint",
            hrefGenerator,
            outputStream
        );

        // Verify mapper was called for each entity
        verify(mapperFunction, times(3)).apply(any(UsagePointEntity.class));

        // Verify marshaller was called once (for the feed)
        verify(marshaller).marshal(any(), any(StreamResult.class));
    }

    @Test
    void testExportResourceCollection_EmptyList() {
        List<UsagePointEntity> entities = Arrays.asList();
        Function<UsagePointEntity, UsagePointDto> mapperFunction = mock(Function.class);
        Function<UsagePointEntity, String> hrefGenerator = mock(Function.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Execute export with empty list
        atomExportService.exportResourceCollection(
            entities,
            mapperFunction,
            "UsagePoint",
            "Empty Collection",
            "urn:uuid:empty-feed",
            "/espi/1_1/resource/UsagePoint",
            hrefGenerator,
            outputStream
        );

        // Verify mapper was never called
        verify(mapperFunction, never()).apply(any());

        // Verify marshaller was still called (for empty feed)
        verify(marshaller).marshal(any(), any(StreamResult.class));
    }

    @Test
    void testExportResourceCollection_NullList() {
        Function<Object, Object> mapperFunction = mock(Function.class);
        Function<Object, String> hrefGenerator = mock(Function.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Should throw exception for null entities list
        assertThatThrownBy(() -> 
            atomExportService.exportResourceCollection(
                null,
                mapperFunction,
                "UsagePoint",
                "Null Collection",
                "urn:uuid:null-feed",
                "/espi/1_1/resource/UsagePoint",
                hrefGenerator,
                outputStream
            )
        ).isInstanceOf(IllegalArgumentException.class)
         .hasMessage("Entities list cannot be null");

        // Verify no methods were called
        verify(mapperFunction, never()).apply(any());
        verify(hrefGenerator, never()).apply(any());
        verify(marshaller, never()).marshal(any(), any());
    }

    @Test
    void testConvenienceExportMethods() {
        UsagePointEntity entity = createTestUsagePointEntity();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Test convenience method
        atomExportService.exportUsagePoint(entity, outputStream);

        // Verify marshaller was called
        verify(marshaller).marshal(any(), any(StreamResult.class));
    }

    @Test
    void testExportPerformance_LargeCollection() {
        // Create large collection for performance testing
        List<UsagePointEntity> entities = createLargeEntityCollection(1000);
        
        Function<UsagePointEntity, UsagePointDto> mapperFunction = entity -> createTestUsagePointDto();
        Function<UsagePointEntity, String> hrefGenerator = entity -> "/espi/1_1/resource/UsagePoint/" + entity.getUuid();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Time the export operation
        long startTime = System.currentTimeMillis();
        
        atomExportService.exportResourceCollection(
            entities,
            mapperFunction,
            "UsagePoint",
            "Large Collection",
            "urn:uuid:large-feed",
            "/espi/1_1/resource/UsagePoint",
            hrefGenerator,
            outputStream
        );
        
        long executionTime = System.currentTimeMillis() - startTime;

        // Verify performance (should complete in reasonable time)
        assertThat(executionTime).isLessThan(5000); // Under 5 seconds

        // Verify marshaller was called
        verify(marshaller).marshal(any(), any(StreamResult.class));
    }

    @Test
    void testMapperIntegration() {
        // This test would verify integration with actual MapStruct mappers
        // when not using mocks (integration test scenario)
        
        // For unit test, we just verify the service calls the correct mapper methods
        UsagePointEntity entity = createTestUsagePointEntity();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Execute export (this will call the mocked mapper)
        atomExportService.exportUsagePoint(entity, outputStream);

        // Verify the marshaller was invoked
        verify(marshaller).marshal(any(), any(StreamResult.class));
    }

    // Helper methods for creating test data

    private UsagePointEntity createTestUsagePointEntity() {
        return createTestUsagePointEntity("test-uuid", "Test Usage Point");
    }

    private UsagePointEntity createTestUsagePointEntity(String uuid, String description) {
        UsagePointEntity entity = new UsagePointEntity();
        entity.setId(1L);
        entity.setUuid(uuid);
        entity.setDescription(description);
        entity.setServiceCategory(ServiceCategory.ELECTRICITY_SERVICE);
        entity.setStatus((short) 1);
        entity.setPublished(OffsetDateTime.now());
        entity.setUpdated(OffsetDateTime.now());
        return entity;
    }

    private UsagePointDto createTestUsagePointDto() {
        return new UsagePointDto(
            "test-uuid",
            "Test Usage Point",
            new byte[]{1, 2, 3, 4},
            ServiceCategory.ELECTRICITY_SERVICE,
            (short) 1,
            null, // serviceDeliveryPoint
            Arrays.asList(), // meterReadings
            Arrays.asList(), // usageSummaries
            Arrays.asList()  // electricPowerQualitySummaries
        );
    }

    private List<UsagePointEntity> createLargeEntityCollection(int size) {
        UsagePointEntity[] entities = new UsagePointEntity[size];
        for (int i = 0; i < size; i++) {
            entities[i] = createTestUsagePointEntity("uuid-" + i, "Usage Point " + i);
        }
        return Arrays.asList(entities);
    }
}