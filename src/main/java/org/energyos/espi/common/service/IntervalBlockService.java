package org.energyos.espi.common.service;

import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.IntervalBlockRepository;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;

import java.io.InputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

public interface IntervalBlockService {
	// TODO: likely deprecated
	List<IntervalBlock> findAllByMeterReadingId(Long meterReadingId);

	String feedFor(List<IntervalBlock> intervalBlocks);

	IntervalBlock findByURI(String uri);

	String entryFor(IntervalBlock intervalBlock);

	void associateByUUID(MeterReading meterReading, UUID uuid);

	List<IntervalBlock> findAllByMeterReading(MeterReading meterReading);

	// persistence management services
	public void setRepository(IntervalBlockRepository repository);

	public void persist(IntervalBlock intervalBlock);

	// accessor services

	public IntervalBlock findById(long retailCustomerId, long usagePointId,
			long meterReadingId, long intervalBlockId);

	public EntryType findEntryType(Long retailCustomerId, Long usagePointId,
			Long meterReadingId, Long intervalBlockId);

	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId, Long usagePointId,
			Long meterReadingId);

	public void delete(IntervalBlock intervalBlock);

	public void add(IntervalBlock intervalBlock);

	// import-exportResource services
	public IntervalBlock importResource(InputStream stream);

	IntervalBlock findById(long intervalBlockId);



}
