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

import org.greenbuttonalliance.espi.common.domain.customer.entity.StatementEntity;
import org.greenbuttonalliance.espi.common.repositories.customer.StatementRepository;
import org.greenbuttonalliance.espi.common.service.customer.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service implementation for Statement management.
 * 
 * Provides business logic for billing statement operations including bill amounts,
 * due dates, payment tracking, and statement status management.
 */
@Service
@Transactional
public class StatementServiceImpl implements StatementService {

    private final StatementRepository statementRepository;

    public StatementServiceImpl(StatementRepository statementRepository) {
        this.statementRepository = statementRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatementEntity> findAll() {
        return statementRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StatementEntity> findById(Long id) {
        return statementRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StatementEntity> findByUuid(String uuid) {
        return statementRepository.findByUuid(uuid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatementEntity> findByIssueDateTimeAfter(OffsetDateTime dateTime) {
        return statementRepository.findByIssueDateTimeAfter(dateTime);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatementEntity> findByIssueDateTimeBefore(OffsetDateTime dateTime) {
        return statementRepository.findByIssueDateTimeBefore(dateTime);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatementEntity> findByIssueDateTimeBetween(OffsetDateTime startDate, OffsetDateTime endDate) {
        return statementRepository.findByIssueDateTimeBetween(startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatementEntity> findStatementsWithReferences() {
        return statementRepository.findStatementsWithReferences();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatementEntity> findStatementsWithoutReferences() {
        return statementRepository.findStatementsWithoutReferences();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatementEntity> findByDescriptionContaining(String description) {
        return statementRepository.findByDescriptionContaining(description);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatementEntity> findRecentStatements(OffsetDateTime cutoffDate) {
        return statementRepository.findRecentStatements(cutoffDate);
    }

    @Override
    public StatementEntity save(StatementEntity statement) {
        // Generate UUID if not present
        if (statement.getId() == null) {
            statement.setId(UUID.randomUUID());
        }
        return statementRepository.save(statement);
    }

    @Override
    public void deleteById(Long id) {
        statementRepository.deleteById(id);
    }

    @Override
    public StatementEntity updateDescription(Long id, String description) {
        Optional<StatementEntity> optionalStatement = statementRepository.findById(id);
        if (optionalStatement.isPresent()) {
            StatementEntity statement = optionalStatement.get();
            statement.setDescription(description);
            return statementRepository.save(statement);
        }
        throw new IllegalArgumentException("Statement not found with id: " + id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countStatements() {
        return statementRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long countStatementsWithReferences() {
        return statementRepository.findStatementsWithReferences().size();
    }

    @Override
    @Transactional(readOnly = true)
    public long countStatementsWithoutReferences() {
        return statementRepository.findStatementsWithoutReferences().size();
    }
}