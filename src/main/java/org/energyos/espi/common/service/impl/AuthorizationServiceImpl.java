package org.energyos.espi.common.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.AuthorizationRepository;
import org.energyos.espi.common.repositories.UsagePointRepository;
import org.energyos.espi.common.service.AuthorizationService;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    @Autowired
    private AuthorizationRepository authorizationRepository;

    @Autowired
    private UsagePointRepository usagePointRepository;

	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private ImportService importService;
	
	public void setImportService(ImportService importService){
		this.importService = importService;
	}
	
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

    public void setAuthorizationRepository(AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }

    public void setUsagePointRepository(UsagePointRepository usagePointRepository) {
        this.usagePointRepository = usagePointRepository;
    }

	// residue from random stories
    @Override
    public List<Authorization> findAllByRetailCustomerId(Long retailCustomerId) {
        return authorizationRepository.findAllByRetailCustomerId(retailCustomerId);
    }

    @Override
    public Authorization findByUUID(UUID uuid) {
        return authorizationRepository.findByUUID(uuid);
    }
    
    @Override
    public Authorization createAuthorization(Subscription subscription, String accessToken) {
        Authorization authorization = new Authorization();
        authorization.setUUID(UUID.randomUUID());
        authorizationRepository.persist(authorization);

        return authorization;
    }

    @Override
    public Authorization findByState(String state) {
        return authorizationRepository.findByState(state);
    }
    
    @Override
    public Authorization findByScope(String scope, Long retailCustomerId) {
    	return authorizationRepository.findByScope(scope, retailCustomerId);
    }

	@Override
	public List<Authorization> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String entryFor(Authorization authorization) {
		// TODO Auto-generated method stub
		return null;
	}
    @Override
    public Authorization findByURI(String uri) {
        UsagePoint usagePoint = usagePointRepository.findByURI(uri);
        return usagePoint.getSubscription().getAuthorization();
    }

	@Override
	public String feedFor(List<Authorization> authorizations) {
		// TODO Auto-generated method stub
		return null;
	}

	// persistence management services
    @Override
    public void persist(Authorization authorization) {
    	authorizationRepository.persist(authorization);
    }

    @Override
    public void merge(Authorization authorization) {
    	authorizationRepository.merge(authorization);
    }

	// accessor services

	@Override
	public Authorization findById(Long authorizationId) {
		return this.authorizationRepository.findById(authorizationId);
	}

	@Override
	public EntryType findEntryType(Long retailCustomerId, Long authorizationId) {
		EntryType result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understand creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			Authorization authorization = authorizationRepository.findById(authorizationId);
			temp.add(authorization.getId());
			result = (new EntryTypeIterator(resourceService, temp)).nextEntry(Authorization.class);
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId) {
		EntryTypeIterator result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understan creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp = authorizationRepository.findAllIds(retailCustomerId);
			result = (new EntryTypeIterator(resourceService, temp));
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public EntryType findRoot(Long authorizationId) {
		EntryType result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understan creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			Authorization authorization = authorizationRepository.findById(authorizationId);
			temp.add(authorization.getId());
			result = (new EntryTypeIterator(resourceService, temp)).next();
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public EntryTypeIterator findEntryTypeIterator() {
		EntryTypeIterator result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understan creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp = authorizationRepository.findAllIds();
			result = (new EntryTypeIterator(resourceService, temp));
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public void add(Authorization authorization) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Authorization authorization) {
	      authorizationRepository.deleteById(authorization.getId());
	}

	// import-exportResource services
	@Override
	public Authorization importResource(InputStream stream) {
		Authorization authorization = null;
		try {
			importService.importData(stream);
			authorization = importService.getEntries().get(0).getContent().getAuthorization();
		} catch (Exception e) {

		}
		return authorization;
	}

	@Override
	public Authorization findById(Long retailCustomerId, long authorizationId) {
		return this.authorizationRepository.findById(authorizationId);
	}
}
