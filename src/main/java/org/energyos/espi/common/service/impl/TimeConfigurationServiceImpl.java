package org.energyos.espi.common.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.TimeConfiguration;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.TimeConfigurationRepository;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.service.TimeConfigurationService;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeConfigurationServiceImpl implements TimeConfigurationService {

    @Autowired
    protected TimeConfigurationRepository timeConfigurationRepository;
 
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
	       timeConfigurationRepository.deleteById(timeConfigurationId);
	}

	@Override
	public UsagePoint getUsagePoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntryType findEntryType(Long retailCustomerId, Long usagePointId,
			Long timeConfigurationId, ExportFilter params) {
		EntryType result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understan creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp.add(timeConfigurationId);
			result = (new EntryTypeIterator(resourceService, temp)).nextEntry(TimeConfiguration.class);
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId, Long usagePointId, ExportFilter params) {
		EntryTypeIterator result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understan creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp = resourceService.findAllIds(TimeConfiguration.class);
			result = (new EntryTypeIterator(resourceService, temp));
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public void add(TimeConfiguration timeConfiguration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(TimeConfiguration timeConfiguration) {
	       timeConfigurationRepository.deleteById(timeConfiguration.getId());
	}

	@Override
	public TimeConfiguration importResource(InputStream stream) {
		TimeConfiguration timeConfiguration = null;
		try {
			importService.importData(stream);
			EntryType entry = importService.getEntries().get(0);
			timeConfiguration = entry.getContent().getLocalTimeParameters();

		} catch (Exception e) {
           
		}
		return timeConfiguration;
	}

}
