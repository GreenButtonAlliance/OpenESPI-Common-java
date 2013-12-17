package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.IntervalBlockRepository;
import org.energyos.espi.common.service.IntervalBlockService;
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
public class IntervalBlockServiceImpl implements IntervalBlockService {

    @Autowired
    protected IntervalBlockRepository intervalBlockRepository;

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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<IntervalBlock> findAllByMeterReading(MeterReading meterReading) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntryType find(Long retailCustomerId, Long usagePointId,
			Long meterReadingId, Long intervalBlockId) {
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public EntryTypeIterator find(Long retailCustomerId, Long usagePointId,
			Long meterReadingId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(IntervalBlock intervalBlock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IntervalBlock importResource(InputStream stream) {
		// TODO Auto-generated method stub
		return null;
	}

}
