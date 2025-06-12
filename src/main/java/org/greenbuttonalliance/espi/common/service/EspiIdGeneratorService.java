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

package org.greenbuttonalliance.espi.common.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Service for generating NAESB ESPI compliant identifiers.
 * 
 * This service generates UUID version 5 identifiers based on href rel="self" values
 * to ensure compliance with NAESB ESPI standard requirement for 48-bit minimum identifiers.
 * 
 * UUID5 provides:
 * - 128-bit identifiers (exceeds 48-bit minimum requirement)
 * - Deterministic generation from href values
 * - RFC 4122 compliance
 * - Global uniqueness when combined with namespace
 */
@Service
@Slf4j
public class EspiIdGeneratorService {
    
    /**
     * NAESB ESPI namespace UUID for consistent UUID5 generation.
     * This namespace ensures all ESPI resources have deterministic UUIDs
     * based on their href rel="self" values.
     */
    public static final UUID ESPI_NAMESPACE = UUID.fromString("6ba7b810-9dad-11d1-80b4-00c04fd430c8");
    
    /**
     * Generates a NAESB ESPI compliant UUID5 identifier from a href rel="self" value.
     * 
     * @param selfHref the href attribute value from atom:link rel="self"
     * @return UUID5 generated from the href value using ESPI namespace
     * @throws IllegalArgumentException if selfHref is null or empty
     */
    public UUID generateEspiId(String selfHref) {
        if (selfHref == null || selfHref.trim().isEmpty()) {
            throw new IllegalArgumentException("Self href cannot be null or empty for ESPI ID generation");
        }
        
        UUID uuid5 = generateUuid5(ESPI_NAMESPACE, selfHref.trim());
        // Removed logging to fix compilation - will be restored when Lombok is properly configured
        return uuid5;
    }
    
    /**
     * Generates a NAESB ESPI compliant UUID5 identifier for a resource type and resource ID.
     * This is used when the full href is not yet available but resource information is known.
     * 
     * @param resourceType the ESPI resource type (e.g., "UsagePoint", "MeterReading")
     * @param resourceId the local resource identifier
     * @return UUID5 generated from the constructed href pattern
     */
    public UUID generateEspiId(String resourceType, String resourceId) {
        if (resourceType == null || resourceType.trim().isEmpty()) {
            throw new IllegalArgumentException("Resource type cannot be null or empty");
        }
        if (resourceId == null || resourceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Resource ID cannot be null or empty");
        }
        
        String constructedHref = String.format("/espi/1_1/resource/%s/%s", resourceType.trim(), resourceId.trim());
        return generateEspiId(constructedHref);
    }
    
    /**
     * Validates that a UUID meets NAESB ESPI requirements.
     * NAESB ESPI requires minimum 48-bit identifiers. UUIDs are 128-bit, so they exceed this requirement.
     * 
     * @param uuid the UUID to validate
     * @return true if the UUID meets NAESB ESPI requirements
     */
    public boolean isValidEspiId(UUID uuid) {
        return uuid != null && uuid.version() == 5;
    }
    
    /**
     * Extracts the most significant 64 bits from a UUID as a long value.
     * This can be used for database storage when needed.
     * 
     * @param uuid the UUID to extract from
     * @return the most significant 64 bits as a long
     */
    public long extractMostSignificantBits(UUID uuid) {
        return uuid.getMostSignificantBits();
    }
    
    /**
     * Extracts the least significant 64 bits from a UUID as a long value.
     * This can be used for database storage when needed.
     * 
     * @param uuid the UUID to extract from
     * @return the least significant 64 bits as a long
     */
    public long extractLeastSignificantBits(UUID uuid) {
        return uuid.getLeastSignificantBits();
    }
    
    /**
     * Reconstructs a UUID from its most and least significant bits.
     * 
     * @param mostSigBits the most significant 64 bits
     * @param leastSigBits the least significant 64 bits
     * @return the reconstructed UUID
     */
    public UUID reconstructUuid(long mostSigBits, long leastSigBits) {
        return new UUID(mostSigBits, leastSigBits);
    }
    
    /**
     * Generates a UUID version 5 based on a namespace UUID and name string.
     * 
     * @param namespace the namespace UUID
     * @param name the name string to hash
     * @return UUID version 5
     */
    private UUID generateUuid5(UUID namespace, String name) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            
            // Add namespace UUID bytes
            sha1.update(toBytes(namespace));
            
            // Add name bytes
            sha1.update(name.getBytes(StandardCharsets.UTF_8));
            
            byte[] hash = sha1.digest();
            
            // Set version (5) and variant bits according to RFC 4122
            hash[6] &= 0x0f;  // Clear version
            hash[6] |= 0x50;  // Set to version 5
            hash[8] &= 0x3f;  // Clear variant
            hash[8] |= 0x80;  // Set to IETF variant
            
            // Create UUID from hash bytes
            long mostSigBits = 0;
            long leastSigBits = 0;
            
            for (int i = 0; i < 8; i++) {
                mostSigBits = (mostSigBits << 8) | (hash[i] & 0xff);
            }
            for (int i = 8; i < 16; i++) {
                leastSigBits = (leastSigBits << 8) | (hash[i] & 0xff);
            }
            
            return new UUID(mostSigBits, leastSigBits);
            
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 algorithm not available", e);
        }
    }
    
    /**
     * Converts a UUID to its byte representation.
     * 
     * @param uuid the UUID to convert
     * @return 16-byte array representing the UUID
     */
    private byte[] toBytes(UUID uuid) {
        byte[] bytes = new byte[16];
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();
        
        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte) (mostSigBits >>> (8 * (7 - i)));
        }
        for (int i = 8; i < 16; i++) {
            bytes[i] = (byte) (leastSigBits >>> (8 * (7 - i)));
        }
        
        return bytes;
    }
}