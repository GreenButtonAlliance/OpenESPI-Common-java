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

import org.greenbuttonalliance.espi.common.domain.customer.entity.CustomerEntity;
import org.greenbuttonalliance.espi.common.domain.customer.enums.CustomerKind;
import org.greenbuttonalliance.espi.common.repositories.customer.CustomerRepository;
import org.greenbuttonalliance.espi.common.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service implementation for Customer PII data management.
 * 
 * Provides business logic for Customer schema operations with proper PII handling.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CustomerEntity> findAll() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerEntity> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerEntity> findByUuid(String uuid) {
        return customerRepository.findByUuid(uuid);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerEntity> findByCustomerName(String customerName) {
        return customerRepository.findByCustomerName(customerName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerEntity> findByKind(CustomerKind kind) {
        return customerRepository.findByKind(kind);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerEntity> findByPucNumber(String pucNumber) {
        return customerRepository.findByPucNumber(pucNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerEntity> findVipCustomers() {
        return customerRepository.findVipCustomers();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerEntity> findCustomersWithSpecialNeeds() {
        return customerRepository.findCustomersWithSpecialNeeds();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerEntity> findByLocale(String locale) {
        return customerRepository.findByLocale(locale);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerEntity> findByPriorityRange(Integer minPriority, Integer maxPriority) {
        return customerRepository.findByPriorityRange(minPriority, maxPriority);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerEntity> findByOrganisationId(Long organisationId) {
        return customerRepository.findByOrganisationId(organisationId);
    }

    @Override
    public CustomerEntity save(CustomerEntity customer) {
        // Generate UUID if not present
        if (customer.getId() == null) {
            customer.setId(UUID.randomUUID());
        }
        return customerRepository.save(customer);
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUuid(String uuid) {
        return customerRepository.findByUuid(uuid).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public long countCustomers() {
        return customerRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long countByKind(CustomerKind kind) {
        return customerRepository.findByKind(kind).size();
    }
}