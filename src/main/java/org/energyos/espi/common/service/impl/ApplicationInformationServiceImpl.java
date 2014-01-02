package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.ApplicationInformationRepository;
import org.energyos.espi.common.service.ApplicationInformationService;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ApplicationInformationServiceImpl implements ApplicationInformationService {

    @Autowired
    private ApplicationInformationRepository applicationInformationRepository;

    @Autowired
    private ResourceService resourceService;
    
    @Autowired
    private ImportService importService;
    
    public void setImportService(ImportService importService) {
    	this.importService = importService;
    }
    
    public void setResourceService(ResourceService resourceService) {
    	this.resourceService = resourceService;
    }
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
    public ApplicationInformation findByUUID(UUID uuid) {
        return applicationInformationRepository.findByUUID(uuid);
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
	       applicationInformationRepository.deleteById(applicationInformation.getId());
		
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
	public EntryType findEntryType(Long applicationInformationId) {
		EntryType result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understand creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			ApplicationInformation applicationInformation = applicationInformationRepository.findById(applicationInformationId);
			temp.add(applicationInformation.getId());
			result = (new EntryTypeIterator(resourceService, temp)).nextEntry(ApplicationInformation.class);
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;		// TODO Auto-generated method stub

	}

	@Override
	public EntryTypeIterator findEntryTypeIterator() {
		EntryTypeIterator result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understand creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp = applicationInformationRepository.findAllIds();
			result = (new EntryTypeIterator(resourceService, temp));
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public ApplicationInformation importResource(InputStream stream) {

		ApplicationInformation applicationInformation = null;
		try {
			importService.importData(stream);
			applicationInformation = importService.getEntries().get(0).getContent().getApplicationInformation();
		} catch (Exception e) {

		}
		return applicationInformation;
	}

}
