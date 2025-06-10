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
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Pure JPA/Hibernate entity for LineItem without JAXB concerns.
 * 
 * Line item of detail for additional cost. Represents individual charges,
 * fees, or cost components that make up part of a billing statement or
 * usage summary. Each line item contains an amount, optional rounding,
 * timestamp, and descriptive note.
 */
@Entity
@Table(name = "line_items", indexes = {
    @Index(name = "idx_line_item_usage_summary", columnList = "usage_summary_id"),
    @Index(name = "idx_line_item_date_time", columnList = "date_time"),
    @Index(name = "idx_line_item_amount", columnList = "amount")
})
@Data
@NoArgsConstructor
@ToString(exclude = {"usageSummary"})
public class LineItemEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Primary key for the line item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Amount for this line item in currency minor units (e.g., cents).
     * Positive values represent charges, negative values represent credits.
     */
    @Column(name = "amount", nullable = false)
    @NotNull(message = "Amount cannot be null")
    private Long amount;

    /**
     * Rounding adjustment applied to this line item.
     * Optional field for handling rounding differences in currency calculations.
     */
    @Column(name = "rounding")
    private Long rounding;

    /**
     * Timestamp when this line item was created or applies to.
     * Stored as Unix timestamp (seconds since epoch).
     */
    @Column(name = "date_time", nullable = false)
    @NotNull(message = "Date time cannot be null")
    private Long dateTime;

    /**
     * Descriptive note explaining this line item.
     * Contains human-readable description of the charge or credit.
     */
    @Column(name = "note", nullable = false, length = 256)
    @NotNull(message = "Note cannot be null")
    @Size(max = 256, message = "Note cannot exceed 256 characters")
    private String note;

    // ElectricPowerUsageSummary relationship removed - deprecated resource

    /**
     * Usage summary that contains this line item.
     * Many line items can belong to one usage summary.
     */
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "usage_summary_id")
    private UsageSummaryEntity usageSummary;

    /**
     * Constructor with basic line item information.
     * 
     * @param amount the amount in currency minor units
     * @param dateTime the timestamp
     * @param note the descriptive note
     */
    public LineItemEntity(Long amount, Long dateTime, String note) {
        this.amount = amount;
        this.dateTime = dateTime;
        this.note = note;
    }

    /**
     * Constructor with full line item information.
     * 
     * @param amount the amount in currency minor units
     * @param rounding the rounding adjustment
     * @param dateTime the timestamp
     * @param note the descriptive note
     */
    public LineItemEntity(Long amount, Long rounding, Long dateTime, String note) {
        this.amount = amount;
        this.rounding = rounding;
        this.dateTime = dateTime;
        this.note = note;
    }

    // ElectricPowerUsageSummary setter removed - deprecated resource

    /**
     * Sets the usage summary for this line item.
     * This method ensures the bidirectional relationship is maintained.
     * 
     * @param usageSummary the usage summary to set
     */
    public void setUsageSummary(UsageSummaryEntity usageSummary) {
        // Remove from old summary if exists
        if (this.usageSummary != null && this.usageSummary != usageSummary) {
            this.usageSummary.removeLineItem(this);
        }
        
        this.usageSummary = usageSummary;
        
        // Add to new summary if not null
        if (usageSummary != null && !usageSummary.getLineItems().contains(this)) {
            usageSummary.addLineItem(this);
        }
    }

    /**
     * Gets the amount as a BigDecimal in major currency units.
     * Converts from minor units (cents) to major units (dollars).
     * 
     * @param scale the number of decimal places for the result
     * @return amount as BigDecimal in major currency units
     */
    public BigDecimal getAmountAsBigDecimal(int scale) {
        if (amount == null) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(100), scale, RoundingMode.HALF_UP);
    }

    /**
     * Gets the amount as a BigDecimal with default 2 decimal places.
     * 
     * @return amount as BigDecimal in major currency units
     */
    public BigDecimal getAmountAsBigDecimal() {
        return getAmountAsBigDecimal(2);
    }

    /**
     * Gets the rounding as a BigDecimal in major currency units.
     * 
     * @param scale the number of decimal places for the result
     * @return rounding as BigDecimal in major currency units, or null if not set
     */
    public BigDecimal getRoundingAsBigDecimal(int scale) {
        if (rounding == null) {
            return null;
        }
        return BigDecimal.valueOf(rounding).divide(BigDecimal.valueOf(100), scale, RoundingMode.HALF_UP);
    }

    /**
     * Gets the rounding as a BigDecimal with default 2 decimal places.
     * 
     * @return rounding as BigDecimal in major currency units, or null if not set
     */
    public BigDecimal getRoundingAsBigDecimal() {
        return getRoundingAsBigDecimal(2);
    }

    /**
     * Gets the total amount including rounding.
     * 
     * @return total amount including rounding, or just amount if no rounding
     */
    public Long getTotalAmount() {
        if (rounding == null) {
            return amount;
        }
        return amount + rounding;
    }

    /**
     * Gets the total amount as BigDecimal including rounding.
     * 
     * @param scale the number of decimal places for the result
     * @return total amount as BigDecimal in major currency units
     */
    public BigDecimal getTotalAmountAsBigDecimal(int scale) {
        Long total = getTotalAmount();
        if (total == null) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(total).divide(BigDecimal.valueOf(100), scale, RoundingMode.HALF_UP);
    }

    /**
     * Gets the total amount as BigDecimal with default 2 decimal places.
     * 
     * @return total amount as BigDecimal in major currency units
     */
    public BigDecimal getTotalAmountAsBigDecimal() {
        return getTotalAmountAsBigDecimal(2);
    }

    /**
     * Gets the date time as Instant.
     * 
     * @return date time as Instant, or null if not set
     */
    public Instant getDateTimeAsInstant() {
        return dateTime != null ? Instant.ofEpochSecond(dateTime) : null;
    }

    /**
     * Gets the date time as LocalDateTime in system default timezone.
     * 
     * @return date time as LocalDateTime, or null if not set
     */
    public LocalDateTime getDateTimeAsLocalDateTime() {
        Instant instant = getDateTimeAsInstant();
        return instant != null ? LocalDateTime.ofInstant(instant, ZoneId.systemDefault()) : null;
    }

    /**
     * Sets the date time from Instant.
     * 
     * @param instant the instant to set
     */
    public void setDateTimeFromInstant(Instant instant) {
        this.dateTime = instant != null ? instant.getEpochSecond() : null;
    }

    /**
     * Sets the date time from LocalDateTime in system default timezone.
     * 
     * @param localDateTime the LocalDateTime to set
     */
    public void setDateTimeFromLocalDateTime(LocalDateTime localDateTime) {
        if (localDateTime != null) {
            setDateTimeFromInstant(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        } else {
            this.dateTime = null;
        }
    }

    /**
     * Checks if this line item represents a charge (positive amount).
     * 
     * @return true if this is a charge, false otherwise
     */
    public boolean isCharge() {
        return amount != null && amount > 0;
    }

    /**
     * Checks if this line item represents a credit (negative amount).
     * 
     * @return true if this is a credit, false otherwise
     */
    public boolean isCredit() {
        return amount != null && amount < 0;
    }

    /**
     * Checks if this line item represents zero amount.
     * 
     * @return true if amount is zero, false otherwise
     */
    public boolean isZeroAmount() {
        return amount != null && amount == 0;
    }

    /**
     * Checks if this line item has rounding applied.
     * 
     * @return true if rounding is applied, false otherwise
     */
    public boolean hasRounding() {
        return rounding != null && rounding != 0;
    }

    /**
     * Gets a formatted amount string in currency format.
     * 
     * @param currencySymbol the currency symbol to use
     * @return formatted amount string
     */
    public String getFormattedAmount(String currencySymbol) {
        BigDecimal amountDecimal = getAmountAsBigDecimal();
        String symbol = currencySymbol != null ? currencySymbol : "$";
        
        if (isCredit()) {
            return "-" + symbol + amountDecimal.abs().toString();
        } else {
            return symbol + amountDecimal.toString();
        }
    }

    /**
     * Gets a formatted amount string with default dollar symbol.
     * 
     * @return formatted amount string
     */
    public String getFormattedAmount() {
        return getFormattedAmount("$");
    }

    /**
     * Gets a formatted total amount string including rounding.
     * 
     * @param currencySymbol the currency symbol to use
     * @return formatted total amount string
     */
    public String getFormattedTotalAmount(String currencySymbol) {
        BigDecimal totalDecimal = getTotalAmountAsBigDecimal();
        String symbol = currencySymbol != null ? currencySymbol : "$";
        
        if (getTotalAmount() < 0) {
            return "-" + symbol + totalDecimal.abs().toString();
        } else {
            return symbol + totalDecimal.toString();
        }
    }

    /**
     * Gets a formatted total amount string with default dollar symbol.
     * 
     * @return formatted total amount string
     */
    public String getFormattedTotalAmount() {
        return getFormattedTotalAmount("$");
    }

    /**
     * Gets the note with null safety.
     * 
     * @return note string, or empty string if null
     */
    public String getNoteSafe() {
        return note != null ? note : "";
    }

    /**
     * Gets a summary string for this line item.
     * 
     * @return summary string with key information
     */
    public String getSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Line Item: ").append(getNoteSafe());
        summary.append(" - ").append(getFormattedTotalAmount());
        
        if (hasRounding()) {
            summary.append(" (Base: ").append(getFormattedAmount());
            summary.append(", Rounding: ").append(getRoundingAsBigDecimal()).append(")");
        }
        
        LocalDateTime dateTime = getDateTimeAsLocalDateTime();
        if (dateTime != null) {
            summary.append(" on ").append(dateTime.toLocalDate());
        }
        
        return summary.toString();
    }

    /**
     * Validates the line item data.
     * 
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        return amount != null && dateTime != null && 
               note != null && !note.trim().isEmpty();
    }

    /**
     * Compares this line item with another for business equality.
     * Two line items are considered equal if they have the same amount,
     * rounding, dateTime, and note.
     * 
     * @param other the other line item to compare
     * @return true if business equal, false otherwise
     */
    public boolean isBusinessEqual(LineItemEntity other) {
        if (other == null) {
            return false;
        }
        
        return java.util.Objects.equals(this.amount, other.amount) &&
               java.util.Objects.equals(this.rounding, other.rounding) &&
               java.util.Objects.equals(this.dateTime, other.dateTime) &&
               java.util.Objects.equals(this.note, other.note);
    }

    /**
     * Pre-persist callback to set default values.
     */
    @PrePersist
    protected void onCreate() {
        if (dateTime == null) {
            dateTime = Instant.now().getEpochSecond();
        }
    }
}