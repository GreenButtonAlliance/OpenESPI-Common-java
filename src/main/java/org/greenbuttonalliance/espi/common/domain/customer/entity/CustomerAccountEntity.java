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

package org.greenbuttonalliance.espi.common.domain.customer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.greenbuttonalliance.espi.common.domain.common.IdentifiedObject;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Pure JPA/Hibernate entity for CustomerAccount without JAXB concerns.
 * 
 * Assignment of a group of products and services purchased by the customer through a 
 * customer agreement, used as a mechanism for customer billing and payment. 
 * It contains common information from the various types of customer agreements to 
 * create billings (invoices) for a customer and receive payment.
 * 
 * This is an actual ESPI resource entity that extends IdentifiedObject directly.
 */
@Entity
@Table(name = "customer_accounts", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"notifications"})
public class CustomerAccountEntity extends IdentifiedObject {

    // Document fields (previously inherited from Document superclass)
    
    /**
     * Date and time that this document was created.
     */
    @Column(name = "created_date_time")
    private OffsetDateTime createdDateTime;

    /**
     * Date and time that this document was last modified.
     */
    @Column(name = "last_modified_date_time")
    private OffsetDateTime lastModifiedDateTime;

    /**
     * Revision number for this document.
     */
    @Column(name = "revision_number", length = 256)
    private String revisionNumber;

    /**
     * Subject of this document, intended for this document to be found by a search engine.
     */
    @Column(name = "subject", length = 256)
    private String subject;

    /**
     * Title of this document.
     */
    @Column(name = "title", length = 256)
    private String title;

    /**
     * Type of this document.
     */
    @Column(name = "type", length = 256)
    private String type;

    // CustomerAccount specific fields

    /**
     * Cycle day on which the associated customer account will normally be billed, 
     * used to determine when to produce the billing.
     */
    @Column(name = "billing_cycle", length = 256)
    private String billingCycle;

    /**
     * Budget bill code.
     */
    @Column(name = "budget_bill", length = 256)
    private String budgetBill;

    /**
     * The last amount that will be billed to the customer prior to shut off of the account.
     */
    @Column(name = "last_bill_amount")
    private Long lastBillAmount;

    /**
     * Set of customer account notifications.
     */
    @OneToMany(mappedBy = "customerAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccountNotification> notifications;

    /**
     * [extension] Customer contact information used to identify individual 
     * responsible for billing and payment of CustomerAccount.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_info_id")
    private Organisation contactInfo;

    /**
     * [extension] Customer account identifier
     */
    @Column(name = "account_id", length = 256)
    private String accountId;
    
    /**
     * Customer that owns this account.
     * Many customer accounts can belong to one customer.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;
}