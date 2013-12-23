package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.MeterReadingRepository;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.MeterReadingService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.utils.EntryBuilder;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;
import org.energyos.espi.common.utils.UsagePointBuilder;
import org.energyos.espi.common.utils.XMLMarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class MeterReadingServiceImpl implements MeterReadingService {
	
	@Autowired 
	private ImportService importService;
	
    @Autowired
    protected MeterReadingRepository meterReadingRepository;
    
	@Autowired
	private ResourceService resourceService;
	
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
	
	public void setImportService(ImportService importService) {
		this.importService = importService;
	}
    public void setRepository(MeterReadingRepository meterReadingRepository) {
        this.meterReadingRepository = meterReadingRepository;
    }
    
    @Override
    public MeterReading findByUUID(UUID uuid) {
        return meterReadingRepository.findByUUID(uuid);
    }

	@Override
	public MeterReading findById(Long retailCustomerId, Long usagePointId,
			Long meterReadingId) {
		// TODO need to scope to the retailCustomer.usagePoint.meterReading
		// for now, just do it the old way
		return meterReadingRepository.findById(meterReadingId);
	}


    @Override
    public void persist(MeterReading meterReading) {
    	meterReadingRepository.persist(meterReading);
    }

	@Override
	public List<MeterReading> findAllByUsagePoint(UsagePoint usagePoint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String feedFor(List<MeterReading> meterReadings) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String entryFor(MeterReading meterReading) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void associateByUUID(UsagePoint usagePoint, UUID uuid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(MeterReading meterReading) {
	       meterReadingRepository.deleteById(meterReading.getId());
		
	}

	@Override
	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId, Long usagePointId, ExportFilter params) {
		EntryTypeIterator result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understand creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp = resourceService.findAllIds(MeterReading.class);
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
			Long meterReadingId, ExportFilter params) {
		EntryType result = null;
		try {
			List<Long> allIds = new ArrayList<Long>();
			allIds.add(meterReadingId);
			result = (new EntryTypeIterator(resourceService, allIds)).next();
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	private List<Long> findAllIds(Long meterReadingId) {
	   return resourceService.findAllIds(MeterReading.class);
	}

	@Override
	public MeterReading importResource(InputStream stream) {
		try{
		importService.importData(stream);
		EntryType entry = importService.getEntries().get(0);
		MeterReading meterReading = entry.getContent().getMeterReading();
		return meterReading;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public MeterReading findById(long meterReadingId) {
		return meterReadingRepository.findById(meterReadingId);
	}

	@Override
	public EntryTypeIterator findEntryTypeIterator(ExportFilter params) {
		EntryTypeIterator result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understan creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp = resourceService.findAllIds(MeterReading.class);
			result = (new EntryTypeIterator(resourceService, temp));
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public EntryType findEntryType(Long meterReadingId, ExportFilter params) {
		EntryType result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understan creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp.add(meterReadingId);
			result = (new EntryTypeIterator(resourceService, temp)).next();
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

}
