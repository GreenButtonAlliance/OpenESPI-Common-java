package org.energyos.espi.common.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.ReadingType;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.ReadingTypeRepository;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.ReadingTypeService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
public class ReadingTypeServiceImpl implements ReadingTypeService {

    @Autowired
    protected ReadingTypeRepository repository;
    
    @Autowired
	private ResourceService resourceService;
    
    @Autowired
    private ImportService importService;

    @Autowired
	private ReadingTypeRepository readingTypeRepository;
    
    public void setImportService(ImportService importService) {
    	this.importService = importService;
    }
    
	@Override
	public void setReadingTypeRepository(ReadingTypeRepository readingTypeRepository) {
		this.readingTypeRepository = readingTypeRepository;
		
	}
    public void setResourceService(ResourceService resourceService) {
    	this.resourceService = resourceService;
    }

    @Override
    public ReadingType findByUUID(UUID uuid) {
        return repository.findByUUID(uuid);
    }

    public ReadingType findById(Long readingTypeId) {
        return repository.findById(readingTypeId);
    }

    @Override
    public void persist(ReadingType readingType) {
        repository.persist(readingType);
    }

	@Override
	public String feedFor(ReadingType readingType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String entryFor(ReadingType readingType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void associateByUUID(MeterReading meterReading, UUID uuid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(long readingTypeId) {
		readingTypeRepository.deleteById(readingTypeId);
	}

	@Override
	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId, Long usagePointId) {
		EntryTypeIterator result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understand creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp = resourceService.findAllIds(ReadingType.class);
			result = (new EntryTypeIterator(resourceService, temp));
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;	
	}

	@Override
	public EntryType findEntryType(Long retailCustomerId, Long usagePointId, Long meterReadingId, Long readingTypeId) {
		EntryType result = null;
		try {
			List<Long> allIds = new ArrayList<Long>();
			allIds.add(readingTypeId);
			result = (new EntryTypeIterator(resourceService, allIds)).nextEntry(ReadingType.class);
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public void add(ReadingType readingType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ReadingType readingType) {
	       readingTypeRepository.deleteById(readingType.getId());
	}

	@Override
	public ReadingType importResource(InputStream stream) {
		try{
		importService.importData(stream);
		EntryType entry = importService.getEntries().get(0);
		ReadingType readingType = entry.getContent().getReadingType();
		return readingType;
		} catch (Exception e) {
			return null;
		}
	}


}
