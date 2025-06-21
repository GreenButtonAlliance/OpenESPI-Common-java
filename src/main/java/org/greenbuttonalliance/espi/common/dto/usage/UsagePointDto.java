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

package org.greenbuttonalliance.espi.common.dto.usage;

import org.greenbuttonalliance.espi.common.domain.legacy.ServiceCategory;
import org.greenbuttonalliance.espi.common.dto.SummaryMeasurementDto;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.HexBinaryAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * UsagePoint DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents a logical point on a network at which consumption or production
 * is either physically measured (e.g., metered) or estimated (e.g., unmetered street lights).
 * Supports Atom protocol XML wrapping.
 */
@XmlRootElement(name = "UsagePoint", namespace = "http://naesb.org/espi")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "UsagePoint", namespace = "http://naesb.org/espi", propOrder = {
    "description", "roleFlags", "serviceCategory", "status", "estimatedLoad", 
    "nominalServiceVoltage", "ratedCurrent", "ratedPower", "serviceDeliveryPoint",
    "pnodeRefs", "aggregatedNodeRefs"
})
public record UsagePointDto(
    
    String uuid,
    String description,
    byte[] roleFlags,
    ServiceCategory serviceCategory,
    Short status,
    SummaryMeasurementDto estimatedLoad,
    SummaryMeasurementDto nominalServiceVoltage,
    SummaryMeasurementDto ratedCurrent,
    SummaryMeasurementDto ratedPower,
    ServiceDeliveryPointDto serviceDeliveryPoint,
    PnodeRefsDto pnodeRefs,
    AggregatedNodeRefsDto aggregatedNodeRefs,
    Object meterReadings,  // List<MeterReadingDto> - temporarily Object for compilation
    Object usageSummaries, // List<UsageSummaryDto> - temporarily Object for compilation  
    Object electricPowerQualitySummaries // List<ElectricPowerQualitySummaryDto> - temporarily Object for compilation
) {
    
    @XmlTransient
    public String getUuid() {
        return uuid;
    }
    
    @XmlElement(name = "description")
    public String getDescription() {
        return description;
    }
    
    @XmlElement(name = "roleFlags", type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    public byte[] getRoleFlags() {
        return roleFlags;
    }
    
    @XmlElement(name = "ServiceCategory")
    public ServiceCategory getServiceCategory() {
        return serviceCategory;
    }
    
    @XmlElement(name = "status")
    public Short getStatus() {
        return status;
    }
    
    /**
     * Estimated load for the usage point as SummaryMeasurement.
     */
    @XmlElement(name = "estimatedLoad")
    public SummaryMeasurementDto getEstimatedLoad() {
        return estimatedLoad;
    }
    
    /**
     * Nominal service voltage for the usage point as SummaryMeasurement.
     */
    @XmlElement(name = "nominalServiceVoltage")
    public SummaryMeasurementDto getNominalServiceVoltage() {
        return nominalServiceVoltage;
    }
    
    /**
     * Rated current for the usage point as SummaryMeasurement.
     */
    @XmlElement(name = "ratedCurrent")
    public SummaryMeasurementDto getRatedCurrent() {
        return ratedCurrent;
    }
    
    /**
     * Rated power for the usage point as SummaryMeasurement.
     */
    @XmlElement(name = "ratedPower")
    public SummaryMeasurementDto getRatedPower() {
        return ratedPower;
    }
    
    @XmlElement(name = "ServiceDeliveryPoint")
    public ServiceDeliveryPointDto getServiceDeliveryPoint() {
        return serviceDeliveryPoint;
    }
    
    /**
     * Array of pricing node references.
     */
    @XmlElement(name = "pnodeRefs")
    public PnodeRefsDto getPnodeRefs() {
        return pnodeRefs;
    }
    
    /**
     * Array of aggregated node references.
     */
    @XmlElement(name = "aggregatedNodeRefs")
    public AggregatedNodeRefsDto getAggregatedNodeRefs() {
        return aggregatedNodeRefs;
    }
    
    @XmlTransient
    public Object getMeterReadings() {
        return meterReadings;
    }
    
    @XmlTransient
    public Object getUsageSummaries() {
        return usageSummaries;
    }
    
    @XmlTransient
    public Object getElectricPowerQualitySummaries() {
        return electricPowerQualitySummaries;
    }
    
    /**
     * Default constructor for JAXB.
     */
    public UsagePointDto() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }
    
    /**
     * Minimal constructor for basic usage point data.
     */
    public UsagePointDto(String uuid, ServiceCategory serviceCategory) {
        this(uuid, null, null, serviceCategory, null, null, null, null, null, null, null, null, null, null, null);
    }
    
    /**
     * Constructor with core ESPI elements.
     */
    public UsagePointDto(String uuid, String description, ServiceCategory serviceCategory, 
                        SummaryMeasurementDto estimatedLoad, SummaryMeasurementDto nominalServiceVoltage, 
                        SummaryMeasurementDto ratedCurrent, SummaryMeasurementDto ratedPower,
                        ServiceDeliveryPointDto serviceDeliveryPoint) {
        this(uuid, description, null, serviceCategory, null, estimatedLoad, nominalServiceVoltage, 
             ratedCurrent, ratedPower, serviceDeliveryPoint, null, null, null, null, null);
    }
    
    /**
     * Generates the default self href for a usage point.
     * 
     * @return default self href
     */
    public String generateSelfHref() {
        return uuid != null ? "/espi/1_1/resource/UsagePoint/" + uuid : null;
    }
    
    /**
     * Generates the default up href for a usage point.
     * 
     * @return default up href
     */
    public String generateUpHref() {
        return "/espi/1_1/resource/UsagePoint";
    }
    
    /**
     * Gets the total number of meter readings.
     * 
     * @return meter reading count
     */
    public int getMeterReadingCount() {
        return 0; // Temporarily disabled for compilation
    }
    
    /**
     * Gets the total number of usage summaries.
     * 
     * @return usage summary count
     */
    public int getUsageSummaryCount() {
        return 0; // Temporarily disabled for compilation
    }
}