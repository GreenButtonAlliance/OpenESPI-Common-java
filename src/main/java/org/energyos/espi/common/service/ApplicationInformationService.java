package org.energyos.espi.common.service;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface ApplicationInformationService {

	// TODO: likely deprecated
	public String feedFor(List<ApplicationInformation> applicationInformations);

	public String entryFor(ApplicationInformation applicationInformation);

	public ApplicationInformation findById(Long id);

	public ApplicationInformation findByClientId(String clientId);

	public ClientDetails loadClientByClientId(String clientId);

	public ApplicationInformation findByDataCustodianClientId(
			String dataCustodianClientId);

	public List<ApplicationInformation> findAll();

	// persistence management services
	public void persist(ApplicationInformation applicationInformation);

	public void merge(ApplicationInformation applicationInformation);

	// accessor services
	public ApplicationInformation findByURI(String uri);

	public EntryType find(Long applicationInformationId);

	public EntryTypeIterator find();

	public void add(ApplicationInformation applicationInformation);

	public void delete(ApplicationInformation applicationInformation);

	// import-export services
	public ApplicationInformation importResource(InputStream stream);

}
