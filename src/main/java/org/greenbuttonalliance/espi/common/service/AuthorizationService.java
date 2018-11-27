/*
 * Copyright (c) 2013, 2014, 2015, 2016, 2017, 2018 EnergyOS.org
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
 *
 * ********************************************************************************
 *
 *  Copyright (c) 2018 Green Button Alliance, Inc.
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
 */

package org.greenbuttonalliance.espi.common.service;

import org.greenbuttonalliance.espi.common.domain.Authorization;
import org.greenbuttonalliance.espi.common.domain.Subscription;
import org.greenbuttonalliance.espi.common.models.atom.EntryType;
import org.greenbuttonalliance.espi.common.utils.EntryTypeIterator;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public interface AuthorizationService {
	// residue from random stories
	public List<Authorization> findAllByRetailCustomerId(Long retailCustomerId);

	/**
	 * @param authorization
	 * @return List<Long> a list of all authorizationIds that are visible from
	 *         the input authorization
	 */
	public List<Long> findAllIdsByApplicationInformationId(
			Long applicationInformationId);

	public Authorization createAuthorization(Subscription subscription,
			String accessToken);

	public Authorization findByState(String state);

	public Authorization findByScope(String scope, Long retailCustomerId);

	public Authorization findByAccessToken(String accessToken);

	public List<Authorization> findAll();

	public String feedFor(List<Authorization> authorizations);

	public String entryFor(Authorization authorization);

	public Authorization findByURI(String uri);

	// persistence management services
	public void persist(Authorization authorization);

	public void merge(Authorization authorization);

	// accessor services
	public Authorization findById(Long authorizationId);

	public EntryType findEntryType(Long retailCustomerId, Long authorizationId);

	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId);

	public void add(Authorization authorization);

	public void delete(Authorization authorization);

	// import-exportResource services
	public Authorization importResource(InputStream stream);

	public Authorization findById(Long retailCustomerId, long authorizationId);

	EntryType findRoot(Long authorizationId);

	EntryTypeIterator findEntryTypeIterator();

	Authorization findByUUID(UUID uuid);

	public Authorization findByRefreshToken(String refreshToken);

	public List<Long> findAllIdsByBulkId(String thirdParty, Long bulkId);

}
