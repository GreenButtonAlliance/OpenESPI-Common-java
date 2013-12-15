package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.MeterReadingRepository;
import org.energyos.espi.common.service.MeterReadingService;
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
public class MeterReadingServiceImpl implements MeterReadingService {

    @Autowired
    protected MeterReadingRepository meterReadingRepository;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public EntryTypeIterator find(Long retailCustomerId, Long usagePointId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntryType find(Long retailCustomerId, Long usagePointId,
			Long meterReadingId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MeterReading importResource(InputStream stream) {
		// TODO Auto-generated method stub
		return null;
	}


}
