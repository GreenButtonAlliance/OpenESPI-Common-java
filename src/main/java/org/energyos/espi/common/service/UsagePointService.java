/*
 * Copyright 2013 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.common.service;


import com.sun.syndication.io.FeedException;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.domain.UsagePoint;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public interface UsagePointService {
    List<UsagePoint> findAllByRetailCustomer(RetailCustomer customer);

    UsagePoint findById(Long id);

    void persist(UsagePoint up);

    String exportUsagePoints(RetailCustomer customer) throws FeedException;

    String exportUsagePointById(Long usagePointId) throws FeedException;

    void createOrReplaceByUUID(UsagePoint usagePoint);

    void associateByUUID(RetailCustomer retailCustomer, UUID uuid);

    UsagePoint findByUUID(UUID uuid);

    UsagePoint findByHashedId(String usagePointHashedId);

    UsagePoint importUsagePoint(InputStream stream);

    List<UsagePoint> findAllUpdatedFor(Subscription subscription);

    void deleteByHashedId(String usagePointHashedId);

    List<Long> findAllIdsForRetailCustomer(Long id);
}
