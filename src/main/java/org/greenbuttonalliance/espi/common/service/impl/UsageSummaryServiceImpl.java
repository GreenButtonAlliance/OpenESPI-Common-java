/*
 *
 *    Copyright (c) 2018-2021 Green Button Alliance, Inc.
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

package org.greenbuttonalliance.espi.common.service.impl;

import org.greenbuttonalliance.espi.common.domain.legacy.UsagePoint;
import org.greenbuttonalliance.espi.common.domain.legacy.UsageSummary;
import org.greenbuttonalliance.espi.common.domain.legacy.atom.EntryType;
import org.greenbuttonalliance.espi.common.repositories.usage.UsageSummaryRepository;
import org.greenbuttonalliance.espi.common.service.ImportService;
import org.greenbuttonalliance.espi.common.service.ResourceService;
import org.greenbuttonalliance.espi.common.service.UsageSummaryService;
import org.greenbuttonalliance.espi.common.utils.EntryTypeIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Donald F. Coffin on 06/28/2019 at 23:04
 */

@Service
public class UsageSummaryServiceImpl implements UsageSummaryService {

    @Autowired
    private UsageSummaryRepository usageSummaryRepository;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ImportService importService;

    @Override
    public UsageSummary findByUUID(UUID uuid) {
        return usageSummaryRepository.findByUuid(uuid).orElse(null);
    }

    public UsageSummary findById(Long usageSummaryId) {
        return usageSummaryRepository
                .findById(usageSummaryId).orElse(null);
    }

    @Override
    public void persist(UsageSummary usageSummary) {
        usageSummaryRepository.save(usageSummary);
    }

    @Override
    public String feedFor(
            List<UsageSummary> electricPowerUsageSummaries) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String entryFor(UsageSummary usageSummary) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void associateByUUID(UsagePoint usagePoint, UUID uuid) {
        // TODO Auto-generated method stub
    }

    @Override
    public void delete(UsageSummary usageSummary) {
        usageSummaryRepository
                .deleteById(usageSummary.getId());
    }

    @Override
    public List<UsageSummary> findAllByUsagePoint(
            UsagePoint usagePoint) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId, Long usagePointId) {
        EntryTypeIterator result = null;
        try {
            // TODO - this is sub-optimal (but defers the need to understan
            // creation of an EntryType
            List<Long> temp = new ArrayList<Long>();
            temp = resourceService.findAllIdsByXPath(retailCustomerId,
                    usagePointId, UsageSummary.class);
            result = (new EntryTypeIterator(resourceService, temp,
                    UsageSummary.class));
        } catch (Exception e) {
            // TODO need a log file entry as we are going to return a null if
            // it's not found
            result = null;
        }
        return result;
    }

    @Override
    public EntryType findEntryType(Long retailCustomerId, Long usagePointId, Long usageSummaryId) {
        EntryType result = null;
        try {
            // TODO - this is sub-optimal (but defers the need to understand creation of an EntryType
            List<Long> temp = new ArrayList<Long>();
            temp = resourceService.findAllIdsByXPath(retailCustomerId,
                    usagePointId, UsageSummary.class);
            // temp.add(usageSummaryId);
            if (temp.contains(usageSummaryId)) {
                temp.clear();
                temp.add(usageSummaryId);
            } else {
                temp.clear();
            }

            result = (new EntryTypeIterator(resourceService, temp,
                    UsageSummary.class))
                    .nextEntry(UsageSummary.class);
        } catch (Exception e) {
            // TODO need a log file entry as we are going to return a null if it's not found
            result = null;
        }
        return result;
    }

    @Override
    public void add(UsageSummary usageSummary) {
        // TODO Auto-generated method stub

    }

    @Override
    public UsageSummary importResource(InputStream stream) {
        try {
            importService.importData(stream, null);
            // TODO: Implement modern import logic using DTOs
            // Legacy getContent().getUsageSummary() no longer supported
            UsageSummary usageSummary = null; // Placeholder
            return usageSummary;

        } catch (Exception e) {
            return null;
        }
    }

    public void setUsageSummaryRepository(UsageSummaryRepository usageSummaryRepository) {
        this.usageSummaryRepository = usageSummaryRepository;
    }

    public UsageSummaryRepository getUsageSummaryRepository() {
        return this.usageSummaryRepository;
    }

    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    public ResourceService getResourceService() {
        return this.resourceService;
    }

    public void setImportService(ImportService importService) {
        this.importService = importService;
    }

    public ImportService getImportService() {
        return this.importService;
    }
}
