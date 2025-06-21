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

package org.greenbuttonalliance.espi.common.service;

import org.greenbuttonalliance.espi.common.domain.legacy.UsagePoint;
import org.greenbuttonalliance.espi.common.domain.legacy.UsageSummary;
import org.greenbuttonalliance.espi.common.domain.legacy.atom.EntryType;
import org.greenbuttonalliance.espi.common.utils.EntryTypeIterator;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * Created by Donald F. Coffin on 06/28/2019 at 23:01
 */
public interface UsageSummaryService {

    UsageSummary findByUUID(UUID uuid);

    List<UsageSummary> findAllByUsagePoint(UsagePoint usagePoint);

    String feedFor(List<UsageSummary> usageSummaries);

    String entryFor(UsageSummary usageSummary);

    void associateByUUID(UsagePoint usagePoint, UUID uuid);

    void persist(UsageSummary usageSummary);

    UsageSummary findById(Long usageSummaryId);

    EntryType findEntryType(Long retailCustomerId, Long usagePointId,
                            Long usageSummaryId);

    EntryTypeIterator findEntryTypeIterator(Long retailCustomerId,
                                            Long usagePointId);

    void add(UsageSummary usageSummary);

    void delete(UsageSummary usageSummary);

    UsageSummary importResource(InputStream stream);
}
