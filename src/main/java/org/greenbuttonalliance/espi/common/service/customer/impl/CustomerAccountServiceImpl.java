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

package org.greenbuttonalliance.espi.common.service.customer.impl;

import org.greenbuttonalliance.espi.common.domain.customer.entity.CustomerAccountEntity;
import org.greenbuttonalliance.espi.common.repositories.customer.CustomerAccountRepository;
import org.greenbuttonalliance.espi.common.service.customer.CustomerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service implementation for CustomerAccount management.
 * 
 * Provides business logic for customer account operations including billing,
 * payment tracking, and account status management.
 */
@Service
@Transactional
public class CustomerAccountServiceImpl implements CustomerAccountService {

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CustomerAccountEntity> findAll() {
        return customerAccountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerAccountEntity> findById(Long id) {
        return customerAccountRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerAccountEntity> findByUuid(String uuid) {
        return customerAccountRepository.findByUuid(uuid);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerAccountEntity> findByAccountId(String accountId) {
        return customerAccountRepository.findByAccountId(accountId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerAccountEntity> findByBillingCycle(String billingCycle) {
        return customerAccountRepository.findByBillingCycle(billingCycle);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerAccountEntity> findPrePayAccounts() {
        return customerAccountRepository.findPrePayAccounts();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerAccountEntity> findBudgetBillAccounts() {
        return customerAccountRepository.findBudgetBillAccounts();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerAccountEntity> findByContactInfoId(Long organisationId) {
        return customerAccountRepository.findByContactInfoId(organisationId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerAccountEntity> findByLastBillAmountGreaterThan(Long amount) {
        return customerAccountRepository.findByLastBillAmountGreaterThan(amount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerAccountEntity> findByTitleContaining(String title) {
        return customerAccountRepository.findByTitleContaining(title);
    }

    @Override
    public CustomerAccountEntity save(CustomerAccountEntity customerAccount) {
        // Generate UUID if not present
        if (customerAccount.getId() == null) {
            customerAccount.setId(UUID.randomUUID());
        }
        return customerAccountRepository.save(customerAccount);
    }

    @Override
    public void deleteById(Long id) {
        customerAccountRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByAccountId(String accountId) {
        return customerAccountRepository.findByAccountId(accountId).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public long countCustomerAccounts() {
        return customerAccountRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long countPrePayAccounts() {
        return customerAccountRepository.findPrePayAccounts().size();
    }

    @Override
    @Transactional(readOnly = true)
    public long countBudgetBillAccounts() {
        return customerAccountRepository.findBudgetBillAccounts().size();
    }
}