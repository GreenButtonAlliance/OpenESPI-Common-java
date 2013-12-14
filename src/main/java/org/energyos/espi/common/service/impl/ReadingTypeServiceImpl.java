package org.energyos.espi.common.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.ReadingType;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.ReadingTypeRepository;
import org.energyos.espi.common.service.ReadingTypeService;
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

    public void setRepository(ReadingTypeRepository repository) {
        this.repository = repository;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public EntryTypeIterator find(Long retailCustomerId, Long usagePointId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntryType find(Long retailCustomerId, Long usagePointId,
			Long readingTypeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(ReadingType readingType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ReadingType readingType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ReadingType importResource(InputStream stream) {
		// TODO Auto-generated method stub
		return null;
	}

}
