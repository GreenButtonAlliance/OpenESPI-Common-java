package org.energyos.espi.common.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.ElectricPowerUsageSummary;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.ElectricPowerUsageSummaryRepository;
import org.energyos.espi.common.service.ElectricPowerUsageSummaryService;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElectricPowerUsageSummaryServiceImpl implements ElectricPowerUsageSummaryService {

    @Autowired
    protected ElectricPowerUsageSummaryRepository electricPowerUsageSummaryRepository;
    
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

    public void setRepository(ElectricPowerUsageSummaryRepository electricPowerUsageSummaryRepository) {
        this.electricPowerUsageSummaryRepository = electricPowerUsageSummaryRepository;
    }

    @Override
    public ElectricPowerUsageSummary findByUUID(UUID uuid) {
        return electricPowerUsageSummaryRepository.findByUUID(uuid);
    }

    public ElectricPowerUsageSummary findById(Long electricPowerUsageSummaryId) {
        return electricPowerUsageSummaryRepository.findById(electricPowerUsageSummaryId);
    }

    @Override
    public void persist(ElectricPowerUsageSummary electricPowerUsageSummary) {
    	electricPowerUsageSummaryRepository.persist(electricPowerUsageSummary);
    }

	@Override
	public String feedFor(
			List<ElectricPowerUsageSummary> electricPowerUsageSummarys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String entryFor(ElectricPowerUsageSummary electricPowerUsageSummary) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void associateByUUID(UsagePoint usagePoint, UUID uuid) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void delete(ElectricPowerUsageSummary electricPowerUsageSummary) {
	       electricPowerUsageSummaryRepository.deleteById(electricPowerUsageSummary.getId());
	}

	@Override
	public List<ElectricPowerUsageSummary> findAllByUsagePoint(
			UsagePoint usagePoint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId, Long usagePointId) {
		EntryTypeIterator result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understan creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp = resourceService.findAllIds(ElectricPowerUsageSummary.class);
			result = (new EntryTypeIterator(resourceService, temp));
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public EntryType findEntryType(Long retailCustomerId, Long usagePointId,
			Long electricPowerUsageSummaryId) {
		EntryType result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understan creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp.add(electricPowerUsageSummaryId);
			result = (new EntryTypeIterator(resourceService, temp)).nextEntry(ElectricPowerUsageSummary.class);
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}


	@Override
	public void add(ElectricPowerUsageSummary electricPowerUsageSummary) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ElectricPowerUsageSummary importResource(InputStream stream) {
		try {
			importService.importData(stream);
			EntryType entry = importService.getEntries().get(0);
			ElectricPowerUsageSummary electricPowerUsageSummary = entry.getContent().getElectricPowerUsageSummary();
			return electricPowerUsageSummary;
		} catch (Exception e) {
			return null;
		}
	}

}
