package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.ApplicationInformationRepository;
import org.energyos.espi.common.service.ApplicationInformationService;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Service
@Transactional
public class ApplicationInformationServiceImpl implements ApplicationInformationService {

    @Autowired
    private ApplicationInformationRepository applicationInformationRepository;

    public void setApplicationInformationRepository(ApplicationInformationRepository applicationInformationRepository) {
        this.applicationInformationRepository = applicationInformationRepository;
    }

    @Override
    public List<ApplicationInformation> findAll() {
        return applicationInformationRepository.findAll();
    }

    @Override
    public ApplicationInformation findById(Long id) {
        return applicationInformationRepository.findById(id);
    }


    @Override
    public void persist(ApplicationInformation applicationInformation) {
    	applicationInformationRepository.persist(applicationInformation);
    }

    @Override
    public ApplicationInformation findByClientId(String clientId) {
        return applicationInformationRepository.findByClientId(clientId);
    }

    @Override
    public ApplicationInformation findByDataCustodianClientId(String dataCustodianClientId) {
        return applicationInformationRepository.findByDataCustodianClientId(dataCustodianClientId);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        return findByClientId(clientId);
    }

	@Override
	public String feedFor(List<ApplicationInformation> applicationInformations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String entryFor(ApplicationInformation applicationInformation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(ApplicationInformation applicationInformation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ApplicationInformation applicationInformation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void merge(ApplicationInformation applicationInformation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ApplicationInformation findByURI(String uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntryType find(Long applicationInformationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntryTypeIterator find() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationInformation importResource(InputStream stream) {
		// TODO Auto-generated method stub
		return null;
	}

}
