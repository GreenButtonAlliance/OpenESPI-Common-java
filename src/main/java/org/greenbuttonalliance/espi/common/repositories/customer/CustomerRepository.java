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

import org.greenbuttonalliance.espi.common.domain.customer.entity.CustomerEntity;
import org.greenbuttonalliance.espi.common.domain.customer.enums.CustomerKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for Customer entities.
 * 
 * Provides Customer schema specific query methods for Customer PII data access.
 * Customer data is separated from Usage data for privacy and compliance reasons.
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    /**
     * Find customer by UUID (case insensitive).
     */
    @Query("SELECT c FROM CustomerEntity c WHERE UPPER(c.uuid) = UPPER(:uuid)")
    Optional<CustomerEntity> findByUuid(@Param("uuid") String uuid);

    /**
     * Find customer by customer name (case insensitive).
     */
    @Query("SELECT c FROM CustomerEntity c WHERE UPPER(c.customerName) = UPPER(:customerName)")
    Optional<CustomerEntity> findByCustomerName(@Param("customerName") String customerName);

    /**
     * Find customers by kind.
     */
    @Query("SELECT c FROM CustomerEntity c WHERE c.kind = :kind")
    List<CustomerEntity> findByKind(@Param("kind") CustomerKind kind);

    /**
     * Find customers by PUC number.
     */
    @Query("SELECT c FROM CustomerEntity c WHERE c.pucNumber = :pucNumber")
    Optional<CustomerEntity> findByPucNumber(@Param("pucNumber") String pucNumber);

    /**
     * Find customers with VIP status.
     */
    @Query("SELECT c FROM CustomerEntity c WHERE c.vip = true")
    List<CustomerEntity> findVipCustomers();

    /**
     * Find customers with special needs.
     */
    @Query("SELECT c FROM CustomerEntity c WHERE c.specialNeed IS NOT NULL AND c.specialNeed != ''")
    List<CustomerEntity> findCustomersWithSpecialNeeds();

    /**
     * Find customers by locale.
     */
    @Query("SELECT c FROM CustomerEntity c WHERE c.locale = :locale")
    List<CustomerEntity> findByLocale(@Param("locale") String locale);

    /**
     * Find customers by priority value range.
     */
    @Query("SELECT c FROM CustomerEntity c WHERE c.priority.value BETWEEN :minPriority AND :maxPriority")
    List<CustomerEntity> findByPriorityRange(@Param("minPriority") Integer minPriority, @Param("maxPriority") Integer maxPriority);

    /**
     * Find customers by organisation ID.
     */
    @Query("SELECT c FROM CustomerEntity c WHERE c.organisation.id = :organisationId")
    List<CustomerEntity> findByOrganisationId(@Param("organisationId") Long organisationId);
}