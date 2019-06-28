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

import org.greenbuttonalliance.espi.common.domain.Authorization;

import java.util.List;
import java.util.UUID;

public interface AuthorizationRepository {

	void persist(Authorization authorization);

	List<Authorization> findAllByRetailCustomerId(Long retailCustomerId);

	List<Long> findAllIdsByApplicationInformationId(
			Long applicationInformationId);

	Authorization findByState(String state);

	Authorization findByScope(String scope, Long retailCustomerId);

	void merge(Authorization authorization);

	Authorization findById(Long authorizationId);

	List<Long> findAllIds(Long retailCustomerId);

	Authorization findByUUID(UUID uuid);

	List<Long> findAllIds();

	void deleteById(Long id);

	void createOrReplaceByUUID(Authorization authorization);

	Authorization findByAccessToken(String accessToken);

	Authorization findByRefreshToken(String refreshToken);

	List<Long> findAllIdsByBulkId(String thirdParty, Long bulkId);

}
