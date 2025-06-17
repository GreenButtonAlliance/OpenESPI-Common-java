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

package org.greenbuttonalliance.espi.common.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import java.math.BigInteger;

/**
 * ReadingInterharmonic DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents an interharmonic measurement with numerator and denominator components
 * as defined in the NAESB ESPI standard for power quality measurements.
 * 
 * Interharmonics are sinusoidal components with frequencies that are not integer
 * multiples of the fundamental frequency. They can cause distortion in power systems.
 * 
 * @author Green Button Alliance
 * @version 1.4.0
 * @since Spring Boot 3.5
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReadingInterharmonic", propOrder = { "numerator", "denominator" })
public record ReadingInterharmonicDto(
    BigInteger numerator,
    BigInteger denominator
) {
    /**
     * Default constructor for JAXB compatibility.
     */
    public ReadingInterharmonicDto() {
        this(null, null);
    }
    
    /**
     * Constructor with numerator only (denominator defaults to 1).
     *
     * @param numerator the numerator value representing the interharmonic ratio
     */
    public ReadingInterharmonicDto(BigInteger numerator) {
        this(numerator, BigInteger.ONE);
    }
    
    /**
     * Create a ReadingInterharmonicDto from a frequency ratio.
     *
     * @param ratio the frequency ratio as a decimal
     * @param precision the number of decimal places to preserve
     * @return ReadingInterharmonicDto representing the frequency ratio
     */
    public static ReadingInterharmonicDto fromRatio(double ratio, int precision) {
        if (precision < 0) {
            throw new IllegalArgumentException("Precision must be non-negative");
        }
        
        BigInteger denominator = BigInteger.TEN.pow(precision);
        BigInteger numerator = BigInteger.valueOf((long) (ratio * Math.pow(10, precision)));
        
        return new ReadingInterharmonicDto(numerator, denominator);
    }
    
    /**
     * Convert this interharmonic to a decimal ratio.
     *
     * @return the decimal representation of the frequency ratio
     */
    public double toRatio() {
        if (denominator == null || denominator.equals(BigInteger.ZERO)) {
            return 0.0;
        }
        if (numerator == null) {
            return 0.0;
        }
        return numerator.doubleValue() / denominator.doubleValue();
    }
    
    /**
     * Calculate the interharmonic frequency given a fundamental frequency.
     *
     * @param fundamentalFrequency the fundamental frequency (typically 50 or 60 Hz)
     * @return the interharmonic frequency in Hz
     */
    public double getFrequency(double fundamentalFrequency) {
        return toRatio() * fundamentalFrequency;
    }
    
    /**
     * Check if this interharmonic represents zero (no interharmonic distortion).
     *
     * @return true if the interharmonic ratio is zero
     */
    public boolean isZero() {
        return numerator == null || numerator.equals(BigInteger.ZERO);
    }
    
    /**
     * Get the simplified form of this interharmonic ratio.
     *
     * @return simplified ReadingInterharmonicDto
     */
    public ReadingInterharmonicDto simplify() {
        if (isZero()) {
            return new ReadingInterharmonicDto(BigInteger.ZERO, BigInteger.ONE);
        }
        
        if (numerator == null || denominator == null) {
            return this;
        }
        
        BigInteger gcd = numerator.gcd(denominator);
        if (gcd.equals(BigInteger.ONE)) {
            return this;
        }
        
        return new ReadingInterharmonicDto(
            numerator.divide(gcd), 
            denominator.divide(gcd)
        );
    }
    
    /**
     * Check if this represents a harmonic (integer multiple of fundamental).
     *
     * @return true if this is a harmonic rather than an interharmonic
     */
    public boolean isHarmonic() {
        if (isZero() || denominator == null || numerator == null) {
            return false;
        }
        
        // Check if numerator is divisible by denominator (integer ratio)
        return numerator.remainder(denominator).equals(BigInteger.ZERO);
    }
}