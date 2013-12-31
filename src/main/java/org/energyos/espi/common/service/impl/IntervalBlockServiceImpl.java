package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.IntervalBlockRepository;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.IntervalBlockService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class IntervalBlockServiceImpl implements IntervalBlockService {

    @Autowired
    protected IntervalBlockRepository intervalBlockRepository;
    
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

    @Override
    public List<IntervalBlock> findAllByMeterReadingId(Long meterReadingId) {
        return intervalBlockRepository.findAllByMeterReadingId(meterReadingId);
    }

    public void setRepository(IntervalBlockRepository repository) {
        this.intervalBlockRepository = repository;
    }

	@Override
	public String feedFor(List<IntervalBlock> intervalBlocks) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntervalBlock findById(long retailCustomerId, long usagePointId, long meterReadingId, long intervalBlockId) {
        return intervalBlockRepository.findById(intervalBlockId);
	}

	@Override
	public String entryFor(IntervalBlock intervalBlock) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void associateByUUID(MeterReading meterReading, UUID uuid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(IntervalBlock intervalBlock) {
	       intervalBlockRepository.deleteById(intervalBlock.getId());
	}

	@Override
	public List<IntervalBlock> findAllByMeterReading(MeterReading meterReading) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntervalBlock findByURI(String uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void persist(IntervalBlock intervalBlock) {
	    intervalBlockRepository.persist(intervalBlock);
	}

	@Override
	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId, Long usagePointId,
			Long meterReadingId) {
		EntryTypeIterator result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understand creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp = resourceService.findAllIds(IntervalBlock.class);
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
			Long meterReadingId, Long intervalBlockId) {
		EntryType result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understan creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp.add(intervalBlockId);
			result = (new EntryTypeIterator(resourceService, temp)).nextEntry(IntervalBlock.class);
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public void add(IntervalBlock intervalBlock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IntervalBlock importResource(InputStream stream) {
		try{
		importService.importData(stream);
		EntryType entry = importService.getEntries().get(0);
		List<IntervalBlock> intervalBlocks = entry.getContent().getIntervalBlocks();
		persist(intervalBlocks.get(0));
		return intervalBlocks.get(0);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public IntervalBlock findById(long intervalBlockId) {
		intervalBlockRepository.findById(intervalBlockId);
		return null;
	}

}
