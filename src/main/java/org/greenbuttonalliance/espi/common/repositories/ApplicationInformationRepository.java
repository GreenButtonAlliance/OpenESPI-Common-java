/*
 *     Copyright (c) 2018-2019 Green Button Alliance, Inc.
 *
 *     Portions copyright (c) 2013-2018 EnergyOS.org
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

package org.greenbuttonalliance.espi.common.repositories;

import org.greenbuttonalliance.espi.common.domain.ApplicationInformation;

import java.util.List;
import java.util.UUID;

public interface ApplicationInformationRepository {

    void deleteById(Long id);

    List<ApplicationInformation> findByKind(String kind);

    List<ApplicationInformation> findAll();

    List<Long> findAllIds();

    ApplicationInformation findByClientId(String clientId);

    ApplicationInformation findByDataCustodianClientId(String dataCustodianId);

    ApplicationInformation findById(Long applicationInformationId);

    void persist(ApplicationInformation applicationInformation);

	ApplicationInformation findByUUID(UUID uuid);
}
