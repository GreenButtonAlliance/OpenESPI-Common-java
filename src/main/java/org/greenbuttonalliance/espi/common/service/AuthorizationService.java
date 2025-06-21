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

import org.greenbuttonalliance.espi.common.domain.legacy.Authorization;
import org.greenbuttonalliance.espi.common.domain.legacy.Subscription;
import org.greenbuttonalliance.espi.common.domain.legacy.atom.EntryType;
import org.greenbuttonalliance.espi.common.utils.EntryTypeIterator;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public interface AuthorizationService {
	// residue from random stories
	List<Authorization> findAllByRetailCustomerId(Long retailCustomerId);

	/**
	 * @param authorization
	 * @return List<Long> a list of all authorizationIds that are visible from
	 *         the input authorization
	 */
	List<Long> findAllIdsByApplicationInformationId(
			Long applicationInformationId);

	Authorization createAuthorization(Subscription subscription,
									  String accessToken);

	Authorization findByState(String state);

	Authorization findByScope(String scope, Long retailCustomerId);

	Authorization findByAccessToken(String accessToken);

	List<Authorization> findAll();

	String feedFor(List<Authorization> authorizations);

	String entryFor(Authorization authorization);

	Authorization findByURI(String uri);

	// persistence management services
	void persist(Authorization authorization);

	void merge(Authorization authorization);

	// accessor services
	Authorization findById(Long authorizationId);

	EntryType findEntryType(Long retailCustomerId, Long authorizationId);

	EntryTypeIterator findEntryTypeIterator(Long retailCustomerId);

	void add(Authorization authorization);

	void delete(Authorization authorization);

	// import-exportResource services
	Authorization importResource(InputStream stream);

	Authorization findById(Long retailCustomerId, long authorizationId);

	EntryType findRoot(Long authorizationId);

	EntryTypeIterator findEntryTypeIterator();

	Authorization findByUUID(UUID uuid);

	Authorization findByRefreshToken(String refreshToken);

	List<Long> findAllIdsByBulkId(String thirdParty, Long bulkId);

}
