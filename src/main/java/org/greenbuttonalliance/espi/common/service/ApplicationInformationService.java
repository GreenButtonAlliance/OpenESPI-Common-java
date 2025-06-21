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

import org.greenbuttonalliance.espi.common.domain.legacy.ApplicationInformation;

import java.io.InputStream;
import java.util.List;

public interface ApplicationInformationService {

	/**
	 * @param kind
	 *            String indicating [ DATA_CUSTODIAN_ADMIN | THIRD_PARTY |
	 *            UPLOAD_ADMIN ]
	 * @return List of ApplicationInformation Resources
	 */
	public List<ApplicationInformation> findByKind(String kind);

	/**
	 * Find an ApplicationInformation resource by using it's clientId.
	 * 
	 * @param clientId
	 *            String uniquely identifying a specific ApplicationInformation.clientId
	 * @return an ApplicationInformation resource
	 */
	public ApplicationInformation findByClientId(String clientId);

	/**
	 * Find an Application Information resource by using it's dataCustodianId.
	 * 
	 * @param dataCustodianClientId
	 * @return an ApplicationInformation resource
	 */
	public ApplicationInformation findByDataCustodianClientId(
			String dataCustodianClientId);

	/**
	 * Import and XML stream, unmarshalling into an ApplicationInformation
	 * resource
	 * 
	 * @param stream
	 * @return an ApplicationInformation resource
	 */
	public ApplicationInformation importResource(InputStream stream);

}
