/*
 *
 *    Copyright (c) 2018-2025 Green Button Alliance, Inc.
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

import java.time.LocalDateTime;

import static org.greenbuttonalliance.espi.common.support.TestDataBuilder.*;

/**
 * Simple test runner to verify ESPI-compliant TestDataBuilder functionality.
 */
public class SimpleTestRunner {

    public static void main(String[] args) {
        System.out.println("=== ESPI-Compliant TestDataBuilder Verification ===\n");
        
        try {
            testBasicUsagePointCreation();
            testEspiTimestampFormat();
            testUuidGeneration();
            testServiceCategorySupport();
            
            System.out.println("✅ All tests passed! TestDataBuilder is ESPI-compliant.");
            
        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private static void testBasicUsagePointCreation() {
        System.out.println("1. Testing basic UsagePointEntity creation...");
        
        UsagePointEntity entity = usagePointEntity().build();
        
        assert entity != null : "Entity should not be null";
        assert entity.getDescription().equals("Test Description") : "Description should match";
        assert entity.getServiceCategory() != null : "Service category should be set";
        assert entity.getServiceCategory().getKind().equals(ServiceCategory.ELECTRICITY_SERVICE) : "Should default to electricity service";
        assert entity.getStatus().equals((short) 1) : "Status should be 1";
        
        System.out.println("   ✓ Basic entity creation successful");
    }
    
    private static void testEspiTimestampFormat() {
        System.out.println("2. Testing ESPI timestamp format...");
        
        UsagePointEntity entity = usagePointEntity().build();
        
        // Test default timestamps
        assert entity.getPublished().equals(DEFAULT_PUBLISHED) : "Published should match default";
        assert entity.getUpdated().equals(DEFAULT_UPDATED) : "Updated should match default";
        
        // Test ESPI string conversion
        String publishedEspi = toEspiTimestamp(entity.getPublished());
        String updatedEspi = toEspiTimestamp(entity.getUpdated());
        
        assert publishedEspi.equals(DEFAULT_PUBLISHED_ESPI) : "Published ESPI format should be: " + DEFAULT_PUBLISHED_ESPI;
        assert updatedEspi.equals(DEFAULT_UPDATED_ESPI) : "Updated ESPI format should be: " + DEFAULT_UPDATED_ESPI;
        assert publishedEspi.endsWith("Z") : "Published should end with Z";
        assert updatedEspi.endsWith("Z") : "Updated should end with Z";
        
        // Test bidirectional conversion
        LocalDateTime convertedPublished = fromEspiTimestamp(publishedEspi);
        assert convertedPublished.equals(entity.getPublished()) : "Conversion should be bidirectional";
        
        System.out.println("   ✓ ESPI timestamp format: " + publishedEspi + ", " + updatedEspi);
    }
    
    private static void testUuidGeneration() {
        System.out.println("3. Testing UUID5 generation...");
        
        UsagePointEntity entity1 = usagePointEntity()
            .withResourceId("test-usage-point-1")
            .build();
            
        UsagePointEntity entity2 = usagePointEntity()
            .withResourceId("test-usage-point-1")  // Same resource ID
            .build();
            
        UsagePointEntity entity3 = usagePointEntity()
            .withResourceId("test-usage-point-2")  // Different resource ID
            .build();
        
        assert entity1.getUUID() != null : "UUID should be generated";
        assert entity2.getUUID() != null : "UUID should be generated";
        assert entity3.getUUID() != null : "UUID should be generated";
        
        // Same resource ID should generate same UUID (deterministic)
        assert entity1.getUUID().equals(entity2.getUUID()) : "Same resource ID should generate same UUID";
        
        // Different resource ID should generate different UUID
        assert !entity1.getUUID().equals(entity3.getUUID()) : "Different resource ID should generate different UUID";
        
        // Check self links are set
        assert entity1.getSelfLink() != null : "Self link should be set";
        assert entity1.getSelfLink().getHref().contains("test-usage-point-1") : "Self link should contain resource ID";
        
        System.out.println("   ✓ UUID5 generation working: " + entity1.getUUID());
        System.out.println("   ✓ Self link: " + entity1.getSelfLink().getHref());
    }
    
    private static void testServiceCategorySupport() {
        System.out.println("4. Testing ServiceCategory support...");
        
        ServiceCategory gasCategory = new ServiceCategory(ServiceCategory.GAS_SERVICE);
        
        UsagePointEntity entity = usagePointEntity()
            .withServiceCategory(gasCategory)
            .withDescription("Gas Usage Point")
            .build();
        
        assert entity.getServiceCategory().getKind().equals(ServiceCategory.GAS_SERVICE) : "Should support gas service";
        assert entity.getDescription().equals("Gas Usage Point") : "Description should be customizable";
        
        System.out.println("   ✓ ServiceCategory support working");
    }
}