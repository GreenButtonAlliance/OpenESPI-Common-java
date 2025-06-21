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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.greenbuttonalliance.espi.common.domain.legacy.DateTimeInterval;
import org.greenbuttonalliance.espi.common.domain.common.IdentifiedObject;
import org.greenbuttonalliance.espi.common.domain.legacy.SummaryMeasurement;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Pure JPA/Hibernate entity for UsageSummary without JAXB concerns.
 * 
 * Represents a general usage summary for any commodity (not specific to electric power).
 * Contains billing information, consumption measurements, peak demand data,
 * and additional cost details for general utility usage.
 */
@Entity
@Table(name = "usage_summaries", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"usagePoint", "costAdditionalDetailLastPeriod"})
public class UsageSummaryEntity extends IdentifiedObject {

    private static final long serialVersionUID = 1L;

    /**
     * Bill amount for the last billing period.
     * Monetary value representing the total bill.
     */
    @Column(name = "bill_last_period")
    private Long billLastPeriod;

    /**
     * Bill amount to date for the current billing period.
     * Running total of charges for the current period.
     */
    @Column(name = "bill_to_date")
    private Long billToDate;

    /**
     * Additional costs for the last billing period.
     * Extra charges beyond base usage costs.
     */
    @Column(name = "cost_additional_last_period")
    private Long costAdditionalLastPeriod;

    /**
     * Currency code for monetary values.
     * ISO 4217 currency codes (USD, EUR, etc.).
     */
    @Column(name = "currency", length = 3)
    private String currency;

    /**
     * Quality indicator for the reading data.
     * Describes the reliability and accuracy of the measurements.
     */
    @Column(name = "quality_of_reading", length = 50)
    private String qualityOfReading;

    /**
     * Timestamp indicating when the status was last updated.
     * Unix timestamp format.
     */
    @Column(name = "status_timestamp")
    private Long statusTimeStamp;

