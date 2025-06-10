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

package org.greenbuttonalliance.espi.common.service.customer;

import org.greenbuttonalliance.espi.common.domain.customer.entity.CustomerAccountEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for CustomerAccount management.
 * 
 * Handles business logic for customer account operations including billing,
 * payment tracking, and account status management.
 */
public interface CustomerAccountService {

    /**
     * Find all customer accounts.
     */
    List<CustomerAccountEntity> findAll();

    /**
     * Find customer account by ID.
     */
    Optional<CustomerAccountEntity> findById(Long id);

    /**
     * Find customer account by UUID.
     */
    Optional<CustomerAccountEntity> findByUuid(String uuid);

    /**
     * Find customer account by account ID.
     */
    Optional<CustomerAccountEntity> findByAccountId(String accountId);

    /**
     * Find customer accounts by billing cycle.
     */
    List<CustomerAccountEntity> findByBillingCycle(String billingCycle);

    /**
     * Find pre-pay accounts.
     */
    List<CustomerAccountEntity> findPrePayAccounts();

    /**
     * Find accounts with budget billing.
     */
    List<CustomerAccountEntity> findBudgetBillAccounts();

    /**
     * Find customer accounts by contact info organisation ID.
     */
    List<CustomerAccountEntity> findByContactInfoId(Long organisationId);

    /**
     * Find customer accounts by last bill amount greater than specified value.
     */
    List<CustomerAccountEntity> findByLastBillAmountGreaterThan(Long amount);

    /**
     * Find customer accounts by title containing text.
     */
    List<CustomerAccountEntity> findByTitleContaining(String title);

    /**
     * Save customer account.
     */
    CustomerAccountEntity save(CustomerAccountEntity customerAccount);

    /**
     * Delete customer account by ID.
     */
    void deleteById(Long id);

    /**
     * Check if account exists by account ID.
     */
    boolean existsByAccountId(String accountId);

    /**
     * Count total customer accounts.
     */
    long countCustomerAccounts();

    /**
     * Count pre-pay accounts.
     */
    long countPrePayAccounts();

    /**
     * Count accounts with budget billing.
     */
    long countBudgetBillAccounts();
}