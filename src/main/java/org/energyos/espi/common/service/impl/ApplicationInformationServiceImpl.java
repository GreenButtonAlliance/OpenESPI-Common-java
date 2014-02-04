/*
 * Copyright 2013, 2014 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.common.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.ApplicationInformationRepository;
import org.energyos.espi.common.service.ApplicationInformationService;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApplicationInformationServiceImpl implements ApplicationInformationService {

    // the cached operational object for this service
	// 
	private ApplicationInformation applicationInformation;
	
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

    // configuration accessors
    
    @Override
    public void setApplicationInformation(ApplicationInformation applicationInformation) {
    	this.applicationInformation = applicationInformation;
    }
    
    @Override
    public String getDataCustodianResourceEndpoint() {
    	if (this.applicationInformation == null) {
    		// default it to the seed value
    		this.setApplicationInformation(this.findById(1L));
    	}
    	return applicationInformation.getDataCustodianResourceEndpoint();
    	// return "http://localhost:8080/DataCustodian/espi/1_1/resource";
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
    
//    @Override
//    public ClientDetails loadClientByClientId(String clientId) {
//        return findByClientId(clientId);
//    }

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
			result = (new EntryTypeIterator(resourceService, temp, ApplicationInformation.class)).nextEntry(ApplicationInformation.class);
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
			result = (new EntryTypeIterator(resourceService, temp, ApplicationInformation.class));
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

	@Override
	public String getThirdPartyNotifyURI() {
		ApplicationInformation applicationInformation;
		// TODO note the assumption on the first (seed) entry
		applicationInformation = resourceService.findById(1L, ApplicationInformation.class);
		return applicationInformation.getThirdPartyNotifyUri();
	}

}