    /**
     * Billing period for this summary.
     * Time interval covered by the billing period.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "start", column = @Column(name = "billing_period_start")),
        @AttributeOverride(name = "duration", column = @Column(name = "billing_period_duration"))
    })
    private DateTimeInterval billingPeriod;

    /**
     * Ratchet demand period.
     * Time period for the highest demand that sets the ratchet.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "start", column = @Column(name = "ratchet_demand_period_start")),
        @AttributeOverride(name = "duration", column = @Column(name = "ratchet_demand_period_duration"))
    })
    private DateTimeInterval ratchetDemandPeriod;

    /**
     * Overall consumption for the last billing period.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "powerOfTenMultiplier", column = @Column(name = "overall_consumption_last_period_multiplier")),
        @AttributeOverride(name = "timeStamp", column = @Column(name = "overall_consumption_last_period_timestamp")),
        @AttributeOverride(name = "uom", column = @Column(name = "overall_consumption_last_period_uom")),
        @AttributeOverride(name = "value", column = @Column(name = "overall_consumption_last_period_value"))
    })
    private SummaryMeasurement overallConsumptionLastPeriod;

    /**
     * Current billing period overall consumption.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "powerOfTenMultiplier", column = @Column(name = "current_billing_period_overall_consumption_multiplier")),
        @AttributeOverride(name = "timeStamp", column = @Column(name = "current_billing_period_overall_consumption_timestamp")),
        @AttributeOverride(name = "uom", column = @Column(name = "current_billing_period_overall_consumption_uom")),
        @AttributeOverride(name = "value", column = @Column(name = "current_billing_period_overall_consumption_value"))
    })
    private SummaryMeasurement currentBillingPeriodOverAllConsumption;

    /**
     * Current day consumption compared to same day last year (net).
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "powerOfTenMultiplier", column = @Column(name = "current_day_last_year_net_consumption_multiplier")),
        @AttributeOverride(name = "timeStamp", column = @Column(name = "current_day_last_year_net_consumption_timestamp")),
        @AttributeOverride(name = "uom", column = @Column(name = "current_day_last_year_net_consumption_uom")),
        @AttributeOverride(name = "value", column = @Column(name = "current_day_last_year_net_consumption_value"))
    })
    private SummaryMeasurement currentDayLastYearNetConsumption;

    /**
     * Current day net consumption.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "powerOfTenMultiplier", column = @Column(name = "current_day_net_consumption_multiplier")),
        @AttributeOverride(name = "timeStamp", column = @Column(name = "current_day_net_consumption_timestamp")),
        @AttributeOverride(name = "uom", column = @Column(name = "current_day_net_consumption_uom")),
        @AttributeOverride(name = "value", column = @Column(name = "current_day_net_consumption_value"))
    })
    private SummaryMeasurement currentDayNetConsumption;

    /**
     * Current day overall consumption.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "powerOfTenMultiplier", column = @Column(name = "current_day_overall_consumption_multiplier")),
        @AttributeOverride(name = "timeStamp", column = @Column(name = "current_day_overall_consumption_timestamp")),
        @AttributeOverride(name = "uom", column = @Column(name = "current_day_overall_consumption_uom")),
        @AttributeOverride(name = "value", column = @Column(name = "current_day_overall_consumption_value"))
    })
    private SummaryMeasurement currentDayOverallConsumption;

    /**
     * Peak demand measurement.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "powerOfTenMultiplier", column = @Column(name = "peak_demand_multiplier")),
        @AttributeOverride(name = "timeStamp", column = @Column(name = "peak_demand_timestamp")),
        @AttributeOverride(name = "uom", column = @Column(name = "peak_demand_uom")),
        @AttributeOverride(name = "value", column = @Column(name = "peak_demand_value"))
    })
    private SummaryMeasurement peakDemand;

    /**
     * Previous day consumption compared to same day last year (overall).
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "powerOfTenMultiplier", column = @Column(name = "previous_day_last_year_overall_consumption_multiplier")),
        @AttributeOverride(name = "timeStamp", column = @Column(name = "previous_day_last_year_overall_consumption_timestamp")),
        @AttributeOverride(name = "uom", column = @Column(name = "previous_day_last_year_overall_consumption_uom")),
        @AttributeOverride(name = "value", column = @Column(name = "previous_day_last_year_overall_consumption_value"))
    })
    private SummaryMeasurement previousDayLastYearOverallConsumption;

    /**
     * Previous day net consumption.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "powerOfTenMultiplier", column = @Column(name = "previous_day_net_consumption_multiplier")),
        @AttributeOverride(name = "timeStamp", column = @Column(name = "previous_day_net_consumption_timestamp")),
        @AttributeOverride(name = "uom", column = @Column(name = "previous_day_net_consumption_uom")),
        @AttributeOverride(name = "value", column = @Column(name = "previous_day_net_consumption_value"))
    })
    private SummaryMeasurement previousDayNetConsumption;

    /**
     * Previous day overall consumption.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "powerOfTenMultiplier", column = @Column(name = "previous_day_overall_consumption_multiplier")),
        @AttributeOverride(name = "timeStamp", column = @Column(name = "previous_day_overall_consumption_timestamp")),
        @AttributeOverride(name = "uom", column = @Column(name = "previous_day_overall_consumption_uom")),
        @AttributeOverride(name = "value", column = @Column(name = "previous_day_overall_consumption_value"))
    })
    private SummaryMeasurement previousDayOverallConsumption;

    /**
     * Ratchet demand measurement.
     * Highest demand that establishes billing demand.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "powerOfTenMultiplier", column = @Column(name = "ratchet_demand_multiplier")),
        @AttributeOverride(name = "timeStamp", column = @Column(name = "ratchet_demand_timestamp")),
        @AttributeOverride(name = "uom", column = @Column(name = "ratchet_demand_uom")),
        @AttributeOverride(name = "value", column = @Column(name = "ratchet_demand_value"))
    })
    private SummaryMeasurement ratchetDemand;

    /**
     * Usage point that this summary belongs to.
     * Many summaries can belong to one usage point.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usage_point_id")
    private UsagePointEntity usagePoint;

    /**
     * Additional cost details for the last billing period.
     * Line items breaking down additional charges.
     */
    @OneToMany(mappedBy = "usageSummary", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<LineItemEntity> costAdditionalDetailLastPeriod = new ArrayList<>();

    /**
     * Constructor with billing period and basic bill amounts.
     * 
     * @param billingPeriod the billing period for this summary
     * @param billLastPeriod the bill amount for last period
     * @param billToDate the bill amount to date
     */
    public UsageSummaryEntity(DateTimeInterval billingPeriod, Long billLastPeriod, Long billToDate) {
        this.billingPeriod = billingPeriod;
        this.billLastPeriod = billLastPeriod;
        this.billToDate = billToDate;
    }

    /**
     * Adds a line item for additional cost details.
     * Sets up the bidirectional relationship.
     * 
     * @param lineItem the line item to add
     */
    public void addCostAdditionalDetailLastPeriod(LineItemEntity lineItem) {
        if (lineItem != null) {
            this.costAdditionalDetailLastPeriod.add(lineItem);
            lineItem.setUsageSummary(this);
        }
    }

    /**
     * Removes a line item from additional cost details.
     * Clears the bidirectional relationship.
     * 
     * @param lineItem the line item to remove
     */
    public void removeCostAdditionalDetailLastPeriod(LineItemEntity lineItem) {
        if (lineItem != null) {
            this.costAdditionalDetailLastPeriod.remove(lineItem);
            lineItem.setUsageSummary(null);
        }
    }

    // Note: Simple setUsagePoint setter is generated by Lombok @Data
    // Complex bidirectional relationship management removed - handled by DataCustodian/ThirdParty applications

    /**
     * Generates the self href for this usage summary.
     * 
     * @return self href string
     */
    public String getSelfHref() {
        if (usagePoint != null) {
            return usagePoint.getSelfHref() + "/UsageSummary/" + getHashedId();
        }
        return "/espi/1_1/resource/UsageSummary/" + getHashedId();
    }

    /**
     * Generates the up href for this usage summary.
     * 
     * @return up href string pointing to the usage point
     */
    public String getUpHref() {
        if (usagePoint != null) {
            return usagePoint.getSelfHref() + "/UsageSummary";
        }
        return "/espi/1_1/resource/UsageSummary";
    }

    /**
     * Overrides the default self href generation to use usage summary specific logic.
     * 
     * @return self href for this usage summary
     */
    @Override
    protected String generateDefaultSelfHref() {
        return getSelfHref();
    }

    /**
     * Overrides the default up href generation to use usage summary specific logic.
     * 
     * @return up href for this usage summary
     */
    @Override
    protected String generateDefaultUpHref() {
        return getUpHref();
    }

    /**
     * Merges data from another UsageSummaryEntity.
     * Updates all summary data and embedded measurements.
     * 
     * @param other the other usage summary entity to merge from
     */
    public void merge(UsageSummaryEntity other) {
        if (other != null) {
            super.merge(other);
            
            // Update billing and cost information
            this.billLastPeriod = other.billLastPeriod;
            this.billToDate = other.billToDate;
            this.costAdditionalLastPeriod = other.costAdditionalLastPeriod;
            this.currency = other.currency;
            this.qualityOfReading = other.qualityOfReading;
            this.statusTimeStamp = other.statusTimeStamp;
            
            // Update time intervals
            this.billingPeriod = other.billingPeriod;
            this.ratchetDemandPeriod = other.ratchetDemandPeriod;
            
            // Update embedded measurements
            this.overallConsumptionLastPeriod = other.overallConsumptionLastPeriod;
            this.currentBillingPeriodOverAllConsumption = other.currentBillingPeriodOverAllConsumption;
            this.currentDayLastYearNetConsumption = other.currentDayLastYearNetConsumption;
            this.currentDayNetConsumption = other.currentDayNetConsumption;
            this.currentDayOverallConsumption = other.currentDayOverallConsumption;
            this.peakDemand = other.peakDemand;
            this.previousDayLastYearOverallConsumption = other.previousDayLastYearOverallConsumption;
            this.previousDayNetConsumption = other.previousDayNetConsumption;
            this.previousDayOverallConsumption = other.previousDayOverallConsumption;
            this.ratchetDemand = other.ratchetDemand;
            
            // Replace cost detail line items with bidirectional setup
            this.costAdditionalDetailLastPeriod.clear();
            if (other.costAdditionalDetailLastPeriod != null) {
                for (LineItemEntity lineItem : other.costAdditionalDetailLastPeriod) {
                    addCostAdditionalDetailLastPeriod(lineItem);
                }
            }
            
            // Update usage point if provided
            if (other.usagePoint != null) {
                this.usagePoint = other.usagePoint;
            }
        }
    }

    /**
     * Clears all relationships when unlinking the entity.
     */
    public void unlink() {
        clearRelatedLinks();
        
        // Clear cost detail line items
        for (LineItemEntity lineItem : new ArrayList<>(costAdditionalDetailLastPeriod)) {
            removeCostAdditionalDetailLastPeriod(lineItem);
        }
        costAdditionalDetailLastPeriod.clear();
        
        // Clear relationships with simple field assignment
        this.usagePoint = null;
    }

    /**
     * Sets up the parent relationship with a usage point.
     * Used by the resource management system.
     * Note: Bidirectional relationship setup handled by applications.
     * 
     * @param resource the parent usage point resource
     */
    public void setUpResource(IdentifiedObject resource) {
        if (resource instanceof UsagePointEntity parentUsagePoint) {
            this.usagePoint = parentUsagePoint;
        }
    }

    /**
     * Gets the query for finding parent resources.
     * Used by the resource management system.
     * 
     * @return the parent query string
     */
    public String getParentQuery() {
        return "UsagePointEntity.findByRelatedHref";
    }

    /**
     * Checks if this summary has billing information.
     * 
     * @return true if any bill amounts are specified, false otherwise
     */
    public boolean hasBillingInformation() {
        return billLastPeriod != null || billToDate != null || costAdditionalLastPeriod != null;
    }

    /**
     * Checks if this summary has consumption data.
     * 
     * @return true if any consumption measurements are present, false otherwise
     */
    public boolean hasConsumptionData() {
        return overallConsumptionLastPeriod != null ||
               currentBillingPeriodOverAllConsumption != null ||
               currentDayNetConsumption != null ||
               currentDayOverallConsumption != null;
    }

    /**
     * Checks if this summary has demand data.
     * 
     * @return true if peak demand or ratchet demand is present, false otherwise
     */
    public boolean hasDemandData() {
        return peakDemand != null || ratchetDemand != null;
    }

    /**
     * Checks if this summary has year-over-year comparison data.
     * 
     * @return true if last year comparison data is present, false otherwise
     */
    public boolean hasYearOverYearData() {
        return currentDayLastYearNetConsumption != null ||
               previousDayLastYearOverallConsumption != null;
    }

    /**
     * Gets the total additional cost details count.
     * 
     * @return number of additional cost line items
     */
    public int getAdditionalCostDetailsCount() {
        return costAdditionalDetailLastPeriod != null ? costAdditionalDetailLastPeriod.size() : 0;
    }

    /**
     * Gets the bill amount in dollars (assuming cost is in cents).
     * 
     * @return bill amount in dollars, or null if not set
     */
    public Double getBillLastPeriodInDollars() {
        return billLastPeriod != null ? billLastPeriod / 100.0 : null;
    }

    /**
     * Gets the bill to date amount in dollars (assuming cost is in cents).
     * 
     * @return bill to date amount in dollars, or null if not set
     */
    public Double getBillToDateInDollars() {
        return billToDate != null ? billToDate / 100.0 : null;
    }

    /**
     * Gets the additional cost amount in dollars (assuming cost is in cents).
     * 
     * @return additional cost amount in dollars, or null if not set
     */
    public Double getCostAdditionalLastPeriodInDollars() {
        return costAdditionalLastPeriod != null ? costAdditionalLastPeriod / 100.0 : null;
    }

    /**
     * Gets the commodity type being measured based on UOM.
     * 
     * @return commodity type description
     */
    public String getCommodityType() {
        if (overallConsumptionLastPeriod != null && overallConsumptionLastPeriod.getUom() != null) {
            String uom = overallConsumptionLastPeriod.getUom();
            if (uom.contains("WH") || uom.contains("W")) return "Electricity";
            if (uom.contains("BTU") || uom.contains("THERM")) return "Gas";
            if (uom.contains("GAL") || uom.contains("L")) return "Water";
        }
        return "Unknown";
    }

    /**
     * Alias method for addCostAdditionalDetailLastPeriod.
     * Provides a more generic name for line item management.
     * 
     * @param lineItem the line item to add
     */
    public void addLineItem(LineItemEntity lineItem) {
        addCostAdditionalDetailLastPeriod(lineItem);
    }

    /**
     * Alias method for removeCostAdditionalDetailLastPeriod.
     * Provides a more generic name for line item management.
     * 
     * @param lineItem the line item to remove
     */
    public void removeLineItem(LineItemEntity lineItem) {
        removeCostAdditionalDetailLastPeriod(lineItem);
    }

    /**
     * Alias getter for costAdditionalDetailLastPeriod.
     * Provides a more generic name for line item access.
     * 
     * @return the list of line items
     */
    public List<LineItemEntity> getLineItems() {
        return costAdditionalDetailLastPeriod;
    }
}