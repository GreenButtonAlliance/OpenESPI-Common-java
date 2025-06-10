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

package org.greenbuttonalliance.espi.common.repositories.customer;

import org.greenbuttonalliance.espi.common.domain.customer.entity.CustomerAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for CustomerAccount entities.
 * 
 * Manages customer billing and payment account data with proper PII separation.
 */
@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccountEntity, Long> {

    /**
     * Find customer account by UUID (case insensitive).
     */
    @Query("SELECT ca FROM CustomerAccountEntity ca WHERE UPPER(ca.uuid) = UPPER(:uuid)")
    Optional<CustomerAccountEntity> findByUuid(@Param("uuid") String uuid);

    /**
     * Find customer account by account ID.
     */
    @Query("SELECT ca FROM CustomerAccountEntity ca WHERE ca.accountId = :accountId")
    Optional<CustomerAccountEntity> findByAccountId(@Param("accountId") String accountId);

    /**
     * Find customer accounts by billing cycle.
     */
    @Query("SELECT ca FROM CustomerAccountEntity ca WHERE ca.billingCycle = :billingCycle")
    List<CustomerAccountEntity> findByBillingCycle(@Param("billingCycle") String billingCycle);

    /**
     * Find customer accounts that are pre-pay.
     */
    @Query("SELECT ca FROM CustomerAccountEntity ca WHERE ca.isPrePay = true")
    List<CustomerAccountEntity> findPrePayAccounts();

    /**
     * Find customer accounts with budget billing.
     */
    @Query("SELECT ca FROM CustomerAccountEntity ca WHERE ca.budgetBill IS NOT NULL AND ca.budgetBill != ''")
    List<CustomerAccountEntity> findBudgetBillAccounts();

    /**
     * Find customer accounts by contact info organisation ID.
     */
    @Query("SELECT ca FROM CustomerAccountEntity ca WHERE ca.contactInfo.id = :organisationId")
    List<CustomerAccountEntity> findByContactInfoId(@Param("organisationId") Long organisationId);

    /**
     * Find customer accounts with last bill amount greater than specified value.
     */
    @Query("SELECT ca FROM CustomerAccountEntity ca WHERE ca.lastBillAmount > :amount")
    List<CustomerAccountEntity> findByLastBillAmountGreaterThan(@Param("amount") Long amount);

    /**
     * Find customer accounts by title (from Document base class).
     */
    @Query("SELECT ca FROM CustomerAccountEntity ca WHERE UPPER(ca.title) LIKE UPPER(CONCAT('%', :title, '%'))")
    List<CustomerAccountEntity> findByTitleContaining(@Param("title") String title);
}