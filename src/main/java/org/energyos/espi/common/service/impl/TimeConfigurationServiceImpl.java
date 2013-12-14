package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.TimeConfiguration;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.TimeConfigurationRepository;
import org.energyos.espi.common.service.TimeConfigurationService;
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
public class TimeConfigurationServiceImpl implements TimeConfigurationService {

    @Autowired
    protected TimeConfigurationRepository timeConfigurationRepository;

    public void setRepository(TimeConfigurationRepository timeConfigurationRepository) {
        this.timeConfigurationRepository = timeConfigurationRepository;
    }

    @Override
    public TimeConfiguration findByUUID(UUID uuid) {
        return timeConfigurationRepository.findByUUID(uuid);
    }

    public TimeConfiguration findById(Long timeConfigurationId) {
        return timeConfigurationRepository.findById(timeConfigurationId);
    }

    @Override
    public void persist(TimeConfiguration timeConfiguration) {
    	timeConfigurationRepository.persist(timeConfiguration);
    }

	@Override
	public List<TimeConfiguration> findAllByRetailCustomer(
			RetailCustomer retailCustomer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String feedFor(List<TimeConfiguration> timeConfiguration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String entryFor(TimeConfiguration timeConfiguration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void associateByUUID(UsagePoint usagePoint, UUID uuid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TimeConfiguration importTimeConfiguration(InputStream stream) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(long timeConfigurationId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UsagePoint getUsagePoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntryType find(Long retailCustomerId, Long usagePointId,
			Long timeConfigurationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntryTypeIterator find(Long retailCustomerId, Long usagePointId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(TimeConfiguration timeConfiguration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(TimeConfiguration timeConfiguration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TimeConfiguration importResource(InputStream stream) {
		// TODO Auto-generated method stub
		return null;
	}

}
