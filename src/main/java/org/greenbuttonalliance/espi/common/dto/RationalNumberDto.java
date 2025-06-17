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
 * RationalNumber DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents a rational number with numerator and denominator components
 * as defined in the NAESB ESPI standard.
 * 
 * @author Green Button Alliance
 * @version 1.4.0
 * @since Spring Boot 3.5
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RationalNumber", propOrder = { "numerator", "denominator" })
public record RationalNumberDto(
    BigInteger numerator,
    BigInteger denominator
) {
    /**
     * Default constructor for JAXB compatibility.
     */
    public RationalNumberDto() {
        this(null, null);
    }
    
    /**
     * Constructor with numerator only (denominator defaults to 1).
     *
     * @param numerator the numerator value
     */
    public RationalNumberDto(BigInteger numerator) {
        this(numerator, BigInteger.ONE);
    }
    
    /**
     * Create a RationalNumberDto from a decimal value.
     *
     * @param decimal the decimal value to convert
     * @param precision the number of decimal places to preserve
     * @return RationalNumberDto representing the decimal
     */
    public static RationalNumberDto fromDecimal(double decimal, int precision) {
        if (precision < 0) {
            throw new IllegalArgumentException("Precision must be non-negative");
        }
        
        BigInteger denominator = BigInteger.TEN.pow(precision);
        BigInteger numerator = BigInteger.valueOf((long) (decimal * Math.pow(10, precision)));
        
        return new RationalNumberDto(numerator, denominator);
    }
    
    /**
     * Convert this rational number to a decimal value.
     *
     * @return the decimal representation
     */
    public double toDecimal() {
        if (denominator == null || denominator.equals(BigInteger.ZERO)) {
            return 0.0;
        }
        if (numerator == null) {
            return 0.0;
        }
        return numerator.doubleValue() / denominator.doubleValue();
    }
    
    /**
     * Check if this rational number represents zero.
     *
     * @return true if the rational number is zero
     */
    public boolean isZero() {
        return numerator == null || numerator.equals(BigInteger.ZERO);
    }
    
    /**
     * Get the simplified form of this rational number.
     *
     * @return simplified RationalNumberDto
     */
    public RationalNumberDto simplify() {
        if (isZero()) {
            return new RationalNumberDto(BigInteger.ZERO, BigInteger.ONE);
        }
        
        if (numerator == null || denominator == null) {
            return this;
        }
        
        BigInteger gcd = numerator.gcd(denominator);
        if (gcd.equals(BigInteger.ONE)) {
            return this;
        }
        
        return new RationalNumberDto(
            numerator.divide(gcd), 
            denominator.divide(gcd)
        );
    }
}