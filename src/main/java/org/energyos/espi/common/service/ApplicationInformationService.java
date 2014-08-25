package org.energyos.espi.common.service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.springframework.security.oauth2.provider.ClientDetails;

public interface ApplicationInformationService {

	/**
	 * @param kind String indicating [ DATA_CUSTODIAN_ADMIN | THIRD_PARTY | UPLOAD_ADMIN ]
	 * @return List of ApplicationInformation Resources
	 */
	public List<ApplicationInformation> findByKind(String kind);

	/**
	 * Find an ApplicationInformation resource by using it's clientId.
	 * 
	 * @param clientId String uniquely identifying a specific ApplicationInformation.clientId
	 * @return an ApplicationInformation resource
	 */
	public ApplicationInformation findByClientId(String clientId);

	/**
	 * Find an Application Information resource by using it's dataCustodianId.
	 * 
	 * @param String dataCustodianClientId
	 * @return an ApplicationInformation resource
	 */
	public ApplicationInformation findByDataCustodianClientId(
			String dataCustodianClientId);

	/**
	 * Import and XML stream, unmarshalling into an ApplicationInformation resource
	 * 
	 * @param stream
	 * @return an ApplicationInformation resource
	 */
	public ApplicationInformation importResource(InputStream stream);

}
