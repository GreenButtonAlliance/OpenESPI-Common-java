package org.energyos.espi.common.service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.springframework.security.oauth2.provider.ClientDetails;

public interface ApplicationInformationService {

	// TODO: likely deprecated
	public String feedFor(List<ApplicationInformation> applicationInformations);

	public String entryFor(ApplicationInformation applicationInformation);

	public ApplicationInformation findById(Long id);

	public ApplicationInformation findByClientId(String clientId);

//	public ClientDetails loadClientByClientId(String clientId);

	public ApplicationInformation findByDataCustodianClientId(
			String dataCustodianClientId);

	public List<ApplicationInformation> findAll();

	// persistence management services
	public void persist(ApplicationInformation applicationInformation);

	public void merge(ApplicationInformation applicationInformation);

	// accessor services
	public ApplicationInformation findByURI(String uri);

	public EntryType findEntryType(Long applicationInformationId);

	public EntryTypeIterator findEntryTypeIterator();

	public void add(ApplicationInformation applicationInformation);

	public void delete(ApplicationInformation applicationInformation);

	// import-export services
	public ApplicationInformation importResource(InputStream stream);

	public ApplicationInformation findByUUID(UUID uuid);

	public void setApplicationInformation(ApplicationInformation applicationInformation);

	public String getDataCustodianResourceEndpoint();
	
	public String getAuthorizationServerTokenEndpoint();
	
	public String getThirdPartyNotifyURI();

}
