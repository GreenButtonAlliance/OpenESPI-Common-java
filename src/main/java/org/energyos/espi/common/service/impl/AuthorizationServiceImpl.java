package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.AuthorizationRepository;
import org.energyos.espi.common.repositories.UsagePointRepository;
import org.energyos.espi.common.service.AuthorizationService;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    @Autowired
    private AuthorizationRepository authorizationRepository;

    @Autowired
    private UsagePointRepository usagePointRepository;

    // services setters

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
    public Authorization createAuthorization(Subscription subscription, String accessToken) {
        Authorization authorization = new Authorization();
        authorization.setUUID(UUID.randomUUID());
        authorization.setAccessToken(accessToken);
        authorization.setResource(Routes.DATA_CUSTODIAN_SUBSCRIPTION.replace("{SubscriptionID}", subscription.getUUID().toString()));
        authorizationRepository.persist(authorization);

        return authorization;
    }

    @Override
    public Authorization findByState(String state) {
        return authorizationRepository.findByState(state);
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
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public EntryType find(Long retailCustomerId, Long authorizationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntryTypeIterator find(Long retailCustomerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Authorization authorization) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Authorization authorization) {
		// TODO Auto-generated method stub
		
	}

	// import-exportResource services
	@Override
	public Authorization importResource(InputStream stream) {
		// TODO Auto-generated method stub
		return null;
	}
}
