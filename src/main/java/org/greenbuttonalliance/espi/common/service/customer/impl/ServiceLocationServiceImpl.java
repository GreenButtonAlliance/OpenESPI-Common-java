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

import org.greenbuttonalliance.espi.common.domain.customer.entity.ServiceLocationEntity;
import org.greenbuttonalliance.espi.common.repositories.customer.ServiceLocationRepository;
import org.greenbuttonalliance.espi.common.service.customer.ServiceLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service implementation for ServiceLocationEntity management.
 * 
 * Provides business logic for real estate location operations.
 */
@Service
@Transactional
public class ServiceLocationServiceImpl implements ServiceLocationService {

    private final ServiceLocationRepository serviceLocationRepository;

    public ServiceLocationServiceImpl(ServiceLocationRepository serviceLocationRepository) {
        this.serviceLocationRepository = serviceLocationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceLocationEntity> findAll() {
        return serviceLocationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceLocationEntity> findById(Long id) {
        return serviceLocationRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceLocationEntity> findByUuid(String uuid) {
        return serviceLocationRepository.findByUuid(uuid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceLocationEntity> findByOutageBlock(String outageBlock) {
        return serviceLocationRepository.findByOutageBlock(outageBlock);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceLocationEntity> findLocationsThatNeedInspection() {
        return serviceLocationRepository.findLocationsThatNeedInspection();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceLocationEntity> findLocationsWithAccessProblems() {
        return serviceLocationRepository.findLocationsWithAccessProblems();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceLocationEntity> findByMainAddressStreetContaining(String street) {
        return serviceLocationRepository.findByMainAddressStreetContaining(street);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceLocationEntity> findByDirectionContaining(String direction) {
        return serviceLocationRepository.findByDirectionContaining(direction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceLocationEntity> findByType(String type) {
        return serviceLocationRepository.findByType(type);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceLocationEntity> findByPhone1AreaCode(String areaCode) {
        return serviceLocationRepository.findByPhone1AreaCode(areaCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceLocationEntity> findByGeoInfoReference(String geoInfoReference) {
        return serviceLocationRepository.findByGeoInfoReference(geoInfoReference);
    }

    @Override
    public ServiceLocationEntity save(ServiceLocationEntity serviceLocation) {
        // Generate UUID if not present
        if (serviceLocation.getId() == null) {
            serviceLocation.setId(UUID.randomUUID());
        }
        return serviceLocationRepository.save(serviceLocation);
    }

    @Override
    public void deleteById(Long id) {
        serviceLocationRepository.deleteById(id);
    }

    @Override
    public ServiceLocationEntity markForInspection(Long id, boolean needsInspection) {
        Optional<ServiceLocationEntity> optionalLocation = serviceLocationRepository.findById(id);
        if (optionalLocation.isPresent()) {
            ServiceLocationEntity location = optionalLocation.get();
            location.setNeedsInspection(needsInspection);
            return serviceLocationRepository.save(location);
        }
        throw new IllegalArgumentException("ServiceLocationEntity not found with id: " + id);
    }

    @Override
    public ServiceLocationEntity updateAccessProblem(Long id, String accessProblem) {
        Optional<ServiceLocationEntity> optionalLocation = serviceLocationRepository.findById(id);
        if (optionalLocation.isPresent()) {
            ServiceLocationEntity location = optionalLocation.get();
            location.setSiteAccessProblem(accessProblem);
            return serviceLocationRepository.save(location);
        }
        throw new IllegalArgumentException("ServiceLocationEntity not found with id: " + id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countServiceLocationEntitys() {
        return serviceLocationRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long countLocationsNeedingInspection() {
        return serviceLocationRepository.findLocationsThatNeedInspection().size();
    }

    @Override
    @Transactional(readOnly = true)
    public long countLocationsWithAccessProblems() {
        return serviceLocationRepository.findLocationsWithAccessProblems().size();
    }
}